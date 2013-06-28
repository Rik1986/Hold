package com.gs7.web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author yongbiaoli
 * 
 *         出错后先redirectUrl，如果没有再loginFailClass，还没有就执行默认
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
    /**
     * 是否需要登陆
     * 
     * @return
     */
    boolean need();

    /**
     * 跳转的url
     * 
     * @return
     */
    String redirectUrl() default "";

    /**
     * 登陆失败的后续处理
     * 
     * @return
     */
    Class<? extends LoginFailInterface>[] loginFailClass() default {};
}