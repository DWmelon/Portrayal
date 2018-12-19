package com.portrayal.open;

import android.app.Application;
import android.util.ArrayMap;
import android.util.Log;

import java.util.Map;

public class Portrayal {

    public static String LOGGER_TAG = "portrayal";

    private static IPortrayal iPortrayal;
    private static PortrayalConfig mPortrayalConfig;

    private static volatile boolean sIsInitialized = false;

    public static void init(Application context){
        init(context,new PortrayalConfig.Builder(context).build());
    }

    public static void init(Application context, PortrayalConfig portrayalConfig){
        if (sIsInitialized){
            Log.w(LOGGER_TAG,"Portrayal_had_initialized");
            return;
        }
        sIsInitialized = true;
        mPortrayalConfig = portrayalConfig;
        iPortrayal = new PortrayalManager(context,portrayalConfig);
    }

    public static IPortrayal getClient(){
        return iPortrayal;
    }

    /**
     * 上报数据块的基础数据
     * set* 覆盖原来的 map
     * @param map
     */
    public static void setBaseParam(Map<String,String> map){
        ArrayMap<String,String> arrayMap = new ArrayMap<>();
        arrayMap.putAll(map);
        mPortrayalConfig.setBaseParam(arrayMap);
    }

    /**
     * 上报数据块的基础数据
     * update* 补充原来的 map
     * @param map
     */
    public static void updateBaseParam(Map<String,String> map){
        ArrayMap<String,String> arrayMap = mPortrayalConfig.getBaseParam();
        arrayMap.putAll(map);
    }

    public static boolean hasBeenInitialized() {
        return sIsInitialized;
    }

}
