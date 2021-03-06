package com.yang.yunfan.ui.news;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yang.yunfan.AppApplication;
import com.yang.yunfan.R;
import com.yang.yunfan.model.News;
import com.yang.yunfan.model.NewsDao;
import com.yang.yunfan.model.ResponeJH;
import com.yang.yunfan.model.ResultJH;
import com.yang.yunfan.source.RemoteDataSourceImpl;
import com.yang.yunfan.ui.base.LazyLoadFragment;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class NewsTabFragment extends LazyLoadFragment implements OnRefreshListener {
    private static final String NEWS_TYPE = "news_type";

    String mNewsType;


    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.btn_retry)
    Button btnRetry;
    @BindView(R.id.ll_neterror)
    LinearLayout llNeterror;

    ArrayList<News> datas;

    private NewsListAdapter adapter;

    private NewsDao newsDao;


    public NewsTabFragment() {
        // Required empty public constructor
    }

    public static NewsTabFragment newInstance(String newsType) {
        NewsTabFragment fragment = new NewsTabFragment();
        Bundle args = new Bundle();
        args.putString(NEWS_TYPE, newsType.split(",")[1]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mNewsType = bundle.getString(NEWS_TYPE);
        }
        //datas可能是恢复的数据
        if (datas == null){
            datas = new ArrayList<>();
        }
        newsDao = AppApplication.getInstance().getDaoSession().getNewsDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_tab, container, false);
        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsListAdapter(getContext(), datas);
        adapter.setOnItemClickListener(new NewsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                News news = datas.get(position);
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(NewsDetailActivity.URL, news.getUrl());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onClickNeterorItem(View v, int position) {

            }
        });
        recyclerView.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);
        return view;
    }

    @Override
    public void lazyLoad() {
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
//        getNewsListFromDb();
        getNewsListByHttp();
    }

    private void getNewsListByHttp() {
        Subscription subscription = RemoteDataSourceImpl.getInstance().getNewsListByType(mNewsType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponeJH<ResultJH<News>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i(TAG, "getNewsListByHttp onError: ");
                        swipeToLoadLayout.setRefreshing(false);
                        if (e instanceof UnknownHostException || !AppApplication.getInstance().getNetworkState().connected){
                            if (datas.isEmpty()){
                                swipeToLoadLayout.setRefreshEnabled(false);
                                llNeterror.setVisibility(View.VISIBLE);
                            }else if (!NewsListAdapter.NETWORK_ERROR.equals(datas.get(0).getCategory())){
                                News news = new News();
                                news.setCategory(NewsListAdapter.NETWORK_ERROR);
                                datas.add(0, news);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onNext(ResponeJH<ResultJH<News>> respone) {
                        if (llNeterror.getVisibility() == View.VISIBLE){
                            llNeterror.setVisibility(View.GONE);
                        }
                        List<News> newsList = respone.getResult().getData();
                        datas.clear();
                        datas.addAll(newsList);
                        adapter.notifyDataSetChanged();
                        swipeToLoadLayout.setRefreshing(false);
                    }
                });
        mSubscriptions.add(subscription);
    }



    @OnClick(R.id.btn_retry)
    public void onClick() {
        swipeToLoadLayout.setRefreshEnabled(true);
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    public void reshowWhenRestoreData() {
        super.reshowWhenRestoreData();
        if (AppApplication.getInstance().getNetworkState().connected){
            if (datas.isEmpty()){
                lazyLoad();
            }else if (NewsListAdapter.NETWORK_ERROR.equals(datas.get(0).getCategory())){
                datas.remove(0);
                adapter.notifyDataSetChanged();
            }
        }else {
            if (datas.isEmpty()){
                swipeToLoadLayout.setRefreshEnabled(false);
                llNeterror.setVisibility(View.VISIBLE);
            }else if (!NewsListAdapter.NETWORK_ERROR.equals(datas.get(0).getCategory())){
                News news = new News();
                news.setCategory(NewsListAdapter.NETWORK_ERROR);
                datas.add(0, news);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
