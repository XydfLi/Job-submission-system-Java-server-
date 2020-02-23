package com.api.interceptor.reader;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * 请求拦截器
 * 实现ReaderInterceptor接口
 *
 * @author 李星源
 * @version 1.0
 * TODO ： 未来根据需要增加拦截操作
 */
@Provider
@Priority(Priorities.AUTHORIZATION+2)//拥有三级优先级（优先级为2003）
public class MyReaderInterceptor implements ReaderInterceptor {
    //日志信息
    private static Logger logger=Logger.getLogger(MyReaderInterceptor.class.getName());

    /**
     * TODO ： 未来根据需要增加拦截操作
     *
     * @param readerInterceptorContext
     * @return
     * @throws IOException
     * @throws WebApplicationException
     */
    @Override
    public Object aroundReadFrom(ReaderInterceptorContext readerInterceptorContext) throws IOException, WebApplicationException {
//        logger.info("进入测试请求拦截器");
        return readerInterceptorContext.proceed();
    }

}
