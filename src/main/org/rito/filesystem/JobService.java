package org.rito.filesystem;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.rito.filesystem.constant.Constant;
import org.rito.filesystem.job.APP000000Server;
import org.rito.filesystem.job.AbstractServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;


/**
 * @author 山五洲
 */
@Service
public class JobService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobService.class);

    private static HashMap<String, String> hashMap;


    public static HashMap<String, String> getHashMap() {
        return hashMap;
    }

    /**
     * 启动所有任务
     */
    void startAll() {
        start();
    }

    /**
     * 启动一个定时任务
     */
    private void start() {
        try {
            inti();
            String[] strings = JobService.hashMap.get(Constant.systemType).split(",");
            for (int i = 0, j = strings.length; i < j; i++) {
                if (Constant.ZERO.equals(strings[i])) {

                    execute(new APP000000Server());
                } else {
                    LOGGER.info("需要配置");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void execute(AbstractServer o) throws SchedulerException {
        //调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();


        //具体工作内容
        JobDetail jobDetail = JobBuilder.newJob(o.getClass()).withIdentity(o.getClass().getName(),
                o.getClass().getName() + "group").build();


        //设置触发器，默认30秒
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(hashMap.get(Constant.ftpScanInterval) == null
                        ? Constant.FTP_SCAN_INTERVAL : Integer.parseInt(hashMap.get(Constant.ftpScanInterval)))
                .repeatForever();


        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(o.getClass().getName(), o.getClass().getName() + "group")
                .startNow().withSchedule(simpleScheduleBuilder).build();


        //加入调度器管理
        scheduler.scheduleJob(jobDetail, trigger);
        //开始调度
        scheduler.start();
    }

    private void inti() throws Exception {
        hashMap = new HashMap<>(16);
        Properties config = new Properties();
        InputStream inStream;
        inStream = new FileInputStream(Constant.cfgPath);
        config.load(inStream);
        Set set = config.keySet();
        for (Object o : set) {
            String keyName = (String) o;
            JobService.hashMap.put(keyName, config.getProperty(keyName));
        }
        inStream.close();
    }
}
