package com.example.lyfuelgas.view;

import android.app.Activity;
import android.content.Intent;

import com.example.lyfuelgas.activity.LoginActivity;
import com.example.lyfuelgas.common.exit.ExitApp;
import com.example.lyfuelgas.common.utils.LyLog;

import java.util.ArrayList;

import androidx.fragment.app.FragmentActivity;


public class TokenExpiredDialog {
    private static String TAG = "TokenExpiredDialog";
    private static TokenExpiredDialog sInstance;
    private CustomAlertDialog mDialog;

    public static synchronized TokenExpiredDialog getInstance() {
        if (sInstance == null) {
            LyLog.e(TAG,"getInstance is null");
            sInstance = new TokenExpiredDialog();
        }
        LyLog.e(TAG,"getInstance");
        return sInstance;
    }


    public void show(){
        LyLog.e(TAG,"show");
        if(null == mDialog){
            LyLog.e(TAG,"show mDialog is null");
            mDialog = CustomAlertDialog.newInstance("token已过期，请重新登录");
            mDialog.setOnConfirmClickListener(new CustomAlertDialog.OnConfirmClickListener() {
                @Override
                public void onCallBack() {
                    FragmentActivity activity = (FragmentActivity) ExitApp.getInstance().getLastActivity();
                    if(null != activity) {
                        Intent intent = new Intent(activity, LoginActivity.class);
                        activity.startActivity(intent);
                        ArrayList<Activity> mActivityList = ExitApp.getInstance().getActivityList();
                        for (Activity activity1 : mActivityList) {
                            if(activity1 instanceof LoginActivity) {
                                continue;
                            }
                            activity.finish();
                        }
                    }

                }
            });
        }
        FragmentActivity activity = (FragmentActivity) ExitApp.getInstance().getLastActivity();
        if(null != activity){
            mDialog.show(activity.getSupportFragmentManager(),"TokenExpiredDialog");
        }

    }

}
