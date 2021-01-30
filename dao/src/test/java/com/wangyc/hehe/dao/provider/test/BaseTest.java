package com.wangyc.hehe.dao.provider.test;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Describe: BaseTest
 * Date: 2020/3/26
 * Time: 10:57 上午
 * Author: wangyc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= BaseTest.SpringConfig.class)
public class BaseTest {

    private static String PROPERTIES = "db.properties";
    private static String SQL = "db.sql";

    @Configuration
    @MapperScan(basePackages = "com.wangyc.hehe.dao.provider.test")
    public static class SpringConfig {
        @Bean
        public DataSource getDataSource() throws IOException, SQLException {
            Properties props = Resources.getResourceAsProperties(PROPERTIES);
            UnpooledDataSource ds = new UnpooledDataSource();
            ds.setDriver(props.getProperty("driver"));
            ds.setUrl(props.getProperty("url"));
            ds.setUsername(props.getProperty("username"));
            ds.setPassword(props.getProperty("password"));

            try (Connection connection = ds.getConnection()) {
                ScriptRunner runner = new ScriptRunner(connection);
                runner.setAutoCommit(true);
                runner.setStopOnError(false);
                runner.setLogWriter(null);
                runner.setErrorLogWriter(null);
                try (Reader reader = Resources.getResourceAsReader(SQL)) {
                    runner.runScript(reader);
                }
            }
            return ds;
        }
        @Bean
        public SqlSessionFactoryBean getSqlSessionFactoryBean(DataSource dataSource){
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            return sqlSessionFactoryBean;
        }
    }
}