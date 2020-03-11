package com.coder.lesson.service.impl;

import com.coder.lesson.entity.Log;
import com.coder.lesson.mapper.LogMapper;
import com.coder.lesson.service.LogService;
import com.coder.lesson.utils.CoderUtil;
import com.coder.lesson.utils.PageUtil;
import com.coder.lesson.vo.req.LogPageReqVO;
import com.coder.lesson.vo.resp.PageRespVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @类名 LogServiceImpl
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/22 16:46
 * @版本 1.0
 **/
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public int saveLog(Log log) {
        return logMapper.insertSelective(log);
    }

    @Override
    public PageRespVO<Log> findAllLogsByPage(LogPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<Log> logs = logMapper.selectAll(vo);
        return PageUtil.getPageVO(logs);
    }

    @Override
    public int removeLogByIds(List<String> ids) {
        int res = 0;
        if (!CoderUtil.validArrayIsEmpty(ids)) {
            res = logMapper.deleteByIds(ids);
            CoderUtil.crudIsSuccess(res);
        }
        return res;
    }
}
