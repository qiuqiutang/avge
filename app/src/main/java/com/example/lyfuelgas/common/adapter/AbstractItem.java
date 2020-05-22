package com.example.lyfuelgas.common.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

/**
 * RecyclerView item
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */
public abstract class AbstractItem<D> extends RecyclerView.ViewHolder implements BaseItem<D> {
    public AbstractItem(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setExcessiveObj(Object object) {
    }

}