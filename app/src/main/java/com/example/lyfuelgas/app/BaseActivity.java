package com.example.lyfuelgas.app;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.fragment.app.FragmentActivity;

import com.example.lyfuelgas.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by admin on 2018/6/8.
 */
public abstract class BaseActivity extends FragmentActivity {
    private static final String TAG = "BaseActivity";
    protected Context baseContext;
    @BindView(R.id.base_left)
    ImageView baseLeft;
    @BindView(R.id.base_middle)
    TextView baseMiddle;
    @BindView(R.id.base_right)
    ImageView baseRight;
    @BindView(R.id.base_gray_bg)
    RelativeLayout baseGrayBg;
    @BindView(R.id.layout_container)
    ViewFlipper layoutContainer;
    @BindView(R.id.base_bg)
    LinearLayout baseBg;


    private ViewFlipper mContentView;
    ActivityManager activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.ly_layout_base);
        ButterKnife.bind(this);
        activityManager = ActivityManager.getInstance();
        BaseActivity.this.activityManager.pushActivity(this);
        baseContext = this;
        initView();
    }

    private void initView() {
        mContentView = super.findViewById(R.id.layout_container);

    }


    @Override
    public void setContentView(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        mContentView.addView(view, lp);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }


    /**
     * 设置系统返回键
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }
        return false;
    }

    /**
     * 设置头部是否可见
     */

    public void setHeadVisibility(int visibility){
        baseGrayBg.setVisibility(visibility);
    }

    /**
     * 设置左边图标是否可见
     */

    public void setHeadLeftVisibility(int visibility){
        baseLeft.setVisibility(visibility);
    }



    /**
     * 设置右边图标是否可见
     */

    public void setHeadRightVisibility(int visibility){
        baseRight.setVisibility(visibility);
    }



    /**
     * 设置标题
     */

    public void setTitle(String title){
        baseMiddle.setText(title);
    }


    /**
     * 添加get/set方法方便调用
     * @return
     */
    public RelativeLayout getmRelaveLayout() {
        return baseGrayBg;
    }

    public LinearLayout baseLinearLayout() {
        return baseBg;
    }
    public ImageView baseLeftIv() {
        return baseLeft;
    }
    public ImageView baseRightIv() {
        return baseRight;
    }
    public TextView baseMiddleTv() {
        return baseMiddle;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityManager.popActivity(this);

    }


    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

}
