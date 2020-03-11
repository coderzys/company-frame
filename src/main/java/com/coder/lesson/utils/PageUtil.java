package com.coder.lesson.utils;

import com.coder.lesson.vo.resp.PageRespVO;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @类名 PageUtil
 * @描述 数据分页工具类
 * @创建人 张全蛋
 * @创建日期 2020/2/19 15:16
 * @版本 1.0
 **/
public class PageUtil {
    private PageUtil() {
    }

    public static <T> PageRespVO<T> getPageVO(List<T> list) {
        PageRespVO<T> result = new PageRespVO<>();
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            result.setTotalRows(page.getTotal());
            result.setTotalPages(page.getPages());
            result.setPageNum(page.getPageNum());
            result.setCurPageSize(page.getPageSize());
            result.setPageSize(page.size());
            result.setList(page.getResult());
        }
        return result;
    }
}
