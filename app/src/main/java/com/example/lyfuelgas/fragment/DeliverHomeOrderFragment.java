package com.example.lyfuelgas.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.adapter.HomeActivityAdapter;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.MVPBaseFragment;
import com.example.lyfuelgas.common.utils.LyLog;
import com.example.lyfuelgas.common.utils.eventbus.EventType;
import com.example.lyfuelgas.common.utils.eventbus.MyEvent;
import com.example.lyfuelgas.view.NoSlidingViewPager;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;


public class DeliverHomeOrderFragment extends MVPBaseFragment {
    private static final String TAG="HomeOrder";
    @BindView(R.id.order_now)
    TextView orderNow;
    @BindView(R.id.order_control)
    TextView orderControl;
    @BindView(R.id.order_vp)
    NoSlidingViewPager orderVp;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_deliver_home_order;
    }

    @Override
    protected void initData() {
        setViewPager();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void setViewPager() {
        orderVp.setAdapter(new HomeActivityAdapter(getChildFragmentManager(), getFragments()));
    }

    private List<Fragment> getFragments() {
        List<Fragment> mList = new ArrayList<>();
        mList.add(DispatchFragment.getInstance(0));
        mList.add(DispatchFragment.getInstance(1));
        return mList;
    }


   private void setColor(String click){
        if(click.equals("order_now")){
            orderNow.setTextColor(this.getResources().getColor(R.color.white,null));
            orderNow.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_order_bt1, null));
            orderControl.setTextColor(this.getResources().getColor(R.color.home_head_text,null));
            orderControl.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_white_gray2, null));
        }else{
            orderControl.setTextColor(this.getResources().getColor(R.color.white,null));
            orderControl.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_order_bt2, null));
            orderNow.setTextColor(this.getResources().getColor(R.color.home_head_text,null));
            orderNow.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_white_gray1, null));
        }
    }

    @OnClick({R.id.order_now, R.id.order_control})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_now:
                LyLog.i(TAG,"order_now点击");
                setColor("order_now");
                orderVp.setCurrentItem(0);
                break;
            case R.id.order_control:
                LyLog.i(TAG,"order_control点击");
                setColor("order_control");
                orderVp.setCurrentItem(1);
                break;
        }
    }



}
