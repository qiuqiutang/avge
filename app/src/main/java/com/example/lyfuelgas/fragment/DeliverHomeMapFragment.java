package com.example.lyfuelgas.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.lyfuelgas.R;
import com.example.lyfuelgas.activity.DriveRouteActivity;
import com.example.lyfuelgas.activity.FillActivity;
import com.example.lyfuelgas.bean.OrderObject;
import com.example.lyfuelgas.common.mvp.MVPBaseFragment;
import com.example.lyfuelgas.common.utils.CallPhone;
import com.example.lyfuelgas.contact.DeliverHomeMapContact;
import com.example.lyfuelgas.presenter.DeliverHomeMapPresenter;
import com.example.lyfuelgas.view.CustomConfirmDialog;
import com.example.lyfuelgas.view.MapPoiOverlay;
import com.example.lyfuelgas.view.OrderDetailDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;

public class DeliverHomeMapFragment extends MVPBaseFragment<DeliverHomeMapPresenter> implements DeliverHomeMapContact.View,
        AMap.OnMarkerClickListener,
        LocationSource,
        AMapLocationListener {
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.tvRoutePlan)
    TextView tvRoutePlan;


    AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    //画站点
    private MyPoiOverlay mPoiOverlay;
    private ArrayList<OrderObject> poiInfos;


    OrderDetailDialog orderDetailDialog;
    OnDeliverMapListener onDeliverMapListener;

    private boolean isFistLoad = true;
    LatLng myLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        return view;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_deliver_home_map;
    }

    @Override
    protected void initData() {
        aMap = mapView.getMap();
        setUpMap();
        mPresenter.getOrderList();
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.gps_point));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLUE);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(50, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setZoomControlsEnabled(false);//设置右下角缩放按钮是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

    }


    @Override
    protected void initEvent() {
        aMap.setOnMarkerClickListener(this);
    }

    @OnClick({R.id.tvRoutePlan})
    public void onClick(View v){
        if(null == myLocation)
            return;
        if(null == poiInfos || poiInfos.isEmpty())
            return;
        Bundle bundle = new Bundle();
        bundle.putSerializable("list",poiInfos);
        bundle.putDouble("latitude",myLocation.latitude);
        bundle.putDouble("longtitude",myLocation.longitude);
        launchActivity(DriveRouteActivity.class,false,bundle);
    }

    @Override
    protected DeliverHomeMapPresenter loadPresenter() {
        return new DeliverHomeMapPresenter();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onGetOrderListFail() {

    }

    @Override
    public void onGetOrderListSuccess(ArrayList<OrderObject> data) {
        if(null == poiInfos){
            poiInfos = new ArrayList<>();
        }
        poiInfos.clear();
        if(null != data && !data.isEmpty()){
            for (OrderObject orderObject : data){
                if(null != orderObject.getLatLng()){
                    poiInfos.add(orderObject);
                }
            }
        }
        if(null == mPoiOverlay){
            mPoiOverlay = new MyPoiOverlay(aMap,poiInfos);
        }
        mPoiOverlay.removeFromMap();
        mPoiOverlay.setPois(poiInfos);
        if (poiInfos.size() > 0) {
            mPoiOverlay.addToMap();
            zoomToSpan();
            tvRoutePlan.setVisibility(View.VISIBLE);
        }else {
            tvRoutePlan.setVisibility(View.GONE);
        }

        setUpMap();

    }


    /////////////////////////////////////////////////////
    /////////////////////画站点 begin////////////////////
    /////////////////////////////////////////////////////
    private void zoomToSpan(){
        if(null != mPoiOverlay){
            mPoiOverlay.zoomToSpan(getActivity());
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (OrderObject orderObject : poiInfos){
            if(marker.getTitle().equals(orderObject.id)){
                if(null == orderDetailDialog){
                    orderDetailDialog = new OrderDetailDialog(getActivity());
                }
                orderDetailDialog.setOnInnerListener(new OrderDetailDialog.OnInnerListener() {
                    @Override
                    public void onCall(String strMobile) {
                        CustomConfirmDialog customConfirmDialog = CustomConfirmDialog.newInstance("确定要联系客户？");
                        customConfirmDialog.setOnConfirmClickListener(new CustomConfirmDialog.OnConfirmClickListener() {
                            @Override
                            public void onCallBack() {
                                CallPhone.call(getActivity(),strMobile);
                            }
                        });
                        customConfirmDialog.show(getChildFragmentManager(),"exitDialog");
                    }

                    @Override
                    public void onSubmit(OrderObject orderObject) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("order",orderObject);
                        launchActivityForResult(FillActivity.class,bundle,100);
                        orderDetailDialog.dismiss();
                    }
                });
                View v = getView();
                if(null != onDeliverMapListener){
                    v = onDeliverMapListener.getButtomView();
                }
                orderDetailDialog.show(v);
                orderDetailDialog.setData(orderObject);
                break;
            }
        }
        return true;
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                myLocation = new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
                if(isFistLoad ){
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
                    isFistLoad = false;
                }
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(mContext);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    private class MyPoiOverlay extends MapPoiOverlay {
        private List<OrderObject> mPois;
        /**
         * 通过此构造函数创建Poi图层。
         *
         * @param map 地图对象。
         * @param pois     要在地图上添加的poi。列表中的poi对象详见搜索服务模块的基础核心包（com.amap.api.services.core）中的类<strong> <a href="../../../../../../Search/com/amap/api/services/core/PoiItem.html" title="com.amap.api.services.core中的类">PoiItem</a></strong>。
         */
        public MyPoiOverlay(AMap map, List<OrderObject> pois) {
            super(map);
            mPois = new ArrayList<>();
            if(null != pois){
                mPois.addAll(pois);
            }
        }

        public void setPois(List<OrderObject> pois) {
            this.mPois.clear();
            if(null != pois){
                this.mPois.addAll(pois);
            }
        }

        @Override
        public List<MarkerOptions> getOverlayOptions() {
            if (mPois == null) {
                return null;
            }

            List<MarkerOptions> markerList = new ArrayList<>();

            for (int i = 0; i < mPois.size(); i++) {
                OrderObject poiInfo = mPois.get(i);
                if (poiInfo.getLatLng() == null) {
                    continue;
                }

                Bundle bundle = new Bundle();
                bundle.putInt("index", i);
                markerList.add(new MarkerOptions()
                        .icon(getBitmapDescriptor(i))
                        .zIndex(1)
                        .title(poiInfo.id)
                        .anchor(0.1f,0.5f)
                        .position(poiInfo.getLatLng()));

            }

            return markerList;
        }

        @Override
        protected BitmapDescriptor getBitmapDescriptor(int index) {
            return BitmapDescriptorFactory.fromResource(R.drawable.ico_location);
        }

        @Override
        protected void refreshOverlayIcons() {

        }
    }


    /////////////////////////////////////////////////////
    /////////////////////画站点 end////////////////////
    /////////////////////////////////////////////////////


    public interface OnDeliverMapListener{
        View getButtomView();
    }

    public void setDeliverMapListener(OnDeliverMapListener onDeliverMapListener){
        this.onDeliverMapListener = onDeliverMapListener;
    }
}
