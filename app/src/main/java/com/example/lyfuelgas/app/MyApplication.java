package com.example.lyfuelgas.app;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;


/**
 * Created on 2020/4/27.
 *
 * @author QiuYan
 * @since 1.0.0
 */
public class MyApplication extends Application {
    private static Context mContext = null;
    public static final String TAG = "MyApplication";

    public static Context getInstance() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initMap(){

    }


}
