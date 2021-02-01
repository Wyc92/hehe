package com.wangyc.hehe.dao.provider.test;

import com.wangyc.hehe.dao.PageResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
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

    @Before
    public void before() {
        studentDao.deleteByEntity(StudentDao.Student.builder().build());
        assert CollectionUtils.isEmpty(studentDao.findByEntity(StudentDao.Student.builder().build()));

        studentDao.insertIgnoreNull(StudentDao.Student.builder().id(1L).username("username1").password("passowrd1").email("email1").gender("male").build());
        studentDao.insertIgnoreNull(StudentDao.Student.builder().id(2L).username("username2").password("passowrd2").email("email2").gender("male").build());
        studentDao.insertIgnoreNull(StudentDao.Student.builder().id(3L).username("username3").password("passowrd3").email("email3").gender("male").build());
        studentDao.insertIgnoreNull(StudentDao.Student.builder().id(4L).username("username4").password("passowrd4").email("email4").gender("female").build());
        studentDao.insertIgnoreNull(StudentDao.Student.builder().id(5L).username("username5").password("passowrd5").email("email5").gender("female").build());

        assert studentDao.findByEntity(StudentDao.Student.builder().build()).size() == 5;
    }

    @Test
    public void findByEntity() {
        List<StudentDao.Student> byEntity1 = studentDao.findByEntity(StudentDao.Student.builder().id(1L).build());
        List<StudentDao.Student> byEntity2 = studentDao.findByEntity(StudentDao.Student.builder().gender("male").build());
        List<StudentDao.Student> byEntity3 = studentDao.findByEntity(StudentDao.Student.builder().gender("male").id(2L).build());
        List<StudentDao.Student> byEntity4 = studentDao.findByEntity(StudentDao.Student.builder().build());
        List<StudentDao.Student> byEntity5 = studentDao.findByEntity(StudentDao.Student.builder().id(100L).build());

        assert byEntity1.size() == 1;
        assert byEntity2.size() == 3;
        assert byEntity3.size() == 1;
        assert byEntity4.size() == 5;
        assert CollectionUtils.isEmpty(byEntity5);
    }

    //@Test
    public void findByEntityWithPage() {
        //List<StudentDao.Student> byEntity = studentDao.findByEntity(StudentDao.Student.builder().id(1L).build());
        //assert byEntity.size()==1;
        //TODO
        //PageResponse<StudentDao.Student> byEntityWithPage = studentDao.findByEntityWithPage(StudentDao.Student.builder().id(1L).build(), 1L,10L);
        //assert null !=byEntityWithPage;
    }

    @Test
    public void getByEntity() {
        StudentDao.Student byEntity1 = studentDao.getByEntity(StudentDao.Student.builder().id(1L).build());
        StudentDao.Student byEntity2 = studentDao.getByEntity(StudentDao.Student.builder().gender("male").build());
        StudentDao.Student byEntity3 = studentDao.getByEntity(StudentDao.Student.builder().gender("male").id(2L).build());
        StudentDao.Student byEntity4 = studentDao.getByEntity(StudentDao.Student.builder().build());
        StudentDao.Student byEntity5 = studentDao.getByEntity(StudentDao.Student.builder().id(100L).build());
        assert null != byEntity1;
        assert null != byEntity2;
        assert null != byEntity3;
        assert null != byEntity4;
        assert null == byEntity5;
    }

    @Test
    public void getByPrimaryKey() {
        StudentDao.Student byPrimaryKey1 = studentDao.getByPrimaryKey(1L);
        StudentDao.Student byPrimaryKey2 = studentDao.getByPrimaryKey(50L);

        assert null != byPrimaryKey1;
        assert null == byPrimaryKey2;

    }

    @Test
    public void insertIgnoreNull() {
        Long id = randomLong();
        StudentDao.Student byPrimaryKey = studentDao.getByPrimaryKey(id);
        studentDao.insertIgnoreNull(StudentDao.Student.builder().id(id).username("username6").password("password6").build());
        StudentDao.Student byPrimaryKey1 = studentDao.getByPrimaryKey(id);
        assert null == byPrimaryKey;
        assert null != byPrimaryKey1;
        assert id.equals(byPrimaryKey1.getId()) && "username6".equals(byPrimaryKey1.getUsername());
        assert "male".equals(byPrimaryKey1.getGender());
    }

    @Test
    public void insertNoIgnoreNull() {
        Long id = randomLong();
        StudentDao.Student byPrimaryKey = studentDao.getByPrimaryKey(id);
        studentDao.insertNoIgnoreNull(StudentDao.Student.builder().id(id).username("username6").password("password6").build());
        StudentDao.Student byPrimaryKey1 = studentDao.getByPrimaryKey(id);
        assert null == byPrimaryKey;
        assert null != byPrimaryKey1;
        assert id.equals(byPrimaryKey1.getId()) && "username6".equals(byPrimaryKey1.getUsername());
        assert null==byPrimaryKey1.getGender();
    }


    @Test
    public void updateByPrimaryIgnoreNull() {
        StudentDao.Student byPrimaryKey1 = studentDao.getByPrimaryKey(1L);
        byPrimaryKey1.setEmail(null);
        byPrimaryKey1.setUsername("usernamea");
        byPrimaryKey1.setId(1L);
        studentDao.updateByPrimaryIgnoreNull(byPrimaryKey1);
        StudentDao.Student byPrimaryKey2 = studentDao.getByPrimaryKey(1L);
        assert "usernamea".equals(byPrimaryKey2.getUsername());
        assert null != byPrimaryKey2.getEmail();
    }

    @Test
    public void updateByPrimaryNoIgnoreNull() {
        StudentDao.Student byPrimaryKey1 = studentDao.getByPrimaryKey(1L);
        byPrimaryKey1.setEmail(null);
        byPrimaryKey1.setId(1L);
        byPrimaryKey1.setUsername("usernamea");
        studentDao.updateByPrimaryNoIgnoreNull(byPrimaryKey1);
        StudentDao.Student byPrimaryKey2 = studentDao.getByPrimaryKey(1L);
        assert "usernamea".equals(byPrimaryKey2.getUsername());
        assert null == byPrimaryKey2.getEmail();

    }

    @Test
    public void deleteByPrimaryKey() {
        List<StudentDao.Student> byEntity1 = studentDao.findByEntity(StudentDao.Student.builder().build());
        studentDao.deleteByPrimaryKey(1L);
        studentDao.deleteByPrimaryKey(3L);
        List<StudentDao.Student> byEntity2 = studentDao.findByEntity(StudentDao.Student.builder().build());
        studentDao.deleteByPrimaryKey(2L);
        List<StudentDao.Student> byEntity3 = studentDao.findByEntity(StudentDao.Student.builder().build());

        studentDao.deleteByPrimaryKey(100L);
        List<StudentDao.Student> byEntity4 = studentDao.findByEntity(StudentDao.Student.builder().build());
        assert byEntity1.size() == 5;
        assert byEntity2.size() == 3;
        assert byEntity3.size() == 2;
        assert byEntity4.size() == 2;
    }

    @Test
    public void deleteByEntity() {
        List<StudentDao.Student> byEntity1 = studentDao.findByEntity(StudentDao.Student.builder().build());

        studentDao.deleteByEntity(StudentDao.Student.builder().id(3L).build());
        List<StudentDao.Student> byEntity2 = studentDao.findByEntity(StudentDao.Student.builder().build());

        studentDao.deleteByEntity(StudentDao.Student.builder().gender("female").build());
        List<StudentDao.Student> byEntity3 = studentDao.findByEntity(StudentDao.Student.builder().build());


        assert byEntity1.size() == 5;
        assert byEntity2.size() == 4;
        assert byEntity3.size() == 2;
    }


    public static Long randomLong() {
        return System.currentTimeMillis() % 10000L;
    }


}
