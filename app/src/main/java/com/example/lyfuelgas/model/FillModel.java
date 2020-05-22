package com.example.lyfuelgas.model;

import android.content.Context;

import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.http.RestClient;
import com.example.lyfuelgas.common.mvp.IModel;

import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FillModel extends IModel {
    public FillModel(Context context) {
        super(context);
    }

    /**
     * 添加fill
     * @param request
     * @param subscriber
     */
    public void addFill(Map<String,Object> request, BaseSubscriber<BaseResponse> subscriber){
            Subscription subscribe = RestClient
                    .getApiService(mContext)
                    .addFill(request).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
            putSubscription(subscribe);
    }


}
