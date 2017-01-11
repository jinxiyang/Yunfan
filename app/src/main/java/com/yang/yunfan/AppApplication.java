package com.yang.yunfan;

import com.yang.yunfan.manager.DayNightManager;
import com.yang.yunfan.ui.base.BaseApplication;
import com.yang.yunfan.utils.FrescoUtil;

import icepick.Icepick;

/**
 * Created by yang on 2017/1/1.
 */

public class AppApplication extends BaseApplication {

    private static AppApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        DayNightManager.initAppUiMode(this);

        //保存和恢复状态的日志
        Icepick.setDebug(BuildConfig.DEBUG);

        FrescoUtil.init(getApplicationContext());


    }

    public static AppApplication getInstance() {
        return instance;
    }
}
