package com.portrayal.open.executor;

import android.util.Log;


import com.portrayal.open.PortrayalManager;
import com.portrayal.open.util.DataFormatUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.portrayal.open.Portrayal.LOGGER_TAG;


public class PortrayalFlowExecutor {

    private static final String DEFAULT_CACHE_FILE_PATH = "/portrayal/event_file";

    //单位KB
    private static final int DEFAULT_EACH_UPLOAD_SIZE = 10;

    private int mEachUploadSize = DEFAULT_EACH_UPLOAD_SIZE;
    private String mCacheFilePath;


    private Executor fileIOExecutor = Executors.newSingleThreadExecutor();

    private String jsonData;
    private PortrayalManager portrayalManager;


    public PortrayalFlowExecutor(PortrayalManager portrayalManager, String filePath, int eachUploadSize){
        this.portrayalManager = portrayalManager;
        this.mCacheFilePath = filePath;
        this.mEachUploadSize = eachUploadSize;
        fileIOExecutor.execute(mFileExistCheckRunnable);
    }

    public void handleDataToFile(String jsonStr){
        this.jsonData = jsonStr;
        File file = new File(mCacheFilePath);
        if (!file.exists()){
            fileIOExecutor.execute(mFileExistCheckRunnable);
        }
        if (file.length() == 0){
            jsonData = DataFormatUtils.formatFileStartChar(jsonData);
        }
        fileIOExecutor.execute(mFileWriteRunnable);
        fileIOExecutor.execute(mFileFullCheckRunnable);
    }

    private Runnable mFileExistCheckRunnable = new Runnable() {
        @Override
        public void run() {
            File file = new File(mCacheFilePath);
            if (!file.exists()){
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    boolean isSuccess = file.getParentFile().mkdirs();
                    if (!isSuccess){
                        Log.e(LOGGER_TAG,"cache_file_create_parent_fail");
                        return;
                    }
                }
                try {
                    boolean isSuccess = file.createNewFile();
                    if (!isSuccess){
                        Log.e(LOGGER_TAG,"cache_file_create_fail");
                    }
                } catch (IOException e) {
                    Log.e(LOGGER_TAG,"cache_file_create_fail");
                }
            }
        }
    };

    private Runnable mFileWriteRunnable = new Runnable() {
        @Override
        public void run() {
            File file = new File(mCacheFilePath);
            if (!file.exists()){
                Log.e(LOGGER_TAG,"cache_file_input_fail");
                return;
            }
            FileOutputStream fos2;
            try {
                fos2 = new FileOutputStream(file, true);
            } catch (FileNotFoundException e) {
                Log.e(LOGGER_TAG,"cache_file_input_fail");
                return;
            }

            try {
                fos2.write(jsonData.getBytes());
                fos2.close();
            } catch (IOException e) {
                Log.e(LOGGER_TAG,"cache_file_input_fail");
            }
        }
    };

    public Runnable mFileFullCheckRunnable = new Runnable() {
        @Override
        public void run() {
            File file = new File(mCacheFilePath);
            if (!file.exists()){
                Log.w(LOGGER_TAG,"cache_file_full_no_exist");
                return;
            }

            //文件大小未达到上报阙值，返回
            Log.d(LOGGER_TAG, "file_"+file.length()/1024);
            if (file.length()/1024 < mEachUploadSize){
                return;
            }

            //从文件中读出内容（json字符串）
            FileInputStream fis;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                Log.w(LOGGER_TAG,"cache_file_full_read_fail");
                return;
            }
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String str;
            try {
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
            } catch (IOException e) {
                Log.w(LOGGER_TAG,"cache_file_full_read_fail");
                return;
            } finally {
                try {
                    br.close();
                    isr.close();
                    fis.close();
                } catch (IOException e) {
                    Log.w(LOGGER_TAG,"cache_file_full_read_fail");
                    return;
                }
            }

            //上报完成删除文件
            file.delete();
            portrayalManager.uploadFile(DataFormatUtils.formatFileEndChar(sb.toString()));
        }
    };

}
