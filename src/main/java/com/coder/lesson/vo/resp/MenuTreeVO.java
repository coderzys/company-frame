package com.coder.lesson.vo.resp;

import com.coder.lesson.entity.Permission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @类名 MenuTreeVO
 * @描述 首页菜单数据模型
 * @创建人 张全蛋
 * @创建日期 2020/2/19 20:11
 * @版本 1.0
 **/
@Data
@NoArgsConstructor
public class MenuTreeVO {

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

    @ApiModelProperty(value = "节点是否选中")
    private boolean checked;

    public MenuTreeVO(Permission permission) {
        this.id = permission.getId();
        this.url = permission.getUrl();
        this.title = permission.getName();
    }

}
