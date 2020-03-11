package com.coder.lesson.shiro;

import com.alibaba.fastjson.JSON;
import com.coder.lesson.constants.Constant;
import com.coder.lesson.exception.BusinessException;
import com.coder.lesson.exception.code.BaseResponseCode;
import com.coder.lesson.utils.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @类名 CustomAccessControllerFilter
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/19 16:00
 * @版本 1.0
 **/
@Slf4j
public class CustomAccessControllerFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info(request.getMethod());
        log.info(request.getRequestURI());

        try {
            // 校验token
            String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
            if (StringUtils.isEmpty(accessToken)) {
                throw new BusinessException(BaseResponseCode.TOKEN_NOT_NULL);
            }
            // 交由shiro安全管理器进行token校验
            CustomUsernamePasswordToken token = new CustomUsernamePasswordToken(accessToken);
            getSubject(servletRequest, servletResponse).login(token);
        } catch (BusinessException e) {
            customResponse(e.getCode(), e.getDefaultMessage(), servletResponse);
            return false;
        } catch (AuthenticationException e) {
            if (e.getCause() instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e.getCause();
                customResponse(businessException.getCode(), businessException.getDefaultMessage(), servletResponse);
            } else {
                customResponse(BaseResponseCode.SHIRO_AUTHENTICATION_ERROR.getCode(), BaseResponseCode.SHIRO_AUTHENTICATION_ERROR.getMessage(), servletResponse);
            }
            return false;
        }
        return true;
    }

    /**
     * 自定义错误响应
     *
     * @param code
     * @param msg
     * @param response
     */
    private void customResponse(int code, String msg, ServletResponse response) {
        // 自定义异常的类，用户返回给客户端相应的JSON格式的信息
        try {
            DataResult result = DataResult.getResult(code, msg);
            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");

            String userJson = JSON.toJSONString(result);
            OutputStream out = response.getOutputStream();
            out.write(userJson.getBytes("UTF-8"));
            out.flush();
        } catch (IOException e) {
            log.error("eror={}", e);
        }
    }
}
