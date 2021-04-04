package com.wangyc.hehe.forum.config;

import com.wangyc.hehe.forum.entity.Topic;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Describe: MyHandlerExceptionResolver
 * Date: 2021/2/8
 * Time: 11:23 上午
 * Author: wangyc
 */
@ControllerAdvice
public class SystemExceptionHandler  {
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object accountExceptionHandler(Exception ex) {
        return new Topic();
    }
}
