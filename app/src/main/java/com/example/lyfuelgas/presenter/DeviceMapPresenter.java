package com.example.lyfuelgas.presenter;

import android.util.Log;

import com.example.lyfuelgas.activity.DeviceMapActivity;
import com.example.lyfuelgas.activity.SelectDeviceActivity;
import com.example.lyfuelgas.app.DeviceTypeManager;
import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.bean.DeviceTypeObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.IModel;
import com.example.lyfuelgas.common.utils.GsonUtils;
import com.example.lyfuelgas.contact.DeviceMapContact;
import com.example.lyfuelgas.contact.SelectDeviceContact;
import com.example.lyfuelgas.model.DeviceModel;
import com.example.lyfuelgas.model.DeviceTypeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DeviceMapPresenter extends BasePresenter<DeviceMapActivity> implements DeviceMapContact.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new DeviceModel(getIView()));
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("device", models[0]);
        return map;
    }


    @Override
    public void setDeviceAddress(String deviceId, String address, String gps) {
        Map<String, Object> request = new HashMap<>();
        request.put("address",address);
        request.put("location",gps);
        Log.e("====http", GsonUtils.toJsonString(request));
        ((DeviceModel)(mModels.get("device"))).setDeviceInfo(deviceId,request,new BaseSubscriber<BaseResponse>(getIView()){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(BaseResponse baseResponse) {
                super.onNext(baseResponse);
                if(baseResponse.isSuccess()){
                    getIView().onSetDeviceSuccess();
                }
            }
        });
    }
}
