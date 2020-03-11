package com.coder.lesson.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @类名 UserInfoVO
 * @描述 用户信息数据模型
 * @创建人 张全蛋
 * @创建日期 2020/2/19 20:07
 * @版本 1.0
 **/
@Data
public class UserInfoVO {

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "所属部门id")
    private String deptId;

    @ApiModelProperty(value = "所属部门名称")
    private String deptName;

}
