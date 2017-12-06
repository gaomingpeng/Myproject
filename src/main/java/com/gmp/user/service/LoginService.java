package com.gmp.user.service;

import com.gmp.user.entity.User;
import com.gmp.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserMapper userMapper;

    public User getUserInfo(String userid){
        return  userMapper.checklogin(userid);
    }

    public  int  insertUser(User user){
        return  userMapper.insert(user);
    }


    public  int checkRegister (String userid){
        return  userMapper.checkRegister(userid);
    }
}
