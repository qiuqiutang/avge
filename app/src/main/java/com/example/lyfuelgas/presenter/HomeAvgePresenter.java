package com.example.lyfuelgas.presenter;

import com.example.lyfuelgas.app.CustomerManager;
import com.example.lyfuelgas.app.SupplierManager;
import com.example.lyfuelgas.app.UserManager;
import com.example.lyfuelgas.bean.CustomerObject;
import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.bean.MeasureObject;
import com.example.lyfuelgas.bean.RequestObject;
import com.example.lyfuelgas.bean.RequestSortObject;
import com.example.lyfuelgas.bean.RequestTermObject;
import com.example.lyfuelgas.bean.SupplierObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.http.ResponsePageObject;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.IModel;
import com.example.lyfuelgas.contact.HomeAvgeContact;
import com.example.lyfuelgas.fragment.HomeAvgeFragment;
import com.example.lyfuelgas.model.CustomerModel;
import com.example.lyfuelgas.model.DeviceModel;
import com.example.lyfuelgas.model.MeasureModel;
import com.example.lyfuelgas.model.SupplierModel;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeAvgePresenter extends BasePresenter<HomeAvgeFragment> implements HomeAvgeContact.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new MeasureModel(getIView().mContext),
                new CustomerModel(getIView().mContext),
                new SupplierModel(getIView().mContext),
                new DeviceModel(getIView().mContext));
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("measure", models[0]);
        map.put("customer",models[1]);
        map.put("supplier",models[2]);
        map.put("device",models[3]);
        return map;
    }



    @Override
    public void getMeasureInfo(String imei) {
        RequestObject requestObject = new RequestObject();
        requestObject.pageSize = 1;
        RequestTermObject requestTermObject = new RequestTermObject();
        requestTermObject.column = "imei";
        requestTermObject.type = "and";
        requestTermObject.termType = "eq";
        requestTermObject.value = imei;
        requestObject.terms = new ArrayList<RequestTermObject>();
        requestObject.terms.add(requestTermObject);

        requestObject.sorts = new ArrayList<RequestSortObject>();
        RequestSortObject requestSortObject = new RequestSortObject();
        requestSortObject.name = "createTime";
        requestSortObject.order = "desc";
        requestObject.sorts.add(requestSortObject);


        ((MeasureModel)(mModels.get("measure"))).getList(requestObject,new BaseSubscriber<BaseResponse<ResponsePageObject<MeasureObject>>>(getIView().mContext){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(BaseResponse<ResponsePageObject<MeasureObject>> responsePageObjectBaseResponse) {
                super.onNext(responsePageObjectBaseResponse);
                if(null != responsePageObjectBaseResponse && responsePageObjectBaseResponse.isSuccess()){
                    if(null != responsePageObjectBaseResponse.result && null != responsePageObjectBaseResponse.result.data && responsePageObjectBaseResponse.result.data.size() > 0){
                        getIView().onGetMeasureInfoSuccess(responsePageObjectBaseResponse.result.data.get(0));
                    }else {
                        getIView().onGetMeasureInfoSuccess(null);
                    }
                }
            }
        });
    }

    @Override
    public void getCustomer() {
        RequestObject requestObject = new RequestObject();
        requestObject.pageSize = 1;
        RequestTermObject requestTermObject = new RequestTermObject();
        requestTermObject.column = "userId";
        requestTermObject.type = "and";
        requestTermObject.termType = "eq";
        requestTermObject.value = UserManager.getInstance().getUserId();
        requestObject.terms = new ArrayList<RequestTermObject>();
        requestObject.terms.add(requestTermObject);

        ((CustomerModel)(mModels.get("customer"))).getList(requestObject,new BaseSubscriber<BaseResponse<ResponsePageObject<CustomerObject>>>(getIView().mContext){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(BaseResponse<ResponsePageObject<CustomerObject>> customerObjectResponsePageObject) {
                super.onNext(customerObjectResponsePageObject);
                if(null != customerObjectResponsePageObject && customerObjectResponsePageObject.isSuccess()){
                    if(null != customerObjectResponsePageObject.result && null != customerObjectResponsePageObject.result.data && customerObjectResponsePageObject.result.data.size() > 0){
                        CustomerManager.getInstance().setCustomer(customerObjectResponsePageObject.result.data.get(0));
                    }
                }
            }
        });

    }

    @Override
    public void getSupplier(String supplierId) {
        ((SupplierModel)(mModels.get("supplier"))).getSupplier(supplierId,new BaseSubscriber<BaseResponse<SupplierObject>>(getIView().mContext){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(BaseResponse<SupplierObject> supplierObjectBaseResponse) {
                super.onNext(supplierObjectBaseResponse);
                if(null != supplierObjectBaseResponse && supplierObjectBaseResponse.isSuccess()){
                    SupplierManager.getInstance().setSupplier(supplierObjectBaseResponse.result);
                }
            }
        });
    }

    @Override
    public void getDeviceInfo(String id) {
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
            public void onNext(BaseResponse<DeviceObject> deviceObjectBaseResponse) {
                super.onNext(deviceObjectBaseResponse);
                if(null != deviceObjectBaseResponse && deviceObjectBaseResponse.isSuccess()){
                    getIView().onGetDeviceInfoSuccess(deviceObjectBaseResponse.result);
                }
            }
        });
    }
}
