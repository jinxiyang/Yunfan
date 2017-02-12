package com.yang.yunfan.ui.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 2017/1/7.
 */

public class TabFragmentAdapter extends FragmentPagerAdapter {
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments;


    public TabFragmentAdapter(FragmentManager fm, List<String> list, List<Fragment> fragments) {
        super(fm);
        titles.addAll(list);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position).split(",")[0];
    }


}
