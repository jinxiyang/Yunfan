package com.yang.yunfan.ui.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yang.yunfan.R;
import com.yang.yunfan.ui.base.BaseFragment;
import com.yang.yunfan.utils.LogUtil;
import com.yang.yunfan.utils.ToastUtil;

import icepick.Icepick;
import icepick.State;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {

    @State String message;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        view.findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "hello";
//                ToastUtil.showShort("hello");
                Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(message)){
            LogUtil.i(message);
        }else {
            LogUtil.i("message null");

        }
    }
}
