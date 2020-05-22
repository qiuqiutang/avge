package com.example.lyfuelgas.app;

import android.view.View;

import com.example.lyfuelgas.common.utils.LyLog;


public class DoubleClickManager {
    private static DoubleClickManager sInstance;

    public static synchronized DoubleClickManager getInstance() {
        if (sInstance == null) {
            sInstance = new DoubleClickManager();
        }
        return sInstance;
    }

    private final int MIN_DELAY_TIME = 1000;  // 两次点击间隔不能少于1000ms
    private long lastClickTime = 0;
    private View view;

    public boolean isFastClick(View v) {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if(null == view || view.getId() != v.getId() ) {
            flag = false;
            LyLog.e("=====","null == view || view.getId() != v.getId()");
        }else if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        LyLog.e("=====","currentClickTime - lastClickTime："+(currentClickTime - lastClickTime));
        lastClickTime = currentClickTime;
        view = v;
        return flag;
    }
}
