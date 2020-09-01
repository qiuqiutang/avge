package com.example.lyfuelgas.activity;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.adapter.HomeActivityAdapter;
import com.example.lyfuelgas.common.exit.PressExit;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.MVPBaseActivity;
import com.example.lyfuelgas.fragment.DeliverAccountFragment;
import com.example.lyfuelgas.fragment.DeliverHomeMapFragment;
import com.example.lyfuelgas.fragment.DeliverHomeOrderFragment;
import com.example.lyfuelgas.fragment.HomeAccountFragment;
import com.example.lyfuelgas.view.NoSlidingViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;

public class DeliverHomeActivity extends MVPBaseActivity {
    @BindView(R.id.nav)
    BottomNavigationView nav;
    @BindView(R.id.viewpager)
    NoSlidingViewPager viewpager;



    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_deliver_home;
    }

    @Override
    protected void initData() {
        viewpager.setScrollable(false);
        viewpager.setAdapter(new HomeActivityAdapter(getSupportFragmentManager(), getFragments()));
        viewpager.setOffscreenPageLimit(3);
    }

    @Override
    protected void initEvent() {
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuHome:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.menuOrder:
                        viewpager.setCurrentItem(1);
                        break;
                    case R.id.menuMe:
                        viewpager.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            PressExit.pressAgainExit(getApplicationContext());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private List<Fragment> getFragments() {
        List<Fragment> mList = new ArrayList<>();
        DeliverHomeMapFragment deliverHomeMapFragment = new DeliverHomeMapFragment();
        deliverHomeMapFragment.setDeliverMapListener(new DeliverHomeMapFragment.OnDeliverMapListener() {
            @Override
            public View getButtomView() {
                return nav;
            }
        });
        mList.add(deliverHomeMapFragment);
        mList.add(new DeliverHomeOrderFragment());
        mList.add(new DeliverAccountFragment());
        return mList;
    }
}
