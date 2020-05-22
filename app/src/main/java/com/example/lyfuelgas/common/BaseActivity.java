package com.example.lyfuelgas.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.lyfuelgas.common.exit.ExitApp;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Activity基类
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */
public abstract class BaseActivity extends AppCompatActivity {
	protected String TAG = getClass().getSimpleName();

	public Context mContext;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mContext = this;
		ExitApp.getInstance().addActivity(this);
	}

	/**
	 * close soft input panel
	 */
	public void hideSoftInput() {
		View view = getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager imeManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imeManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/**
	 * 打开一个Activity 默认 不关闭当前activity,不带任何参数
	 * @param clz
	 */
	public void launchActivity(Class<?> clz) {
		launchActivity(clz, false);
	}

	/**
	 * 打开一个Activity,默认 不带任何参数
	 * @param clz
	 * @param isCloseCurrentActivity 是否关闭当前activity
	 */
	public void launchActivity(Class<?> clz, boolean isCloseCurrentActivity) {
		launchActivity(clz, isCloseCurrentActivity, null);
	}

	/**
	 * 打开一个Activity
	 * @param clz
	 * @param isCloseCurrentActivity 是否关闭当前activity
	 * @param ex 传的参数
	 */
	public void launchActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
		Intent intent = new Intent(this, clz);
		if (ex != null) {
			intent.putExtras(ex);
		}
		startActivity(intent);
		if (isCloseCurrentActivity) {
			finish();
		}
	}

	/**
	 * [含有Bundle通过Class打开编辑界面]
	 *
	 * @param cls
	 * @param bundle
	 * @param requestCode
	 */
	public void launchActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		ExitApp.getInstance().getActivityList().remove(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			hideSoftInput();
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		//TACAnalyticsService.getInstance().trackPageAppear(this, mStrPackageName);

	}

	@Override
	protected void onPause() {
		super.onPause();
		//TACAnalyticsService.getInstance().trackPageDisappear(this, mStrPackageName);
	}



}
