package com.qunyu.taoduoduo.utils;


import com.qunyu.taoduoduo.global.Constant;

/**
 * 日志打印类，打印统一在这里处理
 * 
 * @author Toby
 * 
 */
public class LogUtil {
	public static final boolean hasLog= Constant.DEBUG;
	public static final String TAG = "taoduoduo";

	/**
	 * @param tag
	 * @param paramString
	 */
	public static void Log(String tag, String paramString) {
		if (hasLog)
			android.util.Log.i(tag, paramString);
	}

	public static void Log(String paramString) {
		Log(TAG, paramString);
	}

	public static void ErrorLog(Exception e) {
		ErrorLog(TAG, e);
	}

	public static void ErrorLog(String tag, Exception e) {
		if (hasLog)
			android.util.Log.e(tag, "", e);
	}
	
	public static void e(String paramString){
		if (hasLog)
			android.util.Log.e(TAG,paramString);
	}
}
