package com.api.filter.response;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

/**
 * 响应过滤器
 *
 * @author 李星源
 * @version 1.0
 */
@Provider
@Priority(Priorities.AUTHENTICATION+1)//拥有二级优先级（优先级为1001）
public class MyResponseFilter implements ContainerResponseFilter {
    //日志信息
    private static Logger logger=Logger.getLogger(MyResponseFilter.class.getName());

    /**
     * 实现filter方法
     *
     * @param containerRequestContext 包含请求相关的内容（不能修改）
     * @param containerResponseContext 包含响应相关的内容
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext){
//        logger.info("进入响应过滤器");
    }

}
