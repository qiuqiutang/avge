package com.example.lyfuelgas.fragment;

import android.os.Bundle;
import android.view.View;

import com.dunyue.xrecyclerview.XRecyclerView;
import com.example.lyfuelgas.R;
import com.example.lyfuelgas.adapter.item.OrderItem;
import com.example.lyfuelgas.bean.OrderObject;
import com.example.lyfuelgas.common.adapter.SimpleAdapter;
import com.example.lyfuelgas.common.mvp.MVPBaseFragment;
import com.example.lyfuelgas.common.utils.CallPhone;
import com.example.lyfuelgas.common.utils.eventbus.EventBusUtils;
import com.example.lyfuelgas.common.utils.eventbus.EventType;
import com.example.lyfuelgas.common.utils.eventbus.MyEvent;
import com.example.lyfuelgas.contact.OrderContact;
import com.example.lyfuelgas.presenter.OrderPresenter;
import com.example.lyfuelgas.view.CustomConfirmDialog;
import com.example.lyfuelgas.view.SpaceItemDecoration;
import com.example.lyfuelgas.view.TokenExpiredDialog;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;

public class OrderFragment extends MVPBaseFragment<OrderPresenter> implements OrderContact.View {
    @BindView(R.id.rvList)
    XRecyclerView rvList;

    ArrayList<OrderObject> mList;
    SimpleAdapter mAdapter;

    private int page = 0;
    private int status = 0;

    public static OrderFragment getInstance(int status){
        OrderFragment orderFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        orderFragment.setArguments(bundle);
        return orderFragment;
    }
    @Override
    protected int getContentLayout() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initData() {
        status = getArguments().getInt("status");
        mList = new ArrayList<>();
        mAdapter = new SimpleAdapter(mList, OrderItem.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvList.setLayoutManager(layoutManager);
        rvList.addItemDecoration(new SpaceItemDecoration(mContext,16));
        rvList.setAdapter(mAdapter);
        rvList.setLoadingMoreEnabled(false);
        View emptyView = View.inflate(mContext,R.layout.layout_empty,null);
        rvList.setEmptyView(emptyView);
        mPresenter.getOrderList(page,status);
    }

    @Override
    protected void initEvent() {
        rvList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 0;
                mPresenter.getOrderList(page,status);
            }

            @Override
            public void onLoadMore() {
                page ++;
                mPresenter.getOrderList(page,status);
            }
        });
        mAdapter.setExcessiveData(new OrderItem.OnExcessiveListener(){

            @Override
            public void onCallSupplier(OrderObject orderObject) {
                CustomConfirmDialog customConfirmDialog = CustomConfirmDialog.newInstance("确定要联系供应商？");
                customConfirmDialog.setOnConfirmClickListener(new CustomConfirmDialog.OnConfirmClickListener() {
                    @Override
                    public void onCallBack() {
                        CallPhone.call(getActivity(),orderObject.supplierMobile);
                    }
                });
                customConfirmDialog.show(getChildFragmentManager(),"exitDialog");
            }
        });
    }

    @Override
    protected OrderPresenter loadPresenter() {
        return new OrderPresenter();
    }

    @Override
    public void onGetOrderListFail() {
        rvList.refreshComplete();
        rvList.loadMoreComplete();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetOrderListSuccess(ArrayList<OrderObject> data, boolean hasNext) {
        if(0 == page){
            mList.clear();
        }
        if(null != data) {
            mList.addAll(data);
        }
        rvList.refreshComplete();
        rvList.loadMoreComplete();
        rvList.setLoadingMoreEnabled(hasNext);
        mAdapter.notifyDataSetChanged();
    }


    @Subscribe
    public void onEventRefreshOrder(MyEvent event) {
        if (EventType.REFRESH_ORDER == event.enventType) {
            page = 0;
            mPresenter.getOrderList(page,status);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }
}
