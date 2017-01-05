package com.yang.yunfan.utils;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.yang.yunfan.AppApplication;

/**
 * Toast util
 * Created by yang on 16/7/8.
 */
public class ToastUtil {

    private static Toast mToast;

    /**
     * 显示短时间的Toast
     * @param text
     */
    public static void showShort(@NonNull CharSequence text){
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示长时间的Toast
     * @param text
     */
    public static void showLong(@NonNull CharSequence text){
        show(text, Toast.LENGTH_LONG);
    }

    private static void show(@NonNull CharSequence text, @NonNull int duration){
        if (mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(AppApplication.getInstance().getApplicationContext(), text, duration);
        mToast.show();
    }
}
