package com.example.lyfuelgas.model;

import android.content.Context;

import com.example.lyfuelgas.bean.CustomerObject;
import com.example.lyfuelgas.bean.RequestObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.http.ResponsePageObject;
import com.example.lyfuelgas.common.http.RestClient;
import com.example.lyfuelgas.common.mvp.IModel;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CustomerModel extends IModel {
    public CustomerModel(Context context) {
        super(context);
    }

    /**
     * 获取客户列表
     * @param requestObject
     * @param subscriber
     */
    public void getList(RequestObject requestObject, BaseSubscriber<BaseResponse<ResponsePageObject<CustomerObject>>> subscriber){
            Subscription subscribe = RestClient
                    .getApiService(mContext)
                    .getCustomerList(requestObject.getMap()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
            putSubscription(subscribe);
    }


}
