package com.coder.lesson.vo.req;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @类名 RoleAddReqVO
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/20 17:38
 * @版本 1.0
 **/
@Data
public class RoleAddReqVO {

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "状态(1:正常0:弃用)")
    private Integer status;

    @ApiModelProperty(value = "拥有的权限id集合")
    private List<String> permissionIds;
}
