package com.portrayal.open.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class ViewInstanceUtils {

    public static String format(String key, View view){
        if (view.getParent() instanceof GridView){
            key += "_" + ((ViewGroup)view.getParent()).indexOfChild(view);
        }
        return key;
    }

}
