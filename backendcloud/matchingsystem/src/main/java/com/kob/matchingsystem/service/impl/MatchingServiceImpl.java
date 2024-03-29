package com.kob.matchingsystem.service.impl;

import com.kob.matchingsystem.service.MatchingService;
import com.kob.matchingsystem.service.impl.utils.MatchingPool;
import org.springframework.stereotype.Service;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/17 - 11:19
 * @projectName:backendcloud
 */
@Service
public class MatchingServiceImpl implements MatchingService {

    public final static MatchingPool matchingPool = new MatchingPool();
    @Override
    public String addPlayer(Integer userId, Integer rating, Integer botId) {
//        System.out.println("add player " + userId + " " + rating);
        matchingPool.addPlayer(userId, rating,botId);
        return "add success";
    }

    @Override
    public String removePlayer(Integer userId) {
//        System.out.println("remove player " + userId + " ");
        matchingPool.removePlayer(userId);
        return "remove success";
    }
}
