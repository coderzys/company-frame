package com.coder.lesson.mapper;

import com.coder.lesson.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> selectAll(@Param("pid") String pid);

    List<Permission> selectByRoleIds(@Param("roleIds") List<String> roleIds);
}