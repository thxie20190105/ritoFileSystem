package org.rito.filesystem.cache;

import org.rito.filesystem.cache.service.EhCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CacheInit implements ApplicationRunner, DisposableBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheInit.class);

	@Override
	public void run(ApplicationArguments args) {
	}

	@Override
	public void destroy() {
		LOGGER.info("Cache开始");
		EhCacheService.shutdown();
		LOGGER.info("Cache完成");
	}

}
