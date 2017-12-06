package com.gmp.user.mapper;

import com.gmp.user.entity.UserOpInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface UserOpInfoMapper {
    int insert(UserOpInfo record);

    int insertSelective(UserOpInfo record);

    long getCount ();

    List<Map<String,Object>> selectByPageQuery(int begin, int limit);

    List<Map<String,Object>> getEveryUserLoginCount();
}