package com.android.control;

import android.content.Context;

public abstract class DataRequestCallBack{

	Context context;
	public DataRequestCallBack(Context context) {
		this.context = context;
	}
	public abstract void onSuccess(Response response);
	public abstract void onFailure();
	
}
