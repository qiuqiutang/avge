package com.example.lyfuelgas.common.http;

import android.content.Context;
import android.text.TextUtils;

import com.example.lyfuelgas.BuildConfig;
import com.example.lyfuelgas.app.UserManager;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.annotations.NonNull;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit Rest Client, to set network configuration
 * Created on 2020/4/24.
 *
 * @author QiuYan
 * @since 1.0.0
 */
public final class RestClient {
    private static final int CONNECT_TIMEOUT = 30; // seconds

    private RestClient() {

    }

    /**
     * Get network service.
     *
     * @param context Context
     * @return the API service
     */
    public static ApiService getApiService(Context context) {
        return getApiService(context, null);
    }

    public static ApiService getApiService(final Context context, Interceptor interceptor) {
        OkHttpClient sClient = createOkHttpClient(context, interceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(sClient)
                .build();
        return retrofit.create(ApiService.class);
    }



    @NonNull
    private static OkHttpClient createOkHttpClient(final Context context, Interceptor cacheInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder = builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);

        // add interceptors in order
        // 1. self-defined header: language header
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Context app = context.getApplicationContext();

                Request.Builder requestBuilder = chain.request().newBuilder();
                String token = UserManager.getInstance().getUserToken();
                if (!TextUtils.isEmpty(token)) {
                    requestBuilder.header("Authorization", token);
                }
                try {
                    return chain.proceed(requestBuilder.build());
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
        };
        builder.addInterceptor(headerInterceptor);

        // 2. log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        // debug show log
        if (BuildConfig.LOG_DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        builder.addInterceptor(interceptor);
        if (cacheInterceptor != null) {
            builder.addInterceptor(cacheInterceptor);
        }
        // no https validation
        OkHttpClient sClient = builder.build();

        if (!BuildConfig.USE_HTTPS) {
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                            throws java.security.cert.CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                            throws java.security.cert.CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }}, new SecureRandom());

                HostnameVerifier hv1 = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };
                String workerClassName = "okhttp3.OkHttpClient";
                Class workerClass = Class.forName(workerClassName);
                Field hostnameVerifier = workerClass.getDeclaredField("hostnameVerifier");
                hostnameVerifier.setAccessible(true);
                hostnameVerifier.set(sClient, hv1);

                Field sslSocketFactory = workerClass.getDeclaredField("sslSocketFactory");
                sslSocketFactory.setAccessible(true);
                sslSocketFactory.set(sClient, sc.getSocketFactory());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sClient;
    }
}