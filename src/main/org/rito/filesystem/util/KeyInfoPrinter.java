package org.rito.filesystem.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class KeyInfoPrinter implements ApplicationRunner, DisposableBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(KeyInfoPrinter.class);

	@Value("${spring.profiles.active}")
	private String profile;

	@Override
	public void destroy() throws Exception {
		LOGGER.info("springContext销毁");
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		LOGGER.info("运行目录：{}", System.getProperty("java.io.tmpdir"));
		LOGGER.info("配置文件：{}", profile);
	}

}
