package com.example.lyfuelgas.presenter;

import android.text.TextUtils;

import com.example.lyfuelgas.activity.SplashActivity;
import com.example.lyfuelgas.app.UserManager;
import com.example.lyfuelgas.bean.UserObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.IModel;
import com.example.lyfuelgas.common.utils.SPUtils;
import com.example.lyfuelgas.constant.SPConstant;
import com.example.lyfuelgas.contact.SplashContact;
import com.example.lyfuelgas.model.AuthorizeModel;

import java.util.HashMap;


public class SplashPresenter extends BasePresenter<SplashActivity> implements SplashContact.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new AuthorizeModel(getIView()));
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("authorize", models[0]);
        return map;
    }


    @Override
    public void checkLogin() {
        String auto = (String) SPUtils.get(getIView(), SPConstant.LOGIN_AUTO,"");
        String username = (String) SPUtils.get(getIView(), SPConstant.LOGIN_USERNAME,"");
        String password = (String) SPUtils.get(getIView(), SPConstant.LOGIN_PASSWORD,"");
        if(!TextUtils.isEmpty(auto) && UserManager.getInstance().isLogin() && !TextUtils.isEmpty(username) && ! TextUtils.isEmpty(password)){
            refreshToken(username,password);
        }else {
            getIView().loginFail();
        }
    }

    @Override
    public void refreshToken(String username, String password) {
        ((AuthorizeModel)(mModels.get("authorize"))).login(username,password,new BaseSubscriber<BaseResponse<UserObject>>(getIView()){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(BaseResponse<UserObject> arrayListResponse) {
                super.onNext(arrayListResponse);
                if(null != arrayListResponse && arrayListResponse.isSuccess()){
                    getIView().loginSuccess(arrayListResponse.result);
                }

            }
        });
    }

}
