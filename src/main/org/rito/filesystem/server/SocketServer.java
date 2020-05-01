package org.rito.filesystem.server;

import org.rito.filesystem.thread.ThreadPool;
import org.rito.filesystem.thread.UploadFileToSftp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xigua
 * @description
 * @date 2020/5/1
 **/
public class SocketServer {

    public void startSocketServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println(serverSocket.getInetAddress());
        Socket socket;
        while (true) {
            socket = serverSocket.accept();

            //来一个就把一个线程扔到线程池里

            ThreadPool.addUploadFileThread(new UploadFileToSftp(socket));
        }
    }
}