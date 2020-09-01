package com.example.lyfuelgas.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.app.SupplierManager;
import com.example.lyfuelgas.app.UserManager;
import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.bean.DeviceStatObject;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.MVPBaseActivity;
import com.example.lyfuelgas.common.utils.CallPhone;
import com.example.lyfuelgas.common.utils.DateUtils;
import com.example.lyfuelgas.common.utils.GsonUtils;
import com.example.lyfuelgas.common.utils.ProgressDialogUtils;
import com.example.lyfuelgas.common.utils.SPUtils;
import com.example.lyfuelgas.contact.AvgeMessageContact;
import com.example.lyfuelgas.presenter.AvgeMessagePresenter;
import com.example.lyfuelgas.view.SimpleToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AvgeMassageActivity extends MVPBaseActivity<AvgeMessagePresenter> implements AvgeMessageContact.View {
    @BindView(R.id.simple_toolbar)
    SimpleToolbar simpleToolbar;
    @BindView(R.id.diagnose_touch_iv)
    ImageView diagnoseTouchIv;
    @BindView(R.id.home_touch)
    RelativeLayout homeTouch;
    @BindView(R.id.layoutContent)
    ViewGroup layoutContent;
    @BindView(R.id.tvQueryTime)
    TextView tvQueryTime;
    @BindView(R.id.tvPrice)
            TextView tvPrice;
    @BindView(R.id.tvUsedDays)
            TextView tvUsedDays;
    @BindView(R.id.tvUsedFeeLast12Month)
            TextView tvUsedFeeLast12Month;
    @BindView(R.id.tvUsedFeeThisMonth)
            TextView tvUsedFeeThisMonth;
    @BindView(R.id.tvUsedFeeToday)
            TextView tvUsedFeeToday;
    @BindView(R.id.tvUsedThisMonth)
            TextView tvUsedThisMonth;
    @BindView(R.id.tvUsedToday)
            TextView tvUsedToday;
    @BindView(R.id.tvUsedLast12Month)
            TextView tvUsedLast12Month;
    DeviceObject deviceObject;

    private String phone;

    @Override
    protected AvgeMessagePresenter loadPresenter() {
        return new AvgeMessagePresenter();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_avge_massage;
    }

    @Override
    protected void initData() {
        phone = getIntent().getStringExtra("phone");
        ProgressDialogUtils.showProgressDialog(mContext,"数据加载中");
        simpleToolbar.setMainTitle("数据统计");
        simpleToolbar.setRightTitleDrawable(R.drawable.ly_refresh);
        String deviceInfo = (String) SPUtils.get(mContext, UserManager.getInstance().getDeviceInfoKey(), "");
        deviceObject = GsonUtils.parseJsonToBean(deviceInfo, DeviceObject.class);
        if(null != deviceInfo){
            mPresenter.getStat(deviceObject.id);
        }
    }

    @Override
    protected void initEvent() {
        simpleToolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        simpleToolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != deviceObject){
                    ProgressDialogUtils.showProgressDialog(mContext,"数据加载中");
                    mPresenter.getStat(deviceObject.id);
                }
            }
        });

    }

    @OnClick({ R.id.home_touch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_touch:
                CallPhone.call(this, phone);
                break;
        }
    }

    @Override
    public void onGetStatSuccess(DeviceStatObject data) {
        if(null != data){
            layoutContent.setVisibility(View.VISIBLE);
            tvPrice.setText(data.price+"元");
            tvUsedDays.setText(data.usedDays+"天");
            tvUsedFeeLast12Month.setText(data.usedFeeLast12Month+"元");
            tvUsedFeeThisMonth.setText(data.usedFeeThisMonth+"元");
            tvUsedFeeToday.setText(data.usedFeeToday+"元");
            tvUsedLast12Month.setText(data.usedlast12Month+"L");
            tvUsedThisMonth.setText(data.usedThisMonth+"L");
            tvUsedToday.setText(data.usedToday+"L");
            tvQueryTime.setText(String.format("查询时间：%s", DateUtils.formatDateTotal(data.timestamp)));
        }
    }
}
