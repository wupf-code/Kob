package com.kob.backend.service.user.account;

import java.util.Map;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/7/27 - 11:22
 * @projectName:backend
 */
public interface RegisterService {
    Map<String,String> register(String username, String password, String confirmedPassword);

}
