package com.example.lyfuelgas.bean;

import com.example.lyfuelgas.common.bean.BaseObject;

public class UserObject extends BaseObject {
    /***
     * 登录返回的用户Id和token
     */
    public String userId;
    public String token;

    ////////////以下是用户信息////////////////////
    /**
     * email (string, optional),
     * id (string, optional),
     * name (string, optional),
     * personId (string, optional),
     * personIdProperty (string, optional),
     * personUser (PersonUserEntity, optional),
     * phone (string, optional),
     * photo (string, optional),
     * positionIds (Array[string], optional),
     * properties (object, optional),
     * remark (string, optional),
     * sex (string, optional),
     * status (string, optional),
     * userId (string, optional),
     * userIdProperty (string, optional)
     */
    public String id;
    public String name;
    public String phone;
    public String photo;
    public String remark;
    public String sex;
    public String email;
    public String personId;
    public String status;



}
