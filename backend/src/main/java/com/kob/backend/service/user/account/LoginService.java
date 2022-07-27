package com.kob.backend.service.user.account;

import java.util.Map;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/7/27 - 11:21
 * @projectName:backend
 */
public interface LoginService {
    Map<String,String> login(String username, String password);
}
