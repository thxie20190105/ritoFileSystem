package org.rito.filesystem.server;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.rito.filesystem.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;


/**
 * @author 山五洲
 */
@Service
public class JobService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobService.class);

    static HashMap<String, String> hashMap;

    /**
     * 启动所有任务
     */
    void startAll() throws SchedulerException {
        start();
    }

    /**
     * 启动一个定时任务
     */
    private void start() throws SchedulerException {
        try {
            inti();
            if (AppConstant.ZERO.equals(JobService.hashMap.get("systemType"))) {
                execute(new APP000000Server());
            } else if (AppConstant.ONE.equals(JobService.hashMap.get("systemType"))) {
                execute(new APP000001Server());
            } else {
                LOGGER.info("需要配置");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void execute(AbstractServer o) throws SchedulerException {
        //调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //具体工作内容
        JobDetail jobDetail = JobBuilder.newJob(o.getClass()).withIdentity("job1", "group1").build();
        //设置触发器，默认30秒
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(JobService.hashMap.get("ftpScanInterval") == null
                        ? AppConstant.FTP_SCAN_INTERVAL : Integer.parseInt(JobService.hashMap.get("ftpScanInterval")))
                .repeatForever();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .startNow().withSchedule(simpleScheduleBuilder).build();
        //加入调度器管理
        scheduler.scheduleJob(jobDetail, trigger);
        //开始调度
        scheduler.start();
    }

    private void inti() throws Exception {
        JobService.hashMap = new HashMap<>(16);
        Properties config = new Properties();
        InputStream inStream;
        String confFileName = "src/main/resources/config.properties";
        inStream = new FileInputStream(confFileName);
        config.load(inStream);
        Set set = config.keySet();
        for (Object o : set) {
            String keyName = (String) o;
            JobService.hashMap.put(keyName, config.getProperty(keyName));
        }
        inStream.close();
    }
}
