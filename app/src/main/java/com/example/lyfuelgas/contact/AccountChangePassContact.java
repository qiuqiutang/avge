package com.example.lyfuelgas.contact;

public class AccountChangePassContact {
    public interface View{

        /**
         * 修改密码成功
         */
        void updateSuccess();
    }

    public interface Presenter{

        /**
         * 修改密码
         */
        void updatePassword(String password, String oldPassword);

    }
}
