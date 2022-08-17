package com.kob.matchingsystem.service.impl.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/17 - 12:41
 * @projectName:backendcloud
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    Integer userId;
    Integer rating;
    Integer waitingTime; //等待时间
}
