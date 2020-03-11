package com.coder.lesson.controller;

import com.coder.lesson.aop.annotation.MyLog;
import com.coder.lesson.entity.Dept;
import com.coder.lesson.service.DeptService;
import com.coder.lesson.utils.DataResult;
import com.coder.lesson.vo.req.DeptUpdateReqVO;
import com.coder.lesson.vo.resp.DeptTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @类名 DeptController
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/20 20:18
 * @版本 1.0
 **/
@RestController
@RequestMapping("/api/dept")
@Api(tags = "部门模块", description = "部门模块相关接口")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/tree")
    @ApiOperation(value = "部门树")
    @MyLog(title = "组织管理-部门管理", action = "获取部门树")
    @RequiresPermissions(value = {"sys:user:update", "sys:user:add", "sys:dept:add", "sys:dept:update"}, logical = Logical.OR)
    public DataResult getDeptTreeModel(@RequestParam(value = "deptId", required = false) String deptId) {
        List<DeptTreeVO> tree = deptService.deptTreeModel(deptId);
        return DataResult.success(tree);
    }

    @GetMapping("/list")
    @ApiOperation(value = "部门列表")
    @MyLog(title = "组织管理-部门管理", action = "获取部门列表")
    @RequiresPermissions("sys:dept:list")
    public DataResult getAllDepts() {
        List<Dept> depts = deptService.findAllDepts();
        return DataResult.success(depts);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增部门")
    @MyLog(title = "组织管理-部门管理", action = "新增部门")
    @RequiresPermissions("sys:dept:add")
    public DataResult addDept(@RequestBody Dept dept) {
        Dept returnDept = deptService.saveDept(dept);
        return DataResult.success(returnDept);
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改部门")
    @MyLog(title = "组织管理-部门管理", action = "修改部门信息")
    @RequiresPermissions("sys:dept:update")
    public DataResult updateDept(@RequestBody @Valid DeptUpdateReqVO vo) {
        deptService.modifyDeptById(vo);
        return DataResult.success();
    }

    @DeleteMapping("/delete/{deptId}")
    @ApiOperation(value = "删除部门")
    @MyLog(title = "组织管理-部门管理", action = "删除部门信息")
    @RequiresPermissions("sys:dept:delete")
    public DataResult updateDept(@PathVariable("deptId") String deptId) {
        deptService.removeDeptById(deptId);
        return DataResult.success();
    }
}
