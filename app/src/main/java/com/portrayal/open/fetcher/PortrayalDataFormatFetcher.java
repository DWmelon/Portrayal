package com.portrayal.open.fetcher;

import android.util.ArrayMap;

public abstract class PortrayalDataFormatFetcher {

    public abstract String fetch(String dataStr, ArrayMap<String,String> map);

}
