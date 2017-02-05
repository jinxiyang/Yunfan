package com.yang.yunfan.ui.video;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yang.yunfan.R;
import com.yang.yunfan.ijkplayer.playerview.IjkPlayerView;
import com.yang.yunfan.source.jsoup.WeipaiJsoup;
import com.yang.yunfan.source.jsoup.WeipaiVideo;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class WeipaiVideoPlayActivity extends AppCompatActivity {

    public static final String WEIPAI_VIDEO = "weipai_video";

    @BindView(R.id.playerview)
    IjkPlayerView playerview;


    private String imgHtmlUrl = "http://www.dbmeinv.com/dbgroup/v-24616";
    private String videoUrl = "/storage/emulated/0/DCIM/Camera/VID_20170118_100821.mp4";
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
    }

}
