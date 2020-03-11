package com.coder.lesson.mapper;

import com.coder.lesson.entity.Log;
import com.coder.lesson.vo.req.LogPageReqVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogMapper {
    int deleteByPrimaryKey(String id);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);

    List<Log> selectAll(LogPageReqVO vo);

    int deleteByIds(@Param("ids") List<String> ids);
}