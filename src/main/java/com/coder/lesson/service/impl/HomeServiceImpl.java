package com.coder.lesson.service.impl;

import com.alibaba.fastjson.JSON;
import com.coder.lesson.entity.User;
import com.coder.lesson.mapper.PermissionMapper;
import com.coder.lesson.mapper.UserMapper;
import com.coder.lesson.service.HomeService;
import com.coder.lesson.service.PermissionService;
import com.coder.lesson.vo.resp.HomeRespVO;
import com.coder.lesson.vo.resp.MenuTreeVO;
import com.coder.lesson.vo.resp.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @类名 HomeServiceImpl
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/19 20:16
 * @版本 1.0
 **/
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    @Lazy
    private PermissionService permissionService;

    @Override
    public HomeRespVO findHomeData(String userId) {
        HomeRespVO vo = new HomeRespVO();
        List<MenuTreeVO> model = permissionService.findMenuTreeModel(userId, 2);
        vo.setMenus(model);

        UserInfoVO user = userMapper.selectByUserId(userId);
        vo.setUserInfo(user);
        return vo;
    }
}
