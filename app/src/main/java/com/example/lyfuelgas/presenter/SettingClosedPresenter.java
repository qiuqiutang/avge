package com.example.lyfuelgas.presenter;

import com.example.lyfuelgas.activity.SettingClosedActivity;
import com.example.lyfuelgas.common.http.ApiCode;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.IModel;
import com.example.lyfuelgas.contact.SettingClosedContact;
import com.example.lyfuelgas.model.CustomerModel;

import java.util.HashMap;

import retrofit2.Response;

/**
 * Created by QiuYan on 2018/8/23.
 */

public class SettingClosedPresenter extends BasePresenter<SettingClosedActivity> implements SettingClosedContact.Presenter{
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new CustomerModel(getIView()));
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("customer",models[0]);
        return map;

    }



    @Override
    public void getStatus() {
        ((CustomerModel)(mModels.get("customer"))).getClosedStatus(new BaseSubscriber<BaseResponse<Integer>>(getIView()){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(BaseResponse<Integer> integerBaseResponse) {
                super.onNext(integerBaseResponse);
                if(integerBaseResponse.isSuccess()){
                    getIView().onGetStatusSuccess(integerBaseResponse.result);
                }
            }
        });
    }


    @Override
    public void setStatus(final int sign) {
        //0不接收/1接收
        ((CustomerModel)(mModels.get("customer"))).setClosedStatus(sign,new BaseSubscriber<BaseResponse>(getIView()){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(BaseResponse baseResponse) {
                super.onNext(baseResponse);
                if(baseResponse.isSuccess()){
                    getIView().onSetStatusSuccess();
                }
            }
        });
    }

}


