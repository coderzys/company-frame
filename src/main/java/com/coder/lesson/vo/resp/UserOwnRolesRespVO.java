package com.coder.lesson.vo.resp;

import com.coder.lesson.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @类名 UserOwnRolesRespVO
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/21 12:50
 * @版本 1.0
 **/
@Data
public class UserOwnRolesRespVO {

    @ApiModelProperty(value = "拥有角色集合")
    private List<String> ownRoles;

    @ApiModelProperty(value = "所有角色列表")
    private List<Role> allRoles;
}
