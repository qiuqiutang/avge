package com.example.lyfuelgas.bean;

import com.amap.api.maps2d.model.LatLng;
import com.example.lyfuelgas.common.bean.BaseObject;
import com.example.lyfuelgas.common.utils.LatLngUtils;

public class OrderObject extends BaseObject implements Comparable<OrderObject>{
    /**
     * address (string, optional): 联系人地址 ,
     * amount (string, optional): 加注量 ,
     * bill (string, optional): 订单金额 ,
     * contact (string, optional): 联系人名称 ,
     * createTime (integer, optional): 创建时间 ,
     * creatorId (string, optional),
     * creatorIdProperty (string, optional),
     * customerId (string, optional): 客户id ,
     * equipmentId (string, optional): 设备id ,
     * fillTime (integer, optional): 加注时间 ,
     * id (string, optional),
     * personId (string, optional): 加注员id ,
     * properties (object, optional),
     * status (integer, optional): 订单加注状态 ,
     * supplierId (string, optional): 供应商id
     */
    public String id;
    public String address;
    public String location;
    public String amount;
    public String bill;
    public String contact;
    public long createTime;
    public String customerId;
    public String customerMobile;
    public String equipmentId;
    public long fillTime;
    public String fillAmount;
    public String fillBill;
    public int status;
    public String supplierId;
    public String supplierMobile;

    public double distance;



    public LatLng getLatLng(){
        return LatLngUtils.getLatLng(location);
    }

    @Override
    public int compareTo(OrderObject o) {
        return (int)(distance - o.distance);
    }
}
