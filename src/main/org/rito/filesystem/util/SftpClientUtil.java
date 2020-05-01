package org.rito.filesystem.util;

import com.jcraft.jsch.*;
import org.rito.filesystem.JobService;
import org.rito.filesystem.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * @author xigua
 * @date 2020/4/29
 **/
public class SftpClientUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SftpClientUtil.class);
    /**
     * 创建一个新的sftp连接
     *
     * @return
     */
    public static ChannelSftp getSftp() {
        return init();
    }

    private static ChannelSftp init() {

        String userName = JobService.getHashMap().get(Constant.userName);
        String hostName = JobService.getHashMap().get(Constant.hostName);
        String port = JobService.getHashMap().get(Constant.port);
        String password = JobService.getHashMap().get(Constant.password);
        ChannelSftp sftp = null;
        long startTime = System.currentTimeMillis();

        try {

            JSch jSch = new JSch();
            Session session = jSch.getSession(userName, hostName, Integer.parseInt(port));
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        LOGGER.info("创建连接时间" + (endTime - startTime) / 1000 + "秒");
        return sftp;

    }


    /**
     * 下载文件
     *
     * @param sftp     新的sftp连接
     * @param fileName 文件名称
     * @param filePath 文件所在路径
     * @return
     */
    public static InputStream downloadFile(ChannelSftp sftp, String fileName, String filePath) {

        InputStream inputStream;

        try {

            sftp.cd(filePath);
            inputStream = sftp.get(fileName);
            return inputStream;
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void close(ChannelSftp sftp) {
        sftp.disconnect();
    }


}
