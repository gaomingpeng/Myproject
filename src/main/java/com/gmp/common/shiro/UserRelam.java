package com.gmp.common.shiro;

import com.gmp.user.entity.User;
import com.gmp.user.service.LoginService;
import com.gmp.user.service.RoleService;
import com.gmp.user.service.UserPermissonService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class UserRelam extends AuthorizingRealm{
    @Autowired
    private LoginService loginService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserPermissonService userPermissonService;


    /**
     * 授权操作
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userid = (String) principalCollection.getPrimaryPrincipal();
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        if (userid!=null&&!"".equals(userid)) {
            SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();

            authenticationInfo.setRoles(roleService.getRolename(userid));

            authenticationInfo.setStringPermissions(userPermissonService.getPermissons(userid));
            session.setAttribute("Permissons",userPermissonService.getPermissons(userid));
            session.setAttribute("roleName",roleService.getRolename(userid));
            return authenticationInfo;
        }

        return null;
    }

    /**
     * 身份验证操作
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String userid = usernamePasswordToken.getUsername()+"";//获取登陆的UserID

        User user = loginService.getUserInfo(userid);//根据用户ID查询用户信息
        AuthenticationInfo authenticationInfo = null;
        if (null != user) {
            String password = new String(usernamePasswordToken.getPassword());
            if (password.equals(user.getPassword())) {//验证密码是否相等
                authenticationInfo = new SimpleAuthenticationInfo(user.getUserid(), user.getPassword(), getName());
            }

        }
        return authenticationInfo;
    }
}
