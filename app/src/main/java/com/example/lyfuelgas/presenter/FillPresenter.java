package com.example.lyfuelgas.presenter;

import com.example.lyfuelgas.activity.FillActivity;
import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.bean.OrderObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.IModel;
import com.example.lyfuelgas.contact.FillContact;
import com.example.lyfuelgas.model.DeviceModel;
import com.example.lyfuelgas.model.FillModel;

import java.util.HashMap;
import java.util.Map;


public class FillPresenter extends BasePresenter<FillActivity> implements FillContact.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new FillModel(getIView().mContext),new DeviceModel(getIView().mContext));
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("fill", models[0]);
        map.put("device",models[1]);
        return map;
    }



    @Override
    public void addFill(OrderObject orderObject, String imei, String capacity, String price, String remark) {
        Map<String, Object> request = new HashMap<>();
        request.put("orderId",orderObject.id);
        request.put("equipmentId",orderObject.equipmentId);
        request.put("customerId",orderObject.customerId);
        request.put("supplierId",orderObject.supplierId);
        request.put("imei",imei);
        request.put("amount",capacity);
        request.put("bill",price);
        request.put("remark",remark);
        ((FillModel)(mModels.get("fill"))).addFill(request,new BaseSubscriber<BaseResponse>(getIView()){
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
                if(null != baseResponse && baseResponse.isSuccess()){
                    getIView().onAddFillSuccess();
                }
            }
        });
    }

    @Override
    public void getDeviceDetail(String id) {
        ((DeviceModel)(mModels.get("device"))).getDetail(id,new BaseSubscriber<BaseResponse<DeviceObject>>(getIView().mContext){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(BaseResponse<DeviceObject> baseResponse) {
                super.onNext(baseResponse);
                if(null != baseResponse && baseResponse.isSuccess()){
                    getIView().onGetDeviceDetailSuccess(baseResponse.result);
                }
            }
        });
    }
}
