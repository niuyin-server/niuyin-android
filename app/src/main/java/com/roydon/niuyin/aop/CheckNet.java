package com.roydon.niuyin.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *    desc   : 检测网络注解
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface CheckNet {}