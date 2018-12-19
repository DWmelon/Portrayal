package com.portrayal.open;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.portrayal.open.bean.ConfigFileBean;
import com.portrayal.open.bean.PageSBean;
import com.portrayal.open.bean.PortrayalBeanFactory;
import com.portrayal.open.executor.DefineConfigFileExecutor;
import com.portrayal.open.executor.ExtraInfoExecutor;
import com.portrayal.open.executor.PortrayalFlowExecutor;
import com.portrayal.open.executor.PortrayalNetworkExecutor;
import com.portrayal.open.observation.SystemObservationServer;
import com.portrayal.open.util.DataFormatUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import static com.portrayal.open.Portrayal.LOGGER_TAG;

public class PortrayalManager implements IPortrayal {

    /**
     *  Portrayal
     */
    public static final String TYPE_EVENT_CATEGORY_VIEW = "view";
    public static final String TYPE_EVENT_CATEGORY_CLICK = "click";
    public static final String TYPE_EVENT_CATEGORY_RECEIVER = "receiver";
    public static final String TYPE_EVENT_CATEGORY_MISC = "misc";

    private PortrayalConfig mPortrayalConfig;

    private Application applicationContext;
    private static LinkedBlockingQueue<PageSBean> pageQueue = new LinkedBlockingQueue<>();

    private ReentrantLock saveFileLock = new ReentrantLock();

    private SparseArray<PageSBean> mCurPageList = new SparseArray<>();
    private WeakReference<Context> mCurPageContext;
    private PageSBean mCurPageBean;
    private Handler mHandler = new Handler();

    private PortrayalFlowExecutor mPrlFlowExecutor;
    private PortrayalNetworkExecutor mPrlNetWorkExecutor;
    private DefineConfigFileExecutor mDefineConfigFileExecutor = null;
    private ExtraInfoExecutor mExtraInfoExecutor;
    private SystemObservationServer mSystemObservationServer;

    PortrayalManager(Application application, PortrayalConfig portrayalConfig){
        mPortrayalConfig = portrayalConfig;
        applicationContext = application;

        mPrlFlowExecutor = new PortrayalFlowExecutor(
                this,
                mPortrayalConfig.getCacheFilePath(),
                mPortrayalConfig.getFileUploadPerSize()
        );

        mPrlNetWorkExecutor = new PortrayalNetworkExecutor(
                mPortrayalConfig.getPortrayalDataFormatFetcher(),
                mPortrayalConfig.getPortrayalNetworkFetcher()
        );

        mSystemObservationServer = new SystemObservationServer(application,this);

        if (mPortrayalConfig.isUseStatisticsFile()){
            mDefineConfigFileExecutor = new DefineConfigFileExecutor(application);
            if (!mDefineConfigFileExecutor.load()){
                mPortrayalConfig.setManualStatistics(true);
            }
        }

        if (!mPortrayalConfig.isManualStatistics()){
            mSystemObservationServer.subscribe();
        }

        mExtraInfoExecutor = new ExtraInfoExecutor(application,this);
        mExtraInfoExecutor.executor();

    }



    @Override
    public void onPageCreate(Activity activity, long number) {
        onPageCreate(System.identityHashCode(activity),activity.getClass().getName(),number);
    }

    @Override
    public void onPageCreate(Fragment fragment, long number) {
        onPageCreate(System.identityHashCode(fragment),fragment.getClass().getName(),number);
    }

    private void onPageCreate(int hashKey, String className, long number) {
        //处理当前打开页面相关
        //获取页面对象，并存入信息
        //将页面对象存入页面队列中
        long classId = number;
        ConfigFileBean.ViewModel.ListModel model = null;
        if (!mPortrayalConfig.isManualStatistics() && mDefineConfigFileExecutor != null) {
            model = mDefineConfigFileExecutor.findPageKeyByName(className);
            if (model != null){
                classId = model.getId();
            }
        }
        if (classId<0){
            return;
        }

        PageSBean preBean = mCurPageBean;
        mCurPageBean = PortrayalBeanFactory.obtain();
        mCurPageBean.setEventCategory(model!=null?model.getCategory():TYPE_EVENT_CATEGORY_VIEW);
        mCurPageBean.setReferer(preBean!=null?preBean.getLocation():-1);
        mCurPageBean.setLocation(classId);
        mCurPageBean.setClientTime(System.currentTimeMillis());
        mCurPageBean.setBPTime(System.currentTimeMillis());
        mCurPageList.put(hashKey,mCurPageBean);
        Log.d(LOGGER_TAG,"create_" + hashKey+"_id:"+classId);
    }

    @Override
    public void onPageResume(Activity activity) {
        onPageResume(System.identityHashCode(activity));
    }

    @Override
    public void onPageResume(Fragment fragment) {
        onPageResume(System.identityHashCode(fragment));
    }

    private void onPageResume(int hashKey){
        PageSBean curPageBean = mCurPageList.get(hashKey);

        //如果找不到对象
        if (curPageBean == null){
//            onPageCreate(id,name);
            return;
        }

        //将当前页面设置为resume页面，并且以当前时间为"续埋"时间
        mCurPageBean = curPageBean;
        mCurPageBean.setBPTime(System.currentTimeMillis());
        Log.d(LOGGER_TAG,"resume_" + curPageBean.getLocation());
    }

    @Override
    public void onPagePause(Activity activity) {
        onPagePause(System.identityHashCode(activity));
    }

    @Override
    public void onPagePause(Fragment fragment) {
        onPagePause(System.identityHashCode(fragment));
    }

    private void onPagePause(int hashKey){
        PageSBean curPageBean = mCurPageList.get(hashKey);

        if (curPageBean == null){
            return;
        }

        long diffTime = System.currentTimeMillis() - curPageBean.getBPTime();
        curPageBean.setShowTime(curPageBean.getShowTime() + diffTime);
        curPageBean.setBPTime(System.currentTimeMillis());
        Log.d(LOGGER_TAG,"pause_" + curPageBean.getLocation() +"_stay:+"+diffTime/1000+"s");
    }

    @Override
    public void onPageDestroy(Activity activity) {
        onPageDestroy(System.identityHashCode(activity));
    }

    @Override
    public void onPageDestroy(Fragment fragment) {
        onPageDestroy(System.identityHashCode(fragment));
    }

    private void onPageDestroy(int hashKey){
        PageSBean curPageBean = mCurPageList.get(hashKey);
        if (curPageBean == null){
            return;
        }
        curPageBean.setUsedTime(curPageBean.getShowTime());
        mCurPageList.remove(hashKey);
        enqueue(curPageBean);
        Log.d(LOGGER_TAG,"destroy_" + curPageBean.getLocation() + "_stay:" + curPageBean.getShowTime()/1000+"s");
    }

    /**
     * 通过控件idInt，获得控件idStr
     * 将idStr作为key,从配置文件中找出对应的服务端值
     * @param view
     */
    @Override
    public void onEventClick(View view) {
        if (mPortrayalConfig.isManualStatistics() || mDefineConfigFileExecutor == null || mCurPageBean == null){
            return;
        }
        String resName;
        try{
            resName = applicationContext.getResources().getResourceEntryName(view.getId());
        } catch (Resources.NotFoundException e){
            return;
        }
        ConfigFileBean.ClickModel.ListModelX listModelX = mDefineConfigFileExecutor.findClickKeyById(mCurPageBean.getLocation(),resName);
        if (listModelX == null){
            return;
        }

        HashMap<String,String> key_value = new HashMap<>();
        // TODO: 2018/12/7 根据配置找出该点击统计的多参数值
        onEvent(listModelX.getId(),key_value,listModelX.getCategory());
    }

    @Override
    public void onEventClick(long id){
        onEvent(id,new HashMap<String, String>(),TYPE_EVENT_CATEGORY_CLICK);
    }

    @Override
    public void onEvent(long id,String... dataList){
        if (dataList == null || mDefineConfigFileExecutor == null){
            onEventClick(id);
            return;
        }
        if (TextUtils.isEmpty(mDefineConfigFileExecutor.findExtraTypeById(id))){
            return;
        }
        String type = mDefineConfigFileExecutor.findExtraTypeById(id);
        onEvent(id,mDefineConfigFileExecutor.findExtraMapByDataList(id,Arrays.asList(dataList)),type);
    }

    @Override
    public void onEvent(long id, HashMap<String,String> key_value, String type) {
        PageSBean bean = PortrayalBeanFactory.obtain();
        bean.setClientTime(System.currentTimeMillis());
        bean.setEventCategory(type);
        bean.setLocation(mCurPageBean!=null?mCurPageBean.getLocation():-1);
        bean.setTarget(id);
        
        if (key_value != null && !key_value.isEmpty()){
            JSONObject object = new JSONObject();
            try {
                for (String k: key_value.keySet()){
                    object.put(k,key_value.get(k));
                }
                bean.setExtra(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(LOGGER_TAG,"CLICK_" + bean.getTarget());
        enqueue(bean);
    }

    private void enqueue(PageSBean bean){
        if (bean != null){
            if (pageQueue.add(bean)) {
                handleMaySaveFile();
            } else {
                Log.e(LOGGER_TAG, "page_view_add_fail");
            }
        }else {
            Log.e(LOGGER_TAG, "page_view_lose");
        }
    }

    private void handleMaySaveFile(){
        if (pageQueue.size() >= mPortrayalConfig.getFileSavePerCount()){
            saveFileLock.lock();
            if (pageQueue.size() >= mPortrayalConfig.getFileSavePerCount()){

                //将废弃对象放入对象缓存池
                PortrayalBeanFactory.enqueue(pageQueue);

                //获得统计列表
                List<PageSBean> list = new ArrayList<>(pageQueue);
                pageQueue.clear();

                //转换统计列表，获得埋点数据，保存到文件
                String eventJsonArrayStr = DataFormatUtils.filterBeanToJsonStr(list);
                mPrlFlowExecutor.handleDataToFile(eventJsonArrayStr);
            }
            saveFileLock.unlock();
        }
    }

    public void uploadFile(final String jsonStr){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mPrlNetWorkExecutor.submitData(jsonStr,mPortrayalConfig.getBaseParam());
            }
        });
    }

}
