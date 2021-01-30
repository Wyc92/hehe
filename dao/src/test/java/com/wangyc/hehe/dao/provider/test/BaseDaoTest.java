package com.wangyc.hehe.dao.provider.test;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Describe: BaseDaoTest
 * Date: 2021/1/30
 * Time: 9:04 下午
 * Author: wangyc
 */
public class BaseDaoTest extends BaseTest {
    @Autowired
    private StudentDao studentDao;

    @Test
    public void findByEntity() {
        List<StudentDao.Student> byEntity1 = studentDao.findByEntity(StudentDao.Student.builder().id(1L).build());
        List<StudentDao.Student> byEntity2 = studentDao.findByEntity(StudentDao.Student.builder().id(3L).build());
        List<StudentDao.Student> byEntity3 = studentDao.findByEntity(StudentDao.Student.builder().build());
        List<StudentDao.Student> byEntity4 = studentDao.findByEntity(StudentDao.Student.builder().id(1L).username("王玉成").build());
        assert byEntity1.size() == 1;
        assert CollectionUtils.isEmpty(byEntity2);
        assert byEntity3.size() == 2;
        assert byEntity4.size() == 1;
    }

    @Test
    public void getByEntity() {
        StudentDao.Student byEntity1 = studentDao.getByEntity(StudentDao.Student.builder().build());
        StudentDao.Student byEntity2 = studentDao.getByEntity(StudentDao.Student.builder().id(2L).build());
        StudentDao.Student byEntity3 = studentDao.getByEntity(StudentDao.Student.builder().id(3L).build());
        assert null != byEntity1;
        assert null != byEntity2;
        assert null == byEntity3;
    }

    @Test
    public void getByPrimaryKey() {
        StudentDao.Student byPrimaryKey1 = studentDao.getByPrimaryKey(1L);
        StudentDao.Student byPrimaryKey2 = studentDao.getByPrimaryKey(2L);
        StudentDao.Student byPrimaryKey3 = studentDao.getByPrimaryKey(3L);
        assert null != byPrimaryKey1;
        assert null != byPrimaryKey2;
        assert null == byPrimaryKey3;
    }

    @Test
    public void insertIgnoreNull() {
        Long id = randomLong();
        StudentDao.Student byPrimaryKey = studentDao.getByPrimaryKey(id);
        studentDao.insertIgnoreNull(StudentDao.Student.builder().id(id).username("uuu").password("1").build());
        StudentDao.Student byPrimaryKey1 = studentDao.getByPrimaryKey(id);
        assert null == byPrimaryKey;
        assert null != byPrimaryKey1;
        assert id.equals(byPrimaryKey1.getId()) && "uuu".equals(byPrimaryKey1.getUsername());
        assert "nan".equals(byPrimaryKey1.getGender());
    }

    @Test
    public void insertNoIgnoreNull() {
        Long id = randomLong();
        StudentDao.Student byPrimaryKey = studentDao.getByPrimaryKey(id);
        studentDao.insertNoIgnoreNull(StudentDao.Student.builder().id(id).username("uuu").password("1").build());
        StudentDao.Student byPrimaryKey1 = studentDao.getByPrimaryKey(id);
        assert null == byPrimaryKey;
        assert null != byPrimaryKey1;
        assert id.equals(byPrimaryKey1.getId()) && "uuu".equals(byPrimaryKey1.getUsername());
        assert null == byPrimaryKey1.getGender();
    }


    /**
    //@Test
    public void updateByPrimaryIgnoreNull() {
        //TODO 好像参数只能是一个
        //org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.binding.BindingException: Parameter 'id' not found. Available parameters are [arg1, arg0, param1, param2]
        StudentDao.Student byPrimaryKey1 = studentDao.getByPrimaryKey(1L);
        byPrimaryKey1.setEmail("123qweasd");
        studentDao.updateByPrimaryIgnoreNull(1L,byPrimaryKey1);
        StudentDao.Student byPrimaryKey2 = studentDao.getByPrimaryKey(1L);
        assert "王玉成".equals(byPrimaryKey2.getUsername());
        assert "123qweasd".equals(byPrimaryKey2.getPassword());

    }

    //@Test
    public void updateByPrimaryNoIgnoreNull() {
        //TODO 好像参数只能是一个
        //org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.binding.BindingException: Parameter 'id' not found. Available parameters are [arg1, arg0, param1, param2]
        StudentDao.Student byPrimaryKey1 = studentDao.getByPrimaryKey(1L);
        byPrimaryKey1.setEmail("123qweasd");
        studentDao.updateByPrimaryNoIgnoreNull(1L,byPrimaryKey1);
        StudentDao.Student byPrimaryKey2 = studentDao.getByPrimaryKey(1L);
        assert "王玉成".equals(byPrimaryKey2.getUsername());
        assert "123qweasd".equals(byPrimaryKey2.getPassword());

    }
    **/
    @Test
    public void deleteByPrimaryKey(){
        List<StudentDao.Student> byEntity1 = studentDao.findByEntity(StudentDao.Student.builder().build());
        studentDao.deleteByPrimaryKey(1L);
        studentDao.deleteByPrimaryKey(3L);
        List<StudentDao.Student> byEntity2 = studentDao.findByEntity(StudentDao.Student.builder().build());
        studentDao.deleteByPrimaryKey(2L);
        List<StudentDao.Student> byEntity3 = studentDao.findByEntity(StudentDao.Student.builder().build());
        assert byEntity1.size()==2;
        assert byEntity2.size()==1;
        assert byEntity3.size()==0;
    }

    @Test
    public void deleteByEntity(){
        List<StudentDao.Student> byEntity1 = studentDao.findByEntity(StudentDao.Student.builder().build());

        studentDao.deleteByEntity(StudentDao.Student.builder().id(3L).build());
        List<StudentDao.Student> byEntity2 = studentDao.findByEntity(StudentDao.Student.builder().build());

        studentDao.deleteByEntity(StudentDao.Student.builder().build());
        List<StudentDao.Student> byEntity3 = studentDao.findByEntity(StudentDao.Student.builder().build());


        assert byEntity1.size()==2;
        assert byEntity2.size()==2;
        assert byEntity3.size()==0;
    }


    public static Long randomLong() {
        return System.currentTimeMillis() % 10000L;
    }


}
