package com.kob.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/7/8 - 10:18
 * @projectName:backend
 */
@RestController
@RequestMapping("/pk/")
public class BotInfoController {
    @RequestMapping("getbotinfo/")
    public  Map<String,String> getInfo() {
//        List<Map<String, String>> list = new LinkedList<>();
        Map<String,String>bot1=new HashMap<>();
        bot1.put("name","wpf");
        bot1.put("rating","8000");
//        Map<String,String>bot2=new HashMap<>();
//        bot2.put("name","zzz");
//        bot2.put("rating","1550");
//        list.add(bot1);
//        list.add(bot2);
        return bot1;
    }

}
