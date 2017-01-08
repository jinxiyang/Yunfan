package com.yang.yunfan.http;

import com.yang.yunfan.model.News;
import com.yang.yunfan.model.ResponeJH;
import com.yang.yunfan.model.ResultJH;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yang on 2017/1/7.
 */

public interface ToutiaoApi {

    public static final String base_url = "http://v.juhe.cn/toutiao/";

    @GET("index?key=9e05423f7ac6acf6d0dce3425c4ea9fe")
    Observable<ResponeJH<ResultJH<News>>> getNewsListByType(@Query("type") String type);
}
