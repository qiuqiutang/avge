package com.example.lyfuelgas.contact;

import com.example.lyfuelgas.bean.OrderObject;

public class FillContact {
    public interface View{

        /**
         * 添加加注成功
         */
        void onAddFillSuccess();
    }

    public interface Presenter{

        /**
         * 添加加注
         */
        void addFill(OrderObject orderObject, String imei, String capacity, String price, String remark);

    }
}
