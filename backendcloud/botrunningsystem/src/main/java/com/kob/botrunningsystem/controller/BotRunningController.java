package com.kob.botrunningsystem.controller;

import com.kob.botrunningsystem.service.BottRunningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/19 - 11:26
 * @projectName:backendcloud
 */
@RestController
public class BotRunningController {
    @Autowired
    private BottRunningService bottRunningService;

    @PostMapping("/bot/add/")
    public String addBot(@RequestParam MultiValueMap<String, String> data) {
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        String  botCode = data.getFirst("bot_code");
       String input = data.getFirst("input");
        return bottRunningService.addBot(userId, botCode, input);

    }
}
