package com.yang.yunfan.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

/**
 * 管理日间/夜间主题
 * Created by yang on 2017/1/10.
 */

public class DayNightManager {

    /**
     * 日间主题
     */
    public static final int DAY = AppCompatDelegate.MODE_NIGHT_NO;

    /**
     * 夜间主题
     */
    public static final int NIGHT = AppCompatDelegate.MODE_NIGHT_YES;


    public static final String DAY_NIGHT_FLAG = "day_night_flag";

    /**
     * 从SharedPreferences获取保存的当前主题
     */
    public static int getUiModeFromSp(Context context){
        SharedPreferences sp = SharedPreferencesManager.getSP(context);
        return sp.getInt(DAY_NIGHT_FLAG, AppCompatDelegate.MODE_NIGHT_NO);
    }

    /**
     * 保存UiMode到SharedPreferences
     */
    public static void saveUiModeFromSp(Context context, int uiMode){
        SharedPreferences.Editor editor = SharedPreferencesManager.getSP(context).edit();
        editor.putInt(DAY_NIGHT_FLAG, uiMode);
        editor.apply();
    }

    /**
     * 切换主题,并保存UiMode到SharedPreferences
     */
    public static void switchDayNight(AppCompatActivity activity) {
        int currentNightMode = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        int nextMode = currentNightMode == Configuration.UI_MODE_NIGHT_NO ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        saveUiModeFromSp(activity, nextMode);
        AppCompatDelegate.setDefaultNightMode(nextMode);
        activity.getDelegate().setLocalNightMode(nextMode);
        activity.recreate();
    }

    /**
     * 判断app当前的主题UiMode
     * @param context
     * @return
     */
    public static int getCurrentUiMode(Context context){
        int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_NO ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES;
    }


    /**
     * 初始化app的主题UiMode，在application的onCreate中执行
     * @param context
     * @return
     */
    public static void initAppUiMode(Context context){
        //设置默认主题：日间（不是夜间）
        int uiMode = DayNightManager.getUiModeFromSp(context);
        AppCompatDelegate.setDefaultNightMode(uiMode == AppCompatDelegate.MODE_NIGHT_YES ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

    }

}
