package com.coder.lesson.controller;

import com.coder.lesson.aop.annotation.MyLog;
import com.coder.lesson.service.UserRoleService;
import com.coder.lesson.utils.DataResult;
import com.coder.lesson.vo.req.UserOwnRolesReqVO;
import com.coder.lesson.vo.resp.UserOwnRolesRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @类名 UserRoleController
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/21 13:07
 * @版本 1.0
 **/
@RestController
@RequestMapping("/api/user/role")
@Api(tags = "用户角色模块", description = "用户角色模块相关接口")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/{userId}")
    @ApiOperation(value = "获取用户角色列表")
    @MyLog(title = "用户角色模块", action ="获取用户角色列表")
    public DataResult getUserRoles(@PathVariable("userId") String userId) {
        UserOwnRolesRespVO vo = userRoleService.findUserRolesByUserId(userId);
        return DataResult.success(vo);
    }

    @PutMapping("/update")
    @ApiOperation(value = "赋予用户角色")
    @MyLog(title = "用户角色模块", action ="赋予用户角色")
    @RequiresPermissions("sys:user:role:update")
    public DataResult addUserRoles(@RequestBody @Valid UserOwnRolesReqVO vo) {
        userRoleService.saveUserRoles(vo);
        return DataResult.success();
    }
}
