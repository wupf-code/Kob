package com.kob.matchingsystem.service.impl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/17 - 12:41
 * @projectName:backendcloud
 */
@Component
public class MatchingPool extends Thread{
    private static List<Player> players = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();
    private final static String startGameUrl = "http://127.0.0.1:3000/pk/start/game/";
    private static RestTemplate restTemplate;
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        MatchingPool.restTemplate = restTemplate;
    }
    public void addPlayer(Integer userId,Integer rating,Integer botId) {
        lock.lock();
        try{
            players.add(new Player(userId, rating, 0,botId));
        } finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId) {
        lock.lock();
        try {
            List<Player> newPlayers = new ArrayList<>();
            for(Player player : players) {
                if(!player.getUserId().equals(userId)) {
                    newPlayers.add(player);
                }
            }
            players = newPlayers;
        } finally {
            lock.unlock();
        }
    }
    private void increaseWaitingTime() {
        for(Player player : players) {
            player.setWaitingTime(player.getWaitingTime()+1);
        }
    }

    private boolean checkMatched(Player a, Player b) {
        int ratingDelta = Math.abs(a.getRating() - b.getRating());
        int waitingTime = Math.min(a.getWaitingTime(), b.getWaitingTime());
        return ratingDelta <= waitingTime * 10;
    }
    private void matchPlayers() {
//        System.out.println("match players " + players.toString());
        boolean[] used = new boolean[players.size()];
        for(int i = 0; i < players.size(); i++) {
            if(used[i]) continue;
            for(int j = i+1; j < players.size(); j++ ){
                if(used[j])continue;;
                Player a = players.get(i);
                Player b = players.get(j);
                if(checkMatched(a, b)) {
                    used[i] = used[j] = true;
                    sendResult(a, b);
                    break;
                }
            }
        }
        List<Player> newPlayers = new ArrayList<>();
        for(int i = 0; i < players.size(); i++) {
            if(!used[i]) {
                newPlayers.add(players.get(i));
            }
        }
        players = newPlayers;
    }

    public  void sendResult(Player a, Player b) {
        System.out.printf("send result " + a + " " + b);
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("a_id", a.getUserId().toString());
        data.add("a_bot_id", a.getBotID().toString());
        data.add("b_id", b.getUserId().toString());
        data.add("b_bot_id", b.getBotID().toString());
        restTemplate.postForObject(startGameUrl,data,String.class);
    }
    public void run(){
        while(true) {
            try {
                Thread.sleep(1000);
                lock.lock();
                try {
                    increaseWaitingTime();
                    matchPlayers();
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
