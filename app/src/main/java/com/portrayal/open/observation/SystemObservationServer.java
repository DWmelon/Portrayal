package com.portrayal.open.observation;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;


import com.portrayal.open.IPortrayal;

import java.util.HashSet;

public class SystemObservationServer {

    private IPortrayal mIPortrayal;
    private Application mApplication;
    private View.AccessibilityDelegate mAccessibilityDelegate;
    private HashSet<String> mPageLoadList = new HashSet<>();
    private FragmentManager.FragmentLifecycleCallbacks mFragmentLifecycleCallbacks;

    public SystemObservationServer(Application application, IPortrayal iPortrayal){
        mIPortrayal = iPortrayal;
        mApplication = application;
    }

    public void subscribe(){
        mAccessibilityDelegate = new MyAccessibilityDelegate();
        mFragmentLifecycleCallbacks = new FragmentLifeCyclingListener();
        mApplication.registerActivityLifecycleCallbacks(new ActivityLifeCyclingListener());
    }



    public class ActivityLifeCyclingListener implements Application.ActivityLifecycleCallbacks{

        @Override
        public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
            mIPortrayal.onPageCreate(activity,-1);
            activity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (!mPageLoadList.contains(activity.toString())){
                        mPageLoadList.add(activity.toString());
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (activity == null){
                                    return;
                                }
                                installAccessibilityDelegate(activity.getWindow().getDecorView());
                                mPageLoadList.remove(activity.toString());
                            }
                        },500);
                    }

                }
            });
//            if (activity instanceof FragmentActivity && mFragmentLifecycleCallbacks != null){
//                ((FragmentActivity)activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycleCallbacks,true);
//            }
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            mIPortrayal.onPageResume(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            mIPortrayal.onPagePause(activity);
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            mIPortrayal.onPageDestroy(activity);
            mPageLoadList.remove(activity.toString());
//            if (activity instanceof FragmentActivity && mFragmentLifecycleCallbacks != null){
//                ((FragmentActivity)activity).getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(mFragmentLifecycleCallbacks);
//            }
        }
    }

    public class FragmentLifeCyclingListener extends FragmentManager.FragmentLifecycleCallbacks{
        @Override
        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            super.onFragmentCreated(fm, f, savedInstanceState);
//            Log.d(LOGGER_TAG,"fragment_create_"+f.getClass().getName());

//            if (!TextUtils.equals(f.getTag(),PYAL_FRAGMENT_TAG)){
//                FragmentObservable observable = new FragmentObservable();
//                observable.subscribe(f,SystemObservationServer.this);
//            }
        }

        @Override
        public void onFragmentResumed(FragmentManager fm, Fragment f) {
            super.onFragmentResumed(fm, f);
//            Log.d(LOGGER_TAG,"fragment_resume_"+f.getClass().getName());
        }

        @Override
        public void onFragmentPaused(FragmentManager fm, Fragment f) {
            super.onFragmentPaused(fm, f);
//            Log.d(LOGGER_TAG,"fragment_pause_"+f.getClass().getName());
        }

        @Override
        public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
            super.onFragmentDestroyed(fm, f);
//            Log.d(LOGGER_TAG,"fragment_destroy_"+f.getClass().getName());
        }

    }

    /**
     * 给所有View设置监听
     * @param view
     */
    private void installAccessibilityDelegate(View view) {
        if (view != null) {
            view.setAccessibilityDelegate(mAccessibilityDelegate);
            if (view instanceof ViewGroup) {
                ViewGroup parent = (ViewGroup) view;
                int count = parent.getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = parent.getChildAt(i);
                    if (child.getVisibility() != View.GONE) {
                        installAccessibilityDelegate(child);
                    }
                }
            }
        }
    }

    /**
     * View监听类
     */
    public class MyAccessibilityDelegate extends View.AccessibilityDelegate{

        @Override
        public void sendAccessibilityEvent(View host, int eventType) {
            super.sendAccessibilityEvent(host,eventType);
            if (host != null && AccessibilityEvent.TYPE_VIEW_CLICKED == eventType){
                handleViewClick(host);
            }
        }

    }

    private void handleViewClick(View view){
        mIPortrayal.onEventClick(view);
    }

}
