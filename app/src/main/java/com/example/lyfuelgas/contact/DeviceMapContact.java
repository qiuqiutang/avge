package com.example.lyfuelgas.contact;

import com.example.lyfuelgas.bean.DeviceTypeObject;

import java.util.ArrayList;

public class DeviceMapContact {
    public interface View{

        /**
         * 修改成功
         */
        void onSetDeviceSuccess();
    }

    public interface Presenter{


        /**
         * 设置设备位置
         * @param deviceId
         */
        void setDeviceAddress(String deviceId, String address, String gps);

    }
}
