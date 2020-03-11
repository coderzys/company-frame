package com.coder.lesson.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @类名 HttpContextUtils
 * @描述 TODO http上下文
 * @创建人 张全蛋
 * @创建日期 2020/2/22 16:42
 * @版本 1.0
 **/
public class HttpContextUtils {

	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
}
