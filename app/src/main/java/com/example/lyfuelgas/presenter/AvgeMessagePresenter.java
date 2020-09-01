package com.example.lyfuelgas.presenter;

import com.example.lyfuelgas.activity.AvgeFillActivity;
import com.example.lyfuelgas.activity.AvgeMassageActivity;
import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.bean.DeviceStatObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.IModel;
import com.example.lyfuelgas.contact.AvgeFillContact;
import com.example.lyfuelgas.contact.AvgeMessageContact;
import com.example.lyfuelgas.model.DeviceModel;
import com.example.lyfuelgas.model.OrderModel;

import java.util.HashMap;
import java.util.Map;


public class AvgeMessagePresenter extends BasePresenter<AvgeMassageActivity> implements AvgeMessageContact.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new DeviceModel(getIView().mContext));
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("device", models[0]);
        return map;
    }


    @Override
    public void getStat(String deviceId) {
        ((DeviceModel)(mModels.get("device"))).getStat(deviceId, new BaseSubscriber<BaseResponse<DeviceStatObject>>(getIView()){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(BaseResponse<DeviceStatObject> deviceStatObjectBaseResponse) {
                super.onNext(deviceStatObjectBaseResponse);
                if(deviceStatObjectBaseResponse.isSuccess()){
                    deviceStatObjectBaseResponse.result.timestamp = deviceStatObjectBaseResponse.timestamp;
                    getIView().onGetStatSuccess(deviceStatObjectBaseResponse.result);
                }
            }
        });
    }
}
