package org.rito.filesystem.interceptor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xigua
 */
@RestController
public class TestController {

    @RequestMapping(value = "/a")
    @ResponseBody
    public String SayHallo() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("请求来了");
        return "Hallo ";
    }
}
