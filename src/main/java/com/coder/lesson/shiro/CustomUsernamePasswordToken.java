package com.coder.lesson.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @类名 CustomUsernamePasswordToken
 * @描述 自定义token
 * @创建人 张全蛋
 * @创建日期 2020/2/19 15:56
 * @版本 1.0
 **/
public class CustomUsernamePasswordToken extends UsernamePasswordToken {

    private String token;

    public CustomUsernamePasswordToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
}
