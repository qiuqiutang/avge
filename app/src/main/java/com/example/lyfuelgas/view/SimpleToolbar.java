package com.example.lyfuelgas.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lyfuelgas.R;


/**
 * Created by QiuYan on 2020/4/28.
 */

public class SimpleToolbar extends RelativeLayout {

    /**
     * 左侧Title
     */
    private ImageView mTxtLeftTitle;

    /**
     * 左侧Title
     */
    private ImageView mivRightTitle;
    /**
     * 中间Title
     */
    private TextView mTxtMiddleTitle;
    /**
     * 右侧Title
     */
    private TextView mTxtRightTitle;

    public SimpleToolbar(Context context) {
        this(context,null);
    }

    public SimpleToolbar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.include_common_header, this);
        initView();
    }

    protected void initView() {
        mTxtLeftTitle = (ImageView) findViewById(R.id.txt_left_title);
        setLeftTitleDrawable(R.drawable.ico_back);
        mTxtMiddleTitle = (TextView) findViewById(R.id.txt_main_title);
        mTxtRightTitle = (TextView) findViewById(R.id.txt_right_title);
        mivRightTitle = (ImageView) findViewById(R.id.iv_right_title);
    }

    //设置中间title的内容
    public void setMainTitle(String text) {
        mTxtMiddleTitle.setVisibility(View.VISIBLE);
        mTxtMiddleTitle.setText(text);
        mTxtMiddleTitle.setSelected(true);
    }

    //设置中间title的内容文字的颜色
    public void setMainTitleColor(int color) {
        mTxtMiddleTitle.setTextColor(color);
    }

    public void setMainTitleSize(int unit, float fontSize){
        mTxtMiddleTitle.setTextSize(unit,fontSize);
    }


    //设置title左边图标
    public void setLeftTitleDrawable(int res) {
        mTxtLeftTitle.setImageResource(res);
    }
    //设置title左边点击事件
    public void setLeftTitleClickListener(OnClickListener onClickListener){
        mTxtLeftTitle.setOnClickListener(onClickListener);
    }


    //设置title右边文字
    public void setRightTitleText(String text) {
        mTxtRightTitle.setVisibility(View.VISIBLE);
        mivRightTitle.setVisibility(GONE);
        mTxtRightTitle.setText(text);
    }

    //设置title右边文字颜色
    public void setRightTitleColor(int color) {
        mTxtRightTitle.setTextColor(color);
    }

    //设置title右边图标
    public void setRightTitleDrawable(int res) {
        mivRightTitle.setImageResource(res);
        mivRightTitle.setVisibility(VISIBLE);
        mTxtRightTitle.setVisibility(GONE);
        /*Drawable dwRight = ContextCompat.getDrawable(getContext(), res);
        dwRight.setBounds(0, 0, dwRight.getMinimumWidth(), dwRight.getMinimumHeight());
        mTxtRightTitle.setCompoundDrawables(null, null, dwRight, null);*/
    }

    public void setLeftTitleVisible(boolean isVisible){
        mTxtLeftTitle.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void setRightTitleVisible(boolean isVisible){
        mTxtRightTitle.setVisibility(isVisible ? VISIBLE : GONE);
        mivRightTitle.setVisibility(isVisible ? VISIBLE : GONE);
    }

    //设置title右边点击事件
    public void setRightTitleClickListener(OnClickListener onClickListener){
        mTxtRightTitle.setOnClickListener(onClickListener);
        mivRightTitle.setOnClickListener(onClickListener);
    }

    /**
     * 获取中间标题的控件，便于自定义
     * @return
     */
    public TextView getTitleView(){
        return mTxtMiddleTitle;
    }

}