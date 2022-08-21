package com.kob.backend.service.pk;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/17 - 14:45
 * @projectName:backendcloud
 */
public interface StartGameService {

    String startGame(Integer aId, Integer aBotId, Integer bId, Integer bBotId);
}
