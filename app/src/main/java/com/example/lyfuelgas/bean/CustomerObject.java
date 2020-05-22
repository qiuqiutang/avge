package com.example.lyfuelgas.bean;

public class CustomerObject extends RelationObject {
    /**
     * address (string, optional): 地址 ,
     * createTime (integer, optional): 创建时间 ,
     * creatorId (string, optional): 创建人id ,
     * creatorIdProperty (string, optional),
     * deliveFreq (string, optional): 配送频率 ,
     * departmentId (string, optional): 部门id ,
     * departmentIdProperty (string, optional),
     * doorPhoto (string, optional): 门头照 ,
     * fullname (string, optional): 全称 ,
     * id (string, optional),
     * isRealNameAuth (integer, optional): 是否实名 ,
     * level (integer, optional): 级别 ,
     * modifierId (string, optional): 修改人id ,
     * modifierIdProperty (string, optional),
     * modifyTime (integer, optional): 修改时间 ,
     * name (string, optional): 名称 ,
     * price (integer, optional): 订单固定价格 ,
     * properties (object, optional),
     * relDuty (string, optional): 联系人职务 ,
     * relMobile (string, optional): 联系人手机 ,
     * relName (string, optional): 联系人姓名 ,
     * relTel (string, optional): 联系人座机 ,
     * state (integer, optional): 合作状态 ,
     * supplierId (string, optional): 供应商id ,
     * type (integer, optional): 形态 ,
     * userId (string, optional): 登录用户id
     */
    public String address;
    public String deliveFreq;
    public String fullname;
    public String id;
    public int isRealNameAuth;
    public int level;
    public String name;
    public int price;
    public int state;
    public String supplierId;
    public int type;
    public String userId;
}
