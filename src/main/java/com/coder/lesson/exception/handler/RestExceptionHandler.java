package com.coder.lesson.exception.handler;

import com.coder.lesson.exception.BusinessException;
import com.coder.lesson.exception.code.BaseResponseCode;
import com.coder.lesson.utils.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @类名 RestExceptionHandler
 * @描述 全局异常捕获，作用于controller层，无需对代码进行try/catch就可以捕获异常
 * @创建人 张全蛋
 * @创建日期 2020/2/19 1:28
 * @版本 1.0
 **/
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public DataResult exception(Exception e) {
        log.error("Exception,{},{}", e.getLocalizedMessage(), e);
        return DataResult.getResult(BaseResponseCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = BusinessException.class)
    public DataResult businessException(BusinessException e) {
        log.error("businessException,{},{}", e.getLocalizedMessage(), e);
        return DataResult.getResult(e.getCode(), e.getDefaultMessage());
    }

    /**
     * 处理validation 框架异常
     *
     * @param e
     * @return com.coder.lesson.utils.DataResult<T>
     * @throws
     * @Author: 张全蛋
     * @UpdateUser:
     * @Version: 0.0.1
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    <T> DataResult<T> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {

        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}", e.getBindingResult().getAllErrors(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return createValidExceptionResp(errors);
    }

    /**
     * 数据验证异常
     *
     * @param errors
     * @param <T>
     * @return
     */
    private <T> DataResult<T> createValidExceptionResp(List<ObjectError> errors) {
        String[] msgs = new String[errors.size()];
        int i = 0;
        for (ObjectError error : errors) {
            msgs[i] = error.getDefaultMessage();
            log.info("msg={}", msgs[i]);
            i++;
        }
        return DataResult.getResult(BaseResponseCode.METHOD_IDENTITY_ERROR.getCode(), msgs[0]);
    }

    /**
     * 无权限异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public DataResult unauthorizedException(UnauthorizedException e) {
        log.error("UnauthorizedException,{},{}", e.getLocalizedMessage(), e);
        return DataResult.getResult(BaseResponseCode.NOT_PERMISSION);
    }
}
