package com.yang.yunfan.ui.video;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.gson.Gson;
import com.yang.yunfan.R;
import com.yang.yunfan.source.jsoup.WeipaiJsoup;
import com.yang.yunfan.source.jsoup.WeipaiVideo;
import com.yang.yunfan.source.jsoup.WeipaiVideosPage;
import com.yang.yunfan.ui.base.LazyLoadFragment_2;
import com.yang.yunfan.utils.LogUtil;
import com.yang.yunfan.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 微拍视频列表
 */
public class WeipaiVideoListFragment extends LazyLoadFragment_2 implements OnRefreshListener, OnLoadMoreListener{


    @BindView(R.id.swipe_target)
    RecyclerView recyclerView;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private WeipaiVideoAdapter adapter;
    private List<WeipaiVideo> datas;
    private CompositeSubscription mSubscriptions;

    private int mPageNo = 0;

    public WeipaiVideoListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas = new ArrayList<>();
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weipai_video_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WeipaiVideoAdapter(getContext(), datas);
        adapter.setOnItemClickListener(new WeipaiVideoAdapter.OnItemClickListener() {
            @Override
            public void onClickVideo(View v, int position) {
                showPlayVideo(datas.get(position));
            }

            @Override
            public void onClickShare(View v, int position) {
                shareVideo(datas.get(position));
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });
        swipeToLoadLayout.setLoadMoreEnabled(false);
    }

    /**
     * 分享视频
     * @param weipaiVideo
     */
    private void shareVideo(WeipaiVideo weipaiVideo) {
        ToastUtil.showShort("视频分享 正在开发");
    }

    /**
     * 播放微拍视频
     * @param weipaiVideo
     */
    private void showPlayVideo(WeipaiVideo weipaiVideo) {
        Intent intent = new Intent(getContext(), WeipaiVideoPlayActivity.class);
        intent.putExtra(WeipaiVideoPlayActivity.WEIPAI_VIDEO, weipaiVideo);
        startActivity(intent);
    }

    @Override
    public void lazyLoad() {
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    public void onLoadMore() {
        loadVideosByPageNo(mPageNo + 1);
    }

    @Override
    public void onRefresh() {
        loadVideosByPageNo(1);
    }


    public void loadVideosByPageNo(final int pageNo) {

        Subscription subscription = WeipaiJsoup.getWeipaiVideosPageByPageNo(pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeipaiVideosPage>() {
                    @Override
                    public void onCompleted() {
                        swipeToLoadLayout.setRefreshing(false);
                        swipeToLoadLayout.setLoadingMore(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(WeipaiVideosPage weipaiVideosPage) {
                        if (weipaiVideosPage != null && weipaiVideosPage.getVideos() != null && weipaiVideosPage.getVideos().size() > 0){
                            if (weipaiVideosPage.getCurrPageNo() > mPageNo){
                                String json = new Gson().toJson(weipaiVideosPage);
                                LogUtil.i(json);
                                datas.addAll(weipaiVideosPage.getVideos());
                            }else {
                                datas.clear();
                                datas.addAll(weipaiVideosPage.getVideos());
                            }
                            mPageNo = weipaiVideosPage.getCurrPageNo();
                            adapter.notifyDataSetChanged();
                        }
                        if (weipaiVideosPage.isHasNextPage()){
                            swipeToLoadLayout.setLoadMoreEnabled(true);
                        }else {
                            swipeToLoadLayout.setLoadMoreEnabled(false);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void onDestroy() {
        mSubscriptions.clear();
        super.onDestroy();
    }
}
