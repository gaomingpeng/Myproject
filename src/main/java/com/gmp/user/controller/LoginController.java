package com.gmp.user.controller;


//import com.gmp.common.activeMQ.producer.QueueSender;
import com.gmp.common.activeMQ.producer.QueueSender;
import com.gmp.common.activeMQ.producer.TopicSender;
import com.gmp.common.annotation.CurrentUser;
import com.gmp.common.resetAllMappers;
import com.gmp.user.entity.User;
import com.gmp.user.entity.UserOpInfo;
import com.gmp.user.service.LoginService;
import com.gmp.user.service.RoleService;
import com.gmp.user.service.UserOpInfoService;
import com.gmp.user.service.UserPermissonService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RequestMapping("/user")
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private resetAllMappers resetMappers;

    @Autowired
    private UserOpInfoService userOpInfoService;

    @Autowired
     private UserPermissonService userPermissonService;

    @Autowired
    private RoleService roleService;

  @Autowired
    private QueueSender queueSender;

  @Autowired
  private TopicSender topicSender;

    @Value("${queueName}")
   private  String queueName;

    @Value("${topicName}")
    private String topicName;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/checklogin.do")
    @ResponseBody
    public Map<String,Object> checkLogin(String userid, String password, HttpServletRequest request) throws IncorrectCredentialsException {
        Map<String,Object> map=new HashMap<String,Object>();

        /**
         * shrio
         */
         UsernamePasswordToken token = new UsernamePasswordToken(userid, password);
        token.setRememberMe(true);
        Subject  subject = SecurityUtils.getSubject();
        String msg = "";
        try {
            subject.login(token);
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
            e.printStackTrace();
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
            e.printStackTrace();
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
            e.printStackTrace();
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
            e.printStackTrace();
        } catch (UnknownAccountException e) {
            msg = "账号或密码不正确！";
            e.printStackTrace();

        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！" + e.getMessage();
            e.printStackTrace();
        }catch (IncorrectCredentialsException e){
            msg = "账号或密码不正确！";
            e.printStackTrace();
        }
        if (subject.isAuthenticated()) {
            User  user = this.loginService.getUserInfo(userid);
            map.put("code",200);
            map.put("username",user.getUsername());
            insertUserOpInfo(user,request.getRequestURI().toString());
            request.getSession().setAttribute("USER",user);
            request.getSession().setAttribute("roleName",roleService.getRolename(userid));
            queueSender.send(queueName,user.getUsername()+"上线了");

        } else {
            map.put("msg",msg);
            map.put("code",500);
        }

        return map;
    }
//跳转到主界面
    @RequestMapping("/main.do")
    public  String ForwardMain(){

        return "menu/main";
    }
    //退出登录
    @RequestMapping("/exit.do")
    public  void exitCurrentUser (HttpSession session, HttpServletResponse httpServletResponse, @CurrentUser User user){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            topicSender.send(topicName,user.getUsername()+"下线了");
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
        }
        logger.info("退出当前用户成功！");

        try {
            httpServletResponse.sendRedirect("/login.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/register.do")
    public  String toRegister (){

      return "register/register";
    }

    @RequestMapping("/getMenu.do")
    @ResponseBody
    public List<Map<String,Object>> getMenuByPermisson(HttpServletRequest request){
        HttpSession session  = request.getSession();
        User user = (User) session.getAttribute("USER");
        return userPermissonService.getMenus(user.getRole());
    }
    //注册用户
    @RequestMapping("/adduser.do")
    @ResponseBody
    public   Map<String,Object> addUser (User user, HttpServletRequest request) {
        Map<String,Object> map=new HashMap<String,Object>();
        //先去查询用户表中是否已注册相同的用户
         if(this.loginService.checkRegister(user.getUserid())>0){
             map.put("msg","此用户已注册！");
             map.put("code",500);
             return map;
         }
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        int i = this.loginService.insertUser(user);

        if (i > 0) {
            insertUserOpInfo(user,request.getRequestURI().toString());
            map.put("msg","注册成功！");
            map.put("code",200);
        } else {
            map.put("msg","注册失败！");
            map.put("code",500);
        }
        return map;
    }
    //刷新mapper.xml缓存
    @RequestMapping("/resetmapper")
    @ResponseBody
    public List<String> resetMapper (){
        this.resetMappers.ScanMappers();
        this.resetMappers.ClearCache();
        return  this.resetMappers.reLoadMappers();

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
