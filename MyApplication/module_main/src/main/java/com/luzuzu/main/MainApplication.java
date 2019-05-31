package com.luzuzu.main;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;

import com.blankj.utilcode.util.Utils;
import com.luzuzu.library.utils.SharedPreferencesUtils;
import com.mob.MobSDK;

/**
 * Created by a10105 on 2019/3/8.
 */

public class MainApplication extends MultiDexApplication{


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        MobSDK.init(this);
        SharedPreferencesUtils.config(this);
        EmojiCompat.Config config=new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
