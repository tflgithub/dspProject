package com.luzuzu.library.constant;

import android.os.Environment;

/**
 * Created by fula on 2019/3/9.
 */

public interface Constant {

    String LOGIN_NEEDED="needLogin";

    String SP_NAME = "ms";
    String EXTERNAL_STORAGE_DIRECTORY =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

    /**-------------------------------------键-------------------------------------------------**/
    //Sp键
    String KEY_FIRST_SPLASH = "first_splash";                 //是否第一次启动
    String KEY_IS_LOGIN = "is_login";                         //登录
}
