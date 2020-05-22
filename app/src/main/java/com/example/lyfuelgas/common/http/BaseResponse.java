package com.example.lyfuelgas.common.http;

/**
 * Created on 2020/4/24.
 *
 * @author QiuYan
 * @since 1.0.0
 */

public class BaseResponse<Data> {
    public int status = 0;
    public String message;
    public long timestamp;
    public Data result;


    public boolean isSuccess(){
        return status < 300 && status >= 200;
    }
}
