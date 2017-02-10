package com.yang.yunfan.utils;

import android.content.Context;
import android.net.Uri;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.yang.yunfan.AppApplication;
import com.yang.yunfan.BuildConfig;

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
//        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(context, AppHttpClient.getInstance().getOkHttpClient())
//                .setRequestListeners(listeners)
//                .build();

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setRequestListeners(listeners)
                .build();
        Fresco.initialize(context, config);
    }


    /**
     * 为SimpleDraweeView设置图片
     * @param sdv
     * @param uri
     */
    public static void setImageUri(SimpleDraweeView sdv, Uri uri){
        if (AppApplication.getInstance().isSaveMobileData()){
            ImagePipeline pipeline = Fresco.getImagePipeline();
            if (pipeline.isInBitmapMemoryCache(uri) || pipeline.isInDiskCacheSync(uri)){
                sdv.setImageURI(uri);
                return;
            }
        }else {
            sdv.setImageURI(uri);
        }
    }
    /**
     * 为SimpleDraweeView设置图片
     */
    public static void setImageUri(SimpleDraweeView sdv, String url){
        setImageUri(sdv, Uri.parse(url));
    }
}
