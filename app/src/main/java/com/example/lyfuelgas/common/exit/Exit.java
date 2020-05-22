package com.example.lyfuelgas.common.exit;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * 记录连续两次返回操作
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */
public class Exit {

	private boolean mIsExit = false;
	private Runnable mTask = new Runnable() {
		@Override
		public void run() {
			mIsExit = false;
		}
	};

	public void doExitInOneSecond() {
		mIsExit = true;
		HandlerThread thread = new HandlerThread("doTask");
		thread.start();
		new Handler(thread.getLooper()).postDelayed(mTask, 2000);
	}

	public boolean isExit() {
		return mIsExit;
	}

	public void setExit(boolean isExit) {
		this.mIsExit = isExit;
	}
}