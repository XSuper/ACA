package com.android.control.cache;

/**
 * 缓存规则
 *
 */
public enum CachePolicy {

	/**
	 * 仅到内存缓存取数据，没有为null
	 */
	GET_ONLY_MEMORY,
	/**
	 * 先到内存取数据，没有查询数据库
	 */
	GET_MEMORY_DATEBASE,

	/**
	 * 缓存到内存和数据库
	 */
	PUT_MEMORY_DATEBASE,
	/**
	 * 仅缓存到内存
	 */
	PUT_ONLY_MEMORY,
	

	
}
