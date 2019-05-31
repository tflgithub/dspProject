package com.luzuzu.mine;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.luzuzu.library.base.BaseApplication;
import com.luzuzu.mine.wxapi.WxLogin;

/**
 * Created by a10105 on 2019/3/11.
 */

public class MineApplication extends BaseApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        WxLogin.initWx(this);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
