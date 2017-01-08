package com.yang.yunfan.source;

import com.yang.yunfan.model.News;
import com.yang.yunfan.model.ResponeJH;
import com.yang.yunfan.model.ResultJH;

import rx.Observable;

/**
 * Created by yangjinxi on 2016/10/8.
 */

public interface ToutiaoNewsRemoteDataSource {
    Observable<ResponeJH<ResultJH<News>>> getNewsListByType(String type);
}
