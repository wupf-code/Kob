package com.kob.backend.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 武鹏飞
 * @user:ASUS
 * @date:2022/7/21 - 9:09
 * @projectName:backend
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("all/")
    public List<User> getAll(){
       return userMapper.selectList(null);
    }
    @GetMapping("{id}/")
    public User getById(@PathVariable int id){
        //简单查询
       return userMapper.selectById(id);
        //条件查询
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//      queryWrapper.eq("id",id);
//        queryWrapper.gt("id",1).lt("id",3);
//        return userMapper.selectOne(queryWrapper);

    }

    @GetMapping("add/{id}/{username}/{password}")
    public String addUser (@PathVariable int id,@PathVariable String username,@PathVariable String password ){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(password);
        User user = new User(id,username,encodePassword);
        userMapper.insert(user);
        return "ADD SUCCESS";
    }
    @DeleteMapping("{id}/")
    public String deleteUser(@PathVariable int id){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        userMapper.delete(queryWrapper);
        return "DELETE SUCCESS";
    }

}
