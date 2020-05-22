package com.example.lyfuelgas.contact;

import com.example.lyfuelgas.bean.AuthenticationObject;
import com.example.lyfuelgas.bean.UserObject;

public class LoginContact {
    public interface View{
        /**
         * 未登录，去登录
         */
        void loginFail();

        /**
         * 已登录，进入主页
         */
        void loginSuccess(UserObject data);

        /**
         * 获取权限成功
         * @param data
         */
        void onGetRoleSuccess(AuthenticationObject data);

        /**
         * 获取用户信息成功
         */
        void onGetUserInfoSuccess();
    }

    public interface Presenter{

        /**
         * 登录
         */
        void login(String username, String password);

        /**
         * 获取权限信息
         */
        void getRole();

        /**
         * 获取用户信息
         */
        void getUserInfo();

    }
}
