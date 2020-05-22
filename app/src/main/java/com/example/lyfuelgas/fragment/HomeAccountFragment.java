package com.example.lyfuelgas.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.activity.AccountChangePassActivity;
import com.example.lyfuelgas.activity.LoginOptionActivity;
import com.example.lyfuelgas.app.UserManager;
import com.example.lyfuelgas.bean.UserObject;
import com.example.lyfuelgas.common.mvp.MVPBaseFragment;
import com.example.lyfuelgas.common.utils.CallPhone;
import com.example.lyfuelgas.common.utils.GlideUtils;
import com.example.lyfuelgas.contact.HomeAccountContact;
import com.example.lyfuelgas.presenter.HomeAccountPresenter;
import com.example.lyfuelgas.view.CustomConfirmDialog;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeAccountFragment extends MVPBaseFragment<HomeAccountPresenter> implements HomeAccountContact.View {
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;

    UserObject userObject;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_home_account;
    }

    @Override
    protected void initData() {
        mPresenter.getUserInfo();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected HomeAccountPresenter loadPresenter() {
        return new HomeAccountPresenter();
    }

    @OnClick({R.id.ivPhoto, R.id.tvUpdatePassword, R.id.tvService, R.id.tvExit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivPhoto:
                break;
            case R.id.tvUpdatePassword:
                launchActivity(AccountChangePassActivity.class);
                break;
            case R.id.tvService:
                CallPhone.call(getActivity(),"17623257001");
                break;
            case R.id.tvExit:
                CustomConfirmDialog customConfirmDialog = CustomConfirmDialog.newInstance("确定退出登录？");
                customConfirmDialog.setOnConfirmClickListener(new CustomConfirmDialog.OnConfirmClickListener() {
                    @Override
                    public void onCallBack() {
                        mPresenter.logout();
                    }
                });
                customConfirmDialog.show(getChildFragmentManager(),"exitDialog");
                break;
        }
    }

    @Override
    public void onLogoutSuccess() {
        UserManager.getInstance().clearUser();
        launchActivity(LoginOptionActivity.class,true);
    }

    @Override
    public void onGetUserInfoSuccess(UserObject data) {
        if(null != data){
            userObject = data;
            tvName.setText(userObject.name);
            tvPhone.setText("TEL:"+userObject.phone);
            GlideUtils.loadCircleImage(mContext,userObject.photo,ivPhoto);
        }
    }
}
