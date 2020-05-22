package com.example.lyfuelgas.model;

import android.content.Context;

import com.example.lyfuelgas.bean.DeviceTypeObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.http.RestClient;
import com.example.lyfuelgas.common.mvp.IModel;

import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DeviceTypeModel extends IModel {
    public DeviceTypeModel(Context context) {
        super(context);
    }

    /**
     * 获取设备类型列表
     * @param subscriber
     */
    public void getList(BaseSubscriber<BaseResponse<ArrayList<DeviceTypeObject>>> subscriber){
            Subscription subscribe = RestClient
                    .getApiService(mContext)
                    .getDeviceTypeList().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
            putSubscription(subscribe);
    }
}
