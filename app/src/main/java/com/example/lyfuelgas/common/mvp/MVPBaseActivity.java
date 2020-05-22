package com.example.lyfuelgas.common.mvp;

import android.os.Bundle;

import com.example.lyfuelgas.common.BaseActivity;

import butterknife.ButterKnife;


/**
 * MVP Activity基类
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */
public abstract class MVPBaseActivity<P extends BasePresenter> extends BaseActivity implements IView {
	protected String TAG = getClass().getSimpleName();

	protected P mPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(getContentLayout());
		ButterKnife.bind(this);
		mPresenter = loadPresenter();
		initCommonData();
		initData();
		initEvent();


	}

	/**
	 * 加载presenter
	 * @return
	 */
	protected abstract P loadPresenter();
	/**
	 * 获取加载的布局资源id
	 */
	protected abstract int getContentLayout();

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	/**
	 * 初始化事件
	 */
	protected abstract void initEvent();



	private void initCommonData() {
		if (mPresenter != null) {
			mPresenter.attachView(this);
		}
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPresenter != null) {
			mPresenter.detachView();
		}
	}



	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}


}
