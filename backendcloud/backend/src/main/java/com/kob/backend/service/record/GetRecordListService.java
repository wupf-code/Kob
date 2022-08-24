package com.kob.backend.service.record;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/24 - 12:52
 * @projectName:backendcloud
 */

import com.alibaba.fastjson.JSONObject;

public interface GetRecordListService {
    JSONObject getList(Integer page);
}

