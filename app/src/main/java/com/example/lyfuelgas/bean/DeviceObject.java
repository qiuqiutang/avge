package com.example.lyfuelgas.bean;

import android.text.TextUtils;

import com.example.lyfuelgas.adapter.item.DeviceItem;
import com.example.lyfuelgas.common.bean.BaseObject;
import com.example.lyfuelgas.view.treerecycleview.annotation.TreeItemClass;

import java.math.BigDecimal;

@TreeItemClass(iClass = DeviceItem.class)
public class DeviceObject extends BaseObject {
    /**
     *equipmentTypeId (string, optional): 设备型号id ,
     * id (string, optional),
     * imei (string, optional): imei号码 唯一 ,
     * location (string, optional): 经纬度 ,
     * properties (object, optional),
     * qrcode (string, optional): 二维码 ,
     * supplierId (string, optional): 供应商id
     */
    public String equipmentTypeId;
    public String id;
    public String imei;
    public String code;
    public String location;
    public String address;
    public String grcode;
    public String customerId;
    public String customerContact;
    public String customerMobile;
    public String supplierId;
    public String supplierContact;
    public String supplierMobile;
    public String price;
    public int area;
    public int capacity;
    public int height;
    public float remain;
    public float remainPercent;
    public float liquid;
    public float temperature;
    public BigDecimal volume;
    public String picUrl;

    /**
     * "address": "string",
     *     "area": 0,
     *     "capacity": 0,
     *     "code": "string",
     *     "createTime": 0,
     *     "creatorId": "string",
     *     "creatorIdProperty": "string",
     *     "customerContact": "string",
     *     "customerId": "string",
     *     "customerMobile": "string",
     *     "departmentId": "string",
     *     "departmentIdProperty": "string",
     *     "equipmentTypeId": "string",
     *     "height": 0,
     *     "id": "string",
     *     "imei": "string",
     *     "liquid": 0,
     *     "location": "string",
     *     "properties": {},
     *     "qrcode": "string",
     *     "remain": "string",
     *     "supplierContact": "string",
     *     "supplierId": "string",
     *     "supplierMobile": "string",
     *     "temperature": 0,
     *     "volume": 0
     */

    public BigDecimal getPrice(){
        if(TextUtils.isEmpty(price)) {
            return new BigDecimal(0);
        }
        return new BigDecimal(price);
    }
}
