package com.ewhaapp.keepacurfew;

public class MyUserManager {
	private static MyUserManager instance;

	public MyDataClass myHomeData=new MyDataClass();
	public MyDataClass nowLocationData=new MyDataClass();
	public static MyUserManager getInstance() {
		if (instance == null) {
			instance = new MyUserManager();
		}
		return instance;
	}
}
