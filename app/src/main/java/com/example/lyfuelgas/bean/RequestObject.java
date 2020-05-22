package com.example.lyfuelgas.bean;

import android.text.TextUtils;

import com.example.lyfuelgas.common.bean.BaseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestObject extends BaseObject {
    public ArrayList<String> includes;
    public ArrayList<String> excludes;
    public boolean paging;
    public int firstPageIndex;
    public int pageIndex;
    public int pageSize;
    public boolean forUpdate;
    public String termExpression;
    public ArrayList<RequestTermObject> terms;
    public ArrayList<RequestSortObject> sorts;

    public Map<String,Object> getMap(){
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("paging",paging);
        objectMap.put("firstPageIndex",firstPageIndex);
        objectMap.put("pageIndex",pageIndex);
        objectMap.put("pageSize",pageSize);
        objectMap.put("forUpdate",forUpdate);
        objectMap.put("termExpression", TextUtils.isEmpty(termExpression) ? "" : termExpression);
        if(null != terms && terms.size() > 0){
            for (int i = 0; i < terms.size(); i++ ) {
                RequestTermObject termObject = terms.get(i);
                if(null != termObject) {
                    if(null != termObject.options) {
                        objectMap.put("terms[" + i + "].options", termObject.options);
                    }
                    objectMap.put("terms[" + i + "].column", TextUtils.isEmpty(termObject.column) ? "" : termObject.column);
                    objectMap.put("terms[" + i + "].value", termObject.value);
                    objectMap.put("terms[" + i + "].type", TextUtils.isEmpty(termObject.type) ? "" : termObject.type);
                    objectMap.put("terms[" + i + "].termType", TextUtils.isEmpty(termObject.termType) ? "" : termObject.termType);
                }
            }
        }
        if(null != sorts && sorts.size() > 0){
            for(int i = 0; i < sorts.size(); i++){
                RequestSortObject sortObject = sorts.get(i);
                if(null != sortObject){
                    objectMap.put("sorts[" + i + "].name", TextUtils.isEmpty(sortObject.name) ? "" : sortObject.name);
                    objectMap.put("sorts[" + i + "].type", TextUtils.isEmpty(sortObject.type) ? "" : sortObject.type);
                    objectMap.put("sorts[" + i + "].order", TextUtils.isEmpty(sortObject.order) ? "" : sortObject.order);
                }
            }
        }
        return objectMap;
    }
}
