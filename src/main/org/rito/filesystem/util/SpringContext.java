package org.rito.filesystem.util;

import java.io.File;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class SpringContext implements ApplicationContextAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringContext.class);

	private static ApplicationContext applicationContext;

	public SpringContext() {
		LOGGER.info("无需实现");
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		applicationContext = arg0;

	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setAppCtx(ApplicationContext webAppCtx) {
		if (webAppCtx != null) {
			applicationContext = webAppCtx;
		}
	}

	public static String getRealPath() {

		// 获取根目录
		File path = null;
		try {
			path = new File(ResourceUtils.getURL("classpath:").getPath());
		} catch (FileNotFoundException e) {
			LOGGER.info("获取根目录失败", e);
		}

		String pathStr = path.getAbsolutePath();
		return pathStr;
	}

	/**
	 * 拿到ApplicationContext对象实例后就可以手动获取Bean的注入实例对象
	 */
	public static <T> T getBean(Class<T> clazz) {

		return getApplicationContext().getBean(clazz);
	}

	public static <T> T getBean(String name, Class<T> clazz) throws ClassNotFoundException {
		return getApplicationContext().getBean(name, clazz);
	}

	public static final Object getBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}

	public static final Object getBean(String beanName, String className) throws ClassNotFoundException {
		Class clz = Class.forName(className);
		return getApplicationContext().getBean(beanName, clz.getClass());
	}

	public static boolean containsBean(String name) {
		return getApplicationContext().containsBean(name);
	}

	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return getApplicationContext().isSingleton(name);
	}

	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return getApplicationContext().getType(name);
	}

	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return getApplicationContext().getAliases(name);
	}

	public static String[] getBeanNamesForType(Class type) {
		return getApplicationContext().getBeanNamesForType(type);
	}

}
