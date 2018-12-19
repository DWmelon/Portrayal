package com.portrayal.open;

import android.content.Context;
import android.util.ArrayMap;

import com.portrayal.open.fetcher.PortrayalDataFormatFetcher;
import com.portrayal.open.fetcher.PortrayalNetworkFetcher;


public class PortrayalConfig {

    private int mFileSavePerCount;
    private String mCacheFilePath;
    private int mFileUploadPerSize;
    private ArrayMap<String,String> mBaseParam = new ArrayMap<>();
    private PortrayalNetworkFetcher mPortrayalNetworkFetcher;
    private PortrayalDataFormatFetcher mPortrayalDataFormatFetcher;
    private boolean mIsManualStatistics;
    private boolean mIsUseStatisticsFile;

    private PortrayalConfig(Builder builder){
        mFileSavePerCount = builder.mFileSavePerCount;
        mCacheFilePath = builder.mContext.getExternalCacheDir() +
                (builder.mCacheFilePath.startsWith("/")?builder.mCacheFilePath:"/"+builder.mCacheFilePath);
        mFileUploadPerSize = builder.mFileUploadPerSize;
        mPortrayalNetworkFetcher = builder.mPortrayalNetworkFetcher;
        mPortrayalDataFormatFetcher = builder.mPortrayalDataFormatFetcher;
        mIsManualStatistics = builder.mIsManualStatistics;
        mIsUseStatisticsFile = builder.mIsUseStatisticsFile;
    }

    public boolean isUseStatisticsFile() {
        return mIsUseStatisticsFile;
    }

    public int getFileSavePerCount() {
        return mFileSavePerCount;
    }

    public String getCacheFilePath() {
        return mCacheFilePath;
    }

    public int getFileUploadPerSize() {
        return mFileUploadPerSize;
    }

    public PortrayalNetworkFetcher getPortrayalNetworkFetcher() {
        return mPortrayalNetworkFetcher;
    }

    public PortrayalDataFormatFetcher getPortrayalDataFormatFetcher() {
        return mPortrayalDataFormatFetcher;
    }

    public ArrayMap<String,String> getBaseParam(){
        return mBaseParam;
    }

    public void setBaseParam(ArrayMap<String,String> map){
        mBaseParam = map;
    }

    public boolean isManualStatistics() {
        return mIsManualStatistics;
    }

    void setManualStatistics(boolean b) {
        mIsManualStatistics = b;
    }

    public static class Builder{

        private Context mContext;
        private int mFileSavePerCount = 5;
        private String mCacheFilePath = "/portrayal/event_file";
        private int mFileUploadPerSize = 1;
        private PortrayalNetworkFetcher mPortrayalNetworkFetcher;
        private PortrayalDataFormatFetcher mPortrayalDataFormatFetcher;
        private boolean mIsManualStatistics = false;
        private boolean mIsUseStatisticsFile = true;

        public Builder(Context context){
            mContext = context;
        }

        public Builder setFileSavePerCount(int fileSavePerCount){
            mFileSavePerCount = fileSavePerCount;
            return this;
        }

        public Builder setCacheFilePath(String cacheFilePath){
            mCacheFilePath = cacheFilePath;
            return this;
        }

        public Builder setFileUploadPerSize(int fileUploadPerSize){
            mFileUploadPerSize = fileUploadPerSize;
            return this;
        }

        public Builder setPortrayalNetworkFetcher(PortrayalNetworkFetcher portrayalNetworkFetcher){
            mPortrayalNetworkFetcher = portrayalNetworkFetcher;
            return this;
        }

        public Builder setPortrayalDataFormatFetcher(PortrayalDataFormatFetcher portrayalDataFormatFetcher){
            mPortrayalDataFormatFetcher = portrayalDataFormatFetcher;
            return this;
        }

        public Builder setManualStatistics(boolean mIsManualStatistics) {
            this.mIsManualStatistics = mIsManualStatistics;
            return this;
        }

        public void setIsUseStatisticsFile(boolean mIsUseStatisticsFile) {
            this.mIsUseStatisticsFile = mIsUseStatisticsFile;
        }

        public PortrayalConfig build(){
            return new PortrayalConfig(this);
        }

    }

}
