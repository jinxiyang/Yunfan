package com.yang.yunfan.ui.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yang.yunfan.R;
import com.yang.yunfan.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BaseFragment {


    @BindView(R.id.btn_toggle)
    Button btnToggle;
    @BindView(R.id.night_overlay)
    View nightOverlay;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.btn_toggle)
    public void onClick() {
        if (nightOverlay.getVisibility() == View.VISIBLE){
            nightOverlay.setVisibility(View.GONE);
        }else {
            nightOverlay.setVisibility(View.VISIBLE);

        }
    }
}
