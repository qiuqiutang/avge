package com.example.lyfuelgas.activity;

import android.Manifest;
import android.os.Build;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.app.UserManager;
import com.example.lyfuelgas.bean.UserObject;
import com.example.lyfuelgas.common.mvp.MVPBaseActivity;
import com.example.lyfuelgas.common.utils.LyLog;
import com.example.lyfuelgas.contact.SplashContact;
import com.example.lyfuelgas.permissions.RuntimeRationale;
import com.example.lyfuelgas.presenter.SplashPresenter;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

import androidx.annotation.NonNull;

public class SplashActivity extends MVPBaseActivity<SplashPresenter> implements SplashContact.View {

    @Override
    protected SplashPresenter loadPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        setPermissionUtils();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void loginFail() {
        launchActivity(LoginOptionActivity.class,true);
    }

    @Override
    public void loginSuccess(UserObject userObject) {
        UserObject userObject1 = UserManager.getInstance().getUser();
        userObject1.userId = userObject.userId;
        userObject1.token = userObject.token;
        UserManager.getInstance().setUser(userObject1);
        launchActivity(UserManager.getInstance().isSupplier() ? DeliverHomeActivity.class : HomeActivity.class);
    }


    private void setPermissionUtils() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            goNext();
            return;
        }
        if(!AndPermission.hasPermissions(this, Manifest.permission.READ_PHONE_STATE)){
            requestPermission(Manifest.permission.READ_PHONE_STATE);
            return;
        }
        if(!AndPermission.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
            requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            return;
        }
        if(!AndPermission.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }
        if(!AndPermission.hasPermissions(this, Manifest.permission.CALL_PHONE)){
            requestPermission(Manifest.permission.CALL_PHONE);
            return;
        }
        goNext();
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
                        setPermissionUtils();
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


    private void goNext(){
        mPresenter.checkLogin();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
