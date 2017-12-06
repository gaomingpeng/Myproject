package com.gmp.user.controller;

import com.gmp.user.service.UserOpInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userop")
public class UserOpInfoController {
    @Autowired
    private UserOpInfoService userOpInfoService;


    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/getPagequery.do")
    @ResponseBody
    public Map<String,Object> getPageQueryData(int page,int limit ){
        Map<String, Object> result = new HashMap<String, Object>();
        int begin = 0;
        begin = (page-1)*limit;
        List<Map<String, Object>> queryLists = userOpInfoService.getQueryLists(begin, limit);
        long count = userOpInfoService.getCount();
        result.put("count",count);
        result.put("data",queryLists);
        result.put("code","");
        result.put("msg","");
        return result;
    }

    @RequestMapping("/togetLoginCountpage.do")

    public  String togetEveryUserLoginCountPage(){
        return "yw/behavioranalysis";
    }
    @RequestMapping("/getLoginCount.do")
    @ResponseBody
    public  List<Map<String, Object>> getEveryUserLoginCount(){
        return userOpInfoService.getEveryUserLoginCount();
    }
}
