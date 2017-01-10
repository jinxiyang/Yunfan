package com.yang.yunfan.manager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * 统一管理SharedPreferences的使用
 */

public class SharedPreferencesManager {
    /**
     * sp的名称
     */
    public static final String SHARED_PREFERENCES_NAME = "renji_shared_preferences";

    //上次检查版本的时间
    public static final String CHECK_VERSION_TIME = "check_version_time";

    //渠道号
    public static final String CHANNEL = "channel";


    //登录token
    public static final String LOGIN_TOKEN = "login_token";

    //登录token保存的时间
    public static final String SAVE_LOGIN_TOKEN_TIME = "save_login_token_time";


    //网络获取科室列表数据的时间,当小于2小时时,从数据库获取
    public static final String LOAD_DEPARTMENT_LIST_TIME_BY_HTTP = "load_department_list_time_by_http";


    //有未读的消息
    public static final String HAS_UNREAD_MESSAGES = "has_unread_messages";



    /**
     * 获取SharedPreferences
     * @param context
     * @return
     */
    public static SharedPreferences getSP(Context context){
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp;
    }

    /**
     * 记录检查版本的时间
     */
    public static void rememberCheckVersionTime(Context context){
        SharedPreferences.Editor editor = getSP(context).edit();
        long nowTime = new Date().getTime();
        editor.putLong(CHECK_VERSION_TIME, nowTime);
        editor.commit();
    }

    /**
     * 获取上一次检查版本的时间
     * @return
     */
    public static long getLastCheckVersionTime(Context context){
        return getSP(context).getLong(CHECK_VERSION_TIME, 0);
    }


    /**
     * 从SharedPreferences获取渠道版本
     */
    public static String getChannel(Context context){
        return getSP(context).getString(CHANNEL, "");
    }

    /**
     * 记录渠道版本
     */
    public static void rememberChannel(Context context, String channel){
        SharedPreferences.Editor editor = getSP(context).edit();
        editor.putString(CHANNEL, channel);
        editor.commit();
    }

    /**
     * 获取保存登录tokend的时间
     */
    public static long getSaveLoginTokenTime(Context context){
        return getSP(context).getLong(SAVE_LOGIN_TOKEN_TIME, 0);
    }

    /**
     * 是否需要网络加载最新的科室列表数据
     * @param context
     * @return
     */
    public static boolean shouldLoadLatestDepartmentList(Context context){
        SharedPreferences sp = getSP(context);
        long lastLoadTime = sp.getLong(LOAD_DEPARTMENT_LIST_TIME_BY_HTTP, 0);
        if (new Date().getTime() - lastLoadTime > 1 * 60 * 60 * 1000){
            return true;
        }
        return false;
    }

    /**
     *保存此次网络加载最新科室列表数据的时间
     * @param context
     * @return
     */
    public static void saveLoadDepartmentListTime(Context context){
        SharedPreferences.Editor editor = getSP(context).edit();
        editor.putLong(LOAD_DEPARTMENT_LIST_TIME_BY_HTTP, new Date().getTime());
        editor.apply();
    }

    /**
     *判断是否有未读的消息
     * @param context
     * @return
     */
    public static boolean hasUnreadMessage(Context context){
        SharedPreferences sp = getSP(context);
        return sp.getBoolean(HAS_UNREAD_MESSAGES, false);
    }

    /**
     *设置是否有未读的消息
     * @param context
     * @return
     */
    public static void setUnreadMessage(Context context, boolean has){
        SharedPreferences.Editor editor = getSP(context).edit();
        editor.putBoolean(HAS_UNREAD_MESSAGES, has);
        editor.apply();
    }
}
