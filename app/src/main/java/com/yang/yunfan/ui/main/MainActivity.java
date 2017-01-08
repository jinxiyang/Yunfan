package com.yang.yunfan.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.MenuItem;

import com.yang.yunfan.R;
import com.yang.yunfan.ui.base.BaseActivity;
import com.yang.yunfan.utils.MiuiUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import icepick.State;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.bnv_navigation)
    BottomNavigationView bnvNavigation;

    @State String fragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bnvNavigation.setOnNavigationItemSelectedListener(this);
        if (TextUtils.isEmpty(fragmentTag)){
            fragmentTag = R.id.menu_news + "";//默认显示第一个
        }
        bnvNavigation.getMenu().findItem(Integer.parseInt(fragmentTag)).setChecked(true);
        showFragment(fragmentTag);

    }

    //activity第一次显示时，此方法没有被调用
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String tag = item.getItemId() + "";
        if (!tag.equals(fragmentTag)){
            if (fragmentTag.equals(R.id.menu_chat + "")){
                MiuiUtil.clearBlackStatusText(getWindow());
            }
            if (tag.equals(R.id.menu_chat + "")){
                MiuiUtil.blackStatusText(getWindow());
            }
        }
        showFragment(tag);
        return true;
    }

    private void showFragment(String  tag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment == null){
            switch (Integer.parseInt(tag)){
                case R.id.menu_news:
                    fragment = new NewsFragment();
                    break;
                case R.id.menu_chat:
                    fragment = new ChatFragment();
                    break;
                case R.id.menu_me:
                    fragment = new MeFragment();
                    break;
            }
            ft.add(R.id.contentFrame, fragment, tag);
        }

        Fragment currFragment  = null;
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null){
            for (Fragment f : fragments){
                if (f.isVisible() && ! f.isHidden()){
                    currFragment = f;
                    break;
                }
            }
        }
        fragmentTag = tag;
        if (currFragment != null){
            if (currFragment == fragment){
                return;
            }
            ft.hide(currFragment).show(fragment).commit();
        }else {
            ft.show(fragment).commit();
        }
    }
}
