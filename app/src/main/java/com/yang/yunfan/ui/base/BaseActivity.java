package com.yang.yunfan.ui.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.yang.yunfan.R;
import com.yang.yunfan.manager.DayNightManager;
import com.yang.yunfan.utils.LogUtil;

import icepick.Icepick;


/**
 * Created by yangjinxi on 2016/6/14.
 */
public class BaseActivity extends AppCompatActivity{
    protected String TAG = this.getClass().getSimpleName();

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        LogUtil.i(TAG + "------" + "onCreate");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i(TAG + "------" + "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG + "------" + "onDestroy");
    }

    @Override
    protected void onPause() {
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        super.onPause();
        LogUtil.i(TAG + "------" + "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i(TAG + "------" + "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i(TAG + "------" + "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.i(TAG + "------" + "onRestart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.i(TAG + "------" + "onRestoreInstanceState");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
        LogUtil.i(TAG + "------" + "onSaveInstanceState");
    }

    /**
     * 跳转到另一个activity
     * @param clazz
     */
    protected void gotoActivity(Class clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 跳转到另一个activity，附带动画效果
     * @param clazz
     */
    protected void gotoActivityWithAnim(Class clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_or_fragment_enter, R.anim.activity_or_fragment_exit);
    }
    /**
     * 跳转到另一个activity，附带动画效果
     */
    protected void gotoActivityWithAnim(Intent intent){
        startActivity(intent);
        overridePendingTransition(R.anim.activity_or_fragment_enter, R.anim.activity_or_fragment_exit);
    }
    /**
     * 跳转到另一个activity，附带动画效果
     */
    protected void gotoActivitiesWithAnim(Intent[] intents){
        startActivities(intents);
        overridePendingTransition(R.anim.activity_or_fragment_enter, R.anim.activity_or_fragment_exit);
    }
    /**
     * 跳转到另一个activity，附带动画效果
     */
    protected void startActivityForResultWithAnim(Intent intent, int requestCode){
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_or_fragment_enter, R.anim.activity_or_fragment_exit);
    }

    /**
     * 结束，附带动画效果
     */
    protected void finishWithAnim(){
        finish();
        if (animOnFinish()){
            overridePendingTransition(R.anim.activity_or_fragment_pop_enter, R.anim.activity_or_fragment_pop_exit);
        }
    }

    private boolean animOnFinish() {
        return true;
    }

    /**
     * 显示有文字的进度框
     * @param message
     * @return
     */
    public ProgressDialog showProgressDialog(@Nullable CharSequence message, @Nullable DialogInterface.OnCancelListener listener){
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        //点击返回键可以取消
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(listener);
        progressDialog.setMessage(message);
        //设置点击进度框外部不可取消
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        return progressDialog;
    }
    /**
     * 显示有文字的进度框,可传入自定义的进度对话框
     * @return
     */
    public ProgressDialog showProgressDialog(@NonNull ProgressDialog dialog){
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = dialog;
        progressDialog.show();
        return progressDialog;
    }

    /**
     * 显示无文字的进度框
     * @return
     */
    public ProgressDialog showProgressDialog(){
        return showProgressDialog(null, null);
    }

    /**
     * 隐藏进度框
     * @return
     */
    public void hideProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void lightStatusBar(boolean b){
        View decor = getWindow().getDecorView();
        int ui = decor.getSystemUiVisibility();
        if (b) {
            ui |=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        decor.setSystemUiVisibility(ui);
    }
}
