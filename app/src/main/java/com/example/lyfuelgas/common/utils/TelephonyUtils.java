package com.example.lyfuelgas.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import java.util.Locale;

/**
 * Created on 2020/4/24.
 *
 * @author QiuYan
 * @since 1.0.0
 */
public final class TelephonyUtils {
    private TelephonyUtils() {

    }

    /**
     * get current network connection status
     *
     * @param context context
     * @return -1 if no network, otherwise return network type
     */
    public static int getNetStatus(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();

        if (info != null && info.isAvailable()) {
            // wifi: ConnectivityManager.TYPE_WIFI
            // mobile: ConnectivityManager.TYPE_MOBILE
            return info.getType();
        } else {
            return -1;
        }
    }

    /**
     * get current device locale String such as en_US
     *
     * @param context context
     * @return locale string
     */
    public static String getCurrentLocaleString(Context context) {
        if (context == null) {
            return "en_US";
        }

        // Locale.getDefault() will not change if setting changed when app is running
        Locale current;
        if (Build.VERSION.SDK_INT >= 24) {
            current = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            current = context.getResources().getConfiguration().locale;
        }
        return current.toString();
    }




    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }




}