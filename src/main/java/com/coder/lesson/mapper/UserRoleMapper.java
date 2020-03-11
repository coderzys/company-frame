package com.coder.lesson.mapper;

import com.coder.lesson.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    List<String> selectRoleIdsByUserId(@Param("userId") String userId);

    int deleteByUserId(@Param("userId") String userId);

    int batchInsert(@Param("userRoles") List<UserRole> userRoles);

    List<String> selectUserIdsByRoleIds(@Param("roleIds") List<String> roleIds);

    int deleteByRoleId(@Param("roleId") String roleId);
}