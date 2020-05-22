package com.example.lyfuelgas.contact;

import com.example.lyfuelgas.bean.DeviceObject;

public class AvgeFillContact {
    public interface View{
        /**
         * 添加订单成功
         */
        void onAddOrderSuccess();
    }

    public interface Presenter{
        /**
         * 添加订单
         */
        void addOrder(DeviceObject deviceObject,String amount, String bill);

    }
}
