package com.coder.lesson.service;

import com.coder.lesson.entity.Permission;
import com.coder.lesson.entity.Role;
import com.coder.lesson.entity.RolePermission;

import java.util.List;

/**
 * @接口名 RolePermissionService
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/21 20:17
 * @版本 1.0
 **/
public interface RolePermissionService {

    List<String> findRolesByPermissionId(String permissionId);

    int removePermissionByPermissionId(String permissionId);

    List<String> findPermissionIdsByRoleId(String roleId);

    int batchInsertByPerms(List<RolePermission> rps);

    void removePermissionByRoleId(String id);

    List<String> findPermsByRoleIds(List<String> roles);

}
