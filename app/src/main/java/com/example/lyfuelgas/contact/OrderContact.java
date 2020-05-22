package com.example.lyfuelgas.contact;

import com.example.lyfuelgas.bean.OrderObject;

import java.util.ArrayList;

public class OrderContact {
    public interface View{
        void onGetOrderListFail();

        /**
         * 获取设备列表成功
         */
        void onGetOrderListSuccess(ArrayList<OrderObject> data, boolean hasNext);
    }

    public interface Presenter{

        /**
         * 获取订单列表
         */
        void getOrderList(int page, int status);

    }
}
