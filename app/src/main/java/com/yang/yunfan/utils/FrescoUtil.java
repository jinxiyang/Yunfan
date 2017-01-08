package com.yang.yunfan.utils;

import android.content.Context;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.yang.yunfan.BuildConfig;
import com.yang.yunfan.http.AppHttpClient;

import java.util.HashSet;
import java.util.Set;

/**
 * 图片加载库fresco
 */

public class FrescoUtil {

    /**
     * 初始化fresco ，最好在Application中调用
     * @param context
     */
    public static void init(Context context){
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
        Set<RequestListener> listeners = new HashSet<>();
        if(BuildConfig.DEBUG){
            listeners.add(new RequestLoggingListener());
        }
        //使用OKhttp作为fresco的图片请求client
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(context, AppHttpClient.getInstance().getOkHttpClient())
                .setRequestListeners(listeners)
                .build();
        Fresco.initialize(context, config);
    }
}