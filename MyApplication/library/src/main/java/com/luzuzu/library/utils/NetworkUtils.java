package com.luzuzu.library.utils;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.blankj.utilcode.util.Utils;

import java.util.List;

/**
 * Created by a10105 on 2019/3/7.
 */

public class NetworkUtils {

    /**
     * 是否有可用网络
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) Utils.getApp()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * gps能用？
     *
     * @return
     */
    public static boolean isGpsEnabled() {
        LocationManager locationManager = ((LocationManager) Utils.getApp()
                .getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = locationManager.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }


    /**
     * Wifi是否可用
     *
     * @return
     */
    public static boolean isWifiEnabled() {
        ConnectivityManager mgrConn = (ConnectivityManager) Utils.getApp()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) Utils.getApp()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 当前网络是否为Wifi
     *
     * @return
     */
    public static boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Utils.getApp()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }


    /**
     * 当前网络是否为移动网络
     *
     * @return
     */
    public static boolean is4G() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Utils.getApp()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }
}
