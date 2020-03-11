package com.coder.lesson.controller;

import com.coder.lesson.aop.annotation.MyLog;
import com.coder.lesson.entity.Log;
import com.coder.lesson.service.LogService;
import com.coder.lesson.utils.DataResult;
import com.coder.lesson.vo.req.LogPageReqVO;
import com.coder.lesson.vo.resp.PageRespVO;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @类名 LogController
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/22 17:11
 * @版本 1.0
 **/
@RestController
@RequestMapping("/api/log")
@Api(tags = "日志模块", description = "日志模块相关接口")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("/list")
//    @MyLog(title = "系统管理-日志管理", action = "分页+参数查询日志列表")
    @RequiresPermissions("sys:log:list")
    public DataResult getAllLogsByPage(@RequestBody LogPageReqVO vo) {
        PageRespVO<Log> respVo = logService.findAllLogsByPage(vo);
        return DataResult.success(respVo);
    }

    @DeleteMapping("/delete")
    @MyLog(title = "系统管理-日志管理", action = "批量删除日志")
    @RequiresPermissions("sys:log:delete")
    public DataResult deleteBatchByIds(@RequestBody List<String> ids) {
        logService.removeLogByIds(ids);
        return DataResult.success();
    }

}
