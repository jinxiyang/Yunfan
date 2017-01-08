package com.yang.yunfan.model;

import java.util.List;

/**
 * Created by yang on 2017/1/7.
 */

public class ResultJH<T> {
    private String stat;
    private List<T> data;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
