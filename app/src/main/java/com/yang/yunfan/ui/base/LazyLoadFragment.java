package com.yang.yunfan.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import icepick.State;

/**
 * 此fragment配合viewpager使用，切换tab页面时懒加载数据
 * Created by yang on 2017/1/7.
 */

public class LazyLoadFragment extends BaseFragment {

    /**
     * 视图是否已经生成，即onCreateView是否已经调用
     */
    protected boolean isViewCreated = false;

    /**
     * 是否已经加载数据了
     */
    @State
    boolean isLoad = false;

    /**
     * 表示是否重新展示过
     *
     * 当activity因主题切换而recreate时，fragment 从栈中恢复出来，这是就不要重新懒加载数据，直接重新展示恢复的数据
     */
    private boolean isReshow = false;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        attemptLoad();
    }

    private void attemptLoad() {
        if (!isViewCreated){
            return;
        }
        if (getUserVisibleHint()){
            if (isLoad){
                if (!isReshow){
                    reshowWhenRestoreData();
                    isReshow = true;
                }
            }else {
                lazyLoad();
                isLoad = true;
                //避免不是恢复数据时的重新展示
                isReshow = true;
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            attemptLoad();
        }
    }

    /**
     *fragment从栈中恢复出来,直接重新展示恢复的数据
     */
    public void reshowWhenRestoreData(){

    }

    /**
     * 懒加载数据，子类重写此方法
     */
    public void lazyLoad(){
    }
}
