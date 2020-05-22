package com.example.lyfuelgas.common.adapter;

/**
 * adapter item interface
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */
interface BaseItem<T> {
    /**
     * set common data
     *
     * @param object object
     */
    void setExcessiveObj(Object object);


    /**
     * bind data
     *
     * @param t     data
     * @param index data index
     */
    void setData(T t, int index);

}