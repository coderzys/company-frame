package com.coder.lesson.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.coder.lesson.aop.annotation.MyLog;
import com.coder.lesson.constants.Constant;
import com.coder.lesson.entity.Log;
import com.coder.lesson.service.LogService;
import com.coder.lesson.utils.HttpContextUtils;
import com.coder.lesson.utils.IPUtil;
import com.coder.lesson.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

/**
 * @类名 SysLogAspect
 * @描述 系统日志切面类
 * @创建人 张全蛋
 * @创建日期 2020/2/22 16:34
 * @版本 1.0
 **/
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    @Autowired
    private LogService logService;

    /**
     * 配置织入点(以@MyLog注解为标志)
     * 只要出现 @MyLog注解都会进入
     */
    @Pointcut("@annotation(com.coder.lesson.aop.annotation.MyLog)")
    public void logPointCut() {

    }

    /**
     * 环绕增强
     *
     * @param point
     * @return Object
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        try {
            saveSysLog(point, time);
        } catch (Exception e) {
            log.error("e={}", e);
        }

        return result;
    }

    /**
     * 保存日志
     *
     * @param joinPoint
     * @param time
     */
    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Log sysLog = new Log();
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
            //注解上的描述
            sysLog.setOperation(myLog.title() + "-" + myLog.action());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        //打印该方法耗时时间
        log.info("请求{}.{}耗时{}毫秒", className, methodName, time);
        try {
            //请求的参数
            Object[] args = joinPoint.getArgs();
            String params = null;
            if (args.length != 0) {
                params = JSON.toJSONString(args);
            }

            sysLog.setParams(params);
        } catch (Exception e) {

        }
        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(IPUtil.getIpAddr(request));
        log.info("Ip{}，接口地址{}，请求方式{}，入参：{}", sysLog.getIp(), request.getRequestURL(), request.getMethod(), sysLog.getParams());
        //用户名
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        String userId = JwtTokenUtil.getUserId(token);
        String username = JwtTokenUtil.getUserName(token);
        sysLog.setUsername(username);
        sysLog.setUserId(userId);
        sysLog.setTime((int) time);
        sysLog.setId(UUID.randomUUID().toString());
        sysLog.setCreateTime(new Date());
        log.info(sysLog.toString());
        logService.saveLog(sysLog);

    }
}
