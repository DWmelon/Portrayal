package com.portrayal.open;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.HashMap;

public interface IPortrayal {

    void onPageCreate(Activity activity, long number);
    void onPageDestroy(Activity activity);
    void onPageResume(Activity activity);
    void onPagePause(Activity activity);

    void onPageCreate(Fragment fragment, long number);
    void onPageDestroy(Fragment fragment);
    void onPageResume(Fragment fragment);
    void onPagePause(Fragment fragment);

    void onEventClick(View view);
    void onEventClick(long key);
    void onEvent(long key, String... dataList);
    void onEvent(long key, HashMap<String, String> value, String type);

}
