package com.yang.yunfan.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

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
    protected boolean isLoad = false;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        attemptLoad();
    }

    private void attemptLoad() {
        if (!isViewCreated || isLoad){
            return;
        }

        if (getUserVisibleHint()){
            lazyLoad();
            isLoad = true;
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
     * 懒加载数据，子类重写此方法
     */
    public void lazyLoad(){

    }
}
