package com.example.lyfuelgas.common.http;

import android.content.Context;
import android.text.TextUtils;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.common.utils.GsonUtils;
import com.example.lyfuelgas.common.utils.LyLog;
import com.example.lyfuelgas.common.utils.LyToast;
import com.example.lyfuelgas.common.utils.ProgressDialogUtils;
import com.example.lyfuelgas.view.TokenExpiredDialog;

import java.io.IOException;

import retrofit2.HttpException;
import rx.Subscriber;

/**
 * 统一处理处理服务器返回的Code
 * Created on 2018/3/19.
 *
 * @author QiuYan
 * @since 1.0.0
 */

public class BaseSubscriber<T> extends Subscriber<T> {
    private Context mContext;

    public BaseSubscriber(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }



    @Override
    public void onCompleted() {
        if(!isUnsubscribed()){
            unsubscribe();
        }
    }

    @Override
    public void onError(Throwable e) {
        ProgressDialogUtils.closeProgressDialog();
        //Token失效 重新获取
        if (e == null || e.getMessage() == null) {
            LyToast.shortToast(mContext,R.string.msg_connection_unknown);
        } else {
            if(e instanceof HttpException){
                HttpException httpException = (HttpException) e;
                try {
                    String errorBodyStr = httpException.response().errorBody().string();
                    LyLog.e("====HttpException","==//"+errorBodyStr);
                    if(!TextUtils.isEmpty(errorBodyStr)) {
                        BaseResponse errorResponse = GsonUtils.parseJsonToBean(errorBodyStr, BaseResponse.class);
                        if(null != errorResponse && !TextUtils.isEmpty(errorResponse.message)){
                            LyToast.shortToast(mContext,errorResponse.message);
                        }
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getMessage().contains("HTTP 401")) {
                LyLog.e("=========log out",e.getMessage());
                refreshToken();
            } else {
                //ToastUtils.show(mContext, BaseModel.getNetworkErrorMsg(mContext, e));
            }
        }
    }

    @Override
    public void onNext(T t) {
        ProgressDialogUtils.closeProgressDialog();
        if (t == null) {
            //ToastUtils.show(mContext, R.string.msg_connection_not_success);
        } else if (t instanceof BaseResponse) {
            BaseResponse response = (BaseResponse) t;
            if(response.status < 300 && response.status >= 200){
                //返回成功
            }else {
                LyToast.shortToast(mContext,response.message);
            }
            /*if(ApiCode.TOKEN_OVERDUE == response.code){
                ToastUtils.show(mContext, R.string.msg_token_overdue);
                UserManager.getInstance().clearUser();
                Intent intent = new Intent(mContext,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                ArrayList<Activity> mActivityList = ExitApp.getInstance().getActivityList();
                for (Activity activity : mActivityList) {
                    if(activity instanceof LoginActivity) {
                        continue;
                    }
                    activity.finish();
                }
               return;
            }else if (ApiCode.SUCCESS != response.code) {
                ToastUtils.show(mContext, response.msg);
            }*/
        }
    }

    //刷新Token
    private void refreshToken() {
        if (mContext == null) {
            return;
        }
        TokenExpiredDialog.getInstance().show();
        /*GetTokenRequest request = new GetTokenRequest();
        request.lang = TelephonyUtils.getCurrentLocaleString(MyApplication.getInstance());
        request.deviceId = TelephonyUtils.getDeviceId(MyApplication.getInstance());


        ApiService apiService = RestClient.getApiService(mContext);
        apiService.anonymousLogin(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<UserObject>>(mContext){
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(BaseResponse<UserObject> userObjectResponse) {
                        super.onNext(userObjectResponse);
                        if (ApiCode.SUCCESS.equals(userObjectResponse.code)) {
                            if(null != userObjectResponse.data && null != userObjectResponse.data.dataobject){
                                String token = userObjectResponse.data.dataobject.authorizationToken;
                                SPUtils.put(mContext, SPConstant.KEY_TOKEN, token);
                            }
                        }
                    }
                });*/
    }
}
