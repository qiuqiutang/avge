package com.example.lyfuelgas.presenter;

import com.example.lyfuelgas.app.UserManager;
import com.example.lyfuelgas.bean.OrderObject;
import com.example.lyfuelgas.bean.RequestObject;
import com.example.lyfuelgas.bean.RequestSortObject;
import com.example.lyfuelgas.bean.RequestTermObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.IModel;
import com.example.lyfuelgas.constant.PageConstant;
import com.example.lyfuelgas.contact.DeliverHomeMapContact;
import com.example.lyfuelgas.fragment.DeliverHomeMapFragment;
import com.example.lyfuelgas.model.OrderModel;

import java.util.ArrayList;
import java.util.HashMap;


public class DeliverHomeMapPresenter extends BasePresenter<DeliverHomeMapFragment> implements DeliverHomeMapContact.Presenter {
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
    public void getOrderList() {
        RequestObject requestObject = new RequestObject();
        requestObject.pageSize = PageConstant.PAGE_SIZE;
        RequestTermObject requestTermObject = new RequestTermObject();
        requestTermObject.column = "status";
        requestTermObject.type = "and";
        requestTermObject.termType = "eq";
        requestTermObject.value = 0;

      /*  RequestTermObject requestTermObject2 = new RequestTermObject();
        requestTermObject2.column = "personId";
        requestTermObject2.type = "and";
        requestTermObject2.termType = "eq";
        requestTermObject2.value = UserManager.getInstance().getPersonId();*/

        requestObject.terms = new ArrayList<RequestTermObject>();
        requestObject.terms.add(requestTermObject);
        //requestObject.terms.add(requestTermObject2);

        requestObject.sorts = new ArrayList<RequestSortObject>();
        RequestSortObject requestSortObject = new RequestSortObject();
        requestSortObject.name = "createTime";
        requestSortObject.order = "desc";
        requestObject.sorts.add(requestSortObject);

        ((OrderModel)(mModels.get("order"))).getAllDeliverOrder(requestObject,new BaseSubscriber<BaseResponse<ArrayList<OrderObject>>>(getIView().mContext){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(BaseResponse<ArrayList<OrderObject>> orderObjectResponsePageObject) {
                super.onNext(orderObjectResponsePageObject);
                if(null != orderObjectResponsePageObject && orderObjectResponsePageObject.isSuccess()){
                    getIView().onGetOrderListSuccess(orderObjectResponsePageObject.result);
                }
            }
        });
    }
}
