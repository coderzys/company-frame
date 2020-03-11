package com.coder.lesson.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @类名 DeptUpdateReqVO
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/22 14:11
 * @版本 1.0
 **/
@Data
public class DeptUpdateReqVO {
    @ApiModelProperty(value = "部门id")
    @NotBlank(message = "部门id不能为空")
    private String id;
    @ApiModelProperty(value = "部门呢名称")
    private String name;
    @ApiModelProperty(value = "父级id")
    private String pid;
    @ApiModelProperty(value = "部门状态(1:正常；0:弃用)")
    private Integer status;
    @ApiModelProperty(value = "部门经理名称")
    private String managerName;
    @ApiModelProperty(value = "部门经理电话")
    private String phone;
}
