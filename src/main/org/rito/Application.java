package org.rito;


import org.rito.filesystem.server.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


/**
 * @author 山五洲
 */
@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@RequestMapping("/fileSystem/")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        //监听8080端口
        SocketServer socketServer = new SocketServer();
        try {
            socketServer.startSocketServer(8081);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
