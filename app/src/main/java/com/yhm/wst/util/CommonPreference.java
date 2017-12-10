package com.yhm.wst.util;


import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhm.wst.MyApplication;
import com.yhm.wst.database.util.StockProductSaveUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @ClassName: CommonPreference 
 * @Description: SharedPreference管理
 * @author liang_xs
 */
public class CommonPreference {
	private static final String PREF_FILE_NAME = "common_settings";
	public static final String IS_FIRST = "isFirst";
	private static final String USER_TOKEN = "user_token";
	private static final String USER_ID = "user_id";
	private static final String USER_NAME = "user_name";
	private static final String NAME = "name";
	private static final String USER_NO = "user_no";
	private static final String PASS_WORD = "pass_word";
	private static final String USER_ACTIONS = "user_actions";
	public static final String USER_SAVE = "user_save";

	public static String getStringValue(String keyWord, String defaultValue) {
		SharedPreferences sp = MyApplication.getApplication()
				.getSharedPreferences(PREF_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		return sp.getString(keyWord, defaultValue);
	}
	
	public static void setStringValue(String keyWord, String value) {
		SharedPreferences sp = MyApplication.getApplication()
				.getSharedPreferences(PREF_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		sp.edit().putString(keyWord, value).commit();
	}
	
	public static int getIntValue(String keyWord, int defaultValue) {
		SharedPreferences sp = MyApplication.getApplication()
				.getSharedPreferences(PREF_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		return sp.getInt(keyWord, defaultValue);
	}
	
	public static void setIntValue(String keyWord, int value) {
		SharedPreferences sp = MyApplication.getApplication()
				.getSharedPreferences(PREF_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		sp.edit().putInt(keyWord, value).commit();
	}
	
	public static boolean getBooleanValue(String keyWord, boolean defaultValue) {
		SharedPreferences sp = MyApplication.getApplication()
				.getSharedPreferences(PREF_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		return sp.getBoolean(keyWord, defaultValue);
	}
	
	public static void setBooleanValue(String keyWord, boolean isCheck) {
		SharedPreferences sp = MyApplication.getApplication()
				.getSharedPreferences(PREF_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		sp.edit().putBoolean(keyWord, isCheck).commit();
	}
	

	public static long getLongValue(String keyWord, long defaultValue) {
		SharedPreferences sp = MyApplication.getApplication()
				.getSharedPreferences(PREF_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		return sp.getLong(keyWord, defaultValue);
	}
	
	public static void setLongValue(String keyWord, long value) {
		SharedPreferences sp = MyApplication.getApplication()
				.getSharedPreferences(PREF_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		sp.edit().putLong(keyWord, value).commit();
	}

	public static void setSerializable(String key, Serializable value) {
		String json=JSON.toJSONString(value);
		setStringValue(key, json);
	}

	public static <T extends Serializable> T getSerializable(String key, TypeReference<T> type) {
		try {
			return JSON.parseObject(getStringValue(key, null), type);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void setObject(String key, Object obj){
		String jsonString=JSON.toJSONString(obj);
		setStringValue(key, jsonString);
	}
	
	
	public static <T> T getObject(String key, Class<T> clazz){
		
		try {
			String json=getStringValue(key, "");
			return JSON.parseObject(json, clazz);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static void remove(String key){
		SharedPreferences sp = MyApplication.getApplication()
				.getSharedPreferences(PREF_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		sp.edit().remove(key).commit();
	}

	public static String getUserToken(){
		return CommonPreference.getStringValue(USER_TOKEN,"");
	}

	public static void setUserToken(String token){
		CommonPreference.setStringValue(USER_TOKEN,token);
	}

	public static String getUserId() {
		return CommonPreference.getStringValue(USER_ID,"");
	}

	public static void setUserId(String userId){
		CommonPreference.setStringValue(USER_ID,userId);
	}

	public static String getUserName() {
		return CommonPreference.getStringValue(USER_NAME,"");
	}

	public static void setUserName(String userId){
		CommonPreference.setStringValue(USER_NAME,userId);
	}

	public static String getName() {
		return CommonPreference.getStringValue(NAME,"");
	}

	public static void setName(String userId){
		CommonPreference.setStringValue(NAME,userId);
	}
	public static String getUserNo() {
		return CommonPreference.getStringValue(USER_NO,"");
	}

	public static void setUserNo(String userId){
		CommonPreference.setStringValue(USER_NO,userId);
	}

	public static String getPassWord() {
		return CommonPreference.getStringValue(PASS_WORD,"");
	}

	public static void setPassWord(String userId){
		CommonPreference.setStringValue(PASS_WORD,userId);
	}

	public static String getUserActions(){
		return CommonPreference.getStringValue(USER_ACTIONS,"");
	}
	public static void setUserActions(String actions){
		CommonPreference.setStringValue(USER_ACTIONS,actions);
	}


	public static boolean getBackStockCheckStockEnterAction(){
		boolean back_stock_checkstockenter_open = false;
		try {
			String actions = getUserActions();
			if(TextUtils.isEmpty(actions)) {
				return back_stock_checkstockenter_open;
			}
			JsonValidator jsonValidator = new JsonValidator();
			if(jsonValidator.validate(actions)) {
				JSONObject obj = new JSONObject(actions);
				back_stock_checkstockenter_open = (boolean) obj.opt("back_stock_checkstockenter_open");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return back_stock_checkstockenter_open;
	}

	public static boolean getBackDecisionDescreportAction(){
		boolean back_decision_descreport = false;
		try {
			String actions = getUserActions();
			if(TextUtils.isEmpty(actions)) {
				return back_decision_descreport;
			}
			JsonValidator jsonValidator = new JsonValidator();
			if(jsonValidator.validate(actions)) {
				JSONObject obj = new JSONObject(actions);
				back_decision_descreport = (boolean) obj.opt("back_decision_descreport");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return back_decision_descreport;
	}

	public static void clearLocalData(){
		setUserActions("");
		setUserId("");
		setUserToken("");
		new StockProductSaveUtil(MyApplication.getApplication()).deleteAll();
	}

	private final static String API_HOST = "_api_host";
	private final static String DEFAULT_DEV_HOST = "http://api-test:8080/api/";

	public static void setApiHost(String url){
		CommonPreference.setStringValue(API_HOST,url);
	}

	public static String getApiHost(){
		return CommonPreference.getStringValue(API_HOST,DEFAULT_DEV_HOST);
	}


}
