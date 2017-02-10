package com.yang.yunfan.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 省流量模式管理者
 * Created by yang on 2017/1/10.
 */

public class SaveMobileDataManager {

    /**
     * 正常（不省流量）
     */
    public static final int NORMAL = 0;

    /**
     * 省流量
     */
    public static final int SAVE = 1;


    public static final String SAVE_MOBILE_DATA_FLAG = "save_mobile_data_flag";

    /**
     * 是否是省流量模式
     */
    public static boolean isSave(Context context){
        SharedPreferences sp = SharedPreferencesManager.getSP(context);
        return sp.getInt(SAVE_MOBILE_DATA_FLAG, NORMAL) == SAVE;
    }

    /**
     * 保存是否是省流量模式
     */
    public static void saveMode(Context context, boolean save){
        SharedPreferences.Editor editor = SharedPreferencesManager.getSP(context).edit();
        editor.putInt(SAVE_MOBILE_DATA_FLAG, save ? SAVE : NORMAL);
        editor.apply();
    }
}
