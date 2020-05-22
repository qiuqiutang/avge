package com.example.lyfuelgas.contact;

import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.bean.MeasureObject;

public class HomeAvgeContact {
    public interface View{
        /**
         * 获取信息成功
         * @param data
         */
        void onGetMeasureInfoSuccess(MeasureObject data);

        /**
         * 获取设备成功
         * @param data
         */
        void onGetDeviceInfoSuccess(DeviceObject data);

    }

    public interface Presenter{
        /**
         * 获取设备信息
         */
        void getMeasureInfo(String imei);

        /**
         * 获取customer信息
         */
        void getCustomer();

        /**
         * 获取供应商id
         * @param supplierId
         */
        void getSupplier(String supplierId);

        /**
         * 获取设备详情
         * @param id
         */
        void getDeviceInfo(String id);

    }
}
