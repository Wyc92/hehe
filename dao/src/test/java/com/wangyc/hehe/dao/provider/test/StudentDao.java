package com.wangyc.hehe.dao.provider.test;

import com.wangyc.hehe.dao.BaseDao;
import com.wangyc.hehe.dao.annotation.Entity;
import com.wangyc.hehe.dao.annotation.Primary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describe: StudentDap
 * Date: 2021/1/30
 * Time: 9:08 下午
 * Author: wangyc
 */
@Entity(StudentDao.Student.class)
public interface StudentDao extends BaseDao<StudentDao.Student,Long> {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor

    public static class Student{
        @Primary
        private Long id;
        private String username;
    }
}
