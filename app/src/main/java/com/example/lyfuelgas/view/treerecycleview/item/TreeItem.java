package com.example.lyfuelgas.view.treerecycleview.item;

import android.graphics.Rect;
import android.view.View;

import com.example.lyfuelgas.view.treerecycleview.base.ViewHolder;
import com.example.lyfuelgas.view.treerecycleview.manager.ItemManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * TreeRecyclerAdapter的item
 */
public abstract class TreeItem<D> {
    /**
     * 当前item的数据
     */
    protected D data;
    private TreeItemGroup parentItem;
    /**
     * item在每行中的spansize
     * 默认为0,如果为0则占满一行
     *
     * @return 所占值, 比如recyclerview的列数为6, item需要占一半宽度, 就设置3
     */
    private int spanSize;
    private ItemManager mItemManager;
    InnerCallbackListener mInnerCallbackListener;

    public interface InnerCallbackListener{
        void onClick(View v, TreeItem item);
        boolean isEdit();
    }

    public void setParentItem(TreeItemGroup parentItem) {
        this.parentItem = parentItem;
    }

    /**
     * 获取当前item的父级
     *
     * @return
     */
    @Nullable
    public TreeItemGroup getParentItem() {
        return parentItem;
    }

    /**
     * 应该在void onBindViewHolder(ViewHolder viewHolder)的地方使用.
     * 如果要使用,可能为null,请加判断.
     *
     * @return
     */
    public ItemManager getItemManager() {
        return mItemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        mItemManager = itemManager;
    }

    /**
     * 该条目的布局id
     *
     * @return 布局id
     */
    public abstract int getLayoutId();

    /**
     * 觉得item的所占比例
     *
     * @return , 如果设置的列数为6, 返回3, 则代表item占1半宽度
     */
    @Deprecated
    public int getSpanSize() {
        return 0;
    }

    /**
     * @param maxSpan 总数
     * @return
     */
    public int getSpanSize(int maxSpan) {
        return spanSize;
    }


    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    /**
     * 设置当前条目间隔
     *
     * @param outRect
     * @param layoutParams
     * @param position
     */
    public void getItemOffsets(@NonNull Rect outRect, RecyclerView.LayoutParams layoutParams, int position) {

    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    /**
     * 抽象holder的绑定
     */
    public abstract void onBindViewHolder(@NonNull ViewHolder viewHolder);

    /**
     * 当前条目的点击回调
     */
    public void onClick(ViewHolder viewHolder) {

    }

    /**
     * 当前条目的点击回调
     */
    public void setmInnerCallbackListener(InnerCallbackListener innerCallbackListener) {
        this.mInnerCallbackListener = innerCallbackListener;
    }


    public void onInnerCallBack(View v){
        if(null != mInnerCallbackListener){
            mInnerCallbackListener.onClick(v,this);
        }
    }


    /**
     * 是否显示备注
     * @return
     */
    public boolean isEdit(){
        if(null != mInnerCallbackListener){
            return mInnerCallbackListener.isEdit();
        }
        return false;
    }
}
