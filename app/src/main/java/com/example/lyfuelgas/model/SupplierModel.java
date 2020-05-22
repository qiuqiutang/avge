package com.example.lyfuelgas.model;

import android.content.Context;

import com.example.lyfuelgas.bean.SupplierObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.http.RestClient;
import com.example.lyfuelgas.common.mvp.IModel;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SupplierModel extends IModel {
    public SupplierModel(Context context) {
        super(context);
    }

    /**
     * 添加订单
     * @param subscriber
     */
    public void getSupplier(String supplierId, BaseSubscriber<BaseResponse<SupplierObject>> subscriber){
        Subscription subscribe = RestClient
                .getApiService(mContext)
                .getSupplier(supplierId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        putSubscription(subscribe);
    }

}
