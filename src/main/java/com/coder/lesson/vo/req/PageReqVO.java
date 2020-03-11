package com.coder.lesson.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @类名 PageReqVO
 * @描述 通用分页请求vo
 * @创建人 张全蛋
 * @创建日期 2020/2/19 15:15
 * @版本 1.0
 **/
@Data
public class PageReqVO {

    @ApiModelProperty(value = "当前第几页")
    private Integer pageNum=1;

    @ApiModelProperty(value = "当前页数据条数")
    private Integer pageSize=10;
}
