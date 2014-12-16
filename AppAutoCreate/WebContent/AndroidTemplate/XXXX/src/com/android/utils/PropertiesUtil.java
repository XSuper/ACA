package com.android.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;

import com.android.bean.VideoType;
import com.android.xxxx.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PropertiesUtil {

	public String name;//应用名称
	public ArrayList<VideoType> types;//类别数据
	public int themeColor;//主题颜色
	public String price; //价格
	public int pricetype; //价格类型
	public int op;
	public String chid;
	
	
	
	private static PropertiesUtil pUtil;
	public static PropertiesUtil getInstance(Context context){
		if(pUtil ==null){
			pUtil = new PropertiesUtil(context);
		}
		return pUtil;
	}
	private PropertiesUtil(Context context) {
		Properties properties = new Properties();
		try {
			properties.load(context.getResources().openRawResource(R.raw.load));
			name = properties.getProperty("name");
			themeColor = Color.parseColor(properties.getProperty("color"));
			String str = properties.getProperty("typedata");
			Gson gson = new Gson();
			types = gson.fromJson(str, new TypeToken<ArrayList<VideoType>>(){}.getType());
			
			price = properties.getProperty("price");
			//默认包月
			pricetype = Integer.parseInt(properties.getProperty("pricetype"));
			//默认移动联通
			op = Integer.parseInt(properties.getProperty("op"));
			chid = properties.getProperty("chid");
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
