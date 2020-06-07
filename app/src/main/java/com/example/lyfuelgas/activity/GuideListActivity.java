package com.example.lyfuelgas.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.amap.api.services.core.LatLonPoint;
import com.dunyue.xrecyclerview.XRecyclerView;
import com.example.lyfuelgas.R;
import com.example.lyfuelgas.adapter.item.OrderItem;
import com.example.lyfuelgas.adapter.item.RouteItem;
import com.example.lyfuelgas.bean.OrderObject;
import com.example.lyfuelgas.common.adapter.BaseAdapter;
import com.example.lyfuelgas.common.adapter.SimpleAdapter;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.MVPBaseActivity;
import com.example.lyfuelgas.common.utils.MapUtils;
import com.example.lyfuelgas.view.CustomConfirmDialog;
import com.example.lyfuelgas.view.SimpleToolbar;
import com.example.lyfuelgas.view.SpaceItemDecoration;
import com.example.lyfuelgas.view.choose.PopChooseDialog;
import com.example.lyfuelgas.view.choose.SingleObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class GuideListActivity extends MVPBaseActivity {
    @BindView(R.id.simple_toolbar)
    SimpleToolbar simpleToolbar;
    @BindView(R.id.rvList)
    XRecyclerView rvList;

    ArrayList<OrderObject> mList;
    LatLonPoint mStartPoint;
    OrderObject endStation;
    SimpleAdapter mAdapter;

    private List<SingleObject> chooseOptionList;
    private PopChooseDialog popChooseDialog;


    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_guide_list;
    }

    @Override
    protected void initData() {
        simpleToolbar.setMainTitle("导航站点");
        mList = (ArrayList<OrderObject>) getIntent().getSerializableExtra("list");
        mStartPoint = new LatLonPoint(getIntent().getDoubleExtra("start_lat",0.0d),getIntent().getDoubleExtra("start_long",0.0d));

        mAdapter = new SimpleAdapter(mList, RouteItem.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvList.setLayoutManager(layoutManager);
        rvList.addItemDecoration(new SpaceItemDecoration(mContext,16));
        rvList.setLoadingMoreEnabled(false);
        rvList.setPullRefreshEnabled(false);
        rvList.setAdapter(mAdapter);

    }

    @Override
    protected void initEvent() {
        simpleToolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter.setOnItemClick(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View item, int position) {
                endStation = mList.get(position);
                showPopDialog();
            }
        });
    }

    private void showPopDialog(){
        if(null == chooseOptionList){
            chooseOptionList = new ArrayList<>();

        }
        chooseOptionList.clear();
        if(MapUtils.isGdMapInstalled()){
            chooseOptionList.add(new SingleObject(1,"高德地图"));
        }
        if(MapUtils.isBaiduMapInstalled()){
            chooseOptionList.add(new SingleObject(2,"百度地图"));
        }
        if(MapUtils.isTencentMapInstalled()){
            chooseOptionList.add(new SingleObject(3,"腾讯地图"));
        }
        if(chooseOptionList.isEmpty()){
            //没有安装地图软件
            CustomConfirmDialog customConfirmDialog = CustomConfirmDialog.newInstance("暂时没有可用的地图，下载高德地图");
            customConfirmDialog.setOnConfirmClickListener(new CustomConfirmDialog.OnConfirmClickListener() {
                @Override
                public void onCallBack() {
                    Intent intent ;
                    Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
            customConfirmDialog.show(getSupportFragmentManager(), "CustomConfirmDialog");
            return;
        }
        if(null == popChooseDialog){
            popChooseDialog = new PopChooseDialog(this);
            popChooseDialog.setOnChangeResultListener(new PopChooseDialog.OnChangeResultListener() {
                @Override
                public void onChange(SingleObject singleObject) {
                    LatLonPoint mEndPoint = new LatLonPoint(endStation.getLatLng().latitude,endStation.getLatLng().longitude);
                    switch (singleObject.id){
                        case 1:
                            MapUtils.openGaoDeNavi(mContext,mStartPoint.getLatitude(),mStartPoint.getLongitude(),"我的位置",mEndPoint.getLatitude(),mEndPoint.getLongitude(),endStation.address);
                            break;
                        case 2:
                            MapUtils.openBaiDuNavi(mContext,mStartPoint.getLatitude(),mStartPoint.getLongitude(),"我的位置",mEndPoint.getLatitude(),mEndPoint.getLongitude(),endStation.address);
                            break;
                        case 3:
                            MapUtils.openTencentMap(mContext,mStartPoint.getLatitude(),mStartPoint.getLongitude(),"我的位置",mEndPoint.getLatitude(),mEndPoint.getLongitude(),endStation.address);
                            break;
                    }
                }
            });
            popChooseDialog.setData(chooseOptionList);
        }
        popChooseDialog.show(simpleToolbar);
    }

}
