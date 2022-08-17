package com.kob.backend.consumer.utils;

import com.kob.backend.utils.JwtUtil;
import io.jsonwebtoken.Claims;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/10 - 10:02
 * @projectName:backend
 */
public class JwtAuthentication {
    public static Integer getUserId(String token) {
        int userid = -1;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = Integer.parseInt(claims.getSubject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  userid;
    }

}
