package com.example.lyfuelgas.activity;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.common.mvp.MVPBaseActivity;
import com.example.lyfuelgas.common.utils.LyToast;
import com.example.lyfuelgas.common.utils.SPUtils;
import com.example.lyfuelgas.constant.SPConstant;
import com.example.lyfuelgas.contact.AccountChangePassContact;
import com.example.lyfuelgas.presenter.AccountChangePassPresenter;
import com.example.lyfuelgas.view.SimpleToolbar;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountChangePassActivity extends MVPBaseActivity<AccountChangePassPresenter> implements AccountChangePassContact.View {

    @BindView(R.id.simple_toolbar)
    SimpleToolbar toolbar;
    @BindView(R.id.etOldPassword)
    EditText etOldPassword;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.change_modify)
    Button changeModify;

    @Override
    protected AccountChangePassPresenter loadPresenter() {
        return new AccountChangePassPresenter();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_account_change_pass;
    }

    @Override
    protected void initData() {
        toolbar.setMainTitle("修改密码");
    }

    @Override
    protected void initEvent() {
        toolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.change_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_modify:
                if(TextUtils.isEmpty(etOldPassword.getText().toString().trim())){
                    LyToast.shortToast(mContext,"请输入当前密码");
                    return;
                }
                if(TextUtils.isEmpty(etNewPassword.getText().toString().trim())){
                    LyToast.shortToast(mContext,"请输入新密码");
                    return;
                }
                if(TextUtils.isEmpty(etConfirmPassword.getText().toString().trim())){
                    LyToast.shortToast(mContext,"请输入确认密码");
                    return;
                }
                if(!etNewPassword.getText().toString().trim().equals(etConfirmPassword.getText().toString().trim())){
                    LyToast.shortToast(mContext,"新密码与确认密码不一样");
                    return;
                }
                mPresenter.updatePassword(etNewPassword.getText().toString().trim(),etOldPassword.getText().toString().trim());
                break;
        }
    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideInput();
        return super.onTouchEvent(event);
    }

    @Override
    public void updateSuccess() {
        SPUtils.put(mContext, SPConstant.LOGIN_PASSWORD,etNewPassword.getText().toString().trim());
        LyToast.shortToast(mContext,"修改密码成功");
        finish();
    }
}
