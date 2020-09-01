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
    public String capacity;
    public int height;
    public float remain;
    public float remainPercent;
    public float liquid;
    public float temperature;
    public BigDecimal volume;
    public String picUrl;
    public int testStatus;
    public int liquidAbStatus;
    public int temperatureAbStatus;
    //0.1mm/s
    public String max;

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

    public double getCapacity(){
        if(TextUtils.isEmpty(capacity)){
            return 0;
        }else {
            return Double.parseDouble(capacity);
        }
    }

    public BigDecimal getPrice(){
        if(TextUtils.isEmpty(price)) {
            return new BigDecimal(0);
        }
        return new BigDecimal(price);
    }


    public String getMaxTime(){
        if(TextUtils.isEmpty(max) || "0".equals(max)){
            return "--";
        }
        //单位mm
        BigDecimal currentLiquid = new BigDecimal(height).multiply(new BigDecimal(remainPercent)).divide(new BigDecimal(100));
        BigDecimal time = currentLiquid.divide(new BigDecimal(max),2, BigDecimal.ROUND_HALF_UP);;
        int second = time.intValue();
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }
        return String.format("%02d:%02d:%02d",h, d, s);
    }
}
