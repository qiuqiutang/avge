package com.example.lyfuelgas.bean;

import android.text.TextUtils;

import com.example.lyfuelgas.adapter.item.DeviceItem;
import com.example.lyfuelgas.common.bean.BaseObject;
import com.example.lyfuelgas.view.treerecycleview.annotation.TreeItemClass;

import java.math.BigDecimal;

@TreeItemClass(iClass = DeviceItem.class)
public class DeviceStatObject extends BaseObject {
    /**
     * price (number, optional): 燃料价格 ,
     * usedDays (integer, optional): 设备运营天数 ,
     * usedFeeLast12Month (number, optional): 设备最近12月使用燃料费用 ,
     * usedFeeThisMonth (number, optional): 设备当月使用燃料费用 ,
     * usedFeeToday (number, optional): 设备当日使用燃料费用 ,
     * usedThisMonth (number, optional): 设备当月使用燃料 ,
     * usedToday (number, optional): 设备当日使用燃料 ,
     * usedlast12Month (number, optional): 设备最近12月使用燃料
     */
    public String price;
    public String usedDays;
    public String usedFeeLast12Month;
    public String usedFeeThisMonth;
    public String usedFeeToday;
    public String usedThisMonth;
    public String usedToday;
    public String usedlast12Month;

    public long timestamp;

}
