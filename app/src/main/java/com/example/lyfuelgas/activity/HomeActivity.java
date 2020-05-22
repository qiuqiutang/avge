package com.example.lyfuelgas.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.adapter.HomeActivityAdapter;
import com.example.lyfuelgas.app.UserManager;
import com.example.lyfuelgas.common.exit.PressExit;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.MVPBaseActivity;
import com.example.lyfuelgas.common.utils.LyLog;
import com.example.lyfuelgas.common.utils.SPUtils;
import com.example.lyfuelgas.fragment.HomeAccountFragment;
import com.example.lyfuelgas.fragment.HomeAvgeFragment;
import com.example.lyfuelgas.fragment.HomeCamer;
import com.example.lyfuelgas.fragment.HomeDiscount;
import com.example.lyfuelgas.fragment.HomeOrder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends MVPBaseActivity{
    private static final String TAG = "HomeActivity";
    @BindView(R.id.home_vp)
    ViewPager home_vp;
    @BindView(R.id.home_ll_home)
    LinearLayout home_ll_home;
    @BindView(R.id.home_ll_discount)
    LinearLayout home_ll_discount;
    @BindView(R.id.home_ll_news)
    LinearLayout home_ll_news;
    @BindView(R.id.home_ll_account)
    LinearLayout home_ll_account;
    @BindView(R.id.home_ll_camer)
    LinearLayout home_ll_camer;

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        String deviceInfo = (String) SPUtils.get(mContext, UserManager.getInstance().getDeviceInfoKey(),"");
        if(TextUtils.isEmpty(deviceInfo)){
            launchActivityForResult(SelectDeviceActivity.class,null,100);
            return;
        }
        setViewPager();
        home_ll_home.setSelected(true);
    }

    @Override
    protected void initEvent() {

    }


    @OnClick({R.id.home_ll_home, R.id.home_ll_discount, R.id.home_ll_camer, R.id.home_ll_news, R.id.home_ll_account})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_ll_home:
                LyLog.i(TAG, "home_avge");
                home_vp.setCurrentItem(0);
                break;
            case R.id.home_ll_discount:
                LyLog.i(TAG, "home_ll_camer");
                home_vp.setCurrentItem(1);
                break;
            case R.id.home_ll_camer:
                LyLog.i(TAG, "home_ll_discount");
                home_vp.setCurrentItem(2);
                break;
            case R.id.home_ll_news:
                LyLog.i(TAG, "home_ll_news");
                home_vp.setCurrentItem(3);
                break;
            case R.id.home_ll_account:
                LyLog.i(TAG, "home_ll_account");
                home_vp.setCurrentItem(4);
                break;
        }
    }

    private void setViewPager() {
        home_vp.setAdapter(new HomeActivityAdapter(getSupportFragmentManager(), getFragments()));
        home_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                home_ll_home.setSelected(false);
                home_ll_discount.setSelected(false);
                home_ll_news.setSelected(false);
                home_ll_camer.setSelected(false);
                home_ll_account.setSelected(false);
                switch (position){
                    case 0:
                        home_ll_home.setSelected(true);
                        break;
                    case 1:
                        home_ll_discount.setSelected(true);
                        break;
                    case 2:
                        home_ll_camer.setSelected(true);
                        break;
                    case 3:
                        home_ll_news.setSelected(true);
                        break;
                    case 4:
                        home_ll_account.setSelected(true);
                        break;
                        default:
                            break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private List<Fragment> getFragments() {
        List<Fragment> mList = new ArrayList<>();
        mList.add(new HomeAvgeFragment());
        mList.add(new HomeDiscount());
        mList.add(new HomeCamer());
        mList.add(new HomeOrder());
        mList.add(new HomeAccountFragment());
        return mList;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            PressExit.pressAgainExit(getApplicationContext());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private ArrayList<FragmentTouchListener> mFragmentTouchListeners = new ArrayList<>();


    public void registerFragmentTouchListener(FragmentTouchListener listener) {
        mFragmentTouchListeners.add(listener);
    }


    public void unRegisterFragmentTouchListener(FragmentTouchListener listener) {
        mFragmentTouchListeners.remove(listener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        for (FragmentTouchListener listener : mFragmentTouchListeners) {
            listener.onTouchEvent(event);
        }

        return super.dispatchTouchEvent(event);
    }

    public interface FragmentTouchListener {

        boolean onTouchEvent(MotionEvent event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(100 == requestCode){
            if(RESULT_OK == resultCode) {
                setViewPager();
                home_ll_home.setSelected(true);
            }else {
                finish();
            }
        }
    }
}
