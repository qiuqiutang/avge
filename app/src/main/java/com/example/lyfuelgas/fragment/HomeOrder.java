package com.example.lyfuelgas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.adapter.HomeActivityAdapter;
import com.example.lyfuelgas.common.mvp.MVPBaseFragment;
import com.example.lyfuelgas.common.utils.LyLog;
import com.example.lyfuelgas.view.NoSlidingViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeOrder extends Fragment {
    private static final String TAG="HomeOrder";
    @BindView(R.id.order_now)
    TextView orderNow;
    @BindView(R.id.order_control)
    TextView orderControl;

    private NoSlidingViewPager orderVp;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_order, container, false);
        ButterKnife.bind(this, view);
        setViewPager();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void setViewPager() {
        LyLog.i(TAG,"初始化viewpager");
        orderVp=view.findViewById(R.id.order_vp);
        //getChildFragmentManager此处必须用子fragmentmanager否则只会加载一次页面
        orderVp.setAdapter(new HomeActivityAdapter(getChildFragmentManager(), getFragments()));
    }

    private List<Fragment> getFragments() {
        List<Fragment> mList = new ArrayList<>();
        mList.add(OrderFragment.getInstance(0));
        mList.add(OrderFragment.getInstance(1));
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
