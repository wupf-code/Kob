package com.kob.botrunningsystem.service.impl.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/19 - 14:03
 * @projectName:backendcloud
 */
public class BotPool extends Thread {
    private static ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final Queue<Bot> bots = new LinkedList<>();

    public void addBot(Integer userId, String botCode, String input){
        lock.lock();
        try {
            bots.add(new Bot(userId, botCode,input));
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
    private void consume(Bot bot) {
        Consumer consumer = new Consumer();
        consumer.startTimeout(2000,bot);
    }
    @Override
    public void run () {
        while(true) {
            lock.lock();
            if(bots.isEmpty()){
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }else {
                Bot bot = bots.remove();
                lock.unlock();
                consume(bot);
            }
        }
    }


}
