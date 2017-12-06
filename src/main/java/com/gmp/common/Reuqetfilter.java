package com.gmp.common;


import com.gmp.common.annotation.CurrentUser;
import com.gmp.user.entity.User;
import com.gmp.user.entity.UserOpInfo;
import com.gmp.user.service.UserOpInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Reuqetfilter implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(Reuqetfilter.class);
    @Autowired
    private UserOpInfoService userOpInfoService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession httpSession = httpServletRequest.getSession();
        StringBuffer requestUrl = httpServletRequest.getRequestURL();
        Object user = httpSession.getAttribute("USER");
        if(user==null){
            logger.info(requestUrl.toString()+"请求被拦截[用户未登录]");
            httpServletResponse.sendRedirect("/login.jsp");
            return false;
        }else{
            logger.info(requestUrl.toString()+"请求成功！");
            this.insertUserOpInfo((User) user,requestUrl.toString());
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    //添加当前用户成功的操作信息到数据库
    public  void insertUserOpInfo( User user, String action){
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UserOpInfo userOpInfo = new UserOpInfo();
        userOpInfo.setAction(action);
        userOpInfo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        userOpInfo.setOptime(df.format(new Date()));
        userOpInfo.setUserid(user.getUserid());
        userOpInfo.setUsername(user.getUsername());
        userOpInfoService.insertuserOpInfo(userOpInfo);
    }
}
