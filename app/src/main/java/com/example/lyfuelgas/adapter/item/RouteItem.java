package com.example.lyfuelgas.adapter.item;

import android.view.View;
import android.widget.TextView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.bean.OrderObject;
import com.example.lyfuelgas.common.adapter.AbstractItem;
import com.example.lyfuelgas.common.adapter.Layout;

import butterknife.BindView;

@Layout(value = R.layout.item_route)
public class RouteItem extends AbstractItem<OrderObject> {
    @BindView(R.id.tvNum)
    TextView tvNum;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    public RouteItem(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(OrderObject orderObject, int index) {
        if(null != orderObject){
            tvNum.setText(String.format("%d.",(index+1)));
            tvName.setText(orderObject.customerName);
            tvAddress.setText(orderObject.address);
        }
    }
}
