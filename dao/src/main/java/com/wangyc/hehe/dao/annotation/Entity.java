package com.wangyc.hehe.dao.annotation;

import java.lang.annotation.*;

/**
 * Describe: Column
 * Date: 2021/1/25
 * Time: 5:19 下午
 * Author: wangyc
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Entity {
    Class value();
}