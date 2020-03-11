package com.coder.lesson.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @类名 HomeRespVO
 * @描述 首页数据模型
 * @创建人 张全蛋
 * @创建日期 2020/2/19 20:05
 * @版本 1.0
 **/
@Data
public class HomeRespVO {

    @ApiModelProperty(value = "用户信息")
    private UserInfoVO userInfo;

    @ApiModelProperty(value = "首页菜单导航数据")
    private List<MenuTreeVO> menus;

}
