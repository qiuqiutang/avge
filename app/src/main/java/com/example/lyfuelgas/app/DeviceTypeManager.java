package com.example.lyfuelgas.app;

import android.text.TextUtils;

import com.example.lyfuelgas.bean.DeviceTypeObject;
import com.example.lyfuelgas.common.utils.GsonUtils;
import com.example.lyfuelgas.common.utils.SPUtils;
import com.example.lyfuelgas.constant.SPConstant;

import java.util.HashMap;
import java.util.List;


/**
 * Created on 2020/04/27.
 * 设备类型管理，添加、修改、清除
 * @author QiuYan
 * @since 1.0.0
 */
public class DeviceTypeManager {
    private static DeviceTypeManager sInstance;
    private HashMap<String,DeviceTypeObject> deviceTypeMaps;

    public static synchronized DeviceTypeManager getInstance() {
        if (sInstance == null) {
            sInstance = new DeviceTypeManager();
        }
        return sInstance;
    }

    private DeviceTypeManager() {
    }

    /**
     * 设置设备类型
     * @param deviceTypeObjects
     */
    public void setDeviceType(List<DeviceTypeObject> deviceTypeObjects){
        if(null == deviceTypeObjects) {
            return;
        }
        HashMap<String, DeviceTypeObject> maps = new HashMap<>();
        for (DeviceTypeObject deviceType : deviceTypeObjects) {
            if(null != deviceType){
                maps.put(deviceType.id,deviceType);
            }
        }
        this.deviceTypeMaps = maps;
        SPUtils.put(MyApplication.getInstance(), SPConstant.DEVICE_TYPE, GsonUtils.toJsonString(deviceTypeObjects));
    }


    public void initData(){
        String deviceTypeString = (String) SPUtils.get(MyApplication.getInstance(), SPConstant.DEVICE_TYPE,"");
        if(TextUtils.isEmpty(deviceTypeString)){
           return;
        }
        List<DeviceTypeObject> deviceTypeObjects = (List<DeviceTypeObject>)GsonUtils.GsonToList(deviceTypeString,DeviceTypeObject.class);
        setDeviceType(deviceTypeObjects);
    }

    /**
     * 获取数据
     * @return
     */
    public HashMap<String,DeviceTypeObject> getData(){
        if(null == deviceTypeMaps){
            initData();
        }
        return deviceTypeMaps;
    }

    /**
     * 获取设备类型名称
     * @param deviceTypeId
     * @return
     */
    public DeviceTypeObject getDeviceType(String deviceTypeId){
        if(null == deviceTypeMaps){
            initData();
        }
        if(null == deviceTypeMaps || !deviceTypeMaps.containsKey(deviceTypeId)){
            return null;
        }else {
            return deviceTypeMaps.get(deviceTypeId);
        }
    }

}
