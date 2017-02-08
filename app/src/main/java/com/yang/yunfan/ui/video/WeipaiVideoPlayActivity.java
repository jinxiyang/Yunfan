package com.yang.yunfan.ui.video;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.yang.yunfan.R;
import com.yang.yunfan.ijkplayer.playerview.IjkPlayerView;
import com.yang.yunfan.source.jsoup.WeipaiJsoup;
import com.yang.yunfan.source.jsoup.WeipaiVideo;
import com.yang.yunfan.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeipaiVideoPlayActivity extends BaseActivity {

    public static final String WEIPAI_VIDEO = "weipai_video";

    @BindView(R.id.playerview)
    IjkPlayerView playerview;


    private String imgHtmlUrl = "http://www.dbmeinv.com/dbgroup/v-24616";
    private String videoUrl = "/storage/emulated/0/DCIM/Camera/VID_20170118_100821.mp4";
//    private String videoUrl = "http://gslb.miaopai.com/stream/MhBiYX~uUhvHaQwW6JBc0w__.mp4";
    private WeipaiVideo weipaiVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weipai_video_play);

        ButterKnife.bind(this);
        weipaiVideo = getIntent().getParcelableExtra(WEIPAI_VIDEO);
        if (weipaiVideo != null) {
            getImgUrl(weipaiVideo.getVideoHtmlUrl());
        }

        playerview.init().setTitle(weipaiVideo.getTitle());
//        playerview.init().setVideoPath(videoUrl);
    }


    private void getImgUrl(final String htmlUrl) {
        Subscription subscription = WeipaiJsoup.getWeipaiVideoUrl(htmlUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        playerview.init().setVideoPath(s).start();
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        playerview.configurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged: " + newConfig.orientation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerview.onResume();
    }

    @Override
    protected void onPause() {
        playerview.onPause();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (playerview.onBackPressed()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        playerview.onDestroy();
        super.onDestroy();
    }
}
