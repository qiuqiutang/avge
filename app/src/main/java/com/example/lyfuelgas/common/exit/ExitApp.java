package com.example.lyfuelgas.common.exit;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Activity队列管理
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */
public class ExitApp  {
	
	private ArrayList<Activity> mActivityList = new ArrayList<Activity>();
	private static ExitApp mInstance;

	public static ExitApp getInstance() {
		if (mInstance == null) {
			mInstance = new ExitApp();
		}
		return mInstance;

	}

	public void addActivity(Activity activity) {
		mActivityList.add(activity);
	}

	public Activity getLastActivity(){
		return null != mActivityList ? mActivityList.get(mActivityList.size()-1) : null;
	}

	public void exit() {
		for (Activity activity : mActivityList) {
			activity.finish();
		}
		System.exit(0);
	}
	
		
	public void finishAll() {
		for (Activity activity : mActivityList) {
			activity.finish();
		}
	}
	
	public void clearActivityList() {
		for (Activity activity : mActivityList) {
			activity.finish();
		}
		mActivityList.clear();
	}
	
	public ArrayList<Activity> getActivityList() {
		return mActivityList;
	}
}
