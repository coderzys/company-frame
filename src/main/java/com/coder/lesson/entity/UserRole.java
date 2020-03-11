package com.coder.lesson.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserRole implements Serializable {
    private String id;

    private String userId;

    private String roleId;

    private Date createTime;

    private static final long serialVersionUID = 1L;

}