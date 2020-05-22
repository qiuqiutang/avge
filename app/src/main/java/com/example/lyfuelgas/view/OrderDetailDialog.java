package com.example.lyfuelgas.view;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.bean.OrderObject;
import com.example.lyfuelgas.common.utils.DateUtils;
import com.example.lyfuelgas.common.utils.DensityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by QiuYan on 2018/8/25.
 */

public class OrderDetailDialog {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvMobile)
    TextView tvMobile;
    @BindView(R.id.ivStatus)
    ImageView ivStatus;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvLiquid)
    TextView tvLiquid;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvFillTime)
    TextView tvFillTime;
    @BindView(R.id.ivTel)
    ImageView ivTel;
    @BindView(R.id.tvFill)
    TextView tvFill;

    private Activity context;
    private View parentView;
    private PopupWindow popupWindow = null;
    private WindowManager.LayoutParams layoutParams = null;
    private LayoutInflater layoutInflater = null;

    private OrderObject orderObject;

    private OnInnerListener onInnerListener = null;

    public void setOnInnerListener(OnInnerListener onInnerListener) {
        this.onInnerListener = onInnerListener;
    }


    public OrderDetailDialog(Activity context) {
        this.context = context;
        init();
    }

    private void init() {
        layoutParams = context.getWindow().getAttributes();
        layoutInflater = context.getLayoutInflater();
        initView();
        initPopupWindow();
    }

    private void initView() {
        parentView = layoutInflater.inflate(R.layout.dialog_deliver_order, null);
        ButterKnife.bind(this, parentView);

    }

    private void initPopupWindow() {
        popupWindow = new PopupWindow(parentView, WindowManager.LayoutParams.MATCH_PARENT, (int) (DensityUtils.getScreenHeight(context) * (2.0 / 5)));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setAnimationStyle(R.style.AnimBottom);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                layoutParams.alpha = 1.0f;
                context.getWindow().setAttributes(layoutParams);
                popupWindow.dismiss();
            }
        });
    }



    public void show(View v) {
        //layoutParams.alpha = 0.6f;
       // context.getWindow().setAttributes(layoutParams);
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, v.getHeight()+1);
    }

    public void setData(OrderObject data) {
        this.orderObject = data;
        refreshView();

    }

    private void refreshView(){
        tvAddress.setText(orderObject.address);
        tvAmount.setText(orderObject.bill);
        tvLiquid.setText(orderObject.amount);
        tvName.setText(orderObject.contact);
        tvMobile.setText(orderObject.customerMobile);
        StringBuilder sbOrderInfo = new StringBuilder();
        if(!TextUtils.isEmpty(orderObject.amount)){
            sbOrderInfo.append(String.format("下单容量：%sL",orderObject.amount));
            sbOrderInfo.append("\n");
        }
        if(!TextUtils.isEmpty(orderObject.bill)){
            sbOrderInfo.append(String.format("下单金额：%sL",orderObject.bill));
            sbOrderInfo.append("\n");
        }
        sbOrderInfo.append(String.format("下单时间：%s", DateUtils.formatDateTotal(orderObject.createTime)));
        tvTime.setText(sbOrderInfo.toString());
        ivStatus.setSelected(1 == orderObject.status);
        tvFillTime.setVisibility(0 == orderObject.status ? View.GONE : View.VISIBLE);
        StringBuilder sbFillInfo = new StringBuilder();
        if(!TextUtils.isEmpty(orderObject.fillAmount)){
            sbFillInfo.append(String.format("加注容量：%sL",orderObject.fillAmount));
            sbFillInfo.append("\n");
        }
        if(!TextUtils.isEmpty(orderObject.fillBill)){
            sbFillInfo.append(String.format("加注金额：%sL",orderObject.fillBill));
            sbFillInfo.append("\n");
        }
        sbFillInfo.append(String.format("加注时间：%s",DateUtils.formatDateTotal(orderObject.fillTime)));
        tvFillTime.setText(sbFillInfo.toString());
        tvFill.setVisibility(0 == orderObject.status ? View.VISIBLE : View.GONE);
    }


    @OnClick({R.id.ivCancle, R.id.tvFill, R.id.ivTel})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivCancle:
                popupWindow.dismiss();
                break;
            case R.id.tvFill:
                if(null != onInnerListener){
                    onInnerListener.onSubmit(orderObject);
                    dismiss();
                }
                break;
            case R.id.ivTel:
                if(null != onInnerListener){
                    onInnerListener.onCall(orderObject.customerMobile);
                }
                break;
        }
    }

    public void dismiss(){
        popupWindow.dismiss();
    }



    public interface OnInnerListener{
        void onCall(String strMobile);
        void onSubmit(OrderObject orderObject);
    }


}
