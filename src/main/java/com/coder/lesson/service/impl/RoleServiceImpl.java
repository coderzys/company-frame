package com.coder.lesson.service.impl;

import com.coder.lesson.constants.Constant;
import com.coder.lesson.entity.Role;
import com.coder.lesson.entity.RolePermission;
import com.coder.lesson.exception.BusinessException;
import com.coder.lesson.exception.code.BaseResponseCode;
import com.coder.lesson.mapper.RoleMapper;
import com.coder.lesson.mapper.RolePermissionMapper;
import com.coder.lesson.service.*;
import com.coder.lesson.utils.CoderUtil;
import com.coder.lesson.utils.PageUtil;
import com.coder.lesson.utils.TokenSettings;
import com.coder.lesson.vo.req.RoleAddReqVO;
import com.coder.lesson.vo.req.RolePageReqVO;
import com.coder.lesson.vo.req.RoleUpdateReqVO;
import com.coder.lesson.vo.resp.MenuTreeVO;
import com.coder.lesson.vo.resp.PageRespVO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @类名 RoleServiceImpl
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/20 14:04
 * @版本 1.0
 **/
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    @Lazy
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    @Lazy
    private UserRoleService userRoleService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TokenSettings tokenSettings;

    @Override
    public PageRespVO<Role> findAllRoles(RolePageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<Role> roles = roleMapper.selectAll(vo);
        PageRespVO<Role> pageRespVO = PageUtil.getPageVO(roles);
        return pageRespVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role saveRole(RoleAddReqVO vo) {
        Role role = new Role();
        BeanUtils.copyProperties(vo, role);
        String roleId = UUID.randomUUID().toString();
        role.setId(roleId);
        role.setCreateTime(new Date());
        role.setDeleted(1);

        List<String> perms = vo.getPermissionIds();
        if (!CoderUtil.validArrayIsEmpty(perms)) {
            List<RolePermission> rps = new ArrayList<>();
            for (String perm : perms) {
                RolePermission rolePermission = new RolePermission();
                String id = UUID.randomUUID().toString();
                rolePermission.setId(id);
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(perm);
                rolePermission.setCreateTime(new Date());
                rps.add(rolePermission);
//                rolePermissionMapper.insertSelective(rolePermission);
            }
            int i = rolePermissionService.batchInsertByPerms(rps);
            CoderUtil.crudIsSuccess(i);
        }
        int i = roleMapper.insertSelective(role);
        CoderUtil.crudIsSuccess(i);
        return null;
    }

    @Override
    public List<Role> findRoleList() {
        return roleMapper.selectAll(null);
    }

    @Override
    public Role modifyRole(RoleUpdateReqVO vo) {
        Role role = roleMapper.selectByPrimaryKey(vo.getId());
        if (role == null) {
            log.error("传入的id：{}不合法", vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        BeanUtils.copyProperties(vo, role);
        role.setUpdateTime(new Date());
        int i = roleMapper.updateByPrimaryKeySelective(role);
        CoderUtil.crudIsSuccess(i);

        rolePermissionService.removePermissionByRoleId(vo.getId());

        List<String> permissionIds = vo.getPermissionIds();
        List<RolePermission> rps = new ArrayList<>();
        if (!CoderUtil.validArrayIsEmpty(permissionIds)) {
            for (String id : permissionIds) {
                RolePermission rp = new RolePermission();
                rp.setId(CoderUtil.getUUID(true));
                rp.setCreateTime(new Date());
                rp.setRoleId(vo.getId());
                rp.setPermissionId(id);
                rps.add(rp);
            }
            int i1 = rolePermissionService.batchInsertByPerms(rps);
            CoderUtil.crudIsSuccess(i1);
        }

        List<String> roleIds = new ArrayList<>();
        roleIds.add(vo.getId());
        List<String> userIds = userRoleService.findUserIdsByRoleIds(roleIds);
        if (!CoderUtil.validArrayIsEmpty(userIds)) {
            for (String userId : userIds) {
                /**
                 * 标记用户进行token刷新
                 */
                redisService.set(Constant.JWT_REFRESH_KEY + userId, userId, tokenSettings.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
                /**
                 * 清除用户授权缓存
                 */
                redisService.delete(Constant.IDENTIFY_CACHE_KEY + userId);
            }
        }

        return role;
    }

    @Override
    public Role findRoleByRoleId(String roleId, String userId) {
        Role returnRole = roleMapper.selectByPrimaryKey(roleId);
        if (returnRole == null) {
            log.error("传入的id：{}不合法", roleId);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }

        // 获取权限菜单树
        List<MenuTreeVO> menuTreeModel = permissionService.findMenuTreeModel(userId, 1);
        CoderUtil.validArrayIsEmpty(menuTreeModel);

        // 获取当前角色下的权限id集合
        List<String> permissionIds = rolePermissionService.findPermissionIdsByRoleId(roleId);
        Set<String> perms = new HashSet<>(permissionIds);

        // 遍历菜单权限树
        getOwnTreeModel(menuTreeModel, perms);

        returnRole.setMenuTreeModel(menuTreeModel);
        return returnRole;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRoleByRoleId(String roleId) {
        Role updRole = new Role();
        updRole.setId(roleId);
        updRole.setUpdateTime(new Date());
        updRole.setDeleted(0);

        // 删除角色
        int i = roleMapper.updateByPrimaryKeySelective(updRole);
        CoderUtil.crudIsSuccess(i);

        // 删除角色和权限关联表数据
        rolePermissionService.removePermissionByRoleId(roleId);

        // 获取和当前角色关联的用户信息
        List<String> roleIds = new ArrayList<>();
        roleIds.add(roleId);
        List<String> userIds = userRoleService.findUserIdsByRoleIds(roleIds);
        if (!CoderUtil.validArrayIsEmpty(userIds)) {
            /**
             * 标记用户，主动刷新token
             */
            for (String userId : userIds) {
                redisService.set(Constant.JWT_REFRESH_KEY + userId, userId, tokenSettings.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
                /**
                 * 清除用户授权缓存
                 */
                redisService.delete(Constant.IDENTIFY_CACHE_KEY + userId);
            }
        }

        // 删除用户角色关联表数据
        userRoleService.removeByRoleId(roleId);
    }

    /**
     * 递归遍历菜单树
     *
     * @param list
     * @param checkList
     */
    private void getOwnTreeModel(List<MenuTreeVO> list, Set<String> checkList) {

        for (MenuTreeVO node : list) {
            /**
             * 子集选中从它往上到跟目录都被选中，父级选中从它到它所有的叶子节点都会被选中
             * 这样我们就直接遍历最底层及就可以了
             */
            if (checkList.contains(node.getId()) && (node.getChildren() == null || node.getChildren().isEmpty())) {
                node.setChecked(true);
            }
            getOwnTreeModel((List<MenuTreeVO>) node.getChildren(), checkList);

        }
    }
}
