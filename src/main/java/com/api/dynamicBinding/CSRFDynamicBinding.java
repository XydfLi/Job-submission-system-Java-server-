package com.api.dynamicBinding;

import com.api.CSRF.CSRFReaderInterceptor;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 * 动态绑定实现拦截客户端身份异常
 * FIXME : 客户端并未加上"CSRFToken"请求头，故不能使用
 *
 * @author 李星源
 * @version 1.0
 */
//@Provider
public class CSRFDynamicBinding implements DynamicFeature {

    /**
     * 动态注册登入拦截器
     *
     * @param resourceInfo 获得方法名称
     * @param featureContext 实现注册
     */
    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {
        String methodName=resourceInfo.getResourceMethod().getName();

        //登入、上传文件、前台查询考核、注册用户不需要登入身份验证
        if((!"login".equals(methodName))&&
                (!"upload".equals(methodName))&&
                (!"getTask".equals(methodName))&&
                (!"registration".equals(methodName))){
            featureContext.register(CSRFReaderInterceptor.class);
        }
    }

}
