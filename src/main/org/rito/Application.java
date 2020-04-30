package org.rito;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.*;

/**
 * @author 山五洲
 */
@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 配置一个线程池
     *
     * @return
     */
    @Bean
    public ExecutorService getThreadPool() {
        ThreadFactory factory = new ThreadFactoryBuilder()
                .setNameFormat("consumer-queue-thread-%d")
                .build();
        return new ThreadPoolExecutor(
                10,
                20,
                30,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(200),
                factory,
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

}
