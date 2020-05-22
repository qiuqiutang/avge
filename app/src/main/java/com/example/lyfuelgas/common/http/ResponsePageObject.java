package com.example.lyfuelgas.common.http;

import java.util.ArrayList;

/**
 * Created by QiuYan on 2020/4/24.
 */

public class ResponsePageObject<T> {
    /**
     * data (Array[UserEntity], optional): 查询结果 ,
     * pageIndex (integer, optional): 当前页码 ,
     * pageSize (integer, optional): 每页数据数量 ,
     * total (integer, optional): 数据总数量
     */
    public ArrayList<T> data;
    public int pageIndex;
    public int pageSize;
    public int total;
}
