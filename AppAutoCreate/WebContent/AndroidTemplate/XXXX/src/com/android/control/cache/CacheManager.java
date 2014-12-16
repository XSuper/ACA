package com.android.control.cache;

import java.util.Date;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

/**
 * 缓存管理类
 * @author xiaochao
 *
 */

public class CacheManager {
	public  String TAG = this.getClass().getSimpleName();
	private MemoryCache memoryCache;
	private DataBaseCache baseCache;
	private static CacheManager cacheManager;
	
	private CacheManager(){
		memoryCache = MemoryCache.getInstance();
		baseCache = DataBaseCache.getInstance();
		
	}
	public static CacheManager getInstance(){
		if(cacheManager == null){
			cacheManager = new CacheManager();
		}
		return cacheManager;
	}
	public void put(String key,Object value,long timeout,ICacheMethod callback,CachePolicy policy){
		switch (policy) {
		case PUT_MEMORY_DATEBASE:
			baseCache.add(key, value, timeout);
			break;

		case PUT_ONLY_MEMORY:
			break;
			
		default://默认都内存和数据库都缓存
			Log.e(TAG,"请使用put 规则，默认内存和数据库都做缓存");
			baseCache.add(key, value, timeout);
			break;
			
		}
		//缓存到内存
		memoryCache.add(key, value, timeout, callback);
	}
	public void put(String key,Object value,long timeout,CachePolicy policy){
		put(key, value, timeout,null, policy);
	}
	public void put(String key,Object value,CachePolicy policy){
		put(key, value, -1,null, policy);
	}
	public void put(String key,Object value){
		put(key, value, -1,null, CachePolicy.PUT_MEMORY_DATEBASE);
	}
	public void put(String key,Object value,long timeout){
		put(key, value, timeout,null, CachePolicy.PUT_MEMORY_DATEBASE);
	}

	public Object get(String key,TypeToken token,CachePolicy policy){
		Object obj = null;
		switch (policy) {
		case PUT_MEMORY_DATEBASE:
		case PUT_ONLY_MEMORY:
			Log.e(TAG, "请使用get规则，默认GET_MEMORY_DATEBASE方式");
		case GET_MEMORY_DATEBASE:
			obj = memoryCache.get(key);
			if(obj==null){
			DataBaseCacheItem item = baseCache.getItem(key);
				if(item!= null){
					obj = baseCache.get(key, token);
					if(obj!=null){
						//缓存到内存中
						//*此处应该先减去当前时间，因为储存到内存缓存时会再次加上当前时间
						memoryCache.add(key, obj, item.getTimeOut()-new Date().getTime());
					}
				}
			}
			break;
		case GET_ONLY_MEMORY:
			obj = memoryCache.get(key);
			break;
		
		}
		return obj;
	}
	
	/**
	 * 仅到内存获取缓存
	 * @param key
	 * @return
	 */
	public Object getFromMemoryCache(String key){
		return memoryCache.get(key);
	}
	/**
	 * 到内存和数据库缓存取数据
	 * @param key
	 * @param token
	 * @return
	 */
	public Object get(String key,TypeToken token){
		return get(key,token,CachePolicy.GET_MEMORY_DATEBASE);
	}
}
