package com.example.lyfuelgas.presenter;

import com.example.lyfuelgas.activity.SelectDeviceActivity;
import com.example.lyfuelgas.app.DeviceTypeManager;
import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.bean.DeviceTypeObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.IModel;
import com.example.lyfuelgas.contact.SelectDeviceContact;
import com.example.lyfuelgas.model.DeviceModel;
import com.example.lyfuelgas.model.DeviceTypeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SelectDevicePresenter extends BasePresenter<SelectDeviceActivity> implements SelectDeviceContact.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new DeviceModel(getIView()), new DeviceTypeModel(getIView()));
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("device", models[0]);
        map.put("deviceType",models[1]);
        return map;
    }


    @Override
    public void getDeviceList() {
        ((DeviceModel)(mModels.get("device"))).getList(new BaseSubscriber<BaseResponse<ArrayList<DeviceObject>>>(getIView()){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(BaseResponse<ArrayList<DeviceObject>> arrayListResponse) {
                super.onNext(arrayListResponse);
                if(null != arrayListResponse && arrayListResponse.isSuccess()){
                    HashMap<String,ArrayList<DeviceObject>> arrayListHashMap = new HashMap<>();
                    for (DeviceObject deviceObject:arrayListResponse.result
                         ) {
                        if(null != deviceObject){
                            if(!arrayListHashMap.containsKey(deviceObject.equipmentTypeId)){
                                ArrayList<DeviceObject> deviceObjects = new ArrayList<>();
                                arrayListHashMap.put(deviceObject.equipmentTypeId,deviceObjects);
                            }
                            arrayListHashMap.get(deviceObject.equipmentTypeId).add(deviceObject);
                        }
                    }
                    ArrayList<DeviceTypeObject> deviceTypeObjects = new ArrayList<>();
                    for(Map.Entry<String, ArrayList<DeviceObject>> entry: arrayListHashMap.entrySet())
                    {
                        DeviceTypeObject deviceTypeObject = DeviceTypeManager.getInstance().getDeviceType(entry.getKey());
                        deviceTypeObject.id = entry.getKey();
                        deviceTypeObject.deviceList = entry.getValue();
                        deviceTypeObjects.add(deviceTypeObject);
                    }
                    getIView().onGetDeviceListSuccess(deviceTypeObjects);
                }

            }
        });
    }

    @Override
    public void getDeviceTypeList() {
        /*ArrayList<DeviceTypeObject> deviceTypeObjects = new ArrayList<>();
        for (int i =0 ; i < 5 ; i++){
            DeviceTypeObject deviceTypeObject = new DeviceTypeObject();
            deviceTypeObject.id = "00000000"+i;
            deviceTypeObject.name = "==========" + i;
            ArrayList<DeviceObject> deviceObjects = new ArrayList<>();
            for(int j = 0; j < 3; j++) {
                DeviceObject deviceObject = new DeviceObject();
                deviceObject.id = "00000000"+i+"00"+j;
                deviceObjects.add(deviceObject);
            }
            deviceTypeObject.deviceList = deviceObjects;
            deviceTypeObjects.add(deviceTypeObject);
        }
        getIView().onGetDeviceListSuccess(deviceTypeObjects);*/
        ((DeviceTypeModel)(mModels.get("deviceType"))).getList(new BaseSubscriber<BaseResponse<ArrayList<DeviceTypeObject>>>(getIView()){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(null != DeviceTypeManager.getInstance().getData() && !DeviceTypeManager.getInstance().getData().isEmpty()){
                    getDeviceList();
                }
            }

            @Override
            public void onNext(BaseResponse<ArrayList<DeviceTypeObject>> arrayListResponse) {
                super.onNext(arrayListResponse);
                if(null != arrayListResponse && arrayListResponse.isSuccess()){
                    DeviceTypeManager.getInstance().setDeviceType(arrayListResponse.result);
                    getDeviceList();
                }else {
                    if(null != DeviceTypeManager.getInstance().getData() && !DeviceTypeManager.getInstance().getData().isEmpty()){
                        getDeviceList();
                    }
                }

            }
        });
    }
}
