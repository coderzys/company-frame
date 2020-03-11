package com.coder.lesson.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Permission implements Serializable {
    private String id;

    private String code;

    private String name;

    private String perms;

    private String url;

    private String method;

    private String pid;

    private Integer orderNum;

    private Integer type;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    private String pidName;

    private static final long serialVersionUID = 1L;

}