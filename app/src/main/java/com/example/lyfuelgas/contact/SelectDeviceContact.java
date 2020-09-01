package com.example.lyfuelgas.contact;

import com.example.lyfuelgas.bean.DeviceTypeObject;

import java.util.ArrayList;

public class SelectDeviceContact {
    public interface View{

        /**
         * 获取设备列表成功
         */
        void onGetDeviceListSuccess(ArrayList<DeviceTypeObject> data);
    }

    public interface Presenter{

        /**
         * 获取设备列表
         */
        void getDeviceList();

        /**
         * 获取设备类型列表
         */
        void getDeviceTypeList();

        /**
         * 设置设备测试状态
         * @param deviceId
         * @param status
         */
        void setDeviceTest(String deviceId, int status);

    }
}
