package com.kob.backend.service.ranklist;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/24 - 15:56
 * @projectName:backendcloud
 */
public interface GetRanklistService {
    JSONObject getList(Integer page);
}
