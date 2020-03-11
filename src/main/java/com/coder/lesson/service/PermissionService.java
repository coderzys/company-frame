package com.coder.lesson.service;

import com.coder.lesson.entity.Permission;
import com.coder.lesson.vo.req.PermissionAddReqVO;
import com.coder.lesson.vo.req.PermissionUpdateReqVO;
import com.coder.lesson.vo.resp.MenuTreeVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @接口名 PermissionService
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/19 21:51
 * @版本 1.0
 **/
public interface PermissionService {

    List<Permission> findAllPermissions();

    List<MenuTreeVO> findMenuTreeModel(String userId, Integer treeType);

    Permission savePermission(PermissionAddReqVO vo);

    void modifyPermission(PermissionUpdateReqVO vo);

    void removePermissionById(String permissionId);

}
