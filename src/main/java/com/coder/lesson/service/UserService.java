package com.coder.lesson.service;

import com.coder.lesson.entity.User;
import com.coder.lesson.vo.req.*;
import com.coder.lesson.vo.resp.LoginRespVO;
import com.coder.lesson.vo.resp.PageRespVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @接口名 UserService
 * @描述 用户业务接口
 * @创建人 张全蛋
 * @创建日期 2020/2/19 14:07
 * @版本 1.0
 **/
public interface UserService {

    public LoginRespVO login(LoginReqVO vo);

    public void logout(String accessToken, String refreshToken);

    public PageRespVO findAllUsers(UserPageReqVO vo);

    User saveUser(UserAddReqVO vo, HttpServletRequest request);

    String refreshToken(String refreshToken);

    void modifyUser(UserUpdateReqVO vo, HttpServletRequest request);

    void deleteUser(List<String> ids, HttpServletRequest request);

    List<User> findUsersByDeptIds(List<String> deptIds);

    User findUserInfo(HttpServletRequest request);

    void modifyUserInfo(User user);

    void modifyUserPwd(UserUpdatePwdReqVO vo,String accessToken,String refreshToken);
}
