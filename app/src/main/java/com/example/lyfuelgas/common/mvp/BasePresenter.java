package com.example.lyfuelgas.common.mvp;


import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */

public abstract class BasePresenter<V extends IView> implements IPresenter {
    public HashMap<String, IModel> mModels;
    private WeakReference mViewRef;
    protected V mView;

    public abstract HashMap<String, IModel> getiModelMap();

    @Override
    public void attachView(IView iView) {
        mViewRef = new WeakReference(iView);
        mView = (V) mViewRef.get();
        mModels = getiModelMap();
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            if(null != mModels) {
                Iterator iter = mModels.keySet().iterator();
                while (iter.hasNext()) {
                    Object key = iter.next();
                    IModel model = mModels.get(key);
                    model.onStop();
                }
            }

            mViewRef.clear();
            mViewRef = null;
        }
    }

    @Override
    public V getIView() {
        if(null != mViewRef && null != mViewRef.get())
            return (V) mViewRef.get();
        if(null != mView)
            return mView;
        return null;
    }

    /**
     * @param models
     * @return
     * 添加多个model,如有需要
     */
    public abstract HashMap<String, IModel> loadModelMap(IModel... models);

}
