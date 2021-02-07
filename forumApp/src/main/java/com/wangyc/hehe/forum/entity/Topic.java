package com.wangyc.hehe.forum.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Describe: Topic
 * Date: 2021/2/7
 * Time: 2:23 下午
 * Author: wangyc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic implements Serializable {
    private Long id;
    private String title;
    private String content;
    private Date ctime;
    private Date utime;
}
