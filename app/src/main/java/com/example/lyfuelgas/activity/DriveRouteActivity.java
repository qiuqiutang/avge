package com.example.lyfuelgas.activity;

import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DistanceItem;
import com.amap.api.services.route.DistanceResult;
import com.amap.api.services.route.DistanceSearch;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.example.lyfuelgas.R;
import com.example.lyfuelgas.bean.OrderObject;
import com.example.lyfuelgas.common.mvp.BasePresenter;
import com.example.lyfuelgas.common.mvp.MVPBaseActivity;
import com.example.lyfuelgas.common.utils.ProgressDialogUtils;
import com.example.lyfuelgas.view.route.DrivingRouteOverLay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DriveRouteActivity extends MVPBaseActivity implements DistanceSearch.OnDistanceSearchListener, RouteSearch.OnRouteSearchListener {
    @BindView(R.id.route_map)
    MapView mapView;


    private AMap aMap;

    private List<OrderObject> orderObjectList;
    private List<OrderObject> tempOrderList = new ArrayList<>();

    LatLonPoint mOriginPoint;
    List<LatLonPoint> stationPoints;
    DistanceSearch distanceSearch;
    DistanceSearch.DistanceQuery distanceQuery = new DistanceSearch.DistanceQuery();


    private DriveRouteResult mDriveRouteResult;
    private RouteSearch mRouteSearch;
    private LatLonPoint mStartPoint,mEndPoint;
    private List<LatLonPoint> passPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        orderObjectList = (List<OrderObject>) getIntent().getSerializableExtra("list");
        double longtitude = getIntent().getDoubleExtra("longtitude",0);
        double latitude = getIntent().getDoubleExtra("latitude",0);
        mStartPoint = new LatLonPoint(latitude,longtitude);
        mOriginPoint = new LatLonPoint(latitude,longtitude);
        stationPoints = new ArrayList<>();
        for (OrderObject orderObject : orderObjectList){
            LatLng latLng = orderObject.getLatLng();
            stationPoints.add(new LatLonPoint(latLng.latitude, latLng.longitude));
        }
        distanceSearch = new DistanceSearch(mContext);
        distanceSearch.setDistanceSearchListener(this);
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        ProgressDialogUtils.showProgressDialog(mContext,"路径正在规划中……");
        calculateDistance();
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_drive_route;
    }

    @Override
    protected void initData() {

    }

    private void calculateDistance(){
        //设置起点和终点，其中起点支持多个
        distanceQuery.setOrigins(stationPoints);
        distanceQuery.setDestination(mOriginPoint);
        //设置测量方式，支持直线和驾车
        distanceQuery.setType(DistanceSearch.TYPE_DRIVING_DISTANCE);
        distanceSearch.calculateRouteDistanceAsyn(distanceQuery);
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (mStartPoint == null) {
            return;
        }
        if (mEndPoint == null) {
            //LyToast.show(mContext, "终点未设置");
        }
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == 2) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, passPoint,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.ivBack})
    public void onClick(View v){
        finish();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onDistanceSearched(DistanceResult distanceResult, int index) {
        if(1000 == index) {
            if (null != distanceResult && null != distanceResult.getDistanceResults()) {
                List<DistanceItem> distanceItems = distanceResult.getDistanceResults();
                for (int i = 0; i < distanceItems.size(); i++) {
                    DistanceItem item = distanceItems.get(i);
                    orderObjectList.get(i).distance = item.getDistance();
                }
                Collections.sort(orderObjectList);
                tempOrderList.add(orderObjectList.get(0));
                LatLng latLng = orderObjectList.get(0).getLatLng();
                mOriginPoint = new LatLonPoint(latLng.latitude,latLng.longitude);
                orderObjectList.remove(0);
                stationPoints.clear();
                for (OrderObject orderObject : orderObjectList){
                    LatLng latLng1 = orderObject.getLatLng();
                    stationPoints.add(new LatLonPoint(latLng1.latitude, latLng1.longitude));
                }
                if(orderObjectList.size() > 1) {
                    calculateDistance();
                }else {
                    passPoint = new ArrayList<>();
                    for (OrderObject orderObject : tempOrderList){
                        LatLng latLng1 = orderObject.getLatLng();
                        passPoint.add(new LatLonPoint(latLng1.latitude,latLng1.longitude));
                    }
                    mEndPoint = passPoint.get(passPoint.size()-1);
                    searchRouteResult(2, RouteSearch.DRIVING_SINGLE_SHORTEST);
                }
            }else {
                ProgressDialogUtils.closeProgressDialog();
            }

        }else {
            ProgressDialogUtils.closeProgressDialog();
        }

        /*passPoint = new ArrayList<>();
        for (OrderObject orderObject : orderObjectList){
            LatLng latLng = orderObject.getLatLng();
            passPoint.add(new LatLonPoint(latLng.latitude,latLng.longitude));
        }
        mEndPoint = passPoint.get(passPoint.size()-1);
        searchRouteResult(2, RouteSearch.DRIVING_SINGLE_SHORTEST);*/
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverLay drivingRouteOverlay = new DrivingRouteOverLay(
                            mContext, aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), passPoint);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    /*mBottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur)+"("+AMapUtil.getFriendlyLength(dis)+")";
                    mRotueTimeDes.setText(des);
                    mRouteDetailDes.setVisibility(View.VISIBLE);
                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
                    mRouteDetailDes.setText("打车约"+taxiCost+"元");
                    mBottomLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,
                                    DriveRouteDetailActivity.class);
                            intent.putExtra("drive_path", drivePath);
                            intent.putExtra("drive_result",
                                    mDriveRouteResult);
                            startActivity(intent);
                        }
                    });*/
                } else if (result != null && result.getPaths() == null) {
                    //ToastUtil.show(mContext, R.string.no_result);
                }

            } else {
               // ToastUtil.show(mContext, R.string.no_result);
            }
        } else {
            //ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }
        ProgressDialogUtils.closeProgressDialog();
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}
