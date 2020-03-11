package com.coder.lesson.shiro;

import com.coder.lesson.constants.Constant;
import com.coder.lesson.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;

/**
 * @类名 CustomRealm
 * @描述 自定义realm
 * @创建人 张全蛋
 * @创建日期 2020/2/19 16:48
 * @版本 1.0
 **/
public class CustomRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomUsernamePasswordToken;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String accessToken = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 载荷
        Claims claims = JwtTokenUtil.getClaimsFromToken(accessToken);
        /**
         * 返回该用户的角色信息给授权器
         */
        if (claims.get(Constant.JWT_ROLES_KEY) != null) {
            info.addRoles((Collection<String>) claims.get(Constant.JWT_ROLES_KEY));
        }

        /**
         * 返回该用户的权限信息给授权器
         */
        if (claims.get(Constant.JWT_PERMISSIONS_KEY) != null) {
            info.addStringPermissions((Collection<String>) claims.get(Constant.JWT_PERMISSIONS_KEY));
        }

        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CustomUsernamePasswordToken customUsernamePasswordToken = (CustomUsernamePasswordToken) authenticationToken;
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(customUsernamePasswordToken.getPrincipal(), customUsernamePasswordToken.getCredentials(), CustomRealm.class.getName());
        return info;
    }
}
