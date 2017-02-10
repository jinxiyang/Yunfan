package com.yang.yunfan.http.network;

import android.util.Log;

/**
 * 网络连接状态
 * Created by yang on 2016/12/13.
 */

public class NetworkState {

    private static final String TAG = "NetworkState";

    //WiFi开关是否开启
    public boolean enableWifi;

    //是否连接到一个可用的WiFi
    public boolean wifi;

    //是否开启移动数据流量
    public boolean mobile;

    //是否连接上网络
    public boolean connected;


    public boolean isEnableWifi() {
        return enableWifi;
    }

    public void setEnableWifi(boolean enableWifi) {
        this.enableWifi = enableWifi;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    /**
     * 打印网络状态
     */
    public void logNetworkState(){
        Log.i(TAG, "enableWifi:\r" + enableWifi);
        Log.i(TAG, "wifi:\r" + wifi);
        Log.i(TAG, "mobile:\r" + mobile);
        Log.i(TAG, "connected:\r" + connected);
    }
}
