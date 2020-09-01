package com.example.lyfuelgas.contact;

import com.example.lyfuelgas.bean.DeviceStatObject;

public class AvgeMessageContact {
    public interface View{
        /**
         * 获取统计信息成功
         */
        void onGetStatSuccess(DeviceStatObject data);
    }

    public interface Presenter{
        /**
         * 获取设备统计信息
         */
        void getStat(String deviceId);

    }
}
