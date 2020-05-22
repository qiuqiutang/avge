package com.example.lyfuelgas.model;

import android.content.Context;

import com.example.lyfuelgas.bean.UserObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.http.RestClient;
import com.example.lyfuelgas.common.mvp.IModel;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserModel extends IModel {
    public UserModel(Context context) {
        super(context);
    }

    /**
     * 获取用户自己的信息
     * @param subscriber
     */
    public void getUserInfo(BaseSubscriber<BaseResponse<UserObject>> subscriber){
        Subscription subscribe = RestClient
                .getApiService(mContext)
                .getUserInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        putSubscription(subscribe);
    }

    /**
     * 登录状态下修改用户密码
     * @param newPassword
     * @param oldPassword
     * @param subscriber
     */
    public void updatePassword(String newPassword, String oldPassword, BaseSubscriber<BaseResponse> subscriber){
        Subscription subscribe = RestClient
                .getApiService(mContext)
                .updatePassword(newPassword,oldPassword).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        putSubscription(subscribe);
    }
}
