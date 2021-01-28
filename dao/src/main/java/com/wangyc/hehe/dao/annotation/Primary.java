package com.wangyc.hehe.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describe: Primary
 * Date: 2021/1/25
 * Time: 3:37 下午
 * Author: wangyc
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
//TODO 如果让Primary继承Column
public @interface Primary  {

}
