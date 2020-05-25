package com.example.lyfuelgas.view;


import android.app.Activity;


import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


/**
 * Poi图层类。在高德地图API里，如果要显示Poi，可以用此类来创建Poi图层。如不满足需求，也可以自己创建自定义的Poi图层。
 */
public abstract class MapPoiOverlay {
	private AMap aMap;

	private List<MarkerOptions> mOverlayOptionList = null;

	List<Marker> mOverlayList = null;
	/**
	 * 通过此构造函数创建Poi图层。
	 * @param aMap 地图对象。
	 */
	public MapPoiOverlay(AMap aMap) {
		this.aMap = aMap;
		if (mOverlayOptionList == null) {
			mOverlayOptionList = new ArrayList<MarkerOptions>();
		}
		if (mOverlayList == null) {
			mOverlayList = new ArrayList<Marker>();
		}
	}
	/**
	 * 将所有Overlay 添加到地图上
	 */
	public final void addToMap() {
		if (aMap == null) {
			return;
		}

		removeFromMap();
		List<MarkerOptions> overlayOptions = getOverlayOptions();
		if (overlayOptions != null) {
			mOverlayOptionList.addAll(getOverlayOptions());
		}

		for (MarkerOptions option : mOverlayOptionList) {
			mOverlayList.add(aMap.addMarker(option));
		}
	}

	/**
	 * 将所有Overlay 从 地图上消除
	 */
	public final void removeFromMap() {
		if (aMap == null) {
			return;
		}
		for (Marker marker : mOverlayList) {
			marker.remove();
		}
		mOverlayOptionList.clear();
		mOverlayList.clear();

	}


	public List<Marker> getmOverlayList(){
		return this.mOverlayList;
	}

	/**
	 * 缩放地图，使所有Overlay都在合适的视野内
	 * <p>
	 * 注： 该方法只对Marker类型的overlay有效
	 * </p>
	 *
	 */
	public void zoomToSpan(Activity activity) {
		if (aMap == null) {
			return;
		}
		if (mOverlayList.size() > 0) {
			if(mOverlayList.size() == 1){
				aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mOverlayList.get(0).getPosition(),17));
				return;
			}
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			for (Marker overlay : mOverlayList) {
				// polyline 中的点可能太多，只按marker 缩放
				if (overlay instanceof Marker) {
					builder.include(((Marker) overlay).getPosition());
				}
			}
			int width = activity.getResources().getDisplayMetrics().widthPixels;
			int height = activity.getResources().getDisplayMetrics().heightPixels;
			int padding = (int) (width * 0.12);
			aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),width,height,padding));
		}

	}

	/**
	 * 覆写此方法设置要管理的Overlay列表
	 *
	 * @return 管理的Overlay列表
	 */
	public abstract List<MarkerOptions> getOverlayOptions();

	/**
	 * 给第几个Marker设置图标，并返回更换图标的图片。如不用默认图片，需要重写此方法。
	 * @param index 第几个Marker。
	 * @return 更换的Marker图片。
	 */
	protected abstract BitmapDescriptor getBitmapDescriptor(int index);

	/**
     * 修改站点图标
	 */
	protected abstract void refreshOverlayIcons();



}
