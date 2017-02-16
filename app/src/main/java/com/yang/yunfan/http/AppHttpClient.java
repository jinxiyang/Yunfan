package com.yang.yunfan.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.yang.yunfan.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yang on 16/7/5.
 */
public class AppHttpClient {

    private static AppHttpClient mInstance;

    private Retrofit retrofit;
    private Retrofit juheRetrofit;

    private OkHttpClient okHttpClient;


    private AppHttpClient() {

    }

    /**
     * 获取单例
     */
    public static AppHttpClient getInstance()
    {
        if (mInstance == null)
        {
            synchronized (AppHttpClient.class)
            {
                if (mInstance == null)
                {
                    mInstance = new AppHttpClient();
                }
            }
        }
        return mInstance;
    }



    /**
     * 获取pgyer retrofit
     * @return
     */
    public Retrofit retrofit(String baseUrl){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return retrofit;
    }

    /**
     * 获取pgyer retrofit
     * @return
     */
    public Retrofit juheRetrofit(String baseUrl){
        if (juheRetrofit == null){
            juheRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return juheRetrofit;
    }


    /**
     * 获取OkHttpClient
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {//打印请求体
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
                builder.addNetworkInterceptor(new StethoInterceptor());//配合chrome监听网络请求
            }
            builder.connectTimeout(15000, TimeUnit.SECONDS);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }
}
