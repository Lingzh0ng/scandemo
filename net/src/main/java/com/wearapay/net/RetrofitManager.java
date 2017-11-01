package com.wearapay.net;

import android.content.Context;

import com.google.gson.Gson;
import com.wearapay.net.converter.PPRestConverterFactory;
import com.wearapay.net.handler.DefaultHandle;
import com.wearapay.net.handler.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lyz on 2017/10/11.
 */

public class RetrofitManager {

    private RetrofitManager() {
    }

    private static final class RetrofitManagerHolder {
        private static final RetrofitManager instance = new RetrofitManager();
    }

    public static RetrofitManager get() {
        return RetrofitManagerHolder.instance;
    }

    public Retrofit getRetrofit(Context context, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
            .client(getOkHttpClient(context, new DefaultHandle()))
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .addConverterFactory(PPRestConverterFactory.create(new Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
        return retrofit;
    }

    public Retrofit getRetrofit(Context context, RequestHandler handler, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .client(getOkHttpClient(context, handler))
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addConverterFactory(PPRestConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    private OkHttpClient getOkHttpClient(Context context, final RequestHandler handler) {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.cache(new Cache(new File(context.getCacheDir().getAbsolutePath()), 100));

        okHttpBuilder.connectTimeout(NetConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(NetConfig.READ_TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(NetConfig.WRITE_TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (handler != null) {
                    request = handler.onBeforeRequest(chain.request(), chain);
                }

                Response response = chain.proceed(request);
                if (handler != null) {
                    Response tmp = handler.onAfterRequest(response, chain);
                    if (tmp != null) {
                        return tmp;
                    }
                }
                return response;
            }
        });
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别

            //设置 Debug Log 模式
            okHttpBuilder.addInterceptor(loggingInterceptor);
        }
        return okHttpBuilder.build();
    }
}
