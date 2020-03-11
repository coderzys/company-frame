package com.coder.lesson.service;

import com.coder.lesson.entity.Log;
import com.coder.lesson.vo.req.LogPageReqVO;
import com.coder.lesson.vo.resp.PageRespVO;

import java.util.List;

/**
 * @类名 LogService
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/22 16:45
 * @版本 1.0
 **/
public interface LogService {

    int saveLog(Log log);

    PageRespVO<Log> findAllLogsByPage(LogPageReqVO vo);

    int removeLogByIds(List<String> ids);
}
