package com.portrayal.open.observation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.portrayal.open.Portrayal;


public class PortrayalFragment extends Fragment {

    private int mId;
    
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        handleHiddenChanged(hidden);
        setUserVisibleHint(!hidden);
    }

    private void handleHiddenChanged(boolean isHidden){
        if (isHidden){
            Portrayal.getClient().onPagePause(this);
        }else {
            Portrayal.getClient().onPageResume(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Portrayal.getClient().onPageCreate(this,-1);
    }

    @Override
    public void onResume() {
        if (getUserVisibleHint()){
            handleHiddenChanged(false);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (getUserVisibleHint()){
            handleHiddenChanged(true);
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Portrayal.getClient().onPageDestroy(this);
    }
}
