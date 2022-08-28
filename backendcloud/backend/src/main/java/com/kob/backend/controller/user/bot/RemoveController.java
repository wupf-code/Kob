package com.kob.backend.controller.user.bot;

import com.kob.backend.service.user.bot.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/7/30 - 11:39
 * @projectName:backend
 */
@RestController
public class RemoveController {

    @Autowired
    private RemoveService removeService;
    @PostMapping("/api/user/bot/remove/")
    public Map<String,String> remove(@RequestParam Map<String,String>data){
        return removeService.remove(data);
    }
}
