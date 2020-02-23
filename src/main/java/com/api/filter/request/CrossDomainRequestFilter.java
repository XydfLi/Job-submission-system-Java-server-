package com.api.filter.request;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

/**
 * 用于解决跨域问题
 *
 * @author 李星源
 * @version 1.0
 */
@Provider
//拥有请求过滤器中最高优先级（一级优先级）（优先级为1000）
@Priority(Priorities.AUTHENTICATION)
public class CrossDomainRequestFilter implements ContainerRequestFilter {
    //日志信息
    private static Logger logger=Logger.getLogger(CrossDomainRequestFilter.class.getName());

    /**
     * 添加允许的权限
     *
     * @param containerRequestContext 包含请求相关的内容
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
//        logger.info("进入跨域请求过滤器");

        //允许跨域请求
        containerRequestContext.getHeaders().add("Access-Control-Allow-Origin","*");
        containerRequestContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        containerRequestContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        containerRequestContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH");
        containerRequestContext.getHeaders().add("Access-Control-Max-Age", "1209600");//14天
    }

}
