package com.yang.yunfan.ijkplayer.playerview;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
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

public class IjkPlayerView extends FrameLayout implements View.OnClickListener {
    private static final String TAG = "IjkPlayerView";

    // 默认隐藏控制栏时间
    private static final int DEFAULT_HIDE_TIMEOUT = 3000;

    // 进度条最大值
    private static final int MAX_VIDEO_SEEK = 1000;

    // 更新进度消息
    private static final int MSG_UPDATE_SEEK = 10086;

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


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_UPDATE_SEEK) {
                int progress = updateProgressInfo();
                mHandler.sendEmptyMessageDelayed(MSG_UPDATE_SEEK, 1000 - (progress % 1000));
            }
        }
    };

    //加载视频时的加载
    private ImageView mIvLoadingProgress;
    private RotateAnimation mLoadingAnim;

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
        mIvLoadingProgress = (ImageView) findViewById(R.id.iv_loading_progress);

        mIvBack.setOnClickListener(this);
        mIvPlayOrPause.setOnClickListener(this);
        mIvFullscreen.setOnClickListener(this);

        mSeekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mSeekBar.setMax(MAX_VIDEO_SEEK);
        mProgressBar.setMax(MAX_VIDEO_SEEK);

        setClickable(true);
        toggleControlBar(true);

        mOrientationEventListener = new OrientationEventListener(context) {
            @Override
            public void onOrientationChanged(int orientation) {
                handleOrientationChanged(orientation);
            }
        };
    }


    //是否正在拖动进度条
    private boolean mIsSeeking = false;

    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        int targetProgress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            targetProgress = (int) (progress * 1.0f / MAX_VIDEO_SEEK * mVideoView.getDuration());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mIsSeeking = true;
            mHandler.removeCallbacks(mHiddenRunnable);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mIsSeeking = false;
            mVideoView.seekTo(targetProgress);
            mHandler.postDelayed(mHiddenRunnable, DEFAULT_HIDE_TIMEOUT);
            mHandler.sendEmptyMessage(MSG_UPDATE_SEEK);
        }
    };


    /**
     * 更新视频进度的显示信息
     *
     * @return
     */
    private int updateProgressInfo() {
        if (mVideoView == null || mIsSeeking) {
            return 0;
        }
        int duration = mVideoView.getDuration();
        int currentPosition = mVideoView.getCurrentPosition();
        int progress = 0;
        if (duration > 0) {
            progress = (int) (currentPosition * 1.0f / duration * MAX_VIDEO_SEEK);
        }
        int bufferProgress = mVideoView.getBufferPercentage() * 10;
        mSeekBar.setProgress(progress);
        mSeekBar.setSecondaryProgress(bufferProgress);
        mProgressBar.setProgress(progress);
        mProgressBar.setSecondaryProgress(bufferProgress);
        mTvCurrentTime.setText(generateTime(currentPosition));
        mTvTotalTime.setText(generateTime(duration));
        return currentPosition;
    }


    /**
     * 时长格式化显示
     */
    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = totalSeconds / 60;
        return minutes > 99 ? String.format("%d:%02d", minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHandler.removeCallbacks(mHiddenRunnable);
                toggleControlBar(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mHandler.postDelayed(mHiddenRunnable, DEFAULT_HIDE_TIMEOUT);
                break;

        }
        return super.onTouchEvent(event);
    }

    /**
     * 初始化,activity调用
     *
     * @return
     */
    public IjkPlayerView init() {
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
     * 设置标题
     *
     * @return
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
        mIvPlayOrPause.setImageResource(R.drawable.pause_video);
        mHandler.sendEmptyMessage(MSG_UPDATE_SEEK);
        // 视频播放时开启屏幕常亮
        mAttachActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 暂停
     */
    public void pause() {
        mHandler.removeMessages(MSG_UPDATE_SEEK);
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
        }
        mIvPlayOrPause.setImageResource(R.drawable.play_video);
        // 视频暂停时关闭屏幕常亮
        mAttachActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onClick(View v) {
        refreshHiddenRunable();
        switch (v.getId()) {
            case R.id.iv_back:
                mAttachActivity.onBackPressed();
                break;
            case R.id.iv_play_or_pause:
                if (mVideoView.isPlaying()) {
                    pause();
                } else {
                    start();
                }
                break;
            case R.id.iv_fullscreen:
                toggleFullScreen();
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
            toggleControlBar(false);
        }
    };

    /**
     * 显示或者隐藏顶部、底部控制条
     */
    private void toggleControlBar(boolean show) {
        if (show) {
            mTopBar.setVisibility(VISIBLE);
            mIvPlayOrPause.setVisibility(VISIBLE);
            mBottomBar.setVisibility(VISIBLE);
            mProgressBar.setVisibility(GONE);
        } else {
            mTopBar.setVisibility(GONE);
            mIvPlayOrPause.setVisibility(GONE);
            mBottomBar.setVisibility(GONE);
            mProgressBar.setVisibility(VISIBLE);
        }
    }

    /**
     * 显示中央的加载动画
     */
    private void showLoadProgress() {
        if (mLoadingAnim != null) {
            mLoadingAnim.cancel();
            mLoadingAnim = null;
        }
        mLoadingAnim = new RotateAnimation(0f, 359f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator ll = new LinearInterpolator();
        mLoadingAnim.setInterpolator(ll);
        mLoadingAnim.setDuration(1500);
        mLoadingAnim.setFillAfter(true);
        mLoadingAnim.setRepeatCount(Integer.MAX_VALUE);
        mIvLoadingProgress.setVisibility(VISIBLE);
        mIvLoadingProgress.setAnimation(mLoadingAnim);
        mIvLoadingProgress.startAnimation(mLoadingAnim);
    }

    /**
     * 隐藏中央的加载动画
     */
    private void hideLoadProgress() {
        if (mLoadingAnim != null) {
            mLoadingAnim.cancel();
            mLoadingAnim = null;
        }
        mIvLoadingProgress.setAnimation(null);
        mIvLoadingProgress.setVisibility(GONE);
    }


    /**============================ 外部调用接口 ============================*/

    /**
     * Activity.onResume() 里调用
     */
    public void onResume() {
        mVideoView.resume();
        toggleControlBar(true);
        mHandler.postDelayed(mHiddenRunnable, DEFAULT_HIDE_TIMEOUT);
    }

    /**
     * Activity.onPause() 里调用
     */
    public void onPause() {
        mVideoView.pause();
        mIvPlayOrPause.setImageResource(R.drawable.play_video);
    }


    /**
     * Activity.onDestroy() 里调用
     *
     * @return 返回播放进度
     */
    public int onDestroy() {
        // 记录播放进度
        int curPosition = mVideoView.getCurrentPosition();
        mVideoView.release(true);
        IjkMediaPlayer.native_profileEnd();
        // 关闭屏幕常亮
        mAttachActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mAttachActivity = null;
        return curPosition;
    }

    /**
     * 回退，全屏时退回竖屏
     *
     * @return
     */
    public boolean onBackPressed() {
        if (mIsFullscreen) {
            mAttachActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return true;
        }
        return false;
    }


    private OrientationEventListener mOrientationEventListener;

    private void handleOrientationChanged(int orientation) {
        if (mIsFullscreen) {
            // 根据角度进行竖屏切换，如果为固定全屏则只能横屏切换
            if (orientation >= 0 && orientation <= 30 || orientation >= 330) {
                // 请求屏幕翻转
                mAttachActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        } else {
            // 根据角度进行横屏切换
            if (orientation >= 60 && orientation <= 120) {
                mAttachActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            } else if (orientation >= 240 && orientation <= 300) {
                mAttachActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }
    }

    private int normalSystemUiVisibility;

    public void configurationChanged(Configuration newConfig) {
        if (Build.VERSION.SDK_INT >= 14) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                View decorView = mAttachActivity.getWindow().getDecorView();
                normalSystemUiVisibility = decorView.getSystemUiVisibility();
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                );
                setFullscreen(true);
                mAttachActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                View decorView = mAttachActivity.getWindow().getDecorView();
                decorView.setSystemUiVisibility(normalSystemUiVisibility);
                setFullscreen(false);
                mAttachActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
    }

    private boolean mIsFullscreen = false;

    private int mInitHeight;
    private int mWidthPixels;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mInitHeight == 0) {
            mInitHeight = getHeight();
            mWidthPixels = getResources().getDisplayMetrics().widthPixels;
        }
    }

    private void setFullscreen(boolean isFullscreen) {
        mIsFullscreen = isFullscreen;
        ActionBar actionBar = mAttachActivity.getSupportActionBar();
        if (actionBar != null) {
            if (isFullscreen) {
                actionBar.hide();
            } else {
                actionBar.show();
            }
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (isFullscreen) {
            // 高度扩展为横向全屏
            layoutParams.height = mWidthPixels;
            mIvFullscreen.setImageResource(R.drawable.shrink_fullscreen);
        } else {
            // 还原高度
            layoutParams.height = mInitHeight;
            mIvFullscreen.setImageResource(R.drawable.enlarge_fullscreen);
        }
        setLayoutParams(layoutParams);
        mOrientationEventListener.enable();
    }


    /**
     * 全屏切换，点击全屏按钮
     */
    private void toggleFullScreen() {
        if (getScreenOrientation(mAttachActivity) == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            mAttachActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            mAttachActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }


    /**
     * 获取界面方向
     */
    public static int getScreenOrientation(Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int orientation;
        // if the device's natural orientation is portrait:
        if ((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) && height > width ||
                (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) && width > height) {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        }
        // if the device's natural orientation is landscape or if the device
        // is square:
        else {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_180:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                    break;
                case Surface.ROTATION_270:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
                    break;
                default:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }

        return orientation;
    }


}
