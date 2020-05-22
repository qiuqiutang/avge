package com.example.lyfuelgas.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.common.utils.CallPhone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AvgeMassageActivity extends Activity {

    @BindView(R.id.home_return)
    ImageView homeReturn;
    @BindView(R.id.home_refresh)
    ImageView homeRefresh;
    @BindView(R.id.diagnose_touch_iv)
    ImageView diagnoseTouchIv;
    @BindView(R.id.home_touch)
    RelativeLayout homeTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avge_massage);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.home_return, R.id.home_refresh, R.id.home_touch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_return:
                finish();
                break;
            case R.id.home_refresh:
                break;
            case R.id.home_touch:
                CallPhone.call(this,"17623257001");
                break;
        }
    }
}
