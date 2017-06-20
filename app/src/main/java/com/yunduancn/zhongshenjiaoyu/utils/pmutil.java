package com.yunduancn.zhongshenjiaoyu.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;


public class pmutil {
	static String userid;
	static SharedPreferences sp;



	/**
	 * 调用Linux的busybox
	 *
	 * @return
	 */

	public static String getMacAddress() {
		String result = "";
		String Mac = "";
		result = callCmd("busybox ifconfig", "HWaddr");

		if (result == null) {
			return "网络出错，请检查网络";
		}
		if (result.length() > 0 && result.contains("HWaddr")) {
			Mac = result.substring(result.indexOf("HWaddr") + 6,
					result.length() - 1);
			if (Mac.length() > 1) {
				result = Mac.toLowerCase();
			}
		}
		return result.trim();
	}

	public static String callCmd(String cmd, String filter) {
		String result = "";
		String line = "";
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStreamReader is = new InputStreamReader(proc.getInputStream());
			BufferedReader br = new BufferedReader(is);

			// 执行命令cmd，只取结果中含有filter的这一行
			while ((line = br.readLine()) != null
					&& line.contains(filter) == false) {
				// result += line;
				Log.i("test", "line: " + line);
			}

			result = line;
			Log.i("test", "result: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getMacAddress(Context context) {
		// 获取mac地址：
		String macAddress = "000000000000";
		try {
			WifiManager wifiMgr = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = (null == wifiMgr ? null : wifiMgr
					.getConnectionInfo());
			if (null != info) {
				if (!TextUtils.isEmpty(info.getMacAddress()))
					macAddress = info.getMacAddress().replace(":", "");
				else
					return macAddress;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return macAddress;
		}
		return macAddress;
	}


	/**
	 * 获取imsi
	 *
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		String imsi = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();

		return imsi;
	}

	/**
	 * 获取imei
	 *
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		String imei = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		return imei;
	}

	/**
	 * 获取DeviceId
	 */
	public static String getDevice(Context context) {
		String Device = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

		return Device;
	}

	/**
	 * android.os.Build.MANUFACTURER; 获取生产厂家
	 */
	public static String getMANUFACTURER() {

		return android.os.Build.MANUFACTURER;
	}

	public static String md5(Map<String, String> map) {

		String sin = getParamsFromMap(map).toLowerCase() + "gogotoo.net";

		System.out.println("sin======" + sin);

		//generateKey("baseuserid=3&device=000000000000000&imei=000000000000000&imsi=310260000000000&mac=08:00:27:c0:97:a1&manufacturer=unknown&network=wifi&os=android&resolution=768*1184&timestamp=1468984617277&userid=3&ver=1.1.1gogotoo.net");
		return generateKey(sin);

	}

	public static String md52(Map<String, Object> map) {

		String sin = getParamsFromMap1(map).toLowerCase() + "gogotoo.net";

		System.out.println("sin======" + sin);

		//generateKey("baseuserid=3&device=000000000000000&imei=000000000000000&imsi=310260000000000&mac=08:00:27:c0:97:a1&manufacturer=unknown&network=wifi&os=android&resolution=768*1184&timestamp=1468984617277&userid=3&ver=1.1.1gogotoo.net");
		return generateKey(sin);

	}

	public static String getParamsFromMap1(Map<String, Object> map1) {

		// Collection<String> keyset = map.keySet();
		// List<String> list = new ArrayList<String>(keyset);
		// Collections.sort(list);
		StringBuffer buffer = new StringBuffer();
		// for (int i = 0; i < list.size(); i++) {
		// String key = list.get(i);
		// String value = map.get(list.get(i));
		//
		// System.out.println("key键---值: " + list.get(i) + ","
		// + map.get(list.get(i)));
		//
		// buffer.append(key).append("=").append(value).append("&");
		// }

		Map<String, Object> resultMap = sortMapByKey1(map1);
		for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
			String key = entry.getKey().toLowerCase();
			Object value = entry.getValue();
			System.out.println("key键---值: " + key + "," + value);
			//Gson gson = new Gson();
			if(value instanceof String){

			}else{
				continue;
				//value = gson.toJson(value);
			}
			buffer.append(key).append("=").append(value).append("&");
		}
		buffer.deleteCharAt(buffer.length() - 1);

		return buffer.toString();
	}

	public static Map<String, Object> sortMapByKey1(Map<String, Object> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		Map<String, Object> sortMap = new TreeMap<String, Object>(
				new MapKeyComparator());
		sortMap.putAll(map);

		return sortMap;
	}

	public static String getParamsFromMap(Map<String, String> map1) {

		// Collection<String> keyset = map.keySet();
		// List<String> list = new ArrayList<String>(keyset);
		// Collections.sort(list);
		StringBuffer buffer = new StringBuffer();
		// for (int i = 0; i < list.size(); i++) {
		// String key = list.get(i);
		// String value = map.get(list.get(i));
		//
		// System.out.println("key键---值: " + list.get(i) + ","
		// + map.get(list.get(i)));
		//
		// buffer.append(key).append("=").append(value).append("&");
		// }

		Map<String, String> resultMap = sortMapByKey(map1);
		for (Map.Entry<String, String> entry : resultMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			System.out.println("key键---值: " + key + "," + value);
			buffer.append(key).append("=").append(value).append("&");
		}
		buffer.deleteCharAt(buffer.length() - 1);

		return buffer.toString();
	}

	/**
	 * 使用 Map按key进行排序
	 *
	 * @param map
	 * @return
	 */
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		Map<String, String> sortMap = new TreeMap<String, String>(
				new MapKeyComparator());
		sortMap.putAll(map);

		return sortMap;
	}

	public static String generateKey(String url) {
		String cacheKey = null;
		try {
			MessageDigest mDigest = MessageDigest.getInstance("MD5");

			mDigest.update(url.getBytes());
			byte[] digest = mDigest.digest();
			// byte转成16进制的字符串(效果：根据url，加密，后生成一串固定长度的字符串)
			cacheKey = bytesToHexString(digest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cacheKey;

	}

	// byte转成16进制的字符串 32位加密
	private static String bytesToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hexString = Integer.toHexString(0xFF & bytes[i]);
			if (hexString.length() == 1) {
				sb.append('0');
			}
			sb.append(hexString);
		}

		System.out.println("md5=" + sb.toString());
		return sb.toString();
	}

	/**
	 * @return
	 * 		布尔类型转0或1
	 */
	public static int BooleanToInt(boolean b){
		if(b){
			return 1;
		}else{
			return 0;
		}
	}



}

class MapKeyComparator implements Comparator<String> {

	@Override
	public int compare(String str1, String str2) {

		return str1.compareTo(str2);
	}
}