package com.example.lyfuelgas.model;

import android.content.Context;

import com.example.lyfuelgas.bean.MeasureObject;
import com.example.lyfuelgas.bean.RequestObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.http.ResponsePageObject;
import com.example.lyfuelgas.common.http.RestClient;
import com.example.lyfuelgas.common.mvp.IModel;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MeasureModel extends IModel {
    public MeasureModel(Context context) {
        super(context);
    }

    /**
     * 获取设备列表
     * @param requestObject
     * @param subscriber
     */
    public void getList(RequestObject requestObject, BaseSubscriber<BaseResponse<ResponsePageObject<MeasureObject>>> subscriber){
            Subscription subscribe = RestClient
                    .getApiService(mContext)
                    .getMeasureList(requestObject.getMap()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
            putSubscription(subscribe);
    }


}
