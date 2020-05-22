package com.example.lyfuelgas.presenter;

import com.example.lyfuelgas.activity.AccountChangePassActivity;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.IModel;
import com.example.lyfuelgas.contact.AccountChangePassContact;
import com.example.lyfuelgas.model.UserModel;

import java.util.HashMap;


public class AccountChangePassPresenter extends BasePresenter<AccountChangePassActivity> implements AccountChangePassContact.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new UserModel(getIView().mContext));
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("user", models[0]);
        return map;
    }



    @Override
    public void updatePassword(String password, String oldPassword) {
        ((UserModel)(mModels.get("user"))).updatePassword(password,oldPassword,new BaseSubscriber<BaseResponse>(getIView()){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(BaseResponse arrayListResponse) {
                super.onNext(arrayListResponse);
                if(null != arrayListResponse && arrayListResponse.isSuccess()){
                    getIView().updateSuccess();
                }

            }
        });
    }
}
