package com.coder.lesson.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.time.Duration;

/**
 * @类名 TokenSettings
 * @描述 jwt配置文件读取类
 * @创建人 张全蛋
 * @创建日期 2020/2/19 13:27
 * @版本 1.0
 **/
@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class TokenSettings {

    private String secretKey;
    private Duration accessTokenExpireTime;
    private Duration refreshTokenExpireTime;
    private Duration refreshTokenExpireAppTime;
    private String issuer;

}
