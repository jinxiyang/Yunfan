package com.yang.yunfan.ijkplayer.playerview;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yang.yunfan.R;
import com.yang.yunfan.ijkplayer.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by yang on 2017/2/5.
 */

public class IjkPlayerView extends FrameLayout implements View.OnClickListener{

    // 默认隐藏控制栏时间
    private static final int DEFAULT_HIDE_TIMEOUT = 5000;
        
    private AppCompatActivity mAttachActivity;

    //ijk videoview
    private IjkVideoView mVideoView;

    //视频开始前的预览图
    private ImageView mIvPreview;

    //顶部条，包含返回箭头、标题，以后会拓展
    private View mTopBar;

    //返回箭头
    private ImageView mIvBack;

    //标题
    private TextView mTvTitle;

    //播放或者暂停按钮
    private ImageView mIvPlayOrPause;

    //顶部条，包含进度条、当前时间、总时间、全屏，以后会拓展
    private View mBottomBar;

    //全屏按钮
    private ImageView mIvFullscreen;

    //当前时间
    private TextView mTvCurrentTime;

    //总时间
    private TextView mTvTotalTime;

    //进度条，可拖动
    private SeekBar mSeekBar;

    //视屏底部进度条，不可拖动选择
    private ProgressBar mProgressBar;


    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {


        }
    };

    public IjkPlayerView(Context context) {
        this(context, null);
    }

    public IjkPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IjkPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        if (context instanceof AppCompatActivity) {
            mAttachActivity = (AppCompatActivity) context;
        } else {
            throw new IllegalArgumentException("Context must be AppCompatActivity");
        }
        View.inflate(context, R.layout.layout_ijk_player_view, this);
        mVideoView = (IjkVideoView) findViewById(R.id.vedio_view);
        mIvPreview = (ImageView) findViewById(R.id.iv_preview);
        mTopBar = findViewById(R.id.top_bar);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvPlayOrPause = (ImageView) findViewById(R.id.iv_play_or_pause);
        mBottomBar = findViewById(R.id.bottom_bar);
        mIvFullscreen = (ImageView) findViewById(R.id.iv_fullscreen);
        mTvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        mTvTotalTime = (TextView) findViewById(R.id.tv_total_time);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        mIvBack.setOnClickListener(this);
        mIvPlayOrPause.setOnClickListener(this);
        mIvFullscreen.setOnClickListener(this);
    }

    /**
     * 初始化,activity调用
     * @return
     */
    public IjkPlayerView init(){
        // 加载 IjkMediaPlayer 库
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        return this;
    }

    /**
     * 设置播放资源
     *
     * @param url
     * @return
     */
    public IjkPlayerView setVideoPath(String url) {
        return setVideoPath(Uri.parse(url));
    }

    /**
     * 设置播放资源
     *
     * @param uri
     * @return
     */
    public IjkPlayerView setVideoPath(Uri uri) {
        mVideoView.setVideoURI(uri);
        return this;
    }

    /**
     * 设置标题，全屏的时候可见
     *
     * @param title
     */
    public IjkPlayerView setTitle(String title) {
        mTvTitle.setText(title);
        return this;
    }



    /**
     * 开始播放
     *
     * @return
     */
    public void start() {
        if (!mVideoView.isPlaying()) {
            mVideoView.start();
        }
        // 视频播放时开启屏幕常亮
        mAttachActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 是否正在播放
     *
     * @return
     */
    public boolean isPlaying() {
        return mVideoView.isPlaying();
    }

    /**
     * 暂停
     */
    public void pause() {
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
        }
        // 视频暂停时关闭屏幕常亮
        mAttachActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    

    @Override
    public void onClick(View v) {
        refreshHiddenRunable();
        switch (v.getId()){
            case R.id.iv_back:
                break;
            case R.id.iv_play_or_pause:
                break;
            case R.id.iv_fullscreen:
                break;
        }
    }

    private void refreshHiddenRunable() {
        mHandler.removeCallbacks(mHiddenRunnable);
        mHandler.postDelayed(mHiddenRunnable, DEFAULT_HIDE_TIMEOUT);
    }

    /**
     * 隐藏视频的控制视图
     */
    private Runnable mHiddenRunnable = new Runnable() {
        @Override
        public void run() {
            mTopBar.setVisibility(GONE);
            mIvPlayOrPause.setVisibility(GONE);
            mBottomBar.setVisibility(GONE);
            mProgressBar.setVisibility(VISIBLE);
        }
    };
}
