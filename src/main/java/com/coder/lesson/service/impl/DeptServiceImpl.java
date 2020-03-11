package com.coder.lesson.service.impl;

import com.coder.lesson.constants.Constant;
import com.coder.lesson.entity.Dept;
import com.coder.lesson.entity.User;
import com.coder.lesson.exception.BusinessException;
import com.coder.lesson.exception.code.BaseResponseCode;
import com.coder.lesson.mapper.DeptMapper;
import com.coder.lesson.service.DeptService;
import com.coder.lesson.service.RedisService;
import com.coder.lesson.service.UserService;
import com.coder.lesson.utils.CodeUtil;
import com.coder.lesson.utils.CoderUtil;
import com.coder.lesson.vo.req.DeptUpdateReqVO;
import com.coder.lesson.vo.resp.DeptTreeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @类名 DeptServiceImpl
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/20 19:54
 * @版本 1.0
 **/
@Service
@Slf4j
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;

    @Override
    public List<DeptTreeVO> deptTreeModel(String deptId) {
        List<Dept> depts = deptMapper.selectAll();
        if (!CoderUtil.validArrayIsEmpty(depts)) {
            for (Dept dept : depts) {
                if (dept.getId().equals(deptId)) {
                    depts.remove(dept);
                    break;
                }
            }
        }
        List<DeptTreeVO> vos = new ArrayList<>();
        // 自定义根节点
        DeptTreeVO vo = new DeptTreeVO();
        vo.setId("0");
        vo.setTitle("默认顶级部门");
        vo.setChildren(getDeptTreeModel(depts));
        vos.add(vo);
        return vos;
    }

    @Override
    public List<Dept> findAllDepts() {
        List<Dept> depts = deptMapper.selectAll();
        if (!CoderUtil.validArrayIsEmpty(depts)) {
            for (Dept dept : depts) {
                Dept parent = deptMapper.selectByPrimaryKey(dept.getPid());
                if (parent != null) {
                    dept.setPidName(parent.getName());
                }
            }
        }
        return depts;
    }

    @Override
    public Dept saveDept(Dept dept) {
        dept.setCreateTime(new Date());
        dept.setId(UUID.randomUUID().toString());
        dept.setDeleted(1);

        String relationCode;
        long deptCount = redisService.incrby(Constant.DEPT_CODE_KEY, 1);
        String deptNo = CodeUtil.deptCode(String.valueOf(deptCount), 7, "0");
        dept.setDeptNo(deptNo);

        if (dept.getPid().equals("0")) {
            relationCode = deptNo;
        } else {
            Dept parent = deptMapper.selectByPrimaryKey(dept.getPid());
            if (parent == null) {
                log.info("父级数据不存在{}", dept.getPid());
                throw new BusinessException(BaseResponseCode.DATA_ERROR);
            }
            relationCode = parent.getRelationCode() + deptNo;
        }
        dept.setRelationCode(relationCode);

        int i = deptMapper.insertSelective(dept);
        if (i != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        return dept;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyDeptById(DeptUpdateReqVO vo) {
        //保存更新的部门数据
        Dept returnDept = deptMapper.selectByPrimaryKey(vo.getId());
        if (null == returnDept) {
            log.error("传入 的 id:{}不合法", vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        Dept update = new Dept();
        BeanUtils.copyProperties(vo, update);
        update.setUpdateTime(new Date());
        int count = deptMapper.updateByPrimaryKeySelective(update);
        CoderUtil.crudIsSuccess(count);

        // 维护层级关系
        if (!vo.getPid().equals(returnDept.getPid())) {
            // 子集的部门层级关系编码=父级部门层级关系编码+它本身部门编码
            Dept newParent = deptMapper.selectByPrimaryKey(vo.getPid());
            if (!vo.getPid().equals("0") && null == newParent) {
                log.info("修改后的部门在数据库查找不到{}", vo.getPid());
                throw new BusinessException(BaseResponseCode.DATA_ERROR);
            }
            Dept oldParent = deptMapper.selectByPrimaryKey(returnDept.getPid());
            String oleRelation;
            String newRelation;
            /**
             * 根目录挂靠到其它目录
             */
            if (returnDept.getPid().equals("0")) {
                oleRelation = returnDept.getRelationCode();
                newRelation = newParent.getRelationCode() + returnDept.getDeptNo();
            } else if (vo.getPid().equals("0")) {
                oleRelation = returnDept.getRelationCode();
                newRelation = returnDept.getDeptNo();
            } else {
                oleRelation = oldParent.getRelationCode();
                newRelation = newParent.getRelationCode();
            }
            int i = deptMapper.updateRelationCode(oleRelation, newRelation, returnDept.getRelationCode());
            CoderUtil.crudIsSuccess(i);
        }
    }

    @Override
    public void removeDeptById(String deptId) {
        Dept dept = deptMapper.selectByPrimaryKey(deptId);
        if (dept == null) {
            log.info("传入的部门id在数据库不存在{}", deptId);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        List<String> list = deptMapper.selectChildIds(dept.getRelationCode());
        //判断它和它子集的叶子节点是否关联有用户
        List<User> users = userService.findUsersByDeptIds(list);
        if (!CoderUtil.validArrayIsEmpty(users)) {
            throw new BusinessException(BaseResponseCode.NOT_PERMISSION_DELETED_DEPT);
        }

        //逻辑删除部门数据
        int count = deptMapper.batchUpdateDepts(list);
        CoderUtil.crudIsSuccess(count);
    }

    /**
     * 部门数据递归
     *
     * @param depts
     * @return
     */
    private List<DeptTreeVO> getDeptTreeModel(List<Dept> depts) {
        List<DeptTreeVO> deptTreeVO = new ArrayList<>();
        if (!depts.isEmpty() && depts.size() != 0) {
            for (Dept dept : depts) {
                if ("0".equals(dept.getPid())) {
                    DeptTreeVO vo = new DeptTreeVO();
                    BeanUtils.copyProperties(dept, vo);
                    vo.setTitle(dept.getName());
                    vo.setChildren(getTreeChildren(dept.getId(), depts));
                    deptTreeVO.add(vo);
                }
            }
        }
        return deptTreeVO;
    }

    private List<DeptTreeVO> getTreeChildren(String pid, List<Dept> depts) {
        List<DeptTreeVO> deptTreeVO = new ArrayList<>();
        for (Dept dept : depts) {
            if (dept.getPid().equals(pid)) {
                DeptTreeVO vo = new DeptTreeVO();
                BeanUtils.copyProperties(dept, vo);
                vo.setTitle(dept.getName());
                vo.setChildren(getTreeChildren(dept.getId(), depts));
                deptTreeVO.add(vo);
            }
        }
        return deptTreeVO;
    }
}
