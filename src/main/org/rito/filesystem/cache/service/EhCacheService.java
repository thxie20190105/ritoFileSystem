package org.rito.filesystem.cache.service;

import java.util.ArrayList;
import java.util.List;

import org.rito.filesystem.util.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author 山五洲
 */
@Service
public class EhCacheService {
	private static final Logger logger = LoggerFactory.getLogger(EhCacheService.class);
	private static CacheManager cacheManager = null;
	private static Cache cache = null;
	static {
		initCacheManager();
	}

	/**
	 * 初始化缓存管理容器
	 */
	public static void initCacheManager() {
		try {
			if (cacheManager == null) {

				cacheManager = CacheManager.getInstance();
			}
		} catch (Exception e) {
			logger.error("******初始化CacheManager异常", e);
		}
	}

	/**
	 * 初始化cache
	 */
	public static Cache initCache(String cacheName) {
		if (null == cacheManager.getCache(cacheName)) {
			cacheManager.addCache(cacheName);
		}
		cache = cacheManager.getCache(cacheName);
		return cache;
	}

	/**
	 * 向缓存中存值 输入key,value
	 */
	public static void put(Cache caceh, Object key, Object value) {
		Element e = new Element(key, value);
		caceh.put(e);
		logger.info("数据存储成功");
	}

	/**
	 * 停止
	 */
	public static void shutdown() {
		EhCacheCacheManager cacheCacheManager = SpringContext.getApplicationContext()
				.getBean(EhCacheCacheManager.class);
		CacheManager cacheManager = cacheCacheManager.getCacheManager();
		cacheManager.shutdown();
	}
	
	/**
	 * 获取Cache
	 * */
	public static Cache getCache(String cacheName) {
		// 获取EhCacheCacheManager类
		EhCacheCacheManager cacheCacheManager = SpringContext.getApplicationContext()
				.getBean(EhCacheCacheManager.class);
		// 获取CacheManager类
		CacheManager cacheManager = cacheCacheManager.getCacheManager();
		// 获取Cache
		Cache cache = cacheManager.getCache(cacheName);
		return cache;
	}

	/**
	 * 查询缓存中当前空间下的数据
	 */
	public static List<Element> getData(String cacheName) {
		List<Element> list = new ArrayList<Element>();
		// 获取EhCacheCacheManager类
		EhCacheCacheManager cacheCacheManager = SpringContext.getBean(EhCacheCacheManager.class);
		// 获取CacheManager类
		CacheManager cacheManager = cacheCacheManager.getCacheManager();
		// 获取Cache
		Cache cache = cacheManager.getCache(cacheName);
		List<String> keys = (List<String>)cache.getKeys();
		for (String key : keys) {
			Element element = cache.get(key);
			list.add(element);
		}
		return list;
	}

	/**
	 * 按照Key值查询某条数据
	 */
	public static Element getOneData(String cacheName, String key) {
		Cache cache = getCache(cacheName);
		Element element = cache.get(key);
		return element;
	}
}