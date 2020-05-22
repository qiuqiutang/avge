package com.example.lyfuelgas.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.adapter.HomeActivityAdapter;
import com.example.lyfuelgas.fragment.Avge_Auto_left_Fragment;
import com.example.lyfuelgas.fragment.Avge_Auto_right_Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AvgeAutoActivity extends FragmentActivity {

    @BindView(R.id.auto_now)
    TextView autoNow;
    @BindView(R.id.auto_control)
    TextView autoControl;
    @BindView(R.id.auto_vp)
    ViewPager autoVp;
    @BindView(R.id.home_return)
    ImageView homeReturn;
    @BindView(R.id.home_refresh)
    ImageView homeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avge_auto);
        ButterKnife.bind(this);
        setViewPager();

    }

    private List<Fragment> getFragments() {
        List<Fragment> mList = new ArrayList<>();
        mList.add(new Avge_Auto_left_Fragment());
        mList.add(new Avge_Auto_right_Fragment());
        return mList;
    }

    private void setViewPager() {
        autoVp.setAdapter(new HomeActivityAdapter(getSupportFragmentManager(), getFragments()));
        autoVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });
    }

    @OnClick({R.id.auto_now, R.id.auto_control,R.id.home_return, R.id.home_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.auto_now:
                autoVp.setCurrentItem(0);
                break;
            case R.id.auto_control:
                autoVp.setCurrentItem(1);
                break;
            case R.id.home_return:
                finish();
                break;
            case R.id.home_refresh:
                break;
        }
    }


}
