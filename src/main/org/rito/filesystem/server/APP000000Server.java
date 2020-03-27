package org.rito.filesystem.server;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 山五洲
 */
public class APP000000Server extends AbstractServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(APP000000Server.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("准备下载");
        //创建ftp连接
        //增量下载数据，或者根据数据库的流水去下文件。
        LOGGER.info("下载完成");


    }

}
