package com.example.lyfuelgas.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.app.DoubleClickManager;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.MVPBaseActivity;
import com.example.lyfuelgas.common.utils.SPUtils;
import com.example.lyfuelgas.constant.SPConstant;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginOptionActivity extends MVPBaseActivity {

    @BindView(R.id.login_option_user_tv)
    TextView loginOptionUserTv;
    @BindView(R.id.login_option_supplier_tv)
    TextView loginOptionSupplierTv;

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_login_option;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.login_option_user_tv, R.id.login_option_supplier_tv})
    public void onViewClicked(View view) {
        if(DoubleClickManager.getInstance().isFastClick(view)){
            return;
        }
        switch (view.getId()) {
            case R.id.login_option_user_tv:
                SPUtils.put(mContext, SPConstant.USER_TYPE,1);
                startActivity(new Intent(this,LoginActivity.class).putExtra("LoginOption","user"));
                break;
            case R.id.login_option_supplier_tv:
                SPUtils.put(mContext, SPConstant.USER_TYPE,2);
                startActivity(new Intent(this,LoginActivity.class).putExtra("LoginOption","provider"));
                //startActivity(new Intent(this,DispatchingActivity.class).putExtra("LoginOption","provider"));
                break;
        }
    }
}
