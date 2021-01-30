package com.wangyc.hehe.dao.provider.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Describe: BaseDaoTest
 * Date: 2021/1/30
 * Time: 9:04 下午
 * Author: wangyc
 */
public class BaseDaoTest extends BaseTest{
    @Autowired
    private StudentDao studentDao;
    @Test
    public void a(){
        StudentDao.Student byPrimaryKey = studentDao.getByPrimaryKey(1L);
    }

}
