package com.example.lyfuelgas.contact;

import com.example.lyfuelgas.bean.UserObject;

public class SplashContact {
    public interface View{
        /**
         * 未登录，去登录
         */
        void loginFail();

        /**
         * 已登录，进入主页
         */
        void loginSuccess(UserObject data);
    }

    public interface Presenter{

        /**
         * 判断是否登录
         */
        void checkLogin();

        /**
         * 刷新token
         */
        void refreshToken(String username, String password);
    }
}
