package com.example.lyfuelgas.app;

import android.text.TextUtils;

import com.example.lyfuelgas.bean.CustomerObject;
import com.example.lyfuelgas.common.utils.GsonUtils;
import com.example.lyfuelgas.common.utils.SPUtils;
import com.example.lyfuelgas.constant.SPConstant;


/**
 * Created on 2020/04/27.
 * 客户信息管理，添加、修改、清除
 * @author QiuYan
 * @since 1.0.0
 */
public class CustomerManager {
    private static CustomerManager sInstance;
    private CustomerObject userModel;

    public static synchronized CustomerManager getInstance() {
        if (sInstance == null) {
            sInstance = new CustomerManager();
        }
        return sInstance;
    }

    private CustomerManager() {
    }

    /**
     * 设置用户
     * @param customerObject
     */
    public void setCustomer(CustomerObject customerObject){
        if(null == customerObject) {
            return;
        }
        this.userModel = customerObject;
        SPUtils.put(MyApplication.getInstance(), SPConstant.CUSTOMER, GsonUtils.toJsonString(customerObject));
    }


    /**
     * 获取用户信息
     * @return
     */
    public CustomerObject getCustomer(){
        if(null != this.userModel) {
            return this.userModel;
        }
        String strUser = (String) SPUtils.get(MyApplication.getInstance(), SPConstant.CUSTOMER,"");
        if(TextUtils.isEmpty(strUser)) {
            return null;
        }
        this.userModel = GsonUtils.parseJsonToBean(strUser,CustomerObject.class);
        return this.userModel;
    }


    /**
     * 获取用户ID
     * @return
     */
    public String getCustomerId(){
        if(null != getCustomer()) {
            return this.userModel.id;
        }
        return "";
    }



}
