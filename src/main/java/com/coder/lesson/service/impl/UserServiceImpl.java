package com.coder.lesson.service.impl;

import com.coder.lesson.constants.Constant;
import com.coder.lesson.entity.User;
import com.coder.lesson.exception.BusinessException;
import com.coder.lesson.exception.code.BaseResponseCode;
import com.coder.lesson.mapper.UserMapper;
import com.coder.lesson.service.RedisService;
import com.coder.lesson.service.RolePermissionService;
import com.coder.lesson.service.UserRoleService;
import com.coder.lesson.service.UserService;
import com.coder.lesson.utils.*;
import com.coder.lesson.vo.req.*;
import com.coder.lesson.vo.resp.LoginRespVO;
import com.coder.lesson.vo.resp.PageRespVO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @类名 UserServiceImpl
 * @描述 用户业务实现类
 * @创建人 张全蛋
 * @创建日期 2020/2/19 14:05
 * @版本 1.0
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private TokenSettings tokenSettings;

    @Override
    public LoginRespVO login(LoginReqVO vo) {
        String username = vo.getUsername();
        User user = userMapper.selectByUsername(username);
        // 验证账户是否存在
        if (user == null) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_ERROR);
        }
        // 验证账户状态
        if (user.getStatus() == 2) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_LOCK_TIP);
        }
        // 校验密码
        if (!PasswordUtils.matches(user.getSalt(), vo.getPassword(), user.getPassword())) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_PASSWORD_ERROR);
        }

        // 设置响应数据模型内容
        LoginRespVO respVO = new LoginRespVO();
        respVO.setPhone(user.getPhone());
        respVO.setUsername(username);
        respVO.setId(user.getId());

        // 获取用户角色
        List<String> roles = userRoleService.findRolesByUserId(user.getId());
        // 角色权限
        List<String> permissions = new ArrayList<>();
        // 生成token
        Map<String, Object> claims = new HashMap<>();
        if (!CoderUtil.validArrayIsEmpty(roles)) {
            // 获取角色权限
            permissions = rolePermissionService.findPermsByRoleIds(roles);
        }
        claims.put(Constant.JWT_ROLES_KEY, roles);
        claims.put(Constant.JWT_PERMISSIONS_KEY, permissions);
        claims.put(Constant.JWT_USER_NAME, username);
        String accessToken = JwtTokenUtil.getAccessToken(user.getId(), claims);
        respVO.setAccessToken(accessToken);

        // 生成refresh_token
        String refreshToken;
        if ("1".equals(vo.getType())) {
            refreshToken = JwtTokenUtil.getRefreshToken(user.getId(), claims);
        } else {
            refreshToken = JwtTokenUtil.getRefreshAppToken(user.getId(), claims);
        }
        respVO.setRefreshToken(refreshToken);

        return respVO;
    }

    @Override
    public void logout(String accessToken, String refreshToken) {
        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(refreshToken)) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        log.info("subject.getPrincipals()={}", subject.getPrincipals());
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        String userId = JwtTokenUtil.getUserId(accessToken);
        /**
         * 把access_token 加入黑名单 禁止再登录
         */
        redisService.set(Constant.JWT_ACCESS_TOKEN_BLACKLIST + accessToken, userId, JwtTokenUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
        /**
         * 把 refresh_token 加入黑名单 禁止再拿来刷新token
         */
        redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST + refreshToken, userId, JwtTokenUtil.getRemainingTime(refreshToken), TimeUnit.MILLISECONDS);
    }

    @Override
    public PageRespVO findAllUsers(UserPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<User> users = userMapper.selectAll(vo);
        return PageUtil.getPageVO(users);
    }

    @Override
    public User saveUser(UserAddReqVO vo, HttpServletRequest request) {
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        String access_token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = JwtTokenUtil.getUserId(access_token);
        user.setCreateId(userId);
        user.setCreateTime(new Date());
        user.setId(UUID.randomUUID().toString());
        user.setDeleted(1);
        String salt = PasswordUtils.getSalt();
        user.setSalt(salt);
        String encode = PasswordUtils.encode(user.getPassword(), salt);
        user.setPassword(encode);

        int i = userMapper.insertSelective(user);
        if (i != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        return user;
    }

    @Override
    public String refreshToken(String refreshToken) {
        // 判断刷新token是否过期,是否被加入黑名单
        if (!JwtTokenUtil.validateToken(refreshToken) || redisService.hasKey(Constant.JWT_REFRESH_TOKEN_BLACKLIST + refreshToken)) {
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }

        String userId = JwtTokenUtil.getUserId(refreshToken);
        log.info("userId={}", userId);

        // 获取用户角色
        List<String> roles = userRoleService.findRolesByUserId(userId);
        // 角色权限
        List<String> permissions = new ArrayList<>();
        // 生成token
        Map<String, Object> claims = new HashMap<>();
        if (!CoderUtil.validArrayIsEmpty(roles)) {
            // 获取角色权限
            permissions = rolePermissionService.findPermsByRoleIds(roles);
        }
        if (redisService.hasKey(Constant.JWT_REFRESH_KEY + userId)) {
            claims.put(Constant.JWT_ROLES_KEY, roles);
            claims.put(Constant.JWT_PERMISSIONS_KEY, permissions);
        }

        // 重新生成token
        return JwtTokenUtil.refreshToken(refreshToken, claims);
    }

    @Override
    public void modifyUser(UserUpdateReqVO vo, HttpServletRequest request) {
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        user.setUpdateTime(new Date());

        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = JwtTokenUtil.getUserId(accessToken);
        user.setUpdateId(userId);

        if (StringUtils.isEmpty(vo.getPassword())) {
            user.setPassword(null);
        } else {
            String salt = PasswordUtils.getSalt();
            String encode = PasswordUtils.encode(vo.getPassword(), salt);
            user.setPassword(encode);
            user.setSalt(salt);
        }

        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }

        // 如果用户被禁用，则会进行标记
        if (vo.getStatus() == 2) {
            redisService.set(Constant.ACCOUNT_LOCK_KEY + vo.getId(), vo.getId());
        } else {
            // 否则解除标记
            redisService.delete(Constant.ACCOUNT_LOCK_KEY + vo.getId());
        }

    }

    @Override
    public void deleteUser(List<String> ids, HttpServletRequest request) {
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        String updateId = JwtTokenUtil.getUserId(accessToken);
        int i = userMapper.batchUpdate(ids, updateId);
        if (i == 0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }

        // 标记用户
        for (String id : ids) {
            redisService.set(Constant.DELETED_USER_KEY + id, id, tokenSettings.getRefreshTokenExpireAppTime().toMillis(), TimeUnit.MILLISECONDS);
            /**
             * 清除用户授权缓存
             */
            redisService.delete(Constant.IDENTIFY_CACHE_KEY + id);
        }
    }

    @Override
    public List<User> findUsersByDeptIds(List<String> deptIds) {
        return userMapper.selectByDeptIds(deptIds);
    }

    @Override
    public User findUserInfo(HttpServletRequest request) {
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = JwtTokenUtil.getUserId(accessToken);
        User user = userMapper.selectByPrimaryKey(userId);
        user.setPassword(null);
        user.setSalt(null);

        return user;
    }

    @Override
    public void modifyUserInfo(User user) {
        user.setStatus(null);
        user.setUsername(null);
        user.setUpdateTime(new Date());
        user.setUpdateId(user.getId());
        int i = userMapper.updateByPrimaryKeySelective(user);
        CoderUtil.crudIsSuccess(i);
    }

    @Override
    public void modifyUserPwd(UserUpdatePwdReqVO vo, String accessToken, String refreshToken) {
        String userId = JwtTokenUtil.getUserId(accessToken);
        User returnUser = userMapper.selectByPrimaryKey(userId);

        if (returnUser == null) {
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }
        if (!PasswordUtils.matches(returnUser.getSalt(), vo.getOldPwd(), returnUser.getPassword())) {
            throw new BusinessException(BaseResponseCode.OLD_PASSWORD_ERROR);
        }
        User user = new User();
        user.setId(userId);
        user.setUpdateTime(new Date());
        user.setUpdateId(userId);
        user.setPassword(PasswordUtils.encode(vo.getNewPwd(), returnUser.getSalt()));

        int i = userMapper.updateByPrimaryKeySelective(user);
        CoderUtil.crudIsSuccess(i);

        /**
         * 把access_token 加入黑名单 禁止再访问
         */
        redisService.set(Constant.JWT_ACCESS_TOKEN_BLACKLIST + accessToken, userId, JwtTokenUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
        /**
         * 把 refresh_token 加入黑名单 禁止再拿来刷新token
         */
        redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST + refreshToken, userId, JwtTokenUtil.getRemainingTime(refreshToken), TimeUnit.MILLISECONDS);
        /**
         * 清除用户授权缓存
         */
        redisService.delete(Constant.IDENTIFY_CACHE_KEY + userId);
    }

}
