package com.coder.lesson.service;

import com.coder.lesson.vo.req.UserOwnRolesReqVO;
import com.coder.lesson.vo.resp.UserOwnRolesRespVO;

import java.util.List;

/**
 * @接口名 UserRoleService
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/21 12:43
 * @版本 1.0
 **/
public interface UserRoleService {

    UserOwnRolesRespVO findUserRolesByUserId(String userId);

    void saveUserRoles(UserOwnRolesReqVO vo);

    List<String> findUserIdsByRoleIds(List<String> roleIds);

    int removeByRoleId(String roleId);

    List<String> findRolesByUserId(String id);
}
