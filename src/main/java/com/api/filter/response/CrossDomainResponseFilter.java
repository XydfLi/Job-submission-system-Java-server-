package com.api.filter.response;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

/**
 * 用于解决跨域问题
 *
 * @author 李星源
 * @version 1.0
 */
@Provider
//拥有响应过滤器中最高优先级（一级优先级）（优先级为1000）
@Priority(Priorities.AUTHENTICATION)
public class CrossDomainResponseFilter implements ContainerResponseFilter {
    //日志信息
    private static Logger logger=Logger.getLogger(CrossDomainResponseFilter.class.getName());

    /**
     * 添加允许的权限
     *
     * @param containerRequestContext 包含请求相关的内容（不能修改）
     * @param containerResponseContext 包含响应相关的内容
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) {
//        logger.info("进入跨域响应过滤器");

        //允许跨域请求
        if("OPTIONS".equalsIgnoreCase(containerRequestContext.getMethod())) {
            containerResponseContext.setStatus(HttpServletResponse.SC_OK);
        }
        containerResponseContext.getHeaders().add("Access-Control-Allow-Origin","*");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH");
        containerResponseContext.getHeaders().add("Access-Control-Max-Age", "1209600");//14天
    }

}
