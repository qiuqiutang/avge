package com.example.lyfuelgas.model;

import android.content.Context;

import com.example.lyfuelgas.bean.OrderObject;
import com.example.lyfuelgas.bean.RequestObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.http.ResponsePageObject;
import com.example.lyfuelgas.common.http.RestClient;
import com.example.lyfuelgas.common.mvp.IModel;

import java.util.ArrayList;
import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrderModel extends IModel {
    public OrderModel(Context context) {
        super(context);
    }

    /**
     * 添加订单
     * @param request
     * @param subscriber
     */
    public void addOrder(Map<String, Object> request, BaseSubscriber<BaseResponse> subscriber){
        Subscription subscribe = RestClient
                .getApiService(mContext)
                .addOrder(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        putSubscription(subscribe);
    }


    /**
     * 获取订单列表
     * @param requestObject
     * @param subscriber
     */
    public void getList(RequestObject requestObject, BaseSubscriber<BaseResponse<ResponsePageObject<OrderObject>>> subscriber){
        Subscription subscribe = RestClient
                .getApiService(mContext)
                .getOrderList(requestObject.getMap()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        putSubscription(subscribe);
    }

    /**
     * 获取待配送的所有订单
     * @param requestObject
     * @param subscriber
     */
    public void getAllDeliverOrder(RequestObject requestObject, BaseSubscriber<BaseResponse<ArrayList<OrderObject>>> subscriber){
        Subscription subscribe = RestClient
                .getApiService(mContext)
                .getOrderListNoPage(requestObject.getMap()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        putSubscription(subscribe);
    }
}
