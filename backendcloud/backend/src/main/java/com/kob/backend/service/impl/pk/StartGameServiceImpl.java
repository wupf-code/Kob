package com.kob.backend.service.impl.pk;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.service.pk.StartGameService;
import org.springframework.stereotype.Service;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/17 - 14:47
 * @projectName:backendcloud
 */
@Service
public class StartGameServiceImpl implements StartGameService {
    @Override
    public String startGame(Integer aId, Integer bId) {
        System.out.println("开始游戏 " + aId + " " + bId );
        WebSocketServer.startGame(aId,bId);
        return "start game success";
    }
}
