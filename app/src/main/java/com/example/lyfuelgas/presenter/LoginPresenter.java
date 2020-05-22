package com.example.lyfuelgas.presenter;

import com.example.lyfuelgas.activity.LoginActivity;
import com.example.lyfuelgas.app.UserManager;
import com.example.lyfuelgas.bean.AuthenticationObject;
import com.example.lyfuelgas.bean.UserObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.IModel;
import com.example.lyfuelgas.contact.LoginContact;
import com.example.lyfuelgas.model.AuthorizeModel;
import com.example.lyfuelgas.model.UserModel;

import java.util.HashMap;


public class LoginPresenter extends BasePresenter<LoginActivity> implements LoginContact.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new AuthorizeModel(getIView()),new UserModel(getIView()));
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("authorize", models[0]);
        map.put("user",models[1]);
        return map;
    }


    @Override
    public void login(String username, String password) {
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

    @Override
    public void getRole() {
        ((AuthorizeModel)(mModels.get("authorize"))).getRole(new BaseSubscriber<BaseResponse<AuthenticationObject>>(getIView()){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(BaseResponse<AuthenticationObject> authenticationObjectBaseResponse) {
                super.onNext(authenticationObjectBaseResponse);
                if(null != authenticationObjectBaseResponse && authenticationObjectBaseResponse.isSuccess()){
                    getIView().onGetRoleSuccess(authenticationObjectBaseResponse.result);
                }
            }
        });
    }

    @Override
    public void getUserInfo() {
        ((UserModel)(mModels.get("user"))).getUserInfo(new BaseSubscriber<BaseResponse<UserObject>>(getIView()){
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
                    getIView().onGetUserInfoSuccess();
                }
            }
        });
    }
}
