package com.coder.lesson.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @类名 ViewController
 * @描述 视图跳转控制器
 * @创建人 张全蛋
 * @创建日期 2020/2/19 18:59
 * @版本 1.0
 **/
@Controller
@RequestMapping("/view")
@Api(tags = "视图跳转模块", description = "视图跳转控制器")
public class ViewController {

    @GetMapping("/404")
    @ApiOperation(value = "跳转404页面")
    public String error404() {
        return "error/404";
    }

    @GetMapping("/login")
    @ApiOperation(value = "跳转登录页")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    @ApiOperation(value = "跳转首页")
    public String home() {
        return "home";
    }

    @GetMapping("/main")
    @ApiOperation(value = "跳转主页")
    public String main() {
        return "main";
    }

    @GetMapping("/menus")
    @ApiOperation(value = "跳转菜单权限管理页")
    public String menus() {
        return "organization/menus/menu";
    }

    @GetMapping("/roles")
    @ApiOperation(value = "跳转角色管理页")
    public String roles() {
        return "organization/roles/role";
    }

    @GetMapping("/depts")
    @ApiOperation(value = "跳转部门管理页")
    public String depts() {
        return "organization/depts/dept";
    }

    @GetMapping("/users")
    @ApiOperation(value = "跳转用户管理页")
    public String users() {
        return "organization/users/user";
    }

    @GetMapping("/logs")
    @ApiOperation(value = "跳转日志管理页")
    public String logs() {
        return "system/logs/log";
    }

    @GetMapping("/users/info")
    @ApiOperation(value = "跳转用户详情页")
    public String userInfo() {
        return "organization/users/user_edit";
    }

    @GetMapping("/users/pwd")
    @ApiOperation(value = "跳转修改密码页")
    public String userPwd() {
        return "organization/users/user_pwd";
    }

}
