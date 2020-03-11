package com.coder.lesson.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @类名 LoginRespVO
 * @描述 登录响应数据模型
 * @创建人 张全蛋
 * @创建日期 2020/2/19 13:58
 * @版本 1.0
 **/
@Data
public class LoginRespVO {
    @ApiModelProperty(value = "正常的业务token")
    private String accessToken;
    @ApiModelProperty(value = "刷新token")
    private String refreshToken;
    @ApiModelProperty(value = "用户id")
    private String id;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "账号")
    private String username;
}
