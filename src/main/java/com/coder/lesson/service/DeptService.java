package com.coder.lesson.service;

import com.coder.lesson.entity.Dept;
import com.coder.lesson.vo.req.DeptUpdateReqVO;
import com.coder.lesson.vo.resp.DeptTreeVO;

import java.util.List;

/**
 * @类名 DeptService
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/20 19:53
 * @版本 1.0
 **/
public interface DeptService {

    List<DeptTreeVO> deptTreeModel(String deptId);

    List<Dept> findAllDepts();

    Dept saveDept(Dept dept);

    void modifyDeptById(DeptUpdateReqVO vo);

    void removeDeptById(String deptId);
}
