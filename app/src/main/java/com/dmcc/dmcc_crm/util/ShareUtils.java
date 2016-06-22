package com.dmcc.dmcc_crm.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Share工具
 * 
 * @Description
 * @author wushange
 * @ClassName ShareUtils
 */
public class ShareUtils {
	/**
	 * 普通字段存放地址
	 */
	public static String ROOT = Contanst.PROJECT_ROOT;

	public static String getSharePreStr(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(ROOT, 0);
		String s = sp.getString(key, "");// 如果该字段没对应值，则取出字符串0
		return s;
	}
	public static String getSharePreStr_notify(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences("notifyinfo", 0);
		String s = sp.getString(key, "");// 如果该字段没对应值，则取出字符串0
		return s;
	}
	public static String getSharePreStr_Append(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(ROOT, Context.MODE_APPEND);
		String s = sp.getString(key, "");// 如果该字段没对应值，则取出字符串0
		return s;
	}

	public static String getSharePreStr(Context context, String key,
			String defual) {
		SharedPreferences sp = context.getSharedPreferences(ROOT, 0);
		String s = sp.getString(key, defual);// 如果该字段没对应值，则取出字符串0
		return s;
	}

	// 取出whichSp中key字段对应的int类型的值
	public static int getSharePreInt(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(ROOT, 0);
		int i = sp.getInt(key, 0);// 如果该字段没对应值，则取出0
		return i;
	}

	// 取出whichSp中key字段对应的boolean类型的值
	public static boolean getSharePreBoolean(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(ROOT, 0);
		boolean i = sp.getBoolean(key, true);// 如果该字段没对应值，则取出0
		return i;
	}

	// 取出whichSp中key字段对应的boolean类型的值
	public static boolean getSharePreBoolean(Context context, String key,
			boolean defual) {
		SharedPreferences sp = context.getSharedPreferences(ROOT, 0);
		boolean i = sp.getBoolean(key, defual);// 如果该字段没对应值，则取出0
		return i;
	}

	// 取出whichSp中key字段对应的boolean类型的值
	public static boolean getloginSharePreBoolean(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(ROOT, 0);
		boolean i = sp.getBoolean(key, false);// 如果该字段没对应值，则取出0
		return i;
	}

	// 保存string类型的value到whichSp中的key字段
	public static void putSharePre(Context context, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(ROOT, 0);
		sp.edit().putString(key, value).commit();
	}
	// 保存消息通知到notifyinfo中
	public static void putSharePre_notify(Context context, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences("notifyinfo", 0);
		sp.edit().putString(key, value).commit();
	}
	
	// 保存string类型的value到whichSp中的key字段
	public static void putSharePre_Append(Context context, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(ROOT, Context.MODE_APPEND);
		sp.edit().putString(key, value).commit();
	}

	// 保存int类型的value到whichSp中的key字段
	public static void putSharePre(Context context, String key, int value) {
		SharedPreferences sp = context.getSharedPreferences(ROOT, 0);
		sp.edit().putInt(key, value).commit();
	}

	// 保存boolean类型的value到whichSp中的key字段
	public static void putSharePre(Context context, String key, Boolean value) {
		SharedPreferences sp = context.getSharedPreferences(ROOT, 0);
		sp.edit().putBoolean(key, value).commit();
	}

	// 清空保存的数据
	public static void clearSharePre(Context context) {
		try {
			SharedPreferences sp = context.getSharedPreferences(ROOT, 0);
			sp.edit().clear().commit();
		} catch (Exception e) {
		}
	}
	// 清空保存指定名称的数据
	public static void clearSharePre(Context context,String sharepre) {
		try {
			SharedPreferences sp = context.getSharedPreferences(sharepre, 0);
			sp.edit().clear().commit();
		} catch (Exception e) {
		}
	}

}
