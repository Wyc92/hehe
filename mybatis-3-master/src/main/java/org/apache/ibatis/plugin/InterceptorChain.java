/**
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.plugin;

import org.apache.ibatis.session.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Clinton Begin
 */
public class InterceptorChain {

  /**
   * 拦截器列表
   */
  private final List<Interceptor> interceptors = new ArrayList<>();

  /**
   * 拦截
   * @see org.apache.ibatis.plugin.Plugin#getSignatureMap(org.apache.ibatis.plugin.Interceptor) 只能拦截public的方法
   *      而且当this调用的时候 还是不生效的，类似Spring中的 注解aop等代理问题
   * @see Configuration#newParameterHandler(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.mapping.BoundSql)
   * @see Configuration#newResultSetHandler(org.apache.ibatis.executor.Executor, org.apache.ibatis.mapping.MappedStatement, org.apache.ibatis.session.RowBounds, org.apache.ibatis.executor.parameter.ParameterHandler, org.apache.ibatis.session.ResultHandler, org.apache.ibatis.mapping.BoundSql)
   * @see Configuration#newStatementHandler(org.apache.ibatis.executor.Executor, org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler, org.apache.ibatis.mapping.BoundSql)
   * @see Configuration#newExecutor(org.apache.ibatis.transaction.Transaction, org.apache.ibatis.session.ExecutorType)
   * @param target
   * @return
   */
  public Object pluginAll(Object target) {
    for (Interceptor interceptor : interceptors) {
      target = interceptor.plugin(target);
    }
    return target;
  }

  /**
   * 增加拦截器
   * @param interceptor
   */
  public void addInterceptor(Interceptor interceptor) {
    interceptors.add(interceptor);
  }

  /**
   * 获取拦截器列表
   * @return
   */
  public List<Interceptor> getInterceptors() {
    return Collections.unmodifiableList(interceptors);
  }

}
