package com.example.lyfuelgas.app;

import android.text.TextUtils;

import com.example.lyfuelgas.bean.UserObject;
import com.example.lyfuelgas.common.utils.GsonUtils;
import com.example.lyfuelgas.common.utils.SPUtils;
import com.example.lyfuelgas.constant.SPConstant;


/**
 * Created on 2020/04/27.
 * 用户信息管理，添加、修改、清除
 * @author QiuYan
 * @since 1.0.0
 */
public class UserManager {
    private static UserManager sInstance;
    private UserObject userModel;

    public static synchronized UserManager getInstance() {
        if (sInstance == null) {
            sInstance = new UserManager();
        }
        return sInstance;
    }

    private UserManager() {
    }

    /**
     * 设置用户
     * @param UserModel
     */
    public void setUser(UserObject UserModel){
        if(null == UserModel) {
            return;
        }
        this.userModel = UserModel;
        SPUtils.put(MyApplication.getInstance(), SPConstant.USER, GsonUtils.toJsonString(UserModel));
    }


    /**
     * 获取用户信息
     * @return
     */
    public UserObject getUser(){
        if(null != this.userModel) {
            return this.userModel;
        }
        String strUser = (String) SPUtils.get(MyApplication.getInstance(), SPConstant.USER,"");
        if(TextUtils.isEmpty(strUser)) {
            return null;
        }
        this.userModel = GsonUtils.parseJsonToBean(strUser,UserObject.class);
        return this.userModel;
    }


    /**
     * 获取用户ID
     * @return
     */
    public String getUserId(){
        if(null != getUser()) {
            return this.userModel.userId;
        }
        return "";
    }

    /**
     * 获取配送员id
     * @return
     */
    public String getPersonId(){
        if(null != getUser()){
            return this.userModel.personId;
        }
        return "";
    }


    /**
     * 获取用户ID
     * @return
     */
    public String getUserToken(){
        if(null != getUser()) {
            return this.userModel.token;
        }
        return "";
    }


    /**
     * 清空用户信息
     */
    public void clearUser(){
        this.userModel = null;
        SPUtils.put(MyApplication.getInstance(), SPConstant.USER,"");

    }

    /**
     * 判断是否登录
     * @return
     */
    public boolean isLogin(){
        return null != getUser() && !TextUtils.isEmpty(this.userModel.token);
    }

    /**
     * 是否为供应商
     * @return
     */
    public boolean isSupplier(){
        int userType = (int) SPUtils.get(MyApplication.getInstance(), SPConstant.USER_TYPE,1);
        return 2 == userType;
    }


    public String getDeviceInfoKey(){
        return SPConstant.DEVICE_INFO+getUserId();
    }


}
