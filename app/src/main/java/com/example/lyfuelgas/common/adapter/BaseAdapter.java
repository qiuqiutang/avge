package com.example.lyfuelgas.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lyfuelgas.R;

import java.lang.reflect.Constructor;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */

public abstract class BaseAdapter<Data> extends RecyclerView.Adapter<AbstractItem<Data>> {
    List<Data> mList;
    private Object mExcessiveData;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClick;
    private OnItemLongClickListener mOnLongItemClick;

    BaseAdapter(List<Data> mList) {
        this.mList = mList;
    }

    public void setExcessiveData(Object mExcessiveData) {
        this.mExcessiveData = mExcessiveData;
    }

    /**
     * 设置item点击事件
     *
     * @param itemClick itemClick
     */
    public void setOnItemClick(OnItemClickListener itemClick) {
        this.mOnItemClick = itemClick;
    }

    /**
     * 长按
     * @param mOnLongItemClick
     */
    public void setmOnLongItemClick(OnItemLongClickListener mOnLongItemClick) {
        this.mOnLongItemClick = mOnLongItemClick;
    }

    @Override
    public AbstractItem<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(getLayoutId(viewType), parent, false);
        AbstractItem<Data> item = null;
        try {
            Constructor<? extends AbstractItem<Data>> declaredConstructor = getLayoutClass(viewType).getDeclaredConstructor(View.class);
            item = declaredConstructor.newInstance(view);
            item.setExcessiveObj(mExcessiveData);
        } catch (Exception e) {
            return item;
        }
        return item;
    }

    @Override
    public void onBindViewHolder(AbstractItem<Data> holder, final int position) {
        Data data = mList.get(position);
        holder.itemView.setTag(data);
        holder.setData(data, position);

        if (mOnItemClick != null) {
            holder.itemView.setTag(R.id.item_click, holder);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != mOnItemClick){
                        mOnItemClick.onItemClick(v, position);
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(null != mOnLongItemClick){
                        mOnLongItemClick.onItemLongClick(view,position);
                    }
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    /**
     * 获取布局xml
     *
     * @param viewType viewType
     * @return layout id
     */
    protected abstract int getLayoutId(int viewType);

    /**
     * 获取布局适配器
     *
     * @param viewType viewType
     * @return layout class
     */
    protected abstract Class<? extends AbstractItem<Data>> getLayoutClass(int viewType);

    public interface OnItemClickListener {
        void onItemClick(View item, int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View item, int position);
    }
}