package com.api.interceptor.writer;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * 响应拦截器
 * 实现WriterInterceptor接口
 *
 * @author 李星源
 * @version 1.0
 * TODO ： 未来根据需要增加拦截操作
 */
@Provider
@Priority(Priorities.AUTHORIZATION+1)//拥有二级优先级（优先级为2001）
public class MyWriterInterceptor implements WriterInterceptor {
    //日志信息
    private static Logger logger=Logger.getLogger(MyWriterInterceptor.class.getName());

    /**
     * TODO ： 未来根据需要增加拦截操作
     *
     * @param writerInterceptorContext 包含响应实体的内容
     * @throws IOException
     * @throws WebApplicationException
     */
    @Override
    public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext) throws IOException, WebApplicationException {
//        logger.info("进入测试响应拦截器");
        writerInterceptorContext.proceed();
    }

}
