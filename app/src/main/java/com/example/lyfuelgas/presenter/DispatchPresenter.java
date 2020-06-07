package com.example.lyfuelgas.presenter;

import com.example.lyfuelgas.app.UserManager;
import com.example.lyfuelgas.bean.OrderObject;
import com.example.lyfuelgas.bean.RequestObject;
import com.example.lyfuelgas.bean.RequestSortObject;
import com.example.lyfuelgas.bean.RequestTermObject;
import com.example.lyfuelgas.common.http.BaseResponse;
import com.example.lyfuelgas.common.http.BaseSubscriber;
import com.example.lyfuelgas.common.http.ResponsePageObject;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.IModel;
import com.example.lyfuelgas.constant.PageConstant;
import com.example.lyfuelgas.contact.DispatchContact;
import com.example.lyfuelgas.fragment.DispatchFragment;
import com.example.lyfuelgas.model.OrderModel;

import java.util.ArrayList;
import java.util.HashMap;


public class DispatchPresenter extends BasePresenter<DispatchFragment> implements DispatchContact.Presenter {
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
    public void getOrderList(int page, int status) {
        RequestObject requestObject = new RequestObject();
        requestObject.pageIndex = page;
        requestObject.pageSize = PageConstant.PAGE_SIZE;
        RequestTermObject requestTermObject = new RequestTermObject();
        requestTermObject.column = "status";
        requestTermObject.type = "and";
        requestTermObject.termType = "eq";
        requestTermObject.value = status;

        /*RequestTermObject requestTermObject2 = new RequestTermObject();
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

        ((OrderModel)(mModels.get("order"))).getList(requestObject,new BaseSubscriber<BaseResponse<ResponsePageObject<OrderObject>>>(getIView().mContext){
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                getIView().onGetOrderListFail();
            }

            @Override
            public void onNext(BaseResponse<ResponsePageObject<OrderObject>> orderObjectResponsePageObject) {
                super.onNext(orderObjectResponsePageObject);
                if(null != orderObjectResponsePageObject && orderObjectResponsePageObject.isSuccess()){
                    if(null != orderObjectResponsePageObject.result && null != orderObjectResponsePageObject.result.data){
                        getIView().onGetOrderListSuccess(orderObjectResponsePageObject.result.data,(page+1)*PageConstant.PAGE_SIZE < orderObjectResponsePageObject.result.total);
                    }else {
                        getIView().onGetOrderListFail();
                    }
                }else {
                    getIView().onGetOrderListFail();
                }
            }
        });
    }
}
