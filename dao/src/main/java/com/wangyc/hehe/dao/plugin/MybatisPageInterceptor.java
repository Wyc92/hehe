package com.wangyc.hehe.dao.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * 如果是分页的，则
 * Describe: MybatisPageInterceptor
 * Date: 2021/2/1
 * Time: 11:28 上午
 * Author: wangyc
 */
@Deprecated
@Intercepts({ @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class MybatisPageInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        /**
         * Invocation 包含了 method、target，args
         * 即原始所有调用的信息都有
         */
        MappedStatement ms =(MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = ms.getBoundSql(parameter);
        String sql = boundSql.getSql().trim();
        String countSQL = countSQL(sql);
        System.out.println(countSQL);
        Object proceed = invocation.proceed();
        return proceed;
    }

    /**
     * 查询总数的sql
     * @param oriSql
     * @return
     */
    protected String countSQL(String oriSql){
        return "select count(1) from ("+oriSql+") countsql";
    }

    @Deprecated
    public static class PageSource implements SqlSource {
        BoundSql boundSql;

        PageSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

}
