package com.yang.yunfan.ui.news;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yang.yunfan.R;
import com.yang.yunfan.model.News;
import com.yang.yunfan.model.ResponeJH;
import com.yang.yunfan.model.ResultJH;
import com.yang.yunfan.source.ToutiaoNewsRemoteDataSourceImpl;
import com.yang.yunfan.ui.base.LazyLoadFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.State;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class NewsTabFragment extends LazyLoadFragment implements OnRefreshListener{
    private static final String NEWS_TYPE = "news_type";

    @State
    String mNewsType;


    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private List<News> datas;
    private NewsListAdapter adapter;


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
        datas = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_tab, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        });
        recyclerView.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);
    }

    @Override
    public void lazyLoad() {
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
        Subscription subscription = ToutiaoNewsRemoteDataSourceImpl.getInstance().getNewsListByType(mNewsType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponeJH<ResultJH<News>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponeJH<ResultJH<News>> respone) {
                        List<News> newsList = respone.getResult().getData();
                        datas.clear();
                        datas.addAll(newsList);
                        adapter.notifyDataSetChanged();
                        swipeToLoadLayout.setRefreshing(false);
                    }
                });
    }
}
