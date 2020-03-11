package com.coder.lesson.service.impl;

import com.coder.lesson.constants.Constant;
import com.coder.lesson.entity.Permission;
import com.coder.lesson.exception.BusinessException;
import com.coder.lesson.exception.code.BaseResponseCode;
import com.coder.lesson.mapper.PermissionMapper;
import com.coder.lesson.service.PermissionService;
import com.coder.lesson.service.RedisService;
import com.coder.lesson.service.RolePermissionService;
import com.coder.lesson.service.UserRoleService;
import com.coder.lesson.utils.CoderUtil;
import com.coder.lesson.utils.JwtTokenUtil;
import com.coder.lesson.utils.TokenSettings;
import com.coder.lesson.vo.req.PermissionAddReqVO;
import com.coder.lesson.vo.req.PermissionUpdateReqVO;
import com.coder.lesson.vo.resp.MenuTreeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.coder.lesson.utils.CoderUtil.validArrayIsEmpty;

/**
 * @类名 PermissionServiceImpl
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/19 21:52
 * @版本 1.0
 **/
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TokenSettings tokenSettings;

    @Override
    public List<Permission> findAllPermissions() {
        List<Permission> permissions = permissionMapper.selectAll(null);
        if (!permissions.isEmpty()) {
            for (Permission permission : permissions) {
                Permission parent = permissionMapper.selectByPrimaryKey(permission.getPid());
                if (parent != null) {
                    permission.setPidName(parent.getName());
                }
            }
        }
        return permissions;
    }

    /**
     * 菜单树
     *
     * @param userId 用户id
     * @param treeType 1：代表获取所有权限树
     *                 2：代表获取当前角色拥有权限的菜单树,且不包含按钮的权限树
     *                 3：代表获取当前角色拥有权限的菜单树，且包含“默认顶级菜单这一项”,且不包含按钮的权限树
     *                 4: 获取用户有权限的菜单树
     * @return
     */
    @Override
    public List<MenuTreeVO> findMenuTreeModel(String userId, Integer treeType) {
        List<MenuTreeVO> vos = new ArrayList<>();
        List<Permission> permissions = null;
        if (treeType.equals(1)) {
            // 获取所有deleted=1权限树菜单
            permissions = permissionMapper.selectAll(null);
            if (!CoderUtil.validArrayIsEmpty(permissions)) {
                vos = menuTreeModel(permissions, treeType);
            }
        } else {
            // 获取当前用户有管理权限的菜单
            List<String> roleIds = userRoleService.findRolesByUserId(userId);
            if (!CoderUtil.validArrayIsEmpty(roleIds)) {
                permissions = permissionMapper.selectByRoleIds(roleIds);
                if (!CoderUtil.validArrayIsEmpty(permissions)) {
                    vos = menuTreeModel(permissions, treeType);
                    if (treeType.equals(3)) {
                        MenuTreeVO vo = new MenuTreeVO();
                        vo.setId("0");
                        vo.setTitle("默认顶级菜单");
                        vo.setChildren(vos);
                        vos = new ArrayList<>();
                        vos.add(vo);
                    }
                }
            }
        }
        return vos;
    }

    @Override
    public Permission savePermission(PermissionAddReqVO vo) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(vo, permission);
        validPermissionType(permission);
        permission.setId(UUID.randomUUID().toString());
        permission.setCreateTime(new Date());
        int i = permissionMapper.insertSelective(permission);
        if (i != 1) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        return null;
    }

    @Override
    public void modifyPermission(PermissionUpdateReqVO vo) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(vo, permission);
        permission.setUpdateTime(new Date());
        validPermissionType(permission);

        Permission returnPermission = permissionMapper.selectByPrimaryKey(vo.getId());
        if (returnPermission == null) {
            log.info("传入的id在数据库中不存在！");
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        if (permission.getPid().equals(vo.getPid())) {
            List<Permission> children = permissionMapper.selectAll(vo.getId());
            if (!CoderUtil.validArrayIsEmpty(children)) {
                throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_UPDATE);
            }
        }
        int i = permissionMapper.updateByPrimaryKeySelective(permission);
        CoderUtil.crudIsSuccess(i);

        // 判断授权标识符是否发生了变化
        if (!permission.getPerms().equals(vo.getPerms())) {
            List<String> roles = rolePermissionService.findRolesByPermissionId(permission.getId());
            if (!validArrayIsEmpty(roles)) {
                List<String> userIds = userRoleService.findUserIdsByRoleIds(roles);
                if (!validArrayIsEmpty(userIds)) {
                    for (String userId : userIds) {
                        redisService.set(Constant.JWT_REFRESH_KEY + userId, userId, tokenSettings.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
                        /**
                         * 清除用户授权缓存
                         */
                        redisService.delete(Constant.IDENTIFY_CACHE_KEY + userId);
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removePermissionById(String permissionId) {
        List<Permission> children = permissionMapper.selectAll(permissionId);
        if (!CoderUtil.validArrayIsEmpty(children)) {
            throw new BusinessException(BaseResponseCode.ROLE_PERMISSION_RELATION);
        }
        rolePermissionService.removePermissionByPermissionId(permissionId);
//        CoderUtil.crudIsSuccess(i);

        // 逻辑删除菜单权限
        Permission permission = new Permission();
        permission.setId(permissionId);
        permission.setUpdateTime(new Date());
        permission.setDeleted(0);
        int i1 = permissionMapper.updateByPrimaryKeySelective(permission);
        CoderUtil.crudIsSuccess(i1);


        // 判断授权标识符是否发生了变化
        List<String> roles = rolePermissionService.findRolesByPermissionId(permissionId);
        if (!validArrayIsEmpty(roles)) {
            List<String> userIds = userRoleService.findUserIdsByRoleIds(roles);
            if (!validArrayIsEmpty(userIds)) {
                for (String userId : userIds) {
                    redisService.set(Constant.JWT_REFRESH_KEY + userId, userId, tokenSettings.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
                    /**
                     * 清除用户授权缓存
                     */
                    redisService.delete(Constant.IDENTIFY_CACHE_KEY + userId);
                }
            }
        }

    }


    /**
     * 数据验证
     *
     * @param permission
     */
    private void validPermissionType(Permission permission) {
        Permission parent = permissionMapper.selectByPrimaryKey(permission.getPid());
        switch (permission.getType()) {
            case 1:
                // 添加目录的情况
                if (parent != null) {
                    if (parent.getType() != 1) {
                        throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                    }
                } else if (!permission.getPid().equals("0")) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                }
                break;
            case 2:
                if (parent == null || parent.getType() != 1) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                }
                if (StringUtils.isEmpty(permission.getUrl())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }
                break;
            case 3:
                if (parent == null || parent.getType() != 2) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_BTN_ERROR);
                }
                if (StringUtils.isEmpty(permission.getPerms())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_PERMS_NULL);
                }
                if (StringUtils.isEmpty(permission.getUrl())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }
                if (StringUtils.isEmpty(permission.getMethod())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_METHOD_NULL);
                }
                if (StringUtils.isEmpty(permission.getCode())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_CODE_NULL);
                }
                break;
        }

    }

    /**
     * 获取顶级菜单
     *
     * @param treeType 1：代表获取所有权限树
     *                 2：代表获取当前角色拥有权限的菜单树,且不包含按钮的权限树
     *                 3：代表获取当前角色拥有权限的菜单树，且包含“默认顶级菜单这一项”,且不包含按钮的权限树
     *                 4: 获取用户有权限的菜单树
     * @param list 要递归的集合
     * @return
     */
    private List<MenuTreeVO> menuTreeModel(List<Permission> list, Integer treeType) {
        List<MenuTreeVO> treeModel = new ArrayList<>();
        if (!list.isEmpty() && list.size() != 0) {
            for (Permission permission : list) {
                // 首先获取根节点的数据
                if ("0".equals(permission.getPid())) {
                    MenuTreeVO menuTreeVO = new MenuTreeVO(permission);
                    // 获取子节点
                    menuTreeVO.setChildren(getMenuChildren(permission.getId(), list, treeType));
                    treeModel.add(menuTreeVO);
                }
            }
        }
        return treeModel;
    }

    /**
     * 递归获取当前节点下的子节点
     *
     * @param pid 父id
     * @param list 要递归的集合
     * @param treeType 1：代表获取所有权限树
     *                 2：代表获取当前角色拥有权限的菜单树,且不包含按钮的权限树
     *                 3：代表获取当前角色拥有权限的菜单树，且包含“默认顶级菜单这一项”,且不包含按钮的权限树
     *                 4: 获取用户有权限的菜单树
     * @return
     */
    private List<MenuTreeVO> getMenuChildren(String pid, List<Permission> list, Integer treeType) {
        List<MenuTreeVO> treeModel = new ArrayList<>();
        for (Permission permission : list) {
            if (!treeType.equals(1) && !treeType.equals(4)) {
                // 只获取type不为3的子节点数据
                if (permission.getPid().equals(pid) && permission.getType() != 3) {
                    MenuTreeVO menuTreeVO = new MenuTreeVO(permission);
                    menuTreeVO.setChildren(getMenuChildren(permission.getId(), list, treeType));
                    treeModel.add(menuTreeVO);
                }
            } else {
                // 获取所有子节点数据
                if (permission.getPid().equals(pid)) {
                    MenuTreeVO menuTreeVO = new MenuTreeVO(permission);
                    menuTreeVO.setChildren(getMenuChildren(permission.getId(), list, treeType));
                    treeModel.add(menuTreeVO);
                }
            }
        }
        return treeModel;
    }
}
