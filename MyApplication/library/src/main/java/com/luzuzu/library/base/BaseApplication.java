package com.luzuzu.library.base;

import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.luzuzu.library.arouter.ARouterUtils;
import com.luzuzu.library.base.config.AppConfig;

/**
 * Created by fula on 2019/5/15.
 */

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication instance;

    public static BaseApplication get(){
        return instance;
    }

    /**
     * 程序启动的时候执行
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        AppConfig.INSTANCE.initConfig(this);
        //在子线程中初始化
        //InitializeService.start(this);
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        Log.d("Application", "onTerminate");
        super.onTerminate();
        ARouterUtils.destroy();
    }


    /**
     * 低内存的时候执行
     */
    @Override
    public void onLowMemory() {
        Log.d("Application", "onLowMemory");
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    /**
     * HOME键退出应用程序
     * 程序在内存清理的时候执行
     */
    @Override
    public void onTrimMemory(int level) {
        Log.d("Application", "onTrimMemory");
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN){
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    /**
     * onConfigurationChanged
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d("Application", "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }
}
