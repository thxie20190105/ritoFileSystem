package org.rito.filesystem.thread;

import com.jcraft.jsch.ChannelSftp;
import org.rito.filesystem.util.SftpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

/**
 * @author xigua
 * @description
 * @date 2020/4/30
 **/
public class UploadFileToSftp implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileToSftp.class);

    private String fileName;
    private String data;

    private Socket socket;


    public UploadFileToSftp(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {


        String filePath = System.currentTimeMillis() + fileName;
        LOGGER.info("当前线程" + Thread.currentThread().getName());
        LOGGER.info("上传本地文件路径" + filePath);

        long start = System.currentTimeMillis();
        ChannelSftp sftp = SftpClientUtil.getSftp();


        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //关闭连接
        SftpClientUtil.close(sftp);
        long endTime = System.currentTimeMillis();
        LOGGER.info("上传本地文件" + filePath + "花费时间" + (endTime - start) / 1000 + "秒");

    }
}
