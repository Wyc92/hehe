/**
 * Copyright 2010-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.spring.support;

import static org.springframework.util.Assert.notNull;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.DaoSupport;


/**
 * spring整合mybatis的时候就是MapperFactoryBean继承这个类，在MapperFactoryBean中通过sqlsession获得configuration然后addMapper（劣势是 手写的代码很多）
 * dayima的实现方式是继承SqlSessionDaoSupport后、利用spring的动态注入BeanDefinition的方式（劣势是 多个mappeer都是自动生成的，那如果需要定制sql的话就没有合适的地方写了，如果统一写到一个namespace下会很乱的）
 * yiche的实现方式是利用Mybatis自己的 Provider扩展，虽说实现起来比较简单，但是并没有拿到sqlsession，所以实现的功能还是很有限的
 * TODO  自己实现的时候 basedao直接继承SqlSessionDaoSupport应该是能解决上面三个问题
 * @see org.mybatis.spring.mapper.ClassPathMapperScanner#processBeanDefinitions(java.util.Set)
 */
public abstract class SqlSessionDaoSupport extends DaoSupport {

  private SqlSessionTemplate sqlSessionTemplate;

  /**
   * @param sqlSessionFactory
   */
  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
    if (this.sqlSessionTemplate == null || sqlSessionFactory != this.sqlSessionTemplate.getSqlSessionFactory()) {
      this.sqlSessionTemplate = createSqlSessionTemplate(sqlSessionFactory);
    }
  }


  @SuppressWarnings("WeakerAccess")
  protected SqlSessionTemplate createSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    return new SqlSessionTemplate(sqlSessionFactory);
  }


  public final SqlSessionFactory getSqlSessionFactory() {
    return (this.sqlSessionTemplate != null ? this.sqlSessionTemplate.getSqlSessionFactory() : null);
  }


  public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
    this.sqlSessionTemplate = sqlSessionTemplate;
  }


  public SqlSession getSqlSession() {
    return this.sqlSessionTemplate;
  }


  public SqlSessionTemplate getSqlSessionTemplate() {
    return this.sqlSessionTemplate;
  }

  @Override
  protected void checkDaoConfig() {
    notNull(this.sqlSessionTemplate, "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
  }

}
