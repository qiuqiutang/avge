package com.example.lyfuelgas.presenter;

import com.example.lyfuelgas.app.UserManager;
import com.example.lyfuelgas.bean.UserObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.IModel;
import com.example.lyfuelgas.contact.HomeAccountContact;
import com.example.lyfuelgas.fragment.HomeAccountFragment;
import com.example.lyfuelgas.model.AuthorizeModel;
import com.example.lyfuelgas.model.UserModel;

import java.util.HashMap;


public class HomeAccountPresenter extends BasePresenter<HomeAccountFragment> implements HomeAccountContact.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new AuthorizeModel(getIView().mContext),new UserModel(getIView().mContext));
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("authorize", models[0]);
        map.put("user",models[1]);
        return map;
    }


    @Override
    public void logout() {
        ((AuthorizeModel)(mModels.get("authorize"))).logout(new BaseSubscriber<BaseResponse>(getIView().mContext){
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
                    getIView().onLogoutSuccess();
                }

            }
        });
    }

    @Override
    public void getUserInfo() {
        ((UserModel)(mModels.get("user"))).getUserInfo(new BaseSubscriber<BaseResponse<UserObject>>(getIView().mContext){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(BaseResponse<UserObject> userObjectBaseResponse) {
                super.onNext(userObjectBaseResponse);
                if(null != userObjectBaseResponse && userObjectBaseResponse.isSuccess()){
                    UserObject userObject = userObjectBaseResponse.result;
                    userObject.userId = UserManager.getInstance().getUserId();
                    userObject.token = UserManager.getInstance().getUserToken();
                    UserManager.getInstance().setUser(userObject);
                    getIView().onGetUserInfoSuccess(userObjectBaseResponse.result);
                }

            }
        });
    }
}
