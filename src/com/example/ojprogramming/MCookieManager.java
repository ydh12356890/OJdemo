package com.example.ojprogramming;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class MCookieManager {
	private MCookieManager(){}  

	/** 
	 * 应用启动的时候调用，参考：{@link CookieManager#getInstance CookieManager.getInstance()} 
	 * */  
	public static void init(Context context){  
		CookieSyncManager.createInstance(context);  
	}  

	public static String getCookie(String url){  
		CookieManager cookieManager = CookieManager.getInstance();  
		return cookieManager.getCookie(url);  
	}  

	/** 
	 * http://stackoverflow.com/questions/16007084/does-android-webkit-cookiemanager-works-on-android-2-3-6 
	 * */  
	public static void setCookies(String url, Map<String, List<String>> headerFields) {  
		if (null == headerFields) {  
			return;  
		}  
		List<String> cookies = headerFields.get("Set-Cookie");  
		if (null == cookies) {  
			return;  
		}  
		CookieSyncManager.getInstance().startSync();  
		for (String cookie : cookies) {  
			setCookie(url, cookie);  
		}  
		CookieSyncManager.getInstance().sync();  
	}  

	private static void setCookie(String url, String cookie) {  
		CookieManager cookieManager = CookieManager.getInstance();  
		cookieManager.setAcceptCookie(true);  

		/*if(cookie.indexOf("Expires") < 0){  
			cookie = addExpireToCookie(cookie);  
		}*/  
		cookieManager.setCookie(url, cookie);  
	}  


}
