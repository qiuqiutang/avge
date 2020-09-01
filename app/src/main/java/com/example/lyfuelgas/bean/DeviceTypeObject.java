package com.example.lyfuelgas.bean;

import android.text.TextUtils;

import com.example.lyfuelgas.adapter.item.DeviceTypeItem;
import com.example.lyfuelgas.common.bean.BaseObject;
import com.example.lyfuelgas.view.treerecycleview.annotation.TreeItemClass;

import java.util.ArrayList;

@TreeItemClass(iClass = DeviceTypeItem.class)
public class DeviceTypeObject extends BaseObject {
    /**
     * area (integer, optional): 底面积 ,
     * capacity (integer, optional): 容量 ,
     * id (string, optional),
     * name (string, optional): 设备型号名称 ,
     * properties (object, optional)
     */
    public int area;
    public String capacity;
    public int height;
    public String id;
    public String name;
    public String code;

    public ArrayList<DeviceObject> deviceList;

    public boolean isShowToxicGas(){
        return !TextUtils.isEmpty(code) && !"001".equals(code);
    }
}
