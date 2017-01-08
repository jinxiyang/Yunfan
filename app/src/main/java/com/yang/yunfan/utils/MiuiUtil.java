package com.yang.yunfan.utils;

import android.os.Build;
import android.view.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yang on 2017/1/6.
 */

public class MiuiUtil {

    private static final String MIUI_PROPERTY = "ro.miui.ui.version.name";

    /**
     * 是否是小米手机
     *
     * @return
     */
    public static boolean isXiaomiPhone() {
        return Build.MANUFACTURER.equalsIgnoreCase("Xiaomi");
    }

    /**
     * 获取MIUI系统的版本，如果不是MIUI则返回null
     *
     * @return
     */
    public static String getMinuiRomVersion() {
        return getSystemProperty(MIUI_PROPERTY);
    }

    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader br = null;
        try {
            Process process = Runtime.getRuntime().exec("getprop " + propName);
            br = new BufferedReader(new InputStreamReader(process.getInputStream()), 1024);
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    /**
     *当手机系统是MIUI v6及其以上时，将状态栏字体变黑
     * @param window
     */
    public static void blackStatusText(Window window) {
        Class clazz = window.getClass();
        try {
            int tranceFlag = 0;
            int darkModeFlag = 0;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");

            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
            tranceFlag = field.getInt(layoutParams);

            field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);

            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
//            //只需要状态栏透明
//            extraFlagField.invoke(window, tranceFlag, tranceFlag);
            //状态栏透明且黑色字体
            extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
//            //清除黑色字体
//            extraFlagField.invoke(window, 0, darkModeFlag);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     *当手机系统是MIUI v6及其以上时，将状态栏字体变白色
     * @param window
     */
    public static void clearBlackStatusText(Window window) {
        Class clazz = window.getClass();
        try {
            int tranceFlag = 0;
            int darkModeFlag = 0;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");

            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
            tranceFlag = field.getInt(layoutParams);

            field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);

            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
//            //只需要状态栏透明
//            extraFlagField.invoke(window, tranceFlag, tranceFlag);
//            //状态栏透明且黑色字体
//            extraFlagField.invoke(window, tranceFlag | darkModeFlag, tranceFlag | darkModeFlag);
//            //清除黑色字体
//            extraFlagField.invoke(window, 0, darkModeFlag);
            //清除黑色字体
            extraFlagField.invoke(window, 0, darkModeFlag);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
