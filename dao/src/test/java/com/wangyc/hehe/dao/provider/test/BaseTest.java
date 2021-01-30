package com.wangyc.hehe.dao.provider.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Describe: BaseTest
 * Date: 2020/3/26
 * Time: 10:57 上午
 * Author: wangyc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BaseTest.SpringConfig.class)
public class BaseTest {

    private static String PROPERTIES = "db.properties";
    private static String SQL = "db.sql";

    @Test
    public void test() {

    }

    @Configuration
    @MapperScan(basePackages = "com.wangyc.hehe.dao.provider.test")
    public static class SpringConfig {
        @Bean
        public DataSource getDataSource() throws IOException, SQLException {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript(SQL)
                    .build();
        }

        @Bean
        public SqlSessionFactoryBean getSqlSessionFactoryBean(DataSource dataSource) {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            return sqlSessionFactoryBean;
        }
    }
}