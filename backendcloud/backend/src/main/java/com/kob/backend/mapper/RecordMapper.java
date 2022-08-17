package com.kob.backend.mapper;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/8/16 - 11:45
 * @projectName:backend
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kob.backend.pojo.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
}
