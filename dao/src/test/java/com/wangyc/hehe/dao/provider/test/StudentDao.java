package com.wangyc.hehe.dao.provider.test;

import com.wangyc.hehe.dao.BaseDao;
import com.wangyc.hehe.dao.annotation.Primary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describe: StudentDap
 * Date: 2021/1/30
 * Time: 9:08 下午
 * Author: wangyc
 */
public interface StudentDao extends BaseDao<StudentDao.Student,Long> {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Student{
        @Primary
        private Long id;
        private String username;
        private String password;
        private String email;
        private String gender;
    }
}
