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


    public TabFragmentAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        titles.addAll(list);
    }

    @Override
    public Fragment getItem(int position) {
        return NewsTabFragment.newInstance(titles.get(position));
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
