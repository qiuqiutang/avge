package com.example.lyfuelgas.contact;


/**
 * Created by QiuYan on 2018/8/23.
 */

public class SettingClosedContact {
    public interface View{
        /**
         * 成功获取状态
         */
        void onGetStatusSuccess(int status);

        /**
         * 成功设置状态
         */
        void onSetStatusSuccess();
    }

    public interface Presenter{
        /**
         * 获取推送状态
         */
        void getStatus();

        /**
         * 修改状态
         * @param sign
         */
        void setStatus(int sign);
    }
}
