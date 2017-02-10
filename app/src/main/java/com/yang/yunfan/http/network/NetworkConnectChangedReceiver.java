package com.yang.yunfan.http.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

import com.yang.yunfan.AppApplication;

/**
 * 网络改变监控广播
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    private static final String TAG = "Network Receiver";

    public NetworkConnectChangedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkState networkState = AppApplication.getInstance().getNetworkState();
        String action = intent.getAction();
        Log.i(TAG, action);

        //监听WiFi的打开与关闭, 和WiFi的连接无关
        //android.net.wifi.WIFI_STATE_CHANGED
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLING://0
                    break;
                case WifiManager.WIFI_STATE_DISABLED://1
                    networkState.setEnableWifi(false);
                    networkState.setWifi(false);
                    break;
                case WifiManager.WIFI_STATE_ENABLING://2
                    break;
                case WifiManager.WIFI_STATE_ENABLED://3
                    networkState.setEnableWifi(true);
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN://4
                    break;
            }
        }

        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager
        // .WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，
        // 当然刚打开wifi肯定还没有连接到有效的无线

        //android.net.wifi.STATE_CHANGE
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                boolean isConnected = networkInfo.getState() == NetworkInfo.State.CONNECTED;
                networkState.setWifi(isConnected);
                if (isConnected) {
                    networkState.setEnableWifi(true);
                }
            }
        }

        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager
        // .WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，
        // 当然刚打开wifi肯定还没有连接到有效的无线
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {// connected to the internet
                if (activeNetworkInfo.isConnected()) {
                    if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        // connected to wifi
                        networkState.setWifi(true);
                        networkState.setEnableWifi(true);
                        networkState.setMobile(false);
                    } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        // connected to the mobile provider's data plan
                        networkState.setWifi(false);
                        networkState.setMobile(true);
                    }
                    networkState.setConnected(true);
                    return;
                }
            }
            networkState.setWifi(false);
            networkState.setMobile(false);
            networkState.setConnected(false);
        }
    }
}
