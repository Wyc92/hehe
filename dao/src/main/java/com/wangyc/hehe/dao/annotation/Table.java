package com.wangyc.hehe.dao.annotation;

import java.lang.annotation.*;

/**
 * Describe: Table
 * Date: 2021/1/25
 * Time: 5:08 下午
 * Author: wangyc
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    String value() default "";
}