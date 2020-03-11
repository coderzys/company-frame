package com.coder.lesson.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @类名 RoleUpdateReqVO
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/21 21:55
 * @版本 1.0
 **/
@Data
public class RoleUpdateReqVO {
    @ApiModelProperty(value = "角色id")
    @NotBlank(message = "角色 id 不能为空")
    private String id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "状态(1:正常0:弃用)")
    private Integer status;
    @ApiModelProperty(value = "所拥有的菜单权限")
    private List<String> permissionIds;
}
