package com.yang.yunfan.ui.main;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.yang.yunfan.R;
import com.yang.yunfan.ui.base.BaseActivity;
import com.yang.yunfan.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.State;

public class MainActivity extends BaseActivity implements OnTabSelectListener, OnTabReselectListener {

    @State
    int currIndex = -1;//当前fragment的index

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bottomBar.setOnTabSelectListener(this);
        bottomBar.setOnTabReselectListener(this);

        if (currIndex == -1) {
            currIndex = R.id.tab_news;//默认显示第一个
        }
        bottomBar.setDefaultTab(currIndex);
        bottomBar.setDefaultTabPosition(0);
    }

    private void showFragment(int id) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag(id + "");
        if (fragment == null) {
            fragment = createFragment(id);
            ft.add(R.id.contentFrame, fragment, id + "");
        }
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f != fragment && f.isVisible() && !f.isHidden()) {
                    ft.hide(f);
                }
            }
        }
        currIndex = id;
        ft.show(fragment).commit();
    }

    private Fragment createFragment(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.tab_news:
                fragment = new NewsFragment();
                break;
            case R.id.tab_video:
                fragment = new VideoFragment();
                break;
            case R.id.tab_chat:
                fragment = new ChatFragment();
                break;
            case R.id.tab_me:
                fragment = new MeFragment();
                break;
        }
        return fragment;
    }


    @Override
    public void onTabReSelected(@IdRes int tabId) {
        LogUtil.i(tabId + "onTabReSelected");
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        LogUtil.i(tabId + "onTabSelected");
        showFragment(tabId);
    }
}
