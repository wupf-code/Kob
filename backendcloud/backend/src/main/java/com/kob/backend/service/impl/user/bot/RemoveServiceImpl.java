package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.bot.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/7/30 - 11:27
 * @projectName:backend
 */
@Service
public class RemoveServiceImpl implements RemoveService {

    @Autowired
    private BotMapper botMapper;
    @Override
    public Map<String, String> remove(Map<String, String> data) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) usernamePasswordAuthenticationToken.getPrincipal();
        User user = loginUser.getUser();
        Map<String, String> map = new HashMap<>();
        Integer bot_id = Integer.parseInt(data.get("bot_id"));
        Bot bot = botMapper.selectById(bot_id);

        if(bot == null){
            map.put("eror_message","bot不存在");
            return map;
        }
        if (!bot.getUserId().equals(user.getId())) {
            map.put("error_message", "没有权限删除该Bot");
            return map;
        }

        botMapper.deleteById(bot_id);
        map.put("error_message","success");
        return map;
    }
}
