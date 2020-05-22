package com.example.lyfuelgas.common.exit;

import android.content.Context;
import android.widget.Toast;

import com.example.lyfuelgas.R;


/**
 * 连续两次返回退出程序
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */
public class PressExit {
	
	private static Exit mExit = new Exit();

	public static void pressAgainExit(Context context) {
		if (mExit.isExit()) {
			if (ExitApp.getInstance().getActivityList() != null) {
				if (ExitApp.getInstance().getActivityList().size() != 0) { 
					ExitApp.getInstance().exit();  
				} else {
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			} else {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		} else {
			Toast.makeText(context, R.string.hint_click_quit, Toast.LENGTH_SHORT).show();
			mExit.doExitInOneSecond();
		}
	}

}