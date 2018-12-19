package com.portrayal.open.executor;

import android.app.Application;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.portrayal.open.bean.ConfigFileBean;
import com.portrayal.open.bean.KeyListBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

/**
 * 配置文件Executor
 * 负责读取配置文件，保存埋点关系在内存中
 * 当埋点产生时，在埋点关系map中获取埋点信息
 * 返回埋点信息
 */
public class DefineConfigFileExecutor {

    /**
     * 在Assets中配置文件的名称
     */
    private final static String FILE_PATH = "smap";

    private Application mApplication;
    private HashMap<String,ConfigFileBean.ViewModel.ListModel> mPageMap = new HashMap<>();
    private HashMap<String,ConfigFileBean.ClickModel.ListModelX> mClickMap = new HashMap<>();
    private HashMap<Long,KeyListBean> mExtraKeysMap = new HashMap<>();

    public DefineConfigFileExecutor(Application application){
        mApplication = application;
    }

    /**
     * 从Assets中读取配置文件
     * @return 是否读取成功
     */
    public boolean load(){
        InputStream inputStream;
        try {
            inputStream = mApplication.getAssets().open(FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        JSONObject object = null;
        try {
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = bufferedReader.readLine())!=null){
                sb.append(str);
            }
            object = JSONObject.parseObject(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (object == null){
            return false;
        }
        parseJSONObject(object);
        return true;
    }

    /**
     * 转换埋点配置到内存map中
     * @param object
     */
    private void parseJSONObject(JSONObject object){
        ConfigFileBean configFileBean = JSONObject.toJavaObject(object,ConfigFileBean.class);
        for (ConfigFileBean.ViewModel.ListModel listModel : configFileBean.getView().getList()){
            listModel.setCategory(configFileBean.getView().getType());
            if (!TextUtils.isEmpty(listModel.getName())){
                mPageMap.put(listModel.getName(),listModel);
            }
            if (listModel.getExtra_keys() != null && !listModel.getExtra_keys().isEmpty()){
                KeyListBean keyListBean = new KeyListBean();
                keyListBean.setList(listModel.getExtra_keys());
                keyListBean.setType(configFileBean.getView().getType());
                mExtraKeysMap.put(listModel.getId(),keyListBean);
            }
        }
        for (ConfigFileBean.ClickModel.ListModelX listModelX : configFileBean.getClick().getList()){
            if (!TextUtils.isEmpty(listModelX.getName())){
                listModelX.setCategory(configFileBean.getClick().getType());
                mClickMap.put(listModelX.getPage_id() + "_" + listModelX.getName(),listModelX);
            }
            if (listModelX.getExtra_keys() != null && !listModelX.getExtra_keys().isEmpty()){
                KeyListBean keyListBean = new KeyListBean();
                keyListBean.setList(listModelX.getExtra_keys());
                keyListBean.setType(configFileBean.getClick().getType());
                mExtraKeysMap.put(listModelX.getId(),keyListBean);
            }
        }
        for (ConfigFileBean.OtherModel otherModel : configFileBean.getOther()){
            for (ConfigFileBean.OtherModel.ListModelXX listModelXX : otherModel.getList()){
                listModelXX.setCategory(otherModel.getType());
                if (listModelXX.getExtra_keys() != null && !listModelXX.getExtra_keys().isEmpty()){
                    KeyListBean keyListBean = new KeyListBean();
                    keyListBean.setList(listModelXX.getExtra_keys());
                    keyListBean.setType(otherModel.getType());
                    mExtraKeysMap.put(listModelXX.getId(),keyListBean);
                }
            }
        }
    }

    /**
     * 根据点击产生的页面和控件的resId
     * 获取点击事件的埋点信息
     * @param pageId
     * @param resId
     * @return 返回点击事件得埋点信息对象
     */
    public ConfigFileBean.ClickModel.ListModelX findClickKeyById(long pageId,String resId){
        return mClickMap.get(pageId + "_" + resId);
    }

    /**
     * 根据访问页面的路径和名称
     * 获取访问页面的页面埋点信息
     * @param pageName
     * @return 返回访问页面的埋点信息对象
     */
    public ConfigFileBean.ViewModel.ListModel findPageKeyByName(String pageName){
        return mPageMap.get(pageName);
    }

    /**
     * 根据埋点事件的Id和埋点带参数的数据集合
     * 返回装载了带参数的数据集合map
     * @param id
     * @param dataList
     * @return 带参数数据集合map
     */
    public HashMap<String,String> findExtraMapByDataList(long id, List<String> dataList){
        HashMap<String,String> map = new HashMap<>();
        if (!mExtraKeysMap.containsKey(id)){
            return map;
        }
        KeyListBean keyListBean = mExtraKeysMap.get(id);
        List<String> keyList = keyListBean.getList();
        if (dataList.size()>keyList.size()){
            return map;
        }
        for (int i = 0;i < dataList.size();i++){
            map.put(keyList.get(i),dataList.get(i));
        }
        return map;
    }

    /**
     * 根据埋点Id获取埋点类型
     * @param id
     * @return 埋点类型
     */
    public String findExtraTypeById(long id){
        if (!mExtraKeysMap.containsKey(id)){
            return "";
        }
        return mExtraKeysMap.get(id).getType();
    }

}
