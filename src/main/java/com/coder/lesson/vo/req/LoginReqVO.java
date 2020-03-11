package com.coder.lesson.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @类名 LoginReqVO
 * @描述 登录请求数据模型
 * @创建人 张全蛋
 * @创建日期 2020/2/19 14:00
 * @版本 1.0
 **/
@Data
public class LoginReqVO {

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String username;

    @ApiModelProperty(value ="密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "登录类型 1：pc；2：App")
    @NotBlank(message = "用户登录类型不能为空")
    private String type;
}
