package com.yang.yunfan.http;

import com.yang.yunfan.model.Joke;
import com.yang.yunfan.model.ResponeJH;
import com.yang.yunfan.model.ResultJH;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yang on 2017/2/16.
 */

public interface JuheApi {
    public static final String base_url = "http://japi.juhe.cn/";

    @GET("joke/content/text.from")
    Observable<ResponeJH<ResultJH<Joke>>> getJokes(@Query("key") String key, @Query("page") int page, @Query("pagesize") int pagesize);
}
