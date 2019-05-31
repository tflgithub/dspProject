package com.luzuzu.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fula on 2019/5/26.
 */

public class SharedPreferencesUtils {

    public static final String KEY = "luzuzu_dsp";
    private static Context mContext;
    public static void config(Context context) {
        mContext = context;
    }
    public static String getShareData(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }
    public static int getIntShareData(String key, int defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }
    public static void putShareData(String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString(key, value);
        et.commit();
    }

    public static void putIntShareData(String key, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putInt(key, value);
        et.commit();
    }

}
