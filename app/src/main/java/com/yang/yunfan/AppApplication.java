package com.yang.yunfan;

import android.content.IntentFilter;

import com.facebook.stetho.Stetho;
import com.yang.yunfan.http.network.NetworkConnectChangedReceiver;
import com.yang.yunfan.http.network.NetworkState;
import com.yang.yunfan.manager.DayNightManager;
import com.yang.yunfan.manager.SaveMobileDataManager;
import com.yang.yunfan.model.DaoMaster;
import com.yang.yunfan.model.DaoSession;
import com.yang.yunfan.ui.base.BaseApplication;
import com.yang.yunfan.utils.FrescoUtil;

import org.greenrobot.greendao.database.Database;

import icepick.Icepick;

/**
 * Created by yang on 2017/1/1.
 */

public class AppApplication extends BaseApplication {

    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = true;

    private static AppApplication instance;

    private DaoSession daoSession;

    //网络连接状态
    private NetworkState mNetworkState = new NetworkState();

    //是否是省流量模式
    private boolean saveMobileData = false;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        if (BuildConfig.DEBUG){//配合chrome对网络请求和数据库进行监听
            Stetho.initializeWithDefaults(this);
        }


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, AppConstant.DATABASE_NAME);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();


        DayNightManager.initAppUiMode(this);
        saveMobileData = SaveMobileDataManager.isSave(this);

        initNetworkState();

        //保存和恢复状态的日志
        Icepick.setDebug(BuildConfig.DEBUG);

        FrescoUtil.init(getApplicationContext());


    }

    private void initNetworkState() {
        NetworkConnectChangedReceiver mReceiver = new NetworkConnectChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(mReceiver, filter);
    }

    /**
     * 是否是省流量模式
     */
    public boolean isSaveMobileData() {
        return saveMobileData;
    }

    /**
     * 设置是否是省流量模式
     * @param saveMobileData
     */
    public void setSaveMobileData(boolean saveMobileData) {
        this.saveMobileData = saveMobileData;
        SaveMobileDataManager.saveMode(this, saveMobileData);
    }

    public NetworkState getNetworkState() {
        return mNetworkState;
    }

    public static AppApplication getInstance() {
        return instance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
