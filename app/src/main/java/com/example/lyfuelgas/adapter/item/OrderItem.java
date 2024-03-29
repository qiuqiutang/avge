package com.example.lyfuelgas.adapter.item;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.bean.OrderObject;
import com.example.lyfuelgas.common.adapter.AbstractItem;
import com.example.lyfuelgas.common.adapter.Layout;
import com.example.lyfuelgas.common.utils.DateUtils;

import butterknife.BindView;

@Layout(value = R.layout.item_order)
public class OrderItem extends AbstractItem<OrderObject> {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvMobile)
    TextView tvMobile;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
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

    private OnExcessiveListener mListener;

    public OrderItem(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(OrderObject o, int index) {
        tvAddress.setText(o.equipAddress);
        tvAmount.setText(o.bill);
        tvLiquid.setText(o.amount);
        tvName.setText(o.contact);
        tvMobile.setText(o.customerMobile);
        StringBuilder sbOrderInfo = new StringBuilder();
        if(!TextUtils.isEmpty(o.amount)){
            sbOrderInfo.append(String.format("下单容量：%sL",o.amount));
            sbOrderInfo.append("\n");
        }
        if(!TextUtils.isEmpty(o.bill)){
            sbOrderInfo.append(String.format("下单金额：%s元",o.bill));
            sbOrderInfo.append("\n");
        }
        sbOrderInfo.append(String.format("下单时间：%s",DateUtils.formatDateTotal(o.createTime)));
        tvTime.setText(sbOrderInfo.toString());
        tvStatus.setText(0 == o.status ? "未完成" : "已完成");
        tvFillTime.setVisibility(0 == o.status ? View.GONE : View.VISIBLE);
        StringBuilder sbFillInfo = new StringBuilder();
        if(!TextUtils.isEmpty(o.fillAmount)){
            sbFillInfo.append(String.format("加注容量：%sL",o.fillAmount));
            sbFillInfo.append("\n");
        }
        if(!TextUtils.isEmpty(o.fillBill)){
            sbFillInfo.append(String.format("加注金额：%s元",o.fillBill));
            sbFillInfo.append("\n");
        }
        sbFillInfo.append(String.format("加注时间：%s",DateUtils.formatDateTotal(o.fillTime)));
        tvFillTime.setText(sbFillInfo.toString());
        ivTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mListener){
                    mListener.onCallSupplier(o);
                }
            }
        });
    }

    @Override
    public void setExcessiveObj(Object object) {
        super.setExcessiveObj(object);
        this.mListener = (OnExcessiveListener) object;
    }

    public interface OnExcessiveListener{
        void onCallSupplier(OrderObject orderObject);
    }
}
