package com.coder.lesson.controller;

import com.coder.lesson.aop.annotation.MyLog;
import com.coder.lesson.entity.Role;
import com.coder.lesson.service.RoleService;
import com.coder.lesson.utils.CoderUtil;
import com.coder.lesson.utils.DataResult;
import com.coder.lesson.vo.req.RoleAddReqVO;
import com.coder.lesson.vo.req.RolePageReqVO;
import com.coder.lesson.vo.req.RoleUpdateReqVO;
import com.coder.lesson.vo.resp.PageRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @类名 RoleController
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/20 14:08
 * @版本 1.0
 **/
@RestController
@RequestMapping("/api/role")
@Api(tags = "角色模块", description = "角色模块相关接口")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/list")
    @ApiOperation(value = "角色列表")
    @MyLog(title = "组织管理-角色管理", action = "获取角色列表")
    @RequiresPermissions("sys:role:list")
    public DataResult getAllRoles(@RequestBody RolePageReqVO vo) {
        PageRespVO<Role> roles = roleService.findAllRoles(vo);
        return DataResult.success(roles);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加角色")
    @MyLog(title = "组织管理-角色管理", action = "新增角色")
    @RequiresPermissions("sys:role:add")
    public DataResult addRole(@RequestBody @Valid RoleAddReqVO vo) {
        Role role = roleService.saveRole(vo);
        return DataResult.success(role);
    }

    @GetMapping("/{roleId}")
    @ApiOperation(value = "获取角色信息")
    @MyLog(title = "组织管理-角色管理", action = "根据id获取角色信息")
    @RequiresPermissions("sys:role:detail")
    public DataResult getRoleByRoleId(@PathVariable("roleId") String RoleId, HttpServletRequest request) {
        String userId = CoderUtil.getUserId(request);
        Role role = roleService.findRoleByRoleId(RoleId, userId);
        return DataResult.success(role);
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改角色")
    @MyLog(title = "组织管理-角色管理", action = "修改角色信息")
    @RequiresPermissions("sys:role:update")
    public DataResult updateRole(@RequestBody @Valid RoleUpdateReqVO vo) {
        Role role = roleService.modifyRole(vo);
        return DataResult.success(role);
    }

    @DeleteMapping("/delete/{roleId}")
    @ApiOperation(value = "删除角色")
    @MyLog(title = "组织管理-角色管理", action = "删除角色")
    @RequiresPermissions("sys:role:delete")
    public DataResult deleteRole(@PathVariable("roleId") String roleId) {
        roleService.removeRoleByRoleId(roleId);
        return DataResult.success();
    }
}
