package com.api.filter.request;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

/**
 * 请求过滤器
 * 实现ContainerRequestFilter接口
 *
 * @author 李星源
 * @version 1.0
 * TODO ： 未来根据需要增加拦截操作
 */
@Provider
@Priority(Priorities.AUTHENTICATION+2)//拥有三级优先级（优先级为1002）
public class MyRequestFilter implements ContainerRequestFilter {
    //日志信息
    private static Logger logger=Logger.getLogger(MyRequestFilter.class.getName());

    /**
     * 实现filter方法
     *
     * @param containerRequestContext 包含请求相关的内容
     * TODO ： 未来根据需要增加拦截操作
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext){
//        logger.info("进入请求过滤器");
//        版本校对
//        版权信息确认
//        ...
    }

}
