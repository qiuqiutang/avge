package com.example.lyfuelgas.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.adapter.BaseRecyclerAdapter;
import com.example.lyfuelgas.adapter.HomeActivityAdapter;
import com.example.lyfuelgas.fragment.DispatchingLeftFragment;
import com.example.lyfuelgas.fragment.DispatchingRightFragment;
import com.example.lyfuelgas.common.utils.LyLog;
import com.example.lyfuelgas.view.NoSlidingViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DispatchingActivity extends AppCompatActivity {
    private static final String TAG = "DispatchingActivity";
    @BindView(R.id.dp_return)
    ImageView dpReturn;
    @BindView(R.id.dp_ewm)
    ImageView dpEwm;
    @BindView(R.id.order_not)
    TextView orderNot;
    @BindView(R.id.order_already)
    TextView orderAlready;
    @BindView(R.id.dp_find)
    LinearLayout dpFind;
    @BindView(R.id.dp_all_iv)
    ImageView dpAllIv;
    @BindView(R.id.dp_all_tv)
    TextView dpAllTv;
    @BindView(R.id.dp_count)
    TextView dpCount;
    @BindView(R.id.dp_vp)
    NoSlidingViewPager dpVp;

    private List<String> dp_order_List=new ArrayList<>();
    private BaseRecyclerAdapter<String> dpOrderRecyclyerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatching);
        ButterKnife.bind(this);
        initview();
        setViewPager();
    }
    private void initview(){

    }
    private void setViewPager() {
        LyLog.i(TAG,"初始化viewpager");
        //dpVp=findViewById(R.id.order_vp);
        //getChildFragmentManager此处必须用子fragmentmanager否则只会加载一次页面
        dpVp.setAdapter(new HomeActivityAdapter(getSupportFragmentManager(), getFragments()));
    }

    private List<Fragment> getFragments() {
        List<Fragment> mList = new ArrayList<>();
        mList.add(new DispatchingLeftFragment());
        mList.add(new DispatchingRightFragment());
        return mList;
    }


    private void setColor(String click){
        if(click.equals("order_not")){
            orderNot.setTextColor(this.getResources().getColor(R.color.white,null));
            orderNot.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_order_bt1, null));
            orderAlready.setTextColor(this.getResources().getColor(R.color.home_head_text,null));
            orderAlready.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_white_gray2, null));
        }else{
            orderAlready.setTextColor(this.getResources().getColor(R.color.white,null));
            orderAlready.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_order_bt2, null));
            orderNot.setTextColor(this.getResources().getColor(R.color.home_head_text,null));
            orderNot.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_white_gray1, null));
        }
    }

    @OnClick({R.id.dp_return, R.id.dp_ewm, R.id.order_not, R.id.order_already, R.id.dp_find, R.id.dp_all_iv, R.id.dp_all_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dp_return:
                break;
            case R.id.dp_ewm:
                break;
            case R.id.order_not:
                LyLog.i(TAG,"order_not点击");
                setColor("order_not");
                dpVp.setCurrentItem(0);
                break;
            case R.id.order_already:
                LyLog.i(TAG,"order_already点击");
                setColor("order_control");
                dpVp.setCurrentItem(1);
                break;
            case R.id.dp_find:
                break;
            case R.id.dp_all_iv:
                break;
            case R.id.dp_all_tv:
                break;
        }
    }
}
