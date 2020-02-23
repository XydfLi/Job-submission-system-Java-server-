package com.api.interceptor.reader;

import com.api.exception.ApplicationException;
import com.api.exception.InfoCode;
import com.api.model.Account;
import com.api.service.UserService;
import com.api.service.UserServiceImpl;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * 用来根据学生的权限进行拦截
 *
 * @author 李星源
 * @version 1.0
 */
@Provider
@Student
//拥有请求拦截器中二级优先级（优先级为2001）
@Priority(Priorities.AUTHORIZATION+1)
public class StudentReaderInterceptor implements ReaderInterceptor {
    //日志信息
    private static Logger logger=Logger.getLogger(StudentReaderInterceptor.class.getName());
    @Context
    private HttpServletRequest req;//用来获取session

    /**
     * 对学生的一些操作进行拦截
     *
     * @param readerInterceptorContext 包含请求实体的一些内容
     * @return
     * @throws IOException
     */
    @Override
    public Object aroundReadFrom(ReaderInterceptorContext readerInterceptorContext) throws IOException {
//        logger.info("进行学生权限拦截");

        //获得accountId
        HttpSession session=req.getSession(true);
        String accountId=(String)session.getAttribute("accountId");

        UserService user=new UserServiceImpl();
        Account account=new Account();
        account.setAccountId(accountId);
        int mark=user.getIdentity(account);
        if (mark==2){
            throw new ApplicationException(InfoCode.NO_PERMISSION,null);
        }
        return readerInterceptorContext.proceed();
    }

}
