package com.coder.lesson.entity;

import com.coder.lesson.vo.resp.MenuTreeVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Role implements Serializable {
    private String id;

    private String name;

    private String description;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    private List<MenuTreeVO> menuTreeModel;

    private static final long serialVersionUID = 1L;

}