package com.coder.lesson.service.impl;

import com.coder.lesson.constants.Constant;
import com.coder.lesson.entity.Role;
import com.coder.lesson.entity.UserRole;
import com.coder.lesson.exception.BusinessException;
import com.coder.lesson.exception.code.BaseResponseCode;
import com.coder.lesson.mapper.UserRoleMapper;
import com.coder.lesson.service.RedisService;
import com.coder.lesson.service.RoleService;
import com.coder.lesson.service.UserRoleService;
import com.coder.lesson.utils.TokenSettings;
import com.coder.lesson.vo.req.UserOwnRolesReqVO;
import com.coder.lesson.vo.resp.UserOwnRolesRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @类名 UserRoleServiceImpl
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/21 12:44
 * @版本 1.0
 **/
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TokenSettings tokenSettings;

    @Override
    public UserOwnRolesRespVO findUserRolesByUserId(String userId) {
        UserOwnRolesRespVO vo = new UserOwnRolesRespVO();
        List<String> roles = userRoleMapper.selectRoleIdsByUserId(userId);
        vo.setOwnRoles(roles);
        List<Role> allRoles = roleService.findRoleList();
        vo.setAllRoles(allRoles);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserRoles(UserOwnRolesReqVO vo) {
        // 清空原来的用户角色
        userRoleMapper.deleteByUserId(vo.getUserId());

        List<String> roleIds = vo.getRoleIds();
        if (!roleIds.isEmpty() && roleIds.size() != 0) {
            // 赋值新的角色
            List<UserRole> userRoles = new ArrayList<>();
            for (String roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setId(UUID.randomUUID().toString());
                userRole.setCreateTime(new Date());
                userRole.setUserId(vo.getUserId());
                userRole.setRoleId(roleId);
                userRoles.add(userRole);
            }
            int i = userRoleMapper.batchInsert(userRoles);
            if (i == 0) {
                throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
            }
        }
        /**
         * 标记用户刷新token
         */
        redisService.set(Constant.JWT_REFRESH_KEY + vo.getUserId(), vo.getUserId(), tokenSettings.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
        /**
         * 清除用户授权缓存
         */
        redisService.delete(Constant.IDENTIFY_CACHE_KEY + vo.getUserId());
    }

    @Override
    public List<String> findUserIdsByRoleIds(List<String> roleIds) {
        return userRoleMapper.selectUserIdsByRoleIds(roleIds);
    }

    @Override
    public int removeByRoleId(String roleId) {
        return userRoleMapper.deleteByRoleId(roleId);
    }

    @Override
    public List<String> findRolesByUserId(String id) {
        return userRoleMapper.selectRoleIdsByUserId(id);
    }
}
