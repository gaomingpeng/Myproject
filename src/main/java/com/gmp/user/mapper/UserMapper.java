package com.gmp.user.mapper;

import com.gmp.user.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User checklogin(String userid);

    int checkRegister (String userid);
}