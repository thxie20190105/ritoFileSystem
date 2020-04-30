package org.rito.filesystem;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author 山五洲
 */
@Component
public class QuartzInit implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzInit.class);

    private final
    JobService jobService;

    public QuartzInit(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    public void run(ApplicationArguments args) throws InterruptedException, SchedulerException, ClassNotFoundException {
        LOGGER.info("开始调度定时任务");
        jobService.startAll();
        LOGGER.info("完成调度定时任务");
    }

}
