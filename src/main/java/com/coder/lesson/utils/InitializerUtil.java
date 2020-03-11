package com.coder.lesson.utils;

import org.springframework.stereotype.Component;

/**
 * @类名 InitializerUtil
 * @描述 jwt初始化工具类
 * @创建人 张全蛋
 * @创建日期 2020/2/19 13:32
 * @版本 1.0
 **/
@Component
public class InitializerUtil {
    private TokenSettings tokenSettings;
    public InitializerUtil(TokenSettings tokenSettings){
        JwtTokenUtil.setTokenSettings(tokenSettings);
    }
}
