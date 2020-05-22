package com.example.lyfuelgas.adapter.item;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.bean.DeviceTypeObject;
import com.example.lyfuelgas.view.treerecycleview.base.ViewHolder;
import com.example.lyfuelgas.view.treerecycleview.factory.ItemHelperFactory;
import com.example.lyfuelgas.view.treerecycleview.item.TreeItem;
import com.example.lyfuelgas.view.treerecycleview.item.TreeItemGroup;
import com.example.lyfuelgas.view.treerecycleview.manager.ItemManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 设备分类标题
 */
public class DeviceTypeItem extends TreeItemGroup<DeviceTypeObject> {
    @Nullable
    @Override
    protected List<TreeItem> initChildList(DeviceTypeObject data) {
        return ItemHelperFactory.createItems(data.deviceList, this);
    }

    @Override
    protected void onExpand() {
        ItemManager itemManager = getItemManager();
        if (itemManager != null) {
            int itemPosition = itemManager.getItemPosition(this);
            List datas = itemManager.getAdapter().getDatas();
            datas.addAll(itemPosition + 1, getExpandChild());
            itemManager.getAdapter().notifyItemRangeInserted(itemPosition + 1, getExpandChild().size());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_device_type;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {
        viewHolder.setText(R.id.tvName, data.name);
        viewHolder.getImageView(R.id.ivArrow).setSelected(isExpand());

    }
}
