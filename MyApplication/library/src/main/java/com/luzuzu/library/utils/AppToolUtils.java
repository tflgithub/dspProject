package com.luzuzu.library.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.PowerManager;
import java.util.List;

/**
 * Created by fula on 2019/3/7.
 */

public class AppToolUtils {

    /**
     * 判断app是否正在运行
     * @param context                       上下文
     * @param packageName                   应用的包名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list ;
        if (am != null) {
            list = am.getRunningTasks(100);
            if (list.size() <= 0) {
                return false;
            }
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.baseActivity.getPackageName().equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断应用是否处于后台
     * @param context
     * @return
     */
    public static boolean isAppOnBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否锁屏
     * @param context
     * @return
     */
    public static boolean isLockScreen(Context context){
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
        boolean isScreenOn = pm.isScreenOn();
        if (isScreenOn){
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断某Activity是否挂掉
     * @param activity      activity
     * @return
     */
    public static boolean isActivityLiving(Activity activity) {
        if (activity == null) {
            return false;
        }
        return !activity.isFinishing();
    }

}
