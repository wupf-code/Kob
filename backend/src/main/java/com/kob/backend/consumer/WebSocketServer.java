package com.kob.backend.consumer;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/10 - 9:03
 * @projectName:backend
 */
import com.alibaba.fastjson.JSONObject;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.consumer.utils.JwtAuthentication;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.InsufficientResourcesException;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    final private static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();
    //线程安全的set,用作匹配池
    final private static CopyOnWriteArraySet<User> matchPool = new CopyOnWriteArraySet<>();
    private User user;
    private Game game = null;
    private Session session = null;

    //注入usermapper
    private static UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        this.session = session;
        System.out.println("connected");
        int userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);

        if(user != null) {
            users.put(userId,this);

        }else {
            this.session.close();
        }
        System.out.println(users);
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.println("disconnected");
        if(this.user != null) {
            users.remove(this.user.getId());
            matchPool.remove(this.user);
        }
    }
    private void startMatching(){
        System.out.println("开始匹配");
        matchPool.add(this.user);

        while(matchPool.size() >= 2){
            Iterator<User> it = matchPool.iterator();
            User a = it.next();
            User b = it.next();
            matchPool.remove(a);
            matchPool.remove(b);
            Game game = new Game(13,14,20);
            game.createMap();
            JSONObject respA = new JSONObject();
            respA.put("event", "start-matching");
            respA.put("opponent_username", b.getUsername());
            respA.put("opponent_photo", b.getPhoto());
            respA.put("gamemap", game.getG());
            users.get(a.getId()).sendMessage(respA.toJSONString());

            JSONObject respB = new JSONObject();
            respB.put("event", "start-matching");
            respB.put("opponent_username", a.getUsername());
            respB.put("opponent_photo", a.getPhoto());
            respB.put("gamemap", game.getG());
            users.get(b.getId()).sendMessage(respB.toJSONString());
        }
    }
    private  void stopMatching(){
        System.out.println("结束匹配");
        matchPool.remove(this.user);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
        System.out.println("receive message");
        //解析前端发送的信息
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if("start-matching".equals(event)){
            startMatching();
        }else if("stop-matching".equals(event)){
            stopMatching();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message){
        synchronized (this.session) {
            try{
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

