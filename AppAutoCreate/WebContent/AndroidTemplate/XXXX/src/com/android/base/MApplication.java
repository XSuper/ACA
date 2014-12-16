package com.android.base;

import android.app.Application;

public class MApplication extends Application {

	private static MApplication mApplication;
	
	public static MApplication getApplication(){
		return mApplication;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mApplication  = this;
		
	}

}
