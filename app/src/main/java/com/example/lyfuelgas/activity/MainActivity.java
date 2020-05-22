package com.example.lyfuelgas.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.common.utils.LyLog;
import com.example.lyfuelgas.permissions.RuntimeRationale;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

import androidx.annotation.NonNull;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private Boolean isShowPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {

        if (isShowPermission == false) {
            LyLog.i(TAG, "你来获取权限了吗？");
            setPermissionUtils();

        }
    }

    private void enterLoginActivity(){
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,LoginOptionActivity.class));
                finish();
            }
        }, 2000);
    }

    private void setPermissionUtils() {
        if (AndPermission.hasPermissions(this, Manifest.permission.READ_PHONE_STATE) &&
                AndPermission.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION) &&
                AndPermission.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)&&
                AndPermission.hasPermissions(this, Manifest.permission.CALL_PHONE)) {
            LyLog.i(TAG, "不存在权限，直接跳转至登录界面");
            enterLoginActivity();
            isShowPermission = true;
        } else {
            LyLog.i(TAG, "存在权限，正在申请，申请成功后跳转至登录界面");
            requestPermission(Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        }

    }

    /**
     * 请求权限
     */
    private void requestPermission(String... permissions) {
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        LyLog.i(TAG, "获取权限成功");
                        isShowPermission = true;
                        enterLoginActivity();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        LyLog.i(TAG, "获取权限失败");
                    }
                })
                .start();
    }
}
