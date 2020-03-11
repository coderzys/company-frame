package com.coder.lesson.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @类名 RolePageReqVO
 * @描述 角色分页请求vo
 * @创建人 张全蛋
 * @创建日期 2020/2/19 15:15
 * @版本 1.0
 **/
@Data
public class RolePageReqVO {

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "当前第几页")
    private Integer pageNum;

    @ApiModelProperty(value = "当前页数据条数")
    private Integer pageSize;
}
