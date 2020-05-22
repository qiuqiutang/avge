package com.example.lyfuelgas.model;

import android.content.Context;

import com.example.lyfuelgas.bean.AuthenticationObject;
import com.example.lyfuelgas.bean.UserObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.http.RestClient;
import com.example.lyfuelgas.common.mvp.IModel;

import java.util.HashMap;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AuthorizeModel extends IModel {
    public AuthorizeModel(Context context) {
        super(context);
    }


    /**
     * 登录
     * @param subscriber
     */
    public void login(String username, String password, BaseSubscriber<BaseResponse<UserObject>> subscriber){
        HashMap<String,Object> request = new HashMap<>();
        request.put("username",username);
        request.put("password",password);
        request.put("token_type","jwt");
        Subscription subscribe = RestClient
                .getApiService(mContext)
                .login(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        putSubscription(subscribe);
    }

    /**
     * 退出登录
     * @param subscriber
     */
    public void logout(BaseSubscriber<BaseResponse> subscriber){
        Subscription subscribe = RestClient
                .getApiService(mContext)
                .logout().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        putSubscription(subscribe);
    }

    /**
     * 获取权限信息
     * @param subscriber
     */
    public void getRole(BaseSubscriber<BaseResponse<AuthenticationObject>> subscriber){
        Subscription subscribe = RestClient
                .getApiService(mContext)
                .getRole().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        putSubscription(subscribe);

    }


}
