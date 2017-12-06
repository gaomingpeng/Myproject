package com.gmp.user.service;

import com.gmp.user.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public Set<String> getRolename(String userid){
        List<Map<String,Object>> rsList =  this.roleMapper.getRoleName(userid);
        Set<String> set =  new HashSet<String>();
        if (rsList.size()>0){

            for (Map<String,Object> map :rsList){
                set.add(map.get("rolename")+"");
            }
        }
        return set;
    }
}
