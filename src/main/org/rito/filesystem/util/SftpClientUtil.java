package org.rito.filesystem.util;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author xigua
 * @date 2020/4/29
 **/
@Component
public class SftpClientUtil implements InitializingBean {
    private static String cfgPath = "src/main/resources/config/sftp.properties";
    private static Map<String, String> map = new HashMap<>();

    public static Map<String, String> getMap() {
        return map;
    }

    /**
     * 创建一个新的sftp连接
     *
     * @return
     */
    public static ChannelSftp getSftp() {
        return init();
    }

    private static ChannelSftp init() {

        String userName = map.get("userName");
        String hostName = map.get("hostName");
        String port = map.get("port");
        String password = map.get("password");
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

    private static void initCfgMap() {
        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(cfgPath);
            properties.load(inputStream);
            //需要什么直接从set里面取
            Set<String> strings = properties.stringPropertyNames();
            for (String value : strings) {
                map.put(value, properties.getProperty(value));
            }
        } catch (IOException e) {
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

    @Override
    public void afterPropertiesSet() {
        initCfgMap();
    }
}
