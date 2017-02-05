package com.yang.yunfan.ui.base;

/**
 * 懒加载数据
 * Created by yang on 2017/1/7.
 */

public class LazyLoadFragment_2 extends BaseFragment {

    /**
     * 是否已经加载数据了
     */
    private boolean isLoad = false;


    @Override
    public void onStart() {
        super.onStart();
        attemptLoad();
    }

    private void attemptLoad() {
        if (isLoad){
            return;
        }
        lazyLoad();
        isLoad = true;
    }

    /**
     * 懒加载数据，子类重写此方法
     */
    public void lazyLoad(){

    }
}
