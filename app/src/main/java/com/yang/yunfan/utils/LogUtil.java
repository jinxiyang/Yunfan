package com.yang.yunfan.utils;

import android.util.Log;

import com.yang.yunfan.BuildConfig;


/**
 * 日志打印类
 */
public class LogUtil {
	private static final String DEFULT_TAG = "===log===";

	public static void v(String msg) {
		if (BuildConfig.DEBUG) {
			Log.v(DEFULT_TAG, msg);
		}
	}

	public static void d(String msg) {
		if (BuildConfig.DEBUG) {
			Log.d(DEFULT_TAG, msg);
		}
	}

	public static void i(String msg) {
		if (BuildConfig.DEBUG) {
			Log.i(DEFULT_TAG, msg);
		}
	}

	public static void w(String msg) {
		if (BuildConfig.DEBUG) {
			Log.w(DEFULT_TAG, msg);
		}
	}

	public static void e(String msg) {
		if (BuildConfig.DEBUG) {
			Log.e(DEFULT_TAG, msg);
		}
	}


	public static void i(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.i(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.w(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.e(tag, msg);
		}
	}
	public static void v(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.v(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.d(tag, msg);
		}
	}
}
