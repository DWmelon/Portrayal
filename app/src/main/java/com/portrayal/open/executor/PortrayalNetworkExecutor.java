package com.portrayal.open.executor;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;


import com.portrayal.open.fetcher.PortrayalDataFormatFetcher;
import com.portrayal.open.fetcher.PortrayalNetworkFetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.portrayal.open.Portrayal.LOGGER_TAG;


public class PortrayalNetworkExecutor {

    private PortrayalNetworkFetcher mPortrayalNetworkFetcher;
    private PortrayalDataFormatFetcher mPortrayalDataFormatFetcher;

    public PortrayalNetworkExecutor(PortrayalDataFormatFetcher portrayalDataFormatFetcher,PortrayalNetworkFetcher portrayalNetworkFetcher){
        mPortrayalDataFormatFetcher = portrayalDataFormatFetcher;
        mPortrayalNetworkFetcher = portrayalNetworkFetcher;
    }

    public void submitData(String jsonData, ArrayMap<String,String> map){

        jsonData = mPortrayalDataFormatFetcher == null?formatJsonData(jsonData,map):mPortrayalDataFormatFetcher.fetch(jsonData, map);
        if (TextUtils.isEmpty(jsonData)){
            Log.e(LOGGER_TAG,"data_submit_but_empty");
            return;
        }
        if (mPortrayalNetworkFetcher != null){
            mPortrayalNetworkFetcher.fetch(jsonData);
            Log.d(LOGGER_TAG,"submit_" + jsonData);
        }else {
            handleDataSubmit(jsonData);
        }
    }

    private String formatJsonData(String jsonData, ArrayMap<String,String> map){
        if (TextUtils.isEmpty(jsonData)){
            return "";
        }
        JSONObject result = new JSONObject();
        try {
            JSONObject base = new JSONObject();
            for (String key : map.keySet()){
                base.put(key,map.get(key));
            }
            result.put("basic_info",base);
            result.put("event_list",new JSONArray(jsonData));

        } catch (JSONException e) {
            Log.e(LOGGER_TAG,"data_submit_json_format_fail");
        }
        return result.toString();
    }

    private void handleDataSubmit(String dataStr){
        // TODO: 2018/12/7 网络上报
    }

}
