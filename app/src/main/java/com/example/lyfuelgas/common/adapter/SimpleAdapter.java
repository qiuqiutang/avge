package com.example.lyfuelgas.common.adapter;


import java.util.List;

/**
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */
public class SimpleAdapter<Data> extends BaseAdapter<Data> {
    private Class<? extends AbstractItem<Data>> mItemHolder;
    private int mIntLayoutId;

    public SimpleAdapter(List<Data> mListData, Class<? extends AbstractItem<Data>> mItemHolder) {
        super(mListData);
        this.mItemHolder = mItemHolder;
        Layout annotation = mItemHolder.getAnnotation(Layout.class);
        if (annotation != null) {
            this.mIntLayoutId = annotation.value();
        }
    }

    @Override
    protected int getLayoutId(int viewType) {
        return mIntLayoutId;
    }

    @Override
    protected Class<? extends AbstractItem<Data>> getLayoutClass(int viewType) {
        return mItemHolder;
    }
}