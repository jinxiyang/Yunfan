package com.yang.yunfan.manager;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.yang.yunfan.AppApplication;
import com.yang.yunfan.model.UserInfo;
import com.yang.yunfan.model.UserInfoDao;

import java.util.List;

import rx.Observable;

/**
 * Created by yang on 2017/2/14.
 */

public class UserInfoManager {

    private static final String DEFAULT_USER_ID = "default_user_id";

    public static void saveDefaultUserIdInSp(String id){
        SharedPreferences sp = SharedPreferencesManager.getSP(AppApplication.getInstance());
        sp.edit().putString(DEFAULT_USER_ID, id).apply();
    }
    public static String getDefaultUserIdFromSp(){
        SharedPreferences sp = SharedPreferencesManager.getSP(AppApplication.getInstance());
        return sp.getString(DEFAULT_USER_ID, "");
    }

    public static Observable<List<UserInfo>> getAllUser(){
        return AppApplication.getInstance().getDaoSession().getUserInfoDao().queryBuilder().rx().list();
    }

    public static UserInfo getUserInfo(){
        String id = getDefaultUserIdFromSp();
        if (TextUtils.isEmpty(id)){
            return null;
        }else {
            return getUserInfoById(id);
        }
    }
    public static void deleteUserInfo(){
        saveDefaultUserIdInSp("");
    }
    public static UserInfo getUserInfoById(String id){
        List<UserInfo> list = AppApplication.getInstance().getDaoSession().getUserInfoDao().queryBuilder().where(UserInfoDao.Properties.Id.eq(id)).list();
        if (list == null || list.isEmpty()){
            return null;
        }else {
            return list.get(0);
        }
    }

    public static void saveUserInfo(UserInfo userInfo){
        saveDefaultUserIdInSp(userInfo.getId());
        AppApplication.getInstance().getDaoSession().getUserInfoDao().insertOrReplaceInTx(userInfo);
    }

}
