package com.coder.lesson.controller;

import com.coder.lesson.aop.annotation.MyLog;
import com.coder.lesson.constants.Constant;
import com.coder.lesson.service.HomeService;
import com.coder.lesson.utils.DataResult;
import com.coder.lesson.utils.JwtTokenUtil;
import com.coder.lesson.vo.resp.HomeRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @类名 HomeController
 * @描述 主页数据相关
 * @创建人 张全蛋
 * @创建日期 2020/2/19 20:26
 * @版本 1.0
 **/
@RestController
@RequestMapping("/api/home")
@Api(tags = "首页模块", description = "首页模块相关接口")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/data")
    @ApiOperation(value = "获取首页数据接口")
    @MyLog(title = "首页模块", action ="获取菜单和用户信息")
    public DataResult getHomeData(HttpServletRequest request) {
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = JwtTokenUtil.getUserId(accessToken);
        HomeRespVO homeData = homeService.findHomeData(userId);
        return DataResult.success(homeData);
    }

}
