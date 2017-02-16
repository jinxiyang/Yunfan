package com.yang.yunfan.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yang on 2017/2/14.
 */

@Entity
public class UserInfo {

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像地址
     */
    private String iconurl;

    /**
     *账号来源类型，以后可拓展
     *
     * 1、QQ
     */
    private int accountType;

    /**
     *账号id,根据账号来源，对应相应的账号
     *
     * 1、QQ用户时的openid
     */
    @Id
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserInfo() {
    }

    @Generated(hash = 1974616192)
    public UserInfo(String name, String iconurl, int accountType, String id) {
        this.name = name;
        this.iconurl = iconurl;
        this.accountType = accountType;
        this.id = id;
    }
}
