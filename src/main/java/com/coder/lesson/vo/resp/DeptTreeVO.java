package com.coder.lesson.vo.resp;

import com.coder.lesson.entity.Dept;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @类名 DeptTreeVO
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/20 20:00
 * @版本 1.0
 **/
@Data
public class DeptTreeVO {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "跳转地址")
    private String url;

    @ApiModelProperty(value = "菜单权限名称")
    private String title;

    @ApiModelProperty(value = "子节点")
    private List<?> children;

    @ApiModelProperty(value = "默认展开")
    private boolean spread = true;

}
