package com.example.lyfuelgas.common;

import android.content.Context;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.common.utils.TelephonyUtils;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.HttpException;
import rx.Subscription;

/**
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */

public abstract class BaseModel implements LifeCycle {
    public LinkedList<Subscription> mListSubscription;
    public Context mContext;


    protected BaseModel(Context context) {
        mListSubscription = new LinkedList<>();
        this.mContext = context;
    }



    public Context getContext() {
        return mContext;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        for (Subscription s : mListSubscription) {
            if (!s.isUnsubscribed()) {
                s.unsubscribe();
            }
        }
        mListSubscription.clear();
    }

    /**
     * 维护网络请求生命周期
     *
     * @param subscription 一次网络请求
     */
    public void putSubscription(Subscription subscription) {
        mListSubscription.add(subscription);
        Iterator<Subscription> iterator = mListSubscription.iterator();
        while (iterator.hasNext()) {
            Subscription next = iterator.next();
            if (next.isUnsubscribed()) {
                iterator.remove();
            }
        }
    }

    /**
     * 获取网络请求异常数据
     *
     * @param e 异常信息
     * @return 转换成用户友好提示信息
     */
    public String getNetworkErrorMsg(Throwable e) {
        return getNetworkErrorMsg(mContext, e);
    }

    /**
     * 获取网络请求异常数据
     *
     * @param mContext 上下文
     * @param e        异常信息
     * @return 转换成用户友好提示信息
     */
    public static String getNetworkErrorMsg(Context mContext, Throwable e) {
        int resId;
        if (e.getClass() == RuntimeException.class) {
            return e.getMessage();
        } else if ((e instanceof ConnectException || e instanceof UnknownHostException || e instanceof IOException)
                && TelephonyUtils.getNetStatus(mContext) < 0) {
            resId = R.string.msg_connection_no_network;
        } else if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            resId = R.string.msg_connection_timeout;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            resId = R.string.msg_connection_pass_failed;
        } else if (e instanceof HttpException) {
            resId = R.string.msg_connection_not_2xx;
        } else {
            resId = R.string.msg_connection_unknown;
        }
        e.printStackTrace();
        return mContext.getString(resId);
    }


    public RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }


}