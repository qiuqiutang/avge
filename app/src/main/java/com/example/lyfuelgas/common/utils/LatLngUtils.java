package com.example.lyfuelgas.common.utils;

import android.text.TextUtils;

import com.amap.api.maps2d.model.LatLng;

public class LatLngUtils {
    /**
     * 校验double数值是否为0
     *
     * @param value
     *
     * @return
     */
    public static boolean isEqualToZero(double value) {
        return Math.abs(value - 0.0) < 0.01 ? true : false;
    }

    /**
     * 经纬度是否为(0,0)点
     *
     * @return
     */
    public static boolean isZeroPoint(double latitude, double longitude) {
        return isEqualToZero(latitude) && isEqualToZero(longitude);
    }


    public static double parseStrToDouble(String str){
        if(TextUtils.isEmpty(str))
            return 0.0;
        return Double.parseDouble(str);
    }

    public static LatLng getLatLng(String location){
        if(TextUtils.isEmpty(location))
            return null;
        if(!location.contains(",")){
            return null;
        }
        String[] locArr = location.split(",");
        if(locArr.length != 2)
            return null;
        double latitude = parseStrToDouble(locArr[1]);
        double longtitude = parseStrToDouble(locArr[0]);
        if(LatLngUtils.isZeroPoint(latitude,longtitude))
            return null;
        return new LatLng(latitude,longtitude);
    }
}
