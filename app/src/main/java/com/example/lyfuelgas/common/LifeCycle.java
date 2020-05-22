package com.example.lyfuelgas.common;

/**
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */

public interface LifeCycle {
    /**
     * 启动
     */
    void onStart();

    /**
     * 销毁
     */
    void onStop();
}