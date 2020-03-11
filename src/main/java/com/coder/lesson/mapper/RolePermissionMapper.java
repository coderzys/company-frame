package com.coder.lesson.mapper;

import com.coder.lesson.entity.Permission;
import com.coder.lesson.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

    int batchInsertByPerms(@Param("rps") List<RolePermission> rps);

    List<String> selectRolesByPermissionId(@Param("permissionId") String permissionId);

    int deleteByPermissionId(@Param("permissionId") String permissionId);

    List<String> selectPermissionIdsByRoleId(@Param("roleId") String roleId);

    int deleteByRoleId(@Param("roleId") String id);

    List<String> selectPermsByRoleIds(@Param("roleIds") List<String> roleIds);

//    List<Permission> selectByRoleIds(@Param("roleIds") List<String> roleIds);
}