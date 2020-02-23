package com.api.dynamicBinding;

import com.api.filter.request.LoginRequestFilter;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 * 动态绑定实现过滤未登入请求
 *
 * @author 李星源
 * @version 1.0
 */
@Provider
public class LoginDynamicBinding implements DynamicFeature {

    /**
     * 动态注册登入过滤器
     * 仅有"login"方法和"upload"方法不需要注册登入过滤器
     *
     * @param resourceInfo 获得方法名称
     * @param featureContext 实现注册
     */
    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {
        String methodName=resourceInfo.getResourceMethod().getName();

        //登入、上传文件、前台查询考核、注册用户不需要登入
        if((!"login".equals(methodName))&&
                (!"upload".equals(methodName))&&
                (!"getTask".equals(methodName))&&
                (!"registration".equals(methodName))){
            featureContext.register(LoginRequestFilter.class);
        }
    }

}
