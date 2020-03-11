package com.coder.lesson.aop.annotation;

import java.lang.annotation.*;

/**
 * @类名 MyLog
 * @描述 自定义注解类
 * @创建人 张全蛋
 * @创建日期 2020/2/22 16:31
 * @版本 1.0
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog {
    /**
     * 用户操作哪个模块
     */
    String title() default "";

    /**
     * 记录用户操作的动作
     */
    String action() default "";
}
