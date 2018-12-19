package com.portrayal.open.executor;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;


import com.portrayal.open.IPortrayal;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.portrayal.open.Portrayal.LOGGER_TAG;
import static com.portrayal.open.PortrayalConstant.INSTALL_APP;


public class ExtraInfoExecutor {

    private Application mApplication;
    private IPortrayal mIPortrayal;

    public ExtraInfoExecutor(Application application, IPortrayal iPortrayal) {
        mApplication = application;
        mIPortrayal = iPortrayal;
    }

    public void executor() {
        getAppList();
    }

    private void getAppList() {
        PackageManager pm = mApplication.getPackageManager();
        // Return a List of all packages that are installed on the device.
        List<PackageInfo> packages = pm.getInstalledPackages(0);

        List<String> packageNameList = new ArrayList<>();
        for (PackageInfo packageInfo : packages) {
            // 判断系统/非系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) // 非系统应用
            {
                try{
                    packageNameList.add(packageInfo.applicationInfo.loadLabel(pm).toString());
                }catch (Exception e){
                    Log.e(LOGGER_TAG,"install_app_load_fail");
                }
            } else {
                // 系统应用
            }
        }
        setupEvent(packageNameList);
    }

    private void setupEvent(List<String> packageNameList){
        if (packageNameList.isEmpty()){
            return;
        }
        JSONArray array = new JSONArray(packageNameList);
        mIPortrayal.onEvent(INSTALL_APP,array.toString());
    }

}
