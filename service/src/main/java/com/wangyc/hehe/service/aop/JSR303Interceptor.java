package com.wangyc.hehe.service.aop;

import com.wangyc.hehe.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Component
@Slf4j
public class JSR303Interceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        boolean hasError = false;
        try {
            Object[] arguments = invocation.getArguments();
            Method method = invocation.getMethod();
            Parameter[] parameters = method.getParameters();
            for(int i=0;i<parameters.length;i++){
                if (null!=parameters[i].getAnnotation(NotNull.class)&&null==arguments[i]) {
                    hasError = true;
                    break;
                }
                ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(arguments[i]);
                if(validResult.hasErrors()){
                    hasError = true;
                    break;
                }
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        if(hasError){
            //TODO
            //int a = 1/0;
        }
        return invocation.proceed();
    }
}