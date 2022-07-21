package com.kob.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kob.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/7/21 - 9:33
 * @projectName:backend
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
