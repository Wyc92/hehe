package com.wangyc.hehe.service.aop;

import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

@Component
public class JSR303Advisor extends StaticMethodMatcherPointcutAdvisor {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        Parameter[] parameters = method.getParameters();
        //TODO 是否应该换成 其他的注解？
        long count = Arrays.stream(parameters).filter(e -> null != e.getAnnotation(NotNull.class)).count();
        return count>0L;
    }
    @Autowired
    public void setMonitorMethodInterceptor(JSR303Interceptor interceptor) {
        super.setAdvice(interceptor);
    }
}
