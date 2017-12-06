package com.gmp.user.service;

import com.gmp.user.mapper.UserPermissonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserPermissonService {
    @Autowired
    private UserPermissonMapper userPermissonMapper;

    public Set<String> getPermissons(String userid){
        Set<String> mapSet = new HashSet<String>();
        List<Map<String,Object>> rsList =  this.userPermissonMapper.getPermissons(userid);
        for (Map<String,Object> map :rsList){
            mapSet.add(map.get("roleid")+"");
        }
        return mapSet;
    }
    public List<Map<String,Object>> getMenus(String roleid){
        return this.userPermissonMapper.getMenus(roleid);
    }
}
