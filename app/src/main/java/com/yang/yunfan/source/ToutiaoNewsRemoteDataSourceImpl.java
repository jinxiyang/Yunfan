package com.yang.yunfan.source;


import com.yang.yunfan.http.AppHttpClient;
import com.yang.yunfan.http.ToutiaoApi;
import com.yang.yunfan.model.News;
import com.yang.yunfan.model.ResponeJH;
import com.yang.yunfan.model.ResultJH;

import rx.Observable;

/**
 * Created by yangjinxi on 2016/10/8.
 */

public class ToutiaoNewsRemoteDataSourceImpl implements ToutiaoNewsRemoteDataSource {

    private static ToutiaoNewsRemoteDataSource mInstance;
    private final ToutiaoApi api;

    private ToutiaoNewsRemoteDataSourceImpl(){
        api = AppHttpClient.getInstance().retrofit(ToutiaoApi.base_url).create(ToutiaoApi.class);
    }

    public static ToutiaoNewsRemoteDataSource getInstance() {
        if (mInstance == null){
            mInstance = new ToutiaoNewsRemoteDataSourceImpl();
        }
        return mInstance;
    }

    @Override
    public Observable<ResponeJH<ResultJH<News>>> getNewsListByType(String type) {
        return api.getNewsListByType(type);
    }
}
