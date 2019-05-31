package com.luzuzu.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.blankj.utilcode.util.Utils;

/**
 * Created by fula on 2019/5/26.
 */

public class SystemUtils {

    public static int getKeyboardHeight(AppCompatActivity paramActivity) {

        int height = SystemUtils.getScreenHeight(paramActivity) - SystemUtils.getStatusBarHeight(paramActivity)
                - SystemUtils.getAppHeight(paramActivity);
        if (height == 0) {
            height = SharedPreferencesUtils.getIntShareData("KeyboardHeight", 244);//787为默认软键盘高度 基本差不离
        } else {
            SharedPreferencesUtils.putIntShareData("KeyboardHeight", height);
        }
        return height;
    }

    /**
     * 屏幕分辨率高
     **/
    public static int getScreenHeight(AppCompatActivity paramActivity) {
        Display display = paramActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * Get AppCompatActivity from context
     */
    public static AppCompatActivity getAppCompatActivity(Context context) {
        if (context == null) return null;
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        } else if (context instanceof ContextThemeWrapper) {
            return getAppCompatActivity(((ContextThemeWrapper) context).getBaseContext());
        }
        return null;
    }

    /**
     * statusBar高度
     **/
    public static int getStatusBarHeight(AppCompatActivity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.top;

    }

    /**
     * 可见屏幕高度
     **/
    public static int getAppHeight(AppCompatActivity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.height();
    }

    /**
     * 关闭键盘
     **/
    public static void hideSoftInput(View paramEditText) {
        ((InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(paramEditText.getWindowToken(), 0);
    }

    // below actionbar, above softkeyboard
    public static int getAppContentHeight(AppCompatActivity paramActivity) {
        return SystemUtils.getScreenHeight(paramActivity) - SystemUtils.getStatusBarHeight(paramActivity)
                - SystemUtils.getActionBarHeight(paramActivity) - SystemUtils.getKeyboardHeight(paramActivity);
    }

    /**
     * 获取actiobar高度
     **/
    public static int getActionBarHeight(AppCompatActivity paramActivity) {
        if (true) {
            return PxUtils.dp2px(Utils.getApp(), 56);
        }
        int[] attrs = new int[]{android.R.attr.actionBarSize};
        TypedArray ta = paramActivity.obtainStyledAttributes(attrs);
        return ta.getDimensionPixelSize(0, PxUtils.dp2px(Utils.getApp(), 56));
    }


    /**
     * 键盘是否在显示
     **/
    public static boolean isKeyBoardShow(AppCompatActivity paramActivity) {
        int height = SystemUtils.getScreenHeight(paramActivity) - SystemUtils.getStatusBarHeight(paramActivity)
                - SystemUtils.getAppHeight(paramActivity);
        return height != 0;
    }

    /**
     * 显示键盘
     **/
    public static void showKeyBoard(final View paramEditText) {
        paramEditText.requestFocus();
        paramEditText.post(new Runnable() {
            @Override
            public void run() {
                ((InputMethodManager) Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(paramEditText, 0);
            }
        });
    }
}
