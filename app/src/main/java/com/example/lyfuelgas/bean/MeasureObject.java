package com.example.lyfuelgas.bean;

import com.example.lyfuelgas.adapter.item.DeviceItem;
import com.example.lyfuelgas.common.bean.BaseObject;
import com.example.lyfuelgas.view.treerecycleview.annotation.TreeItemClass;

import java.math.BigDecimal;

@TreeItemClass(iClass = DeviceItem.class)
public class MeasureObject extends BaseObject {
    /**
     *createTime (integer, optional): 创建时间 ,
     * creatorId (string, optional),
     * creatorIdProperty (string, optional),
     * customerId (string, optional): 用户id ,
     * departmentId (string, optional): 部门id ,
     * departmentIdProperty (string, optional),
     * id (string, optional),
     * imei (string, optional): imei ,
     * liquid (number, optional): 液位值 ,
     * properties (object, optional),
     * supplierId (string, optional): 供应商id ,
     * temperature (number, optional): 温度值 ,
     * volume (number, optional): 体积值
     */
    public String id;
    public String imei;
    public float liquid;
    public BigDecimal temperature;
    public BigDecimal volume;
}
