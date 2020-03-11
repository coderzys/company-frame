package com.coder.lesson.service;

import com.coder.lesson.vo.resp.HomeRespVO;

/**
 * @接口名 HomeService
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/19 20:15
 * @版本 1.0
 **/
public interface HomeService {

    HomeRespVO findHomeData(String userId);

}
