package com.coder.lesson.controller;

import com.coder.lesson.aop.annotation.MyLog;
import com.coder.lesson.constants.Constant;
import com.coder.lesson.entity.User;
import com.coder.lesson.exception.code.BaseResponseCode;
import com.coder.lesson.service.UserService;
import com.coder.lesson.utils.DataResult;
import com.coder.lesson.utils.JwtTokenUtil;
import com.coder.lesson.vo.req.*;
import com.coder.lesson.vo.resp.LoginRespVO;
import com.coder.lesson.vo.resp.PageRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @类名 UserController
 * @描述 用户相关
 * @创建人 张全蛋
 * @创建日期 2020/2/19 14:36
 * @版本 1.0
 **/
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户模块", description = "用户模块相关接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录接口")
    public DataResult<LoginRespVO> login(@RequestBody @Valid LoginReqVO vo) {
        LoginRespVO respVO = userService.login(vo);
        return new DataResult(respVO);
    }

    /**
     * 用户注销，同时需要把access_token和refresh_token同时注销
     *
     * @param request
     * @return
     */
    @GetMapping("/logout")
    @ApiOperation(value = "用户注销接口")
    public DataResult<LoginRespVO> logout(HttpServletRequest request) {
        try {
            String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
            String refreshToken = request.getHeader(Constant.REFRESH_TOKEN);
            userService.logout(accessToken, refreshToken);
        } catch (Exception e) {
            log.error("logout error{}", e);
        }
        return DataResult.success();
    }

    @GetMapping("/unLogin")
    @ApiOperation(value = "引导客户端去登录")
    public DataResult unLogin() {
        DataResult result = DataResult.getResult(BaseResponseCode.TOKEN_ERROR);
        return result;
    }

    @PostMapping("/list")
    @ApiOperation(value = "获取用户列表接口")
    @MyLog(title = "组织管理-用户管理", action = "分页查询用户列表")
    @RequiresPermissions("sys:user:list")
    public DataResult getAllUsers(@RequestBody UserPageReqVO vo) {
        PageRespVO users = userService.findAllUsers(vo);
        return DataResult.success(users);
    }

    @PostMapping("/add")
    @ApiOperation(value = "获取用户列表接口")
    @MyLog(title = "组织管理-用户管理", action = "新增用户")
    @RequiresPermissions("sys:user:add")
    public DataResult addUser(@RequestBody @Valid UserAddReqVO vo, HttpServletRequest request) {
        User returnUser = userService.saveUser(vo, request);
        return DataResult.success(returnUser);
    }

    @GetMapping("/token")
    @ApiOperation(value = "jwt token刷新接口")
    @MyLog(title = "组织管理-用户管理", action = "用户token刷新")
    public DataResult refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader(Constant.REFRESH_TOKEN);
        String newAccessToken = userService.refreshToken(refreshToken);
        return DataResult.success(newAccessToken);
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改用户信息")
    @MyLog(title = "组织管理-用户管理", action = "修改用户信息")
    @RequiresPermissions("sys:user:update")
    public DataResult updateUserInfo(@RequestBody @Valid UserUpdateReqVO vo, HttpServletRequest request) {
        userService.modifyUser(vo, request);
        return DataResult.success();
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "批量删除用户")
    @MyLog(title = "组织管理-用户管理", action = "批量删除用户数据")
    @RequiresPermissions("sys:user:delete")
    public DataResult batchDelete(@RequestBody List<String> ids, HttpServletRequest request) {
        userService.deleteUser(ids, request);
        return DataResult.success();
    }

    @GetMapping("/info")
    @ApiOperation(value = "用户详情")
    @MyLog(title = "组织管理-用户管理", action = "查询用户详情")
    public DataResult userInfo(HttpServletRequest request) {
        User user = userService.findUserInfo(request);
        return DataResult.success(user);
    }

    @PutMapping("/info/update")
    @ApiOperation(value = "修改用户详情")
    @MyLog(title = "组织管理-用户管理", action = "修改用户详情")
    public DataResult updateUserInfo(@RequestBody User user) {
        userService.modifyUserInfo(user);
        return DataResult.success();
    }

    @PutMapping("/update/pwd")
    @ApiOperation(value = "修改密码")
    @MyLog(title = "组织管理-用户管理", action = "修改密码")
    public DataResult updateUserPwd(@RequestBody @Valid UserUpdatePwdReqVO vo, HttpServletRequest request) {
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        String refreshToken = request.getHeader(Constant.REFRESH_TOKEN);
        userService.modifyUserPwd(vo, accessToken, refreshToken);
        return DataResult.success();
    }
}
