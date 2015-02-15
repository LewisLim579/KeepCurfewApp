package com.ewhaapp.keepacurfew;

import android.content.Context;
import android.content.SharedPreferences;

public class PropertyManager {
	
	private static PropertyManager instance;

	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}

	private static final String PREF_NAME = "mysettting";
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;


	private PropertyManager() {
		mPrefs = MyApplication.getContext().getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		mEditor = mPrefs.edit();
	}
	
	private static final String FIELD_USER_ADDRESS = "useraddress";

	private String mUserAddress;

	public void setUserAddress(String address) {
		mUserAddress = address;
		mEditor.putString(FIELD_USER_ADDRESS, address); // 앞의 태그로 email 세팅.
		mEditor.commit();
	}

	public String getUserAddress() {
		if (mUserAddress == null) {
			mUserAddress = mPrefs.getString(FIELD_USER_ADDRESS, ""); // 태그로설정된거 가져오고
																	// 없으면 비워두기.
		}
		return mUserAddress;
	}
	
	private static final String FIELD_USER_TOSMS = "usertosms";

	private String mUserToSms;

	public void setUserToSms(String tosms) {
		mUserToSms = tosms;
		mEditor.putString(FIELD_USER_ADDRESS, tosms); // 앞의 태그로 email 세팅.
		mEditor.commit();
	}

	public String getUserToSms() {
		if (mUserToSms == null) {
			mUserToSms = mPrefs.getString(FIELD_USER_ADDRESS, ""); // 태그로설정된거 가져오고
																	// 없으면 비워두기.
		}
		return mUserToSms;
	}
	
	private static final String FIELD_USER_MESSAGECONTENT = "usermessagecontent";

	private String mUserMessageContent;

	public void setUserMessageContent(String messagecontent) {
		mUserMessageContent = messagecontent;
		mEditor.putString(FIELD_USER_ADDRESS, messagecontent); // 앞의 태그로 email 세팅.
		mEditor.commit();
	}

	public String getUserMessageContent() {
		if (mUserMessageContent == null) {
			mUserMessageContent = mPrefs.getString(FIELD_USER_ADDRESS, ""); // 태그로설정된거 가져오고
																	// 없으면 비워두기.
		}
		return mUserMessageContent;
	}

}
