package com.kob.botrunningsystem.service;

import org.springframework.stereotype.Service;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/19 - 11:18
 * @projectName:backendcloud
 */
public interface BottRunningService {
    String addBot(Integer userId, String  botCode, String input);
}
