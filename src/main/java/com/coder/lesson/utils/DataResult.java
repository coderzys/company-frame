package com.coder.lesson.utils;

import com.coder.lesson.exception.code.BaseResponseCode;
import com.coder.lesson.exception.code.ResponseCodeInterface;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @类名 DataResult
 * @描述 数据响应模型
 * @创建人 张全蛋
 * @创建日期 2020/2/19 0:58
 * @版本 1.0
 **/
@Data
public class DataResult<T> {

    /**
     * 请求响应编码，0-成功，其它为失败
     */
    @ApiModelProperty(value = "请求响应编码，0-成功，其它为失败", name = "code")
    private int code;
    /**
     * 请求响应到客户端的提示
     */
    @ApiModelProperty(value = "请求响应到客户端的提示", name = "message")
    private String message;
    /**
     * 请求响应的数据
     */
    @ApiModelProperty(value = "请求响应的数据", name = "data")
    private T data;

    public DataResult(int code, T data) {
        this.code = code;
        this.data = data;
        this.message = null;
    }

    public DataResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public DataResult(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public DataResult() {
        this.code = BaseResponseCode.SUCCESS.getCode();
        this.message = BaseResponseCode.SUCCESS.getMessage();
        this.data = null;
    }

    public DataResult(T data) {
        this.data = data;
        this.code = BaseResponseCode.SUCCESS.getCode();
        this.message = BaseResponseCode.SUCCESS.getMessage();
    }

    public DataResult(ResponseCodeInterface responseCodeInterface) {
        this.data = null;
        this.code = responseCodeInterface.getCode();
        this.message = responseCodeInterface.getMessage();
    }

    public DataResult(ResponseCodeInterface responseCodeInterface, T data) {
        this.data = data;
        this.code = responseCodeInterface.getCode();
        this.message = responseCodeInterface.getMessage();
    }

    /**
     * 操作成功 data为null
     *
     * @param
     * @return com.xh.lesson.utils.DataResult<T>
     * @throws
     * @Author: 张全蛋
     * @UpdateUser:
     * @Version: 0.0.1
     */
    public static <T> DataResult success() {
        return new <T>DataResult();
    }

    /**
     * 操作成功 data 不为null
     *
     * @param data
     * @return com.xh.lesson.utils.DataResult<T>
     * @throws
     * @Author: 张全蛋
     * @UpdateUser:
     * @Version: 0.0.1
     */
    public static <T> DataResult success(T data) {
        return new <T>DataResult(data);
    }

    /**
     * 自定义 返回操作 data 可控
     *
     * @param code
     * @param message
     * @param data
     * @return com.xh.lesson.utils.DataResult
     * @throws
     * @Author: 张全蛋
     * @UpdateUser:
     * @Version: 0.0.1
     */
    public static <T> DataResult getResult(int code, String message, T data) {
        return new <T>DataResult(code, message, data);
    }

    /**
     * 自定义返回  data为null
     *
     * @param code
     * @param message
     * @return com.xh.lesson.utils.DataResult
     * @throws
     * @Author: 张全蛋
     * @UpdateUser:
     * @Version: 0.0.1
     */
    public static <T> DataResult getResult(int code, String message) {
        return new <T>DataResult(code, message);
    }

    /**
     * 自定义返回 入参一般是异常code枚举 data为空
     *
     * @param responseCode
     * @return com.xh.lesson.utils.DataResult
     * @throws
     * @Author: 张全蛋
     * @UpdateUser:
     * @Version: 0.0.1
     */
    public static <T> DataResult getResult(BaseResponseCode responseCode) {
        return new <T>DataResult(responseCode);
    }

    /**
     * 自定义返回 入参一般是异常code枚举 data 可控
     *
     * @param responseCode
     * @param data
     * @return com.xh.lesson.utils.DataResult
     * @throws
     * @Author: 张全蛋
     * @UpdateUser:
     * @Version: 0.0.1
     */
    public static <T> DataResult getResult(BaseResponseCode responseCode, T data) {

        return new <T>DataResult(responseCode, data);
    }

}
