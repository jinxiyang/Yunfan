package com.yang.yunfan.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yang.yunfan.R;
import com.yang.yunfan.utils.LogUtil;

import icepick.Icepick;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by yang on 16/7/3.
 */
public class BaseFragment extends Fragment {
    protected String TAG = this.getClass().getSimpleName();
    private ProgressDialog progressDialog;

    protected CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.i(TAG + "------" + "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.i(TAG + "------" + "onDetach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        LogUtil.i(TAG + "------" + "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.i(TAG + "------" + "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.i(TAG + "------" + "onCreateView");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.i(TAG + "------" + "onStart");
    }

    @Override
    public void onStop() {
        mSubscriptions.clear();
        super.onStop();
        LogUtil.i(TAG + "------" + "onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(TAG + "------" + "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (progressDialog != null){
            progressDialog.dismiss();
        }
        LogUtil.i(TAG + "------" + "onPause");
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.i(TAG + "------" + "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG + "------" + "onDestroy");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.i(TAG + "------" + "onHiddenChanged----" + hidden);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.i(TAG + "------" + "onActivityCreated");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
        LogUtil.i(TAG + "------" + "onSaveInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.i(TAG + "------" + "onConfigurationChanged");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.i(TAG + "------" + "setUserVisibleHint----" + isVisibleToUser);
    }

    /**
     * 跳转到另一个activity
     * @param clazz
     */
    protected void goToActivity(Class clazz){
        Intent intent = new Intent(getContext(), clazz);
        startActivity(intent);
    }
    /**
     * 跳转到另一个activity，附带动画效果
     * @param clazz
     */
    protected void gotoActivityWithAnim(Class clazz){
        Intent intent = new Intent(getContext(), clazz);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_or_fragment_enter, R.anim.activity_or_fragment_exit);
    }
    /**
     * 跳转到另一个activity，附带动画效果
     * @param intent
     */
    protected void gotoActivityWithAnim(Intent intent){
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_or_fragment_enter, R.anim.activity_or_fragment_exit);
    }

    /**
     * 跳转到另一个activity，附带动画效果
     * @param intent
     * @param requestCode
     */
    protected void startActivityForResultWithAnim(Intent intent, int requestCode){
        startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.activity_or_fragment_enter, R.anim.activity_or_fragment_exit);
    }

    /**
     * 关闭父activity
     */
    protected void closeParentActivity(){
        FragmentActivity parentAct = getActivity();
        parentAct.finish();
    }
    /**
     * 关闭父activity，附带动画效果
     */
    protected void closeParentActivityWithAnim(){
        FragmentActivity parentAct = getActivity();
        parentAct.finish();
        parentAct.overridePendingTransition(R.anim.activity_or_fragment_pop_enter, R.anim.activity_or_fragment_pop_exit);
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
        progressDialog = new ProgressDialog(getContext());
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



}
