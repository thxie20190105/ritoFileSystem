package org.rito.filesystem.interceptor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xigua
 */
@RestController
public class TestController {

    @RequestMapping(value = "/a", method = RequestMethod.GET)
    @ResponseBody
    public String a() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Integer.parseInt("");
        return "Hallo";
    }


}
