package com.coder.lesson.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @类名 UserOwnRolesReqVO
 * @描述 用户拥有角色请求数据模型
 * @创建人 张全蛋
 * @创建日期 2020/2/21 13:26
 * @版本 1.0
 **/
@Data
public class UserOwnRolesReqVO {

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户id不能为空")
    private String userId;

    @ApiModelProperty("赋予用户的角色id集合")
    private List<String> roleIds;

}
