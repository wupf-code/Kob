package com.kob.matchingsystem.service;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/17 - 11:18
 * @projectName:backendcloud
 */
public interface MatchingService {
    String addPlayer(Integer userId, Integer rating);
    String removePlayer(Integer userId);
}
