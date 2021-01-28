package com.wangyc.hehe.controller;


import com.alibaba.fastjson.JSON;
import com.wangyc.hehe.utils.RSA.RSAPrivateUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Describe: SignDataParamResolver
 * Date: 2019/12/31
 * Time: 6:36 下午
 * Author: wangyc
 */
public class RSADataParamResolver implements HandlerMethodArgumentResolver {

    private String privateKey;

    public RSADataParamResolver(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(RSAData.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class<?> clazz = parameter.getParameterType();
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String bodyString = getBodyString(httpServletRequest);
        if (StringUtils.isNotBlank(bodyString)) {
            String decrypt = RSAPrivateUtil.decrypt(privateKey, bodyString);
            return JSON.parseObject(decrypt, clazz);
        } else {
            return null;
        }
    }
    public String getBodyString(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}


