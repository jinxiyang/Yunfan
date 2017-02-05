package com.yang.yunfan.ui.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yang.yunfan.R;
import com.yang.yunfan.ui.base.BaseFragment;
import com.yang.yunfan.ui.video.WeipaiVideoListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends BaseFragment {


    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, view);
        tvTitle.setText(R.string.video);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getChildFragmentManager().beginTransaction().replace(R.id.contentFrame, new WeipaiVideoListFragment()).commit();
    }
}
