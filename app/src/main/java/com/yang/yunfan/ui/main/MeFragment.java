package com.yang.yunfan.ui.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.suke.widget.SwitchButton;
import com.yang.yunfan.AppApplication;
import com.yang.yunfan.R;
import com.yang.yunfan.manager.DayNightManager;
import com.yang.yunfan.ui.base.BaseFragment;
import com.yang.yunfan.widget.ImageTextItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_person)
    ImageView ivPerson;
    @BindView(R.id.tv_person_name)
    TextView tvPersonName;
    @BindView(R.id.rl_person_layout)
    RelativeLayout rlPersonLayout;
    @BindView(R.id.mgiv_my_message)
    ImageTextItemView mgivMyMessage;
    @BindView(R.id.mgiv_my_collection)
    ImageTextItemView mgivMyCollection;
    @BindView(R.id.sb_night_theme)
    SwitchButton sbNightTheme;
    @BindView(R.id.mgiv_system_setting)
    ImageTextItemView mgivSystemSetting;
    @BindView(R.id.sb_save_mobile_data)
    SwitchButton sbSaveMobileData;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        tvTitle.setText(R.string.personal_center);
        sbNightTheme.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                DayNightManager.switchDayNight((AppCompatActivity) getContext());
            }
        });

        sbSaveMobileData.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                AppApplication.getInstance().setSaveMobileData(isChecked);
            }
        });
        return view;
    }

    @OnClick({R.id.rl_person_layout, R.id.mgiv_my_message, R.id.mgiv_my_collection, R.id.mgiv_system_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_person_layout:
                break;
            case R.id.mgiv_my_message:
                break;
            case R.id.mgiv_my_collection:
                break;
            case R.id.mgiv_system_setting:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        int uiModeFromSp = DayNightManager.getUiModeFromSp(getContext());
        sbNightTheme.setChecked(uiModeFromSp == DayNightManager.NIGHT ? true : false);
        sbSaveMobileData.setChecked(AppApplication.getInstance().isSaveMobileData());
    }
}
