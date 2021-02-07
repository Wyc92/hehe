package com.wangyc.hehe.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Describe: ForumApp
 * 这个配置类需要不能放在default下
 * ** WARNING ** : Your ApplicationContext is unlikely to start due to a @ComponentScan of the default package
 * Date: 2021/2/7
 * Time: 2:03 下午
 * Author: wangyc
 */
@SpringBootApplication
public class ForumApp {
    public static void main(String[] args) {
        SpringApplication.run(ForumApp.class, args);
    }
}
