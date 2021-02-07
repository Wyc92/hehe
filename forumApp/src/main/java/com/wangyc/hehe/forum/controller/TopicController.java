package com.wangyc.hehe.forum.controller;

import com.wangyc.hehe.forum.entity.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * No converter found for return value of type 需要get set方法
 * Describe: TopicController
 * Date: 2021/2/7
 * Time: 2:20 下午
 * Author: wangyc
 */
@Slf4j
@RequestMapping("/topic")
@RestController
public class TopicController {
    @PostMapping(value = "/post")
    public Object haha(@RequestBody Topic topic) {
        return topic;
    }
}
