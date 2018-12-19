package com.portrayal.open.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class PageSBean implements Serializable {

    private static final long serialVersionUID = 5468458279935253554L;
    /**
     * client_time : 1544067579000
     * referer : splash
     * event_category : view
     * location : home
     * target :
     * used_time : 1
     * extra : {}
     */

    private long mClientTime;
    private String mEventCategory;

    private long mReferer;
    private long mLocation;
    private long mTarget;

    private long mUsedTime;
    private JSONObject mExtra;

    /**
     * 额外字段
     */
    private long mShowTime;
    private long mBPTime;

    public void reset(){
        mClientTime = -1L;
        mReferer = -1;
        mEventCategory = "";
        mLocation = -1;
        mTarget = -1;
        mUsedTime = -1;
        mExtra = null;
        mShowTime = 0;
        mBPTime = -1;
    }

    public long getBPTime() {
        return mBPTime;
    }

    public void setBPTime(long mBPTime) {
        this.mBPTime = mBPTime;
    }

    public long getShowTime() {
        return mShowTime;
    }

    public void setShowTime(long mShowTime) {
        this.mShowTime = mShowTime;
    }

    public long getClientTime() {
        return mClientTime;
    }

    public void setClientTime(long mClientTime) {
        this.mClientTime = mClientTime;
    }

    public String getEventCategory() {
        return mEventCategory;
    }

    public void setEventCategory(String mEventCategory) {
        this.mEventCategory = mEventCategory;
    }

    public long getReferer() {
        return mReferer;
    }

    public void setReferer(long mReferer) {
        this.mReferer = mReferer;
    }

    public long getLocation() {
        return mLocation;
    }

    public void setLocation(long mLocation) {
        this.mLocation = mLocation;
    }

    public long getTarget() {
        return mTarget;
    }

    public void setTarget(long mTarget) {
        this.mTarget = mTarget;
    }

    public long getUsedTime() {
        return mUsedTime;
    }

    public void setUsedTime(long mUsedTime) {
        this.mUsedTime = mUsedTime;
    }

    public JSONObject getExtra() {
        return mExtra;
    }

    public void setExtra(JSONObject mExtra) {
        this.mExtra = mExtra;
    }

    @Override
    public String toString(){
        String json = "";
        try {
            JSONObject object = new JSONObject();
            object.put("client_time",mClientTime);
            object.put("referer",mReferer);
            object.put("event_category",mEventCategory);
            object.put("location",mLocation);
            object.put("target",mTarget);
            object.put("used_time",mUsedTime);
            object.put("extra",mExtra);
            json = object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public JSONObject getJSONObject(){
        JSONObject object = new JSONObject();
        try {
            object.put("client_time",mClientTime);
            object.put("referer",mReferer);
            object.put("event_category",mEventCategory);
            object.put("location",mLocation);
            object.put("target",mTarget);
            object.put("used_time",mUsedTime);
            object.put("extra",mExtra);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

}
