package com.api.CSRF;

import com.api.exception.ApplicationException;
import com.api.exception.SecurityException;
import com.api.exception.InfoCode;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import java.io.IOException;

/**
 * 用于防御CSRF攻击
 * FIXME : 客户端并未加上"CSRFToken"请求头，故不能使用
 *
 * @author 李星源
 * @version 1.0
 */
//拥有请求拦截器中最高优先级（一级优先级）（优先级为2000）
@Priority(Priorities.AUTHORIZATION)
public class CSRFReaderInterceptor implements ReaderInterceptor {

    @Context
    private HttpServletRequest req;//用来获取session

    /**
     * 拦截身份未验证、身份错误的客户端请求
     *
     * @param readerInterceptorContext 包含请求实体的一些内容
     * @return
     * @throws WebApplicationException
     * @throws IOException
     */
    @Override
    public Object aroundReadFrom(ReaderInterceptorContext readerInterceptorContext) throws WebApplicationException, IOException {
        HttpSession session=req.getSession();
        String sToken=(String)session.getAttribute("CSRFToken");
        if(sToken!=null) {
            //获得请求头中的CSRFToken
            String xhrToken=req.getHeader("CSRFToken");
            if(xhrToken!=null&&sToken.equals(xhrToken)){
                return readerInterceptorContext.proceed();
            } else {
                throw new SecurityException(InfoCode.CSRF_ATTACK,null);
            }
        }
        throw new ApplicationException(InfoCode.LOGIN_FIRST,null);
    }

}
