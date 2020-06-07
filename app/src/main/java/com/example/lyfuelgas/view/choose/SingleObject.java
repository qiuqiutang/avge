package com.example.lyfuelgas.view.choose;


import com.example.lyfuelgas.common.bean.BaseObject;

/**
 * Created by QiuYan on 2018/8/25.
 */

public class SingleObject extends BaseObject {
    public int id = 0;
    public String value = "";

    public SingleObject(){

    }

    public SingleObject(int id, String value){
        this.id = id;
        this.value = value;

    }

}
