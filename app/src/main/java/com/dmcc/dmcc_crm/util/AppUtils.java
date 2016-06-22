package com.dmcc.dmcc_crm.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {


	/**
	 * 取得版本号
	 *
	 * @param context
	 * @return
	 */
	public static String GetVersion(Context context) {
		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return manager.versionName;
		} catch (NameNotFoundException e) {
			return "Unknown";
		}
	}



	/**
	 *
	 * 获取手机唯一标识符UUID
	 *
	 * @param context
	 * */

	public static String getDeviceID(Context context) {

		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, tmPhone, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String uniqueId = deviceUuid.toString();
		return uniqueId;
	}

	/**
	 *
	 * 简单跳转activity
	 *
	 * @param context
	 * */
	public static void startAct(Context context, Class activity) {
		context.startActivity(new Intent(context, activity));
	}

	/**
	 *
	 * 手机号正则判断
	 *
	 * @param str
	 * @return
	 */
	public static boolean RegexMatches(String str) {
		String pattern = "(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);

		return m.matches();
	}

	/**
	 *
	 * 邮箱正则判断
	 *
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}
}
