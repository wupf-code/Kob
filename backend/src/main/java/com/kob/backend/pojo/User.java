package com.kob.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/7/21 - 9:30
 * @projectName:backend
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private  Integer id;
    private  String username;
    private  String password;

}
