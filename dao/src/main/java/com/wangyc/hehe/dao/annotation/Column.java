package com.wangyc.hehe.dao.annotation;

import java.lang.annotation.*;

/**
 * Describe: Column
 * Date: 2021/1/25
 * Time: 5:19 下午
 * Author: wangyc
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    String value() default "";
}