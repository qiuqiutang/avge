package com.example.lyfuelgas.common.http;


import com.example.lyfuelgas.bean.AuthenticationObject;
import com.example.lyfuelgas.bean.CustomerObject;
import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.bean.DeviceTypeObject;
import com.example.lyfuelgas.bean.MeasureObject;
import com.example.lyfuelgas.bean.OrderObject;
import com.example.lyfuelgas.bean.SupplierObject;
import com.example.lyfuelgas.bean.UserObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Retrofit network API definition
 * Created on 2020/4/24.
 *
 * @author QiuYan
 * @since 1.0.0
 */
public interface ApiService {
    ////////////////////////////////授权/////////////////////////////////////
    /**
     * 登录
     * @param requst
     * @return
     */
    @POST("/authorize/login")
    Observable<BaseResponse<UserObject>> login(@Body HashMap<String, Object> requst);

    /**
     * 退出登录
     * @return
     */
    @GET("/authorize/login-out")
    Observable<BaseResponse> logout();

    /**
     * 获取用户权限
     * @return
     */
    @GET("/authorize/me")
    Observable<BaseResponse<AuthenticationObject>> getRole();

    /**
     * 刷新token
     * @param token
     * @return
     */
    @GET("/user-token/{token}/touch")
    Observable<BaseResponse> refreshToken(@Path("token") String token);

    ////////////////////////////////用户/////////////////////////////////////

    /**
     * 获取自己的信息
     * @return
     */
    @GET("/person/me")
    Observable<BaseResponse<UserObject>> getUserInfo();

    /**
     * 登录状态下修改用户密码
     * @param password
     * @param oldPassword
     * @return
     */
    @FormUrlEncoded
    @PUT("/user/password")
    Observable<BaseResponse> updatePassword(@Field("password") String password,
                                                    @Field("oldPassword") String oldPassword);

    /**
     * 获取客户信息
     * @param request
     * @return
     */
    @GET("/customer")
    Observable<BaseResponse<ResponsePageObject<CustomerObject>>> getCustomerList(@QueryMap Map<String,Object> request);


    /**
     * 获取供应商信息
     * @param id
     * @return
     */
    @GET("/supplier/{id}")
    Observable<BaseResponse<SupplierObject>> getSupplier(@Path("id") String id);
    ////////////////////////////////设备类型/////////////////////////////////////
    /**
     * 获取设备类型列表
     * @return
     */
    @GET("/equipment-type/no-paging")
    Observable<BaseResponse<ArrayList<DeviceTypeObject>>> getDeviceTypeList();


    ////////////////////////////////设备/////////////////////////////////////
    /**
     * 获取设备列表
     * @return
     */
    @GET("/me/equipment")
    Observable<BaseResponse<ArrayList<DeviceObject>>> getDeviceList();

    /**
     * 获取设备详情
     * @return
     */
    @GET("/equipment/detail/{id}")
    Observable<BaseResponse<DeviceObject>> getDeviceDetail(@Path("id") String id);


    ////////////////////////////////设备监测数据/////////////////////////////////////

    /**
     * 业务监测数据
     * @param request
     * @return
     */
    @GET("/measure")
    Observable<BaseResponse<ResponsePageObject<MeasureObject>>> getMeasureList(@QueryMap Map<String,Object> request);


    ////////////////////////////////订单/////////////////////////////////////

    /**
     * 添加订单
     * @param request
     * @return
     */
     @POST("/order")
    Observable<BaseResponse> addOrder(@Body Map<String, Object> request);

    /**
     * 获取订单列表
     * @param request
     * @return
     */
    @GET("/me/order")
    Observable<BaseResponse<ResponsePageObject<OrderObject>>> getOrderList(@QueryMap Map<String, Object> request);

    /**
     * 获取订单列表(不分页)
     * @param request
     * @return
     */
    @GET("/me/order/no-paging")
    Observable<BaseResponse<ArrayList<OrderObject>>> getOrderListNoPage(@QueryMap Map<String, Object> request);

    /**
     * 添加fill
     * @param request
     * @return
     */
    @POST("/fill")
    Observable<BaseResponse> addFill(@Body Map<String, Object> request);
}