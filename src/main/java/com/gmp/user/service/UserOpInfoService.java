package com.gmp.user.service;

import com.gmp.user.entity.UserOpInfo;
import com.gmp.user.mapper.UserOpInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Service
public class UserOpInfoService {
    @Autowired
    private UserOpInfoMapper userOpInfoMapper;

    public int insertuserOpInfo(UserOpInfo userOpInfo){
        return  userOpInfoMapper.insert(userOpInfo);
    }


    public long getCount (){
        return userOpInfoMapper.getCount();
    }

    public  List<Map<String,Object>> getQueryLists(int begin, int limit){
        return userOpInfoMapper.selectByPageQuery(begin,limit);
    }

    public List<Map<String,Object>> getEveryUserLoginCount(){
        return userOpInfoMapper.getEveryUserLoginCount();
    }
}
