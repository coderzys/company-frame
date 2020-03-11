package com.coder.lesson.mapper;

import com.coder.lesson.entity.Role;
import com.coder.lesson.vo.req.RolePageReqVO;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectAll(RolePageReqVO vo);
}