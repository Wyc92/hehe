package com.wangyc.hehe.dao.provider.test;

import com.wangyc.hehe.dao.BaseDao;
import com.wangyc.hehe.dao.annotation.Primary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Describe: StudentDap
 * Date: 2021/1/30
 * Time: 9:08 下午
 * Author: wangyc
 */
public interface StudentDao extends BaseDao<StudentDao.Student,Long> {

    @Select("select * from student")
    List<Student> list();

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
