package com.example.lyfuelgas.common.mvp;

/**
 * mvp之P
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */

public interface IPresenter<V extends IView> {

    /**
     * @param view 绑定
     */
    void attachView(V view);


    /**
     * 防止内存的泄漏,清楚presenter与activity之间的绑定
     */
    void detachView();


    /**
     *
     * @return 获取View
     */
    IView getIView();

}
