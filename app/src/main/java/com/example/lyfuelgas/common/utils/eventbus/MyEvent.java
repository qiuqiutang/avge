package com.example.lyfuelgas.common.utils.eventbus;

/**
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */

public class MyEvent<T> {
    public int enventType;
    public T object;

    public MyEvent(int type) {
        this.enventType = type;
    }

    public MyEvent(int type, T obj) {
        this.enventType = type;
        this.object = obj;
    }
}
