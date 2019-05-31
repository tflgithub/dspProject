package com.luzuzu.library.base.config;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.luzuzu.library.BuildConfig;
import com.luzuzu.library.base.callback.BaseLifecycleCallback;
import com.luzuzu.library.constant.Constant;

/**
 * Created by fula on 2019/3/9.
 */

public enum AppConfig {

    INSTANCE;
    private boolean isLogin;

    public void initConfig(Application application){
        Utils.init(application);
        BaseLifecycleCallback.getInstance().init(application);
        initARouter();
        //1.是否是登录状态
        isLogin = SPUtils.getInstance(Constant.SP_NAME).getBoolean(Constant.KEY_IS_LOGIN, false);
    }


    private void initARouter(){
        if (BuildConfig.IS_DEBUG) {
            //打印日志
            ARouter.openLog();
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        //推荐在Application中初始化
        ARouter.init(Utils.getApp());
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        SPUtils.getInstance(Constant.SP_NAME).put(Constant.KEY_IS_LOGIN,login);
        this.isLogin = login;
    }
}
