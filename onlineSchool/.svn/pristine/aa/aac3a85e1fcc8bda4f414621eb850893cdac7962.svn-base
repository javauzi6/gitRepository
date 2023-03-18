package com.qicong.os.web.shiro;

import com.qicong.os.common.util.HashUtil;
import com.qicong.os.common.util.JsonView;
import com.qicong.os.domain.AuthUser;
import com.qicong.os.service.AuthUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * User: 祁大聪
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private AuthUserService authUserService;

    //用户的 角色:权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authza = new SimpleAuthorizationInfo();
        Set<String> permissions = new HashSet<String>();
        //当用登录之后，去查询此用户的角色和权限，然后放到这里
        //permissions.add("user:delete");

        authza.setStringPermissions(permissions);
        return authza;
    }

    //实现用户的登录功能
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String)token.getPrincipal();
        String password = new String((char[])token.getCredentials());

        //校验用户名和密码就可以了
        AuthUser authUser = authUserService.getByUsername(username);
        if(null == authUser){
            throw new UnknownAccountException();
        }

        //校验密码就可以了
        String authPassword = HashUtil.hmacSha256(password,authUser.getSalt());//盐加密
        if(authPassword.equals(authUser.getPassword())){
            SecurityUtils.getSubject().getSession().setAttribute(ShiroContext.SESSION_KEY, authUser);
        }else{
            throw new UnknownAccountException();
        }

        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
