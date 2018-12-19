package com.portrayal.open.util;

import android.text.TextUtils;

import com.portrayal.open.bean.PageSBean;

import org.json.JSONArray;

import java.util.List;

public class DataFormatUtils {

    public static String filterBeanToJsonStr(List<PageSBean> data){
        String jsonStr = getJsonArrData(data);
        if (TextUtils.isEmpty(jsonStr)){
            return "";
        }
        char[] cArrays = jsonStr.toCharArray();
        cArrays[cArrays.length-1] = ',';
        return new String(cArrays,1,cArrays.length-1);
    }

    private static String getJsonArrData(List<PageSBean> data){
        if (data == null){
            return "";
        }
        JSONArray array = new JSONArray();
        for (PageSBean aData : data) {
            array.put(aData.getJSONObject());
        }
        if (array.length() == 0){
            return "";
        }
        return array.toString();
    }

    public static String formatFileStartChar(String jsonData){
        return "["+jsonData;
    }

    public static String formatFileEndChar(String jsonData){
        return jsonData.substring(0,jsonData.length()-1) + "]";
    }

}
