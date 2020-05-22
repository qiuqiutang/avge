package com.example.lyfuelgas.presenter;

import android.text.TextUtils;

import com.example.lyfuelgas.activity.AvgeFillActivity;
import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.IModel;
import com.example.lyfuelgas.contact.AvgeFillContact;
import com.example.lyfuelgas.model.OrderModel;

import java.util.HashMap;
import java.util.Map;


public class AvgeFIllPresenter extends BasePresenter<AvgeFillActivity> implements AvgeFillContact.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new OrderModel(getIView().mContext));
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("order", models[0]);
        return map;
    }


    @Override
    public void addOrder(DeviceObject deviceObject,String amount, String bill) {
        Map<String, Object> request = new HashMap<>();
        request.put("amount",amount);
        request.put("bill",bill);
        request.put("address", TextUtils.isEmpty(deviceObject.address) ? "" : deviceObject.address);
        request.put("supplierId",deviceObject.supplierId);
        request.put("supplierMobile", deviceObject.supplierMobile);
        request.put("equipmentId",deviceObject.id);
        request.put("customerId", deviceObject.customerId);
        request.put("contact", deviceObject.customerContact);
        request.put("customerMobile", deviceObject.customerMobile);
        request.put("location",deviceObject.location);
        request.put("status",0);
        ((OrderModel)(mModels.get("order"))).addOrder(request,new BaseSubscriber<BaseResponse>(getIView()){
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
                    getIView().onAddOrderSuccess();
                }
            }
        });
    }
}
