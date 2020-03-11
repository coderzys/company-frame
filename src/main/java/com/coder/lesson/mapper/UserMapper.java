package com.coder.lesson.mapper;

import com.coder.lesson.entity.User;
import com.coder.lesson.vo.req.UserPageReqVO;
import com.coder.lesson.vo.resp.UserInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUsername(@Param("username") String username);

    List<User> selectAll(UserPageReqVO vo);

    int batchUpdate(@Param("ids") List<String> ids, @Param("updateId") String updateId);

    List<User> selectByDeptIds(@Param("deptIds") List<String> deptIds);

    UserInfoVO selectByUserId(@Param("userId") String userId);
}