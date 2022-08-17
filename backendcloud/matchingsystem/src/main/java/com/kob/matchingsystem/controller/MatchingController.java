package com.kob.matchingsystem.controller;

import com.kob.matchingsystem.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/17 - 11:22
 * @projectName:backendcloud
 */
@RestController
public class MatchingController {
    @Autowired
    private MatchingService matchingService;

    @PostMapping("/player/add/")
    public String addPlayer(@RequestParam MultiValueMap<String,String>data) { //MultiValueMap可以让一个key对应多个value
        Integer userId = Integer.parseInt(data.getFirst("user_id"));
        Integer rating = Integer.parseInt(data.getFirst("rating"));
        return matchingService.addPlayer(userId,rating);
    }

    @PostMapping("/player/remove/")
    public String removePlayer(@RequestParam MultiValueMap<String,String>data) {
        Integer userId = Integer.parseInt(data.getFirst("user_id"));
        return matchingService.removePlayer(userId);
    }
}
