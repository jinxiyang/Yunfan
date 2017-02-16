package com.yang.yunfan.ui.setting;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yang.yunfan.AppApplication;
import com.yang.yunfan.R;
import com.yang.yunfan.manager.UserInfoManager;
import com.yang.yunfan.ui.base.BaseActivity;
import com.yang.yunfan.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        tvTitle.setText(R.string.system_setting);
        toolBar.setTitle("");
        toolBar.setNavigationIcon(R.drawable.black_leftbackicon);
        setSupportActionBar(toolBar);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (AppApplication.getInstance().getUserInfo() == null){
            tvLogout.setText("您还未登录");
            tvLogout.setClickable(false);
            tvLogout.setAlpha(0.5f);
        }else {
            tvLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserInfoManager.deleteUserInfo();
                    AppApplication.getInstance().setUserInfo(null);
                    ToastUtil.showShort("退出成功");
                    finish();
                }
            });
        }

    }
}
