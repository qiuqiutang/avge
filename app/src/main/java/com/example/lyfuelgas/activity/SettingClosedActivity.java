package com.example.lyfuelgas.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.common.mvp.MVPBaseActivity;
import com.example.lyfuelgas.contact.SettingClosedContact;
import com.example.lyfuelgas.presenter.SettingClosedPresenter;
import com.example.lyfuelgas.view.SimpleToolbar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2018/8/29.
 * 消息提醒设置
 * @author QiuYan
 * @since 1.0.0
 */

public class SettingClosedActivity extends MVPBaseActivity<SettingClosedPresenter> implements SettingClosedContact.View{
    @BindView(R.id.simple_toolbar)
    SimpleToolbar toolbar;
    @BindView(R.id.ivClosed)
    ImageView ivClosed;
    @BindView(R.id.layoutContent)
    ViewGroup layoutContent;

    //PushStatusObject pushStatusObject = new PushStatusObject();
    @Override
    protected void initData() {
        toolbar.setMainTitle("营业状态");
        mPresenter.getStatus();
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

    @Override
    protected SettingClosedPresenter loadPresenter() {
        return new SettingClosedPresenter();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_setting_closed;
    }

    @OnClick({R.id.ivClosed})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivClosed:
                //考勤推送
                mPresenter.setStatus(ivClosed.isSelected() ? 0 : 1);
                break;
        }
    }



    @Override
    public void onGetStatusSuccess(int status) {
        ivClosed.setSelected(1 == status);
    }

    @Override
    public void onSetStatusSuccess() {
        ivClosed.setSelected(!ivClosed.isSelected());
    }
}
