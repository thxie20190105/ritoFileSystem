package org.rito.filesystem.util;

import com.jcraft.jsch.*;
import org.rito.filesystem.JobService;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xigua
 * @date 2020/4/29
 **/
public class SftpClientUtil {
    private static String cfgPath = "src/main/resources/config/sftp.properties";

    /**
     * 创建一个新的sftp连接
     *
     * @return
     */
    public static ChannelSftp getSftp() {
        return init();
    }

    private static ChannelSftp init() {

        String userName = JobService.getHashMap().get("userName");
        String hostName = JobService.getHashMap().get("hostName");
        String port = JobService.getHashMap().get("port");
        String password = JobService.getHashMap().get("password");
        ChannelSftp sftp = null;
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

        InputStream inputStream = null;

        try {

            sftp.cd(filePath);
            inputStream = sftp.get(fileName);
            return inputStream;
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void close(ChannelSftp sftp) {
        sftp.disconnect();
    }


}
