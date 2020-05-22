package com.example.lyfuelgas.model;

import android.content.Context;

import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.http.RestClient;
import com.example.lyfuelgas.common.mvp.IModel;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DeviceModel extends IModel {
    public DeviceModel(Context context) {
        super(context);
    }

    /**
     * 获取设备列表
     * @param subscriber
     */
    public void getList(BaseSubscriber<BaseResponse<ArrayList<DeviceObject>>> subscriber){
            Subscription subscribe = RestClient
                    .getApiService(mContext)
                    .getDeviceList().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
            putSubscription(subscribe);
    }


    /**
     * 获取设备详情
     * @param id
     * @param subscriber
     */
    public void getDetail(String id, BaseSubscriber<BaseResponse<DeviceObject>> subscriber){
        Subscription subscribe = RestClient
                .getApiService(mContext)
                .getDeviceDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        putSubscription(subscribe);
    }


}
