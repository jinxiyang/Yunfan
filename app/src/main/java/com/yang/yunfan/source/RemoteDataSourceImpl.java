package com.yang.yunfan.source;


import com.yang.yunfan.AppConstant;
import com.yang.yunfan.http.AppHttpClient;
import com.yang.yunfan.http.JuheApi;
import com.yang.yunfan.http.ToutiaoApi;
import com.yang.yunfan.model.Joke;
import com.yang.yunfan.model.News;
import com.yang.yunfan.model.ResponeJH;
import com.yang.yunfan.model.ResultJH;

import rx.Observable;

/**
 * Created by yangjinxi on 2016/10/8.
 */

public class RemoteDataSourceImpl implements RemoteDataSource {

    private static RemoteDataSource mInstance;
    private final ToutiaoApi toutiaoApi;
    private final JuheApi juheApi;

    private RemoteDataSourceImpl(){
        toutiaoApi = AppHttpClient.getInstance().retrofit(ToutiaoApi.base_url).create(ToutiaoApi.class);
        juheApi = AppHttpClient.getInstance().juheRetrofit(JuheApi.base_url).create(JuheApi.class);
    }

    public static RemoteDataSource getInstance() {
        if (mInstance == null){
            mInstance = new RemoteDataSourceImpl();
        }
        return mInstance;
    }

    @Override
    public Observable<ResponeJH<ResultJH<News>>> getNewsListByType(String type) {
        return toutiaoApi.getNewsListByType(type);
    }

    @Override
    public Observable<ResponeJH<ResultJH<Joke>>> getJokes(int page, int pagesize) {
        return juheApi.getJokes(AppConstant.JOKE_KEY, page, pagesize);
    }
}
