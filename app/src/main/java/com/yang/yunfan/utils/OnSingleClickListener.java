package com.yang.yunfan.utils;

import android.view.View;

/**
 * 避免响应用户短时间内连续两次单击事件
 * Created by yangjinxi on 2016/4/17.
 */
public abstract class OnSingleClickListener implements View.OnClickListener {
    /**
     * 默认的间隔时间
     */
    private long DEFAULT_INTERVAL_TIME = 500;

    private static long lastTime;

    /**
     * 处理点击事件
     * @param v
     */
    public abstract void onSingleClick(View v);
    @Override
    public void onClick(View v) {
        if (isDoubleClick()){
            return;
        }
        onSingleClick(v);
    }

    private boolean isDoubleClick(){
        boolean flag = false;
        long currentTime = System.currentTimeMillis();
        long time = currentTime - lastTime;
        if (time < 500){
            flag = true;
        }
        lastTime = currentTime;
        return flag;
    }

    /**
     * 获取间隔时间
     * @return
     */
    public long getIntervalTime(){
        return DEFAULT_INTERVAL_TIME;
    }

    /**
     * 设置间隔时间
     * @param intervalTime
     * @return
     */
    public OnSingleClickListener setIntervalTime(long intervalTime){
        DEFAULT_INTERVAL_TIME = intervalTime;
        return this;
    }
}
