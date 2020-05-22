package com.example.lyfuelgas.common;

import android.os.Handler;
import android.os.Message;

import com.example.lyfuelgas.common.mvp.MVPBaseActivity;

import java.lang.ref.WeakReference;

import androidx.fragment.app.Fragment;

public abstract class BaseHandler extends Handler {
    protected WeakReference<MVPBaseActivity> activityWeakReference;
    protected WeakReference<Fragment> fragmentWeakReference;

    private BaseHandler() {
    }//构造私有化,让调用者必须传递一个Activity 或者 Fragment的实例

    public BaseHandler(MVPBaseActivity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    public BaseHandler(Fragment fragment) {
        this.fragmentWeakReference = new WeakReference<>(fragment);
    }

    @Override
    public void handleMessage(Message msg) {
        if(null != activityWeakReference){
            MVPBaseActivity activity = activityWeakReference.get();
            if(null == activity || activity.isFinishing()){
            }else {
                handleMessage(msg, msg.what);
            }
        }
        if(null != fragmentWeakReference){
            Fragment fragment = fragmentWeakReference.get();
            if(null == fragment || fragment.isRemoving()){
            }else {
                handleMessage(msg, msg.what);
            }
        }
    }

    /**
     * 抽象方法用户实现,用来处理具体的业务逻辑
     *
     * @param msg
     * @param what
     */
    public abstract void handleMessage(Message msg, int what);
}
