package com.example.lyfuelgas.common.adapter;

import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.List;

/**
 * Created on 2018/9/5.
 *
 * @author QiuYan
 * @since 1.0.0
 */
public class TypeAdapter extends BaseAdapter<IType> {
    /**
     * 适配器模型
     */
    private SparseArray<Class<? extends AbstractItem<IType>>> mClazzs;
    /**
     * 对应适配器的布局文件
     */
    private SparseIntArray mLayouts;

    public TypeAdapter(List<IType> mList) {
        super(mList);
        mClazzs = new SparseArray<>();
        mLayouts = new SparseIntArray();
    }

    /**
     * add item
     *
     * @param item item
     */
    public void addItem(Class<? extends AbstractItem<? extends IType>> item) {
        Layout annotation = item.getAnnotation(Layout.class);
        //noinspection unchecked
        mClazzs.append(annotation.type(), (Class<? extends AbstractItem<IType>>) item);
        mLayouts.append(annotation.type(), annotation.value());
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    protected int getLayoutId(int viewType) {
        return mLayouts.get(viewType);
    }

    @Override
    protected Class<? extends AbstractItem<IType>> getLayoutClass(int viewType) {
        return mClazzs.get(viewType);
    }

}