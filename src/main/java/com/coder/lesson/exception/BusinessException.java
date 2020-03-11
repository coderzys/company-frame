package com.coder.lesson.exception;

import com.coder.lesson.exception.code.BaseResponseCode;

/**
 * @类名 BusinessException
 * @描述 自定义异常返回消息
 * @创建人 张全蛋
 * @创建日期 2020/2/18 20:55
 * @版本 1.0
 **/
public class BusinessException extends RuntimeException {

    /**
     *  异常代码
     */
    private final int code;

    /**
     *  异常提示
     */
    private final String defaultMessage;

    public BusinessException(int code, String defaultMessage) {
        super(defaultMessage);
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public BusinessException(BaseResponseCode baseResponseCode) {
        this.code = baseResponseCode.getCode();
        this.defaultMessage = baseResponseCode.getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
