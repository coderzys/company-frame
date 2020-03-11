package com.coder.lesson.service.impl;

import com.coder.lesson.entity.Permission;
import com.coder.lesson.entity.RolePermission;
import com.coder.lesson.mapper.RolePermissionMapper;
import com.coder.lesson.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @类名 RolePermissionServiceImpl
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/21 20:18
 * @版本 1.0
 **/
@Service
@Slf4j
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<String> findRolesByPermissionId(String permissionId) {
        return rolePermissionMapper.selectRolesByPermissionId(permissionId);
    }

    @Override
    public int removePermissionByPermissionId(String permissionId) {
        return rolePermissionMapper.deleteByPermissionId(permissionId);
    }

    @Override
    public List<String> findPermissionIdsByRoleId(String roleId) {
        List<String> permissionIds = rolePermissionMapper.selectPermissionIdsByRoleId(roleId);
        return permissionIds;
    }

    @Override
    public int batchInsertByPerms(List<RolePermission> rps) {
        return rolePermissionMapper.batchInsertByPerms(rps);
    }

    @Override
    public void removePermissionByRoleId(String id) {
        rolePermissionMapper.deleteByRoleId(id);
    }

    @Override
    public List<String> findPermsByRoleIds(List<String> roleIds) {
        return rolePermissionMapper.selectPermsByRoleIds(roleIds);
    }
}
