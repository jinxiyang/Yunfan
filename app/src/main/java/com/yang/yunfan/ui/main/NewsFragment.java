package com.yang.yunfan.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yang.yunfan.R;
import com.yang.yunfan.ui.base.BaseFragment;
import com.yang.yunfan.ui.news.EditNewsChannelActivity;
import com.yang.yunfan.ui.news.TabFragmentAdapter;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.iv_add_channel)
    ImageView ivAddChannel;

    private String[] newsTypes = new String[]{
            "头条,top",
            "社会,shehui",
            "国内,guonei",
            "国际,guoji",
            "娱乐,yule",
            "体育,tiyu",
            "军事,junshi",
            "科技,keji",
            "财经,caijing",
            "时尚,shishang"
    };

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        TabFragmentAdapter adapter = new TabFragmentAdapter(getChildFragmentManager(), Arrays.asList(newsTypes));
        viewpager.setOffscreenPageLimit(newsTypes.length);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
        return view;
    }

    @OnClick(R.id.iv_add_channel)
    public void onClick() {
        startActivity(new Intent(getContext(), EditNewsChannelActivity.class));
    }
}
