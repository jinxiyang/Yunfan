package com.yang.yunfan.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.suke.widget.SwitchButton;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yang.yunfan.AppApplication;
import com.yang.yunfan.R;
import com.yang.yunfan.manager.DayNightManager;
import com.yang.yunfan.manager.UserInfoManager;
import com.yang.yunfan.model.UserInfo;
import com.yang.yunfan.ui.base.BaseFragment;
import com.yang.yunfan.ui.setting.SettingActivity;
import com.yang.yunfan.utils.ToastUtil;
import com.yang.yunfan.widget.ImageTextItemView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {


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
    @BindView(R.id.sdv_user_icon)
    SimpleDraweeView sdvUserIcon;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
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

        mgivSystemSetting.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });
        return view;
    }


    @OnClick({R.id.rl_person_layout, R.id.mgiv_my_message, R.id.mgiv_my_collection})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_person_layout:
                qqLogin();
                break;
            case R.id.mgiv_my_message:
                ToastUtil.showShort("点击了");
                break;
            case R.id.mgiv_my_collection:
                ToastUtil.showShort("点击了");
                break;
        }
    }

    private void qqLogin() {
        UMShareAPI.get(getContext()).doOauthVerify((AppCompatActivity) getContext(), SHARE_MEDIA.QQ, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.i(TAG, "onComplete: ");
                Log.i(TAG, "onComplete: " + map.toString());
                getQQInfo();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.i(TAG, "onError: ");
                ToastUtil.showShort(throwable.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.i(TAG, "onCancel: ");
            }
        });
    }

    private void getQQInfo() {
        UMShareAPI.get(getContext()).getPlatformInfo((AppCompatActivity) getContext(), SHARE_MEDIA.QQ, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.i(TAG, "onComplete: " + map.toString());
                String iconurl = map.get("iconurl");
                String name = map.get("name");
                String openid = map.get("openid");
                sdvUserIcon.setImageURI(iconurl);
                tvPersonName.setText(name);
                rlPersonLayout.setClickable(false);

                UserInfo userInfo = new UserInfo();
                userInfo.setName(name);
                userInfo.setIconurl(iconurl);
                userInfo.setAccountType(1);
                userInfo.setId(openid);
                AppApplication.getInstance().setUserInfo(userInfo);
                UserInfoManager.saveUserInfo(userInfo);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                ToastUtil.showShort(throwable.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        int uiModeFromSp = DayNightManager.getUiModeFromSp(getContext());
        sbNightTheme.setChecked(uiModeFromSp == DayNightManager.NIGHT ? true : false);
        sbSaveMobileData.setChecked(AppApplication.getInstance().isSaveMobileData());

        UserInfo userInfo = AppApplication.getInstance().getUserInfo();
        if (userInfo != null){
            sdvUserIcon.setImageURI(userInfo.getIconurl());
            tvPersonName.setText(userInfo.getName());
            rlPersonLayout.setClickable(false);
        }else {
            sdvUserIcon.setImageURI("res://com.yang.yunfan/" + R.drawable.umeng_socialize_qq);
            tvPersonName.setText(R.string.un_login);
            rlPersonLayout.setClickable(true);
        }
    }
}
