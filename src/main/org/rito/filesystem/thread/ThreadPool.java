package org.rito.filesystem.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xigua
 */
@Component
public class ThreadPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPool.class);

    private static ThreadPoolExecutor quartzThreadPoolExecutor;
    private static ThreadPoolExecutor uploadFileThreadPoolExecutor;


    /**
     * 核心线程17个、线程池最大30个
     * 线程池中的线程1个小时没有工作自动销毁
     * 任务队列200个，满了的话自动放弃任务。
     * 线程池初始化
     */
    private static void init() {

        quartzThreadPoolExecutor = new ThreadPoolExecutor(
                17,
                30,
                1,
                TimeUnit.HOURS,
                new ArrayBlockingQueue<>(200),
                new ThreadFactoryBuilder()
                        .setNameFormat("consumer-queue-thread-%d")
                        .build(),
                new ThreadPoolExecutor.DiscardPolicy()
        );

        //使用无界的LinkedBlockingQueue，
        uploadFileThreadPoolExecutor = new ThreadPoolExecutor(
                17,
                30,
                1,
                TimeUnit.HOURS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder()
                        .setNameFormat("upload-File-thread-%d")
                        .build(),
                new ThreadPoolExecutor.DiscardPolicy()
        );

    }

    /**
     * 添加下载任务
     *
     * @param runnable
     */
    public static void addQuartzThread(Runnable runnable) {


        if (quartzThreadPoolExecutor == null) {
            init();
        }

        LOGGER.info("当前线程" + Thread.currentThread().getName());
        quartzThreadPoolExecutor.execute(runnable);

    }

    /**
     * 添加上传任务
     *
     * @param runnable
     */
    public static void addUploadFileThread(Runnable runnable) {

        if (quartzThreadPoolExecutor == null) {
            init();
        }

        LOGGER.info("当前线程" + Thread.currentThread().getName());
        quartzThreadPoolExecutor.execute(runnable);

    }
}
