package com.coder.lesson.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @类名 UserUpdatePwdReqVO
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/22 19:24
 * @版本 1.0
 **/
@Data
public class UserUpdatePwdReqVO {
    @ApiModelProperty(value = "旧密码")
    @NotBlank(message = "旧密码不能为空")
    private String oldPwd;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPwd;
}
