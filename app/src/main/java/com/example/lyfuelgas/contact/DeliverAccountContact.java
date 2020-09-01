package com.example.lyfuelgas.contact;

import com.example.lyfuelgas.bean.UserObject;

public class DeliverAccountContact {
    public interface View{

        /**
         * 退出登录成功
         */
        void onLogoutSuccess();

        /**
         * 获取用户信息成功
         * @param data
         */
        void onGetUserInfoSuccess(UserObject data);
    }

    public interface Presenter{

        /**
         * 退出登录
         */
        void logout();

        /**
         * 获取用户信息
         */
        void getUserInfo();

    }
}
