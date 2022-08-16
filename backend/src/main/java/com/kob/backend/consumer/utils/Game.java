package com.kob.backend.consumer.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.pojo.Record;
import sun.awt.windows.WBufferStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 武鹏飞
 */
public class Game extends Thread{
    final private Integer rows;
    final private Integer cols;
    final private Integer inner_walls_count;
    final private int[][] g;
    final private static int[] dx = {-1, 0, 1, 0};
    private final Player playerA, playerB;
    private Integer nextStepA = null;
    private Integer nextStepB = null;

    private ReentrantLock lock = new ReentrantLock();

    private String status = "playing"; //游戏结束为finished
    private String loser = ""; //all,平局， a 则玩家A没输入，B赢，为b则A赢
    public void setNextStepA(Integer nextStepA) {
        lock.lock();
        try {
            this.nextStepA = nextStepA;
        }finally {
            lock.unlock();
        }

    }
    public void setNextStepB(Integer nextStepB) {
        lock.lock();
        try {
            this.nextStepB = nextStepB;
        }finally {
            lock.unlock();
        }

    }
    private boolean nextStep() { //等待玩家两名玩家下一步操作
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(int i  = 0; i < 50; i++ ) {
            try {
                Thread.sleep(100);
                lock.lock();
                try {
                    if(nextStepA != null && nextStepB != null) {
                        playerA.getSteps().add(nextStepA);
                        playerB.getSteps().add(nextStepB);
                        return true;
                    }
                }finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    private void judge() {  // 判断两名玩家下一步操作是否合法
        List<Cell> cellsA = playerA.getCells();
        List<Cell> cellsB = playerB.getCells();

        boolean validA = check_valid(cellsA, cellsB);
        boolean validB = check_valid(cellsB, cellsA);
        if (!validA || !validB) {
            status = "finished";

            if (!validA && !validB) {
                loser = "all";
            } else if (!validA) {
                loser = "A";
            } else {
                loser = "B";
            }
        }
    }

    private boolean check_valid(List<Cell> cellsA, List<Cell> cellsB) {
        int n = cellsA.size();
        Cell cell = cellsA.get(n - 1);
        if (g[cell.x][cell.y] == 1) return false;

        for (int i = 0; i < n - 1; i ++ ) {
            if (cellsA.get(i).x == cell.x && cellsA.get(i).y == cell.y)
                return false;
        }

        for (int i = 0; i < n - 1; i ++ ) {
            if (cellsB.get(i).x == cell.x && cellsB.get(i).y == cell.y)
                return false;
        }

        return true;

    }

    private  void sendMove() {
        lock.lock();
        try {
            //向玩家传递移动信息
            JSONObject resp = new JSONObject();
            resp.put("event", "move");
            resp.put("a_direction", nextStepA);
            resp.put("b_direction", nextStepB);
            sendAllMessage(resp.toJSONString());
            nextStepA = nextStepB = null;
        } finally {
            lock.unlock();
        }

    }
    private void sendResult() { //向玩家公布结
        JSONObject resp = new JSONObject();
        resp.put("event", "result");
        resp.put("loser", loser);
        saveToDatabase();
        sendAllMessage(resp.toJSONString());
    }

    private String getMapString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < rows; i ++ ) {
            for (int j = 0; j < cols; j ++ ) {
                res.append(g[i][j]);
            }
        }
        return res.toString();
    }

    private void saveToDatabase() {
        Record record = new Record(
                null,
                playerA.getId(),
                playerA.getSx(),
                playerA.getSy(),
                playerB.getId(),
                playerB.getSx(),
                playerB.getSy(),
                playerA.getStepsString(),
                playerB.getStepsString(),
                getMapString(),
                loser,
                new Date()
        );

        WebSocketServer.recordMapper.insert(record);
    }


    private void sendAllMessage(String message) {//向玩家广播信息
        WebSocketServer.users.get(playerA.getId()).sendMessage(message);
        WebSocketServer.users.get(playerB.getId()).sendMessage(message);
        System.out.println("sendallmessage");
    }
    @Override
    public void run() {
        for(int i = 0; i < 1000; i++) {
            if(nextStep()) {
                judge();
                if("playing".equals(status)) {
                    sendMove();
                }else {
                    sendResult();
                    break;
                }
            }else {
                this.status = "finished";
                lock.lock();
                try {
                    if (nextStepA == null && nextStepB == null) {
                        loser = "all";
                    } else if (nextStepA == null) {
                        loser = "A";
                    } else {
                        loser = "B";
                    }
                }
                finally {
                    lock.unlock();
                }
                sendResult();
                break;
            }
        }
    }

    final private static int[] dy = {0, 1, 0, -1};

    public Game(Integer rows, Integer cols, Integer inner_walls_count, Integer idA, Integer idB) {
        this.rows = rows;
        this.cols = cols;
        this.inner_walls_count = inner_walls_count;
        this.g = new int[rows][cols];
        playerA = new Player(idA, rows-2, 1,new ArrayList<>());
        playerB = new Player(idB, 1, cols - 2, new ArrayList<>());

    }

    public Player getPlayerA() {
        return playerA;
    }
    public Player getPlayerB() {
        return playerB;
    }
    public int[][] getG() {
        return g;
    }

    private boolean check_connectivity(int sx, int sy, int tx, int ty) {
        if (sx == tx && sy == ty) {
            return true;
        }
        g[sx][sy] = 1;

        for (int i = 0; i < 4; i ++ ) {
            int x = sx + dx[i], y = sy + dy[i];
            if (x >= 0 && x < this.rows && y >= 0 && y < this.cols && g[x][y] == 0) {
                if (check_connectivity(x, y, tx, ty)) {
                    g[sx][sy] = 0;
                    return true;
                }
            }
        }

        g[sx][sy] = 0;
        return false;
    }

    private boolean draw() {  // 画地图
        for (int i = 0; i < this.rows; i ++ ) {
            for (int j = 0; j < this.cols; j ++ ) {
                g[i][j] = 0;
            }
        }

        for (int r = 0; r < this.rows; r ++ ) {
            g[r][0] = g[r][this.cols - 1] = 1;
        }
        for (int c = 0; c < this.cols; c ++ ) {
            g[0][c] = g[this.rows - 1][c] = 1;
        }

        Random random = new Random();
        for (int i = 0; i < this.inner_walls_count / 2; i ++ ) {
            for (int j = 0; j < 1000; j ++ ) {
                int r = random.nextInt(this.rows);
                int c = random.nextInt(this.cols);

                if (g[r][c] == 1 || g[this.rows - 1 - r][this.cols - 1 - c] == 1) {
                    continue;
                }
                if (r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2) {
                    continue;
                }

                g[r][c] = g[this.rows - 1 - r][this.cols - 1 - c] = 1;
                break;
            }
        }

        return check_connectivity(this.rows - 2, 1, 1, this.cols - 2);
    }

    public void createMap() {
        for (int i = 0; i < 1000; i ++ ) {
            if (draw()) {
                break;
            }
        }
    }
}
