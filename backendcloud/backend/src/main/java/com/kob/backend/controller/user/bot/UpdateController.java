package com.kob.backend.controller.user.bot;

import com.kob.backend.service.user.bot.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/7/30 - 12:05
 * @projectName:backend
 */
@RestController
public class UpdateController {
    @Autowired
    private UpdateService updateService;

    @PostMapping("/api/user/bot/update/")
    public Map<String, String> update(@RequestParam Map<String, String>data){
        System.out.println(data.toString());
        return updateService.update(data);
    }
}
