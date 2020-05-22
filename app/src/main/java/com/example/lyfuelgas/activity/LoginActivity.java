package com.example.lyfuelgas.activity;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.app.DoubleClickManager;
import com.example.lyfuelgas.app.UserManager;
import com.example.lyfuelgas.bean.AuthenticationObject;
import com.example.lyfuelgas.bean.UserObject;
import com.example.lyfuelgas.common.exit.ExitApp;
import com.example.lyfuelgas.common.mvp.MVPBaseActivity;
import com.example.lyfuelgas.common.utils.LyLog;
import com.example.lyfuelgas.common.utils.LyToast;
import com.example.lyfuelgas.common.utils.ProgressDialogUtils;
import com.example.lyfuelgas.common.utils.SPUtils;
import com.example.lyfuelgas.constant.SPConstant;
import com.example.lyfuelgas.contact.LoginContact;
import com.example.lyfuelgas.presenter.LoginPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends MVPBaseActivity<LoginPresenter> implements LoginContact.View,CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.login_name_et)
    EditText loginNameEt;
    @BindView(R.id.login_password_et)
    EditText loginPasswordEt;
    @BindView(R.id.login_remenber_cb)
    CheckBox loginRemenberCb;
    @BindView(R.id.login_auto_cb)
    CheckBox loginAutoCb;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.login_img_left)
    ImageView login_img_left;

    private String option;
    private String id;
    private String password;
    private Boolean remeber;
    private Boolean autoLogin;

    @Override
    protected LoginPresenter loadPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.ly_activity_login;
    }

    @Override
    protected void initData() {
        option = getIntent().getStringExtra("LoginOption");
        initUserInformation();
    }

    @Override
    protected void initEvent() {
        loginAutoCb.setOnCheckedChangeListener(this);
    }



    /**
     * 初始化用户信息
     */
    private void initUserInformation() {
        String auto = (String) SPUtils.get(mContext, SPConstant.LOGIN_AUTO,"");
        String username = (String) SPUtils.get(mContext, SPConstant.LOGIN_USERNAME,"");
        String password = (String) SPUtils.get(mContext, SPConstant.LOGIN_PASSWORD,"");
        if (!TextUtils.isEmpty(auto)){
            loginRemenberCb.setChecked(true);
            loginAutoCb.setChecked(true);
            if (!TextUtils.isEmpty(username)){
                loginNameEt.setText(username);
            }
            if (!TextUtils.isEmpty(password)){
                loginPasswordEt.setText(password);
            }

        }else {
            if (!TextUtils.isEmpty(username)) {
                loginNameEt.setText(username);
                loginPasswordEt.requestFocus();//获得焦点
                if (!TextUtils.isEmpty(password)) {//判断字符串是否为空
                    loginPasswordEt.setText(password);
                    loginRemenberCb.setChecked(true);
                    loginPasswordEt.setSelection(loginPasswordEt.getText().toString().trim().length());//使光标停在密码输入框的最后一位
                }
            }
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.login_auto_cb:
                LyLog.i(TAG,"点击了cb："+b);
                if (b){
                    LyLog.i(TAG,"login_auto_cb点击了");
                    loginRemenberCb.setChecked(true);
                }
                break;
        }
    }

    @Override
    public void loginFail() {

    }

    @Override
    public void loginSuccess(UserObject data) {
        UserManager.getInstance().setUser(data);
        mPresenter.getRole();
    }

    @Override
    public void onGetRoleSuccess(AuthenticationObject data) {
        boolean hasRole = false;
        if(null != data && null != data.roles && data.roles.size() > 0){
            for (int i = 0; i < data.roles.size(); i++){
                if(UserManager.getInstance().isSupplier() && data.roles.get(i).id.equals("deliver")){
                    hasRole = true;
                }else if(!UserManager.getInstance().isSupplier() && data.roles.get(i).id.equals("customer")){
                    hasRole = true;
                }
            }
        }
        if(hasRole){
            if(UserManager.getInstance().isSupplier()){
                mPresenter.getUserInfo();
            }else {
                launchActivity(HomeActivity.class,true);
                ArrayList<Activity> mActivityList = ExitApp.getInstance().getActivityList();
                for (Activity activity : mActivityList) {
                    if(activity instanceof HomeActivity) {
                        continue;
                    }
                    activity.finish();
                }
            }
        }else {
            UserManager.getInstance().clearUser();
            LyToast.shortToast(mContext,"您无该角色的操作权限");
        }
    }

    @Override
    public void onGetUserInfoSuccess() {
        launchActivity(DeliverHomeActivity.class,true);
        ArrayList<Activity> mActivityList = ExitApp.getInstance().getActivityList();
        for (Activity activity : mActivityList) {
            if(activity instanceof DeliverHomeActivity) {
                continue;
            }
            activity.finish();
        }
    }


    @OnClick({R.id.login_img_left,R.id.login_remenber_cb, R.id.login_auto_cb, R.id.login_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_remenber_cb:
                break;
            case R.id.login_auto_cb:
                break;
            case R.id.login_img_left:
                finish();
                break;
            case R.id.login_button:
                if(DoubleClickManager.getInstance().isFastClick(view)){
                    return;
                }
                if (TextUtils.isEmpty(loginNameEt.getText().toString().trim()) && TextUtils.isEmpty(loginPasswordEt.getText().toString().trim())){
                    LyToast.shortToast(this,"账户和密码不能为空");
                    return;
                }
                //清除之前保存的登录信息
                SPUtils.remove(mContext,SPConstant.LOGIN_AUTO);
                SPUtils.remove(mContext,SPConstant.LOGIN_USERNAME);
                SPUtils.remove(mContext,SPConstant.LOGIN_PASSWORD);

                if (loginAutoCb.isChecked()){
                    LyLog.i(TAG,"选中自动登录的情况下选中记住密码 ");
                    SPUtils.put(mContext,SPConstant.LOGIN_AUTO,"自动登录");
                    SPUtils.put(mContext,SPConstant.LOGIN_USERNAME,loginNameEt.getText().toString().trim());
                    SPUtils.put(mContext,SPConstant.LOGIN_PASSWORD,loginPasswordEt.getText().toString().trim());
                }else {
                    if (loginRemenberCb.isChecked()){
                        LyLog.i(TAG,"未选中自动登录的情况下选中记住密码");
                        SPUtils.put(mContext,SPConstant.LOGIN_USERNAME,loginNameEt.getText().toString().trim());
                        SPUtils.put(mContext,SPConstant.LOGIN_PASSWORD,loginPasswordEt.getText().toString().trim());
                    }
                }

                ProgressDialogUtils.showProgressDialog(this,"正在登录，请稍后...");

                mPresenter.login(loginNameEt.getText().toString().trim(),loginPasswordEt.getText().toString().trim());
                break;
        }
    }
}
