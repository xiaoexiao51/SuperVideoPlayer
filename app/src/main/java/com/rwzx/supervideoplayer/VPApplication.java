package com.rwzx.supervideoplayer;

import android.app.Application;
import android.content.Context;

import com.rwzx.supervideoplayer.util.LogUtils;

/**
 * Created by MMM on 2017/11/8.
 * VPApplication
 */
public class VPApplication extends Application {

    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();

        initConfig();
    }

    private void initConfig() {
        if (BuildConfig.DEBUG) {
            LogUtils.setDebugMode("LogUtils");// 初始化日志管理
        }
    }
}
