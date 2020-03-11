package com.coder.lesson.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @类名 LogPageReqVO
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/22 17:02
 * @版本 1.0
 **/
@Data
public class LogPageReqVO {

    @ApiModelProperty("当前页数")
    private Integer pageNum;

    @ApiModelProperty("当前页总数")
    private Integer pageSize;

    @ApiModelProperty(value = "用户操作动作")
    private String operation;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
