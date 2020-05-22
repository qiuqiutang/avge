package com.example.lyfuelgas.app;

import android.text.TextUtils;

import com.example.lyfuelgas.bean.SupplierObject;
import com.example.lyfuelgas.common.utils.GsonUtils;
import com.example.lyfuelgas.common.utils.SPUtils;
import com.example.lyfuelgas.constant.SPConstant;


/**
 * Created on 2020/04/27.
 * 供应商信息管理，添加、修改、清除
 * @author QiuYan
 * @since 1.0.0
 */
public class SupplierManager {
    private static SupplierManager sInstance;
    private SupplierObject userModel;

    public static synchronized SupplierManager getInstance() {
        if (sInstance == null) {
            sInstance = new SupplierManager();
        }
        return sInstance;
    }

    private SupplierManager() {
    }

    /**
     * 设置用户
     * @param customerObject
     */
    public void setSupplier(SupplierObject customerObject){
        if(null == customerObject) {
            return;
        }
        this.userModel = customerObject;
        SPUtils.put(MyApplication.getInstance(), SPConstant.SUPPLIER, GsonUtils.toJsonString(customerObject));
    }


    /**
     * 获取用户信息
     * @return
     */
    public SupplierObject getSupplier(){
        if(null != this.userModel) {
            return this.userModel;
        }
        String strUser = (String) SPUtils.get(MyApplication.getInstance(), SPConstant.SUPPLIER,"");
        if(TextUtils.isEmpty(strUser)) {
            return null;
        }
        this.userModel = GsonUtils.parseJsonToBean(strUser,SupplierObject.class);
        return this.userModel;
    }




}
