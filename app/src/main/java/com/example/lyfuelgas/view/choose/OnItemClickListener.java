package com.example.lyfuelgas.view.choose;

import android.view.View;

/**
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */

public interface OnItemClickListener<T> {
    void onItemClick(T data, View view, int position);
}
