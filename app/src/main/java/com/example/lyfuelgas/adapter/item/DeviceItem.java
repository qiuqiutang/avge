package com.example.lyfuelgas.adapter.item;

import android.view.View;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.view.treerecycleview.base.ViewHolder;
import com.example.lyfuelgas.view.treerecycleview.item.TreeItem;

import androidx.annotation.NonNull;

/**
 * Created on 2020/4/30.
 *
 * @author QiuYan
 * @since 1.0.0
 */
public class DeviceItem extends TreeItem<DeviceObject> {


    @Override
    public int getLayoutId() {
        return R.layout.item_device;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder) {
        holder.setText(R.id.tvName, "编号:"+data.code+"\nimei:"+data.imei);
        holder.setVisible(R.id.ivClosed, isEdit());
        holder.getImageView(R.id.ivClosed).setSelected(1 == data.testStatus);
        holder.setOnClickListener(R.id.ivClosed, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInnerCallBack(v);
            }

        });
    }


}
