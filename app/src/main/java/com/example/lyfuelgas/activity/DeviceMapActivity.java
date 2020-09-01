package com.example.lyfuelgas.activity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.lyfuelgas.R;
import com.example.lyfuelgas.bean.DeviceObject;
import com.example.lyfuelgas.common.mvp.MVPBaseActivity;
import com.example.lyfuelgas.common.utils.GsonUtils;
import com.example.lyfuelgas.common.utils.LyLog;
import com.example.lyfuelgas.common.utils.LyToast;
import com.example.lyfuelgas.contact.DeviceMapContact;
import com.example.lyfuelgas.presenter.DeviceMapPresenter;
import com.example.lyfuelgas.view.SimpleToolbar;

import butterknife.BindView;

public class DeviceMapActivity extends MVPBaseActivity<DeviceMapPresenter> implements DeviceMapContact.View,
        LocationSource,
        AMapLocationListener,
        GeocodeSearch.OnGeocodeSearchListener {
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.simple_toolbar)
    SimpleToolbar simpleToolbar;
    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private GeocodeSearch geocoderSearch;

    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);

    private DeviceObject deviceObject;
    private boolean isFistLoad = true;
    private LatLng myLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceObject = (DeviceObject) getIntent().getSerializableExtra("device");
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
    }

    @Override
    protected DeviceMapPresenter loadPresenter() {
        return new DeviceMapPresenter();
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_device_map;
    }

    @Override
    protected void initData() {
        simpleToolbar.setMainTitle("设备位置上报");
        simpleToolbar.setRightTitleText("提交");
        simpleToolbar.setRightTitleColor(Color.WHITE);
    }

    @Override
    protected void initEvent() {
        simpleToolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null == deviceObject)
                    return;
                String gps = "";
                if(null != myLocation){
                    gps = myLocation.longitude+","+myLocation.latitude;
                }
                mPresenter.setDeviceAddress(deviceObject.id,etAddress.getText().toString().trim(), gps);
            }
        });
        simpleToolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
            aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    LyLog.e("====onCameraChange---", GsonUtils.toJsonString(cameraPosition));
                }

                @Override
                public void onCameraChangeFinish(CameraPosition cameraPosition) {
                    LyLog.e("====onCameraChangeFinish", GsonUtils.toJsonString(cameraPosition));
                    parseAddress(cameraPosition.target);
                }
            });
        }

        // mLocationErrText = (TextView)findViewById(R.id.location_errInfo_text);
        //mLocationErrText.setVisibility(View.GONE);
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                Log.e("====onMyLocationChange",location.toString());
            }
        });
        setupLocationStyle();
    }

    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
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
        deactivate();
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
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                Log.e("====",amapLocation.getAddress()+"==="+amapLocation.getLatitude()+","+amapLocation.getLongitude());
                //latLng = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
                //parseAddress();
                if(isFistLoad) {
                    etAddress.setText(amapLocation.getAddress());
                    mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                    isFistLoad = false;
                }
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
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


    private void parseAddress(LatLng latLng){
        myLocation = latLng;
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLng.latitude,latLng.longitude), 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                String addressName = result.getRegeocodeAddress().getFormatAddress();
                etAddress.setText(addressName);
                //regeoMarker.setPosition(AMapUtil.convertToLatLng(latLonPoint));
                Log.e(TAG, addressName);
            } else {
                Log.e(TAG, "R.string.no_result");
            }
        } else {
            Log.e(TAG, "code:"+rCode);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onSetDeviceSuccess() {
        LyToast.longToast(getApplicationContext(),"位置设置成功");
        finish();
    }
}

