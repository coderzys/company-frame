package com.coder.lesson.mapper;

import com.coder.lesson.entity.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DeptMapper {
    int deleteByPrimaryKey(String id);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);

    List<Dept> selectAll();

    int updateRelationCode(@Param("oldStr") String oldStr, @Param("newStr") String newStr, @Param("relationCode") String relationCode);

    List<String> selectChildIds(@Param("relationCode") String relationCode);

    int batchUpdateDepts(@Param("deptIds") List<String> list);
}