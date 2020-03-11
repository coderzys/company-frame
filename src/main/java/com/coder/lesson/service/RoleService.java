package com.coder.lesson.service;

import com.coder.lesson.entity.Role;
import com.coder.lesson.vo.req.RoleAddReqVO;
import com.coder.lesson.vo.req.RolePageReqVO;
import com.coder.lesson.vo.req.RoleUpdateReqVO;
import com.coder.lesson.vo.resp.PageRespVO;

import java.util.List;

/**
 * @接口名 RoleService
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/20 14:03
 * @版本 1.0
 **/
public interface RoleService {

    PageRespVO<Role> findAllRoles(RolePageReqVO vo);

    Role saveRole(RoleAddReqVO vo);

    List<Role> findRoleList();

    Role modifyRole(RoleUpdateReqVO vo);

    Role findRoleByRoleId(String roleId, String userId);

    void removeRoleByRoleId(String roleId);
}
