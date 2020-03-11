package com.coder.lesson.controller;

import com.coder.lesson.aop.annotation.MyLog;
import com.coder.lesson.constants.Constant;
import com.coder.lesson.entity.Permission;
import com.coder.lesson.service.PermissionService;
import com.coder.lesson.utils.DataResult;
import com.coder.lesson.utils.JwtTokenUtil;
import com.coder.lesson.vo.req.PermissionAddReqVO;
import com.coder.lesson.vo.req.PermissionUpdateReqVO;
import com.coder.lesson.vo.resp.MenuTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.crypto.Data;
import java.util.List;

/**
 * @类名 PermissionController
 * @描述 菜单权限相关
 * @创建人 张全蛋
 * @创建日期 2020/2/19 21:53
 * @版本 1.0
 **/
@RestController
@RequestMapping("/api/permission")
@Api(tags = "菜单权限模块", description = "菜单权限模块相关接口")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/list")
    @ApiOperation(value = "获取菜单列表接口")
    @MyLog(title = "组织管理-菜单权限管理", action = "获取菜单权限列表")
    @RequiresPermissions("sys:permission:list")
    public DataResult getAllPermissions() {
        List<Permission> permissions = permissionService.findAllPermissions();
        return DataResult.success(permissions);
    }

    /**
     * 获取菜单树
     *
     * @param treeType 1：代表获取所有权限树
     *                 2：代表获取当前角色拥有权限的菜单树,且不包含按钮的权限树
     *                 3：代表获取当前角色拥有权限的菜单树，且包含“默认顶级菜单这一项”,且不包含按钮的权限树
     *                 4: 获取用户有权限的菜单树
     * @return
     */
    @GetMapping("/tree")
    @ApiOperation(value = "获取菜单树接口")
    @MyLog(title = "组织管理-菜单权限管理", action = "获取菜单权限树")
    @RequiresPermissions(value = {"sys:role:update", "sys:role:update", "sys:permission:update", "sys:permission:add"}, logical = Logical.OR)
    public DataResult getMenuTree(@RequestParam("treeType") Integer treeType, HttpServletRequest request) {
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = JwtTokenUtil.getUserId(accessToken);
        List<MenuTreeVO> menuTreeModel = permissionService.findMenuTreeModel(userId, treeType);
        return DataResult.success(menuTreeModel);
    }

    @PostMapping("/add")
    @ApiOperation(value = "菜单权限新增接口")
    @MyLog(title = "组织管理-菜单权限管理", action = "新增菜单权限")
    @RequiresPermissions("sys:permission:add")
    public DataResult addPermission(@RequestBody @Valid PermissionAddReqVO vo) {
        Permission permission = permissionService.savePermission(vo);
        return DataResult.success(permission);
    }

    @PutMapping("/update")
    @ApiOperation(value = "菜单权限修改接口")
    @MyLog(title = "组织管理-菜单权限管理", action = "修改菜单权限信息")
    @RequiresPermissions("sys:permission:update")
    public DataResult updatePermission(@RequestBody @Valid PermissionUpdateReqVO vo) {
        permissionService.modifyPermission(vo);
        return DataResult.success();
    }

    @DeleteMapping("/delete/{permissionId}")
    @ApiOperation(value = "菜单权限删除接口")
    @MyLog(title = "组织管理-菜单权限管理", action = "删除菜单权限信息")
    @RequiresPermissions("sys:permission:delete")
    public DataResult deletePermission(@PathVariable("permissionId") String permissionId) {
        permissionService.removePermissionById(permissionId);
        return DataResult.success();
    }

}
