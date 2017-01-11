package com.yang.yunfan.ui.news;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.yang.yunfan.R;
import com.yang.yunfan.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditNewsChannelActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news_channel);
        ButterKnife.bind(this);

        toolBar.setTitle("");
        setSupportActionBar(toolBar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_news_channel_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_close){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
