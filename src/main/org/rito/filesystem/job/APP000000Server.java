package org.rito.filesystem.job;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xigua.study.quartz.thread.DownloadSftpFile;
import org.xigua.study.quartz.thread.ThreadPool;
import org.xigua.util.net.SftpClientUtil;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author 山五洲
 */
@Service
public class APP000000Server extends AbstractServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(APP000000Server.class);


    @Override
    public void execute(JobExecutionContext context) {
        LOGGER.info("准备下载");
        LOGGER.info("当前线程" + Thread.currentThread().getName());
        //创建ftp连接
        try {
            Vector vector = SftpClientUtil.getSftp().ls("/");
            Iterator iterator = vector.iterator();
            while (iterator.hasNext()) {
                ChannelSftp.LsEntry file = (ChannelSftp.LsEntry) iterator.next();
                String fileName = file.getFilename();

                if (fileName.endsWith("txt")) {
                    //加入线程池
                    ThreadPool.addThread(new DownloadSftpFile(fileName));
                }

            }


        } catch (SftpException e) {
            e.printStackTrace();
        }


    }

}
