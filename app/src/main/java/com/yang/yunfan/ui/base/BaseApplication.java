package com.yang.yunfan.ui.base;

import android.app.Application;
import android.content.res.Configuration;

import com.yang.yunfan.utils.LogUtil;

/**
 * Created by yangjinxi on 2016/10/8.
 */

public class BaseApplication extends Application {

    protected String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG + "------" + "onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.i(TAG + "------" + "onTerminate");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.i(TAG + "------" + "onConfigurationChanged --");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtil.i(TAG + "------" + "onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtil.i(TAG + "------" + "onTrimMemory level--"+level);
    }
}
