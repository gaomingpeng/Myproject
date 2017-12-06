package com.gmp.user.mapper;

import com.gmp.user.entity.UserPermisson;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserPermissonMapper {
    int insert(UserPermisson record);

    int insertSelective(UserPermisson record);

    List<Map<String,Object>> getPermissons(String userid);

    List<Map<String,Object>> getMenus(String roleid);


}