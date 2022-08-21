package com.kob.botrunningsystem;

import com.kob.botrunningsystem.service.impl.BotRunningServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: 武鹏飞
 * @user:${USER}
 * @date:${DATE} - ${TIME}
 * @projectName:${PROJECT_NAME}
 */
@SpringBootApplication
public class BotRunningSystem {
    public static void main(String[] args) {
        BotRunningServiceImpl.botPool.start();
        SpringApplication.run(BotRunningSystem.class, args);
    }
}