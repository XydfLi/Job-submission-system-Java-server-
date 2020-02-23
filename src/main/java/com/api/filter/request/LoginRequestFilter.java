package com.api.filter.request;

import com.api.exception.ApplicationException;
import com.api.exception.InfoCode;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import java.util.logging.Logger;

/**
 * 检查登入过滤器
 * 动态绑定
 *
 * @author 李星源
 * @version 1.0
 */
@Priority(Priorities.AUTHENTICATION+1)//拥有二级优先级（优先级为1001）
public class LoginRequestFilter implements ContainerRequestFilter {
    //日志信息
    private static Logger logger=Logger.getLogger(LoginRequestFilter.class.getName());

    @Context
    private HttpServletRequest req;//用于获得session

    /**
     * 如果session为空，则向前台抛出异常
     *
     * @param containerRequestContext 请求相关的内容
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext){
//        logger.info("进入登入过滤器");

        //获得accountId
        HttpSession session=req.getSession(true);
        String accountId=(String)session.getAttribute("accountId");

        if (accountId==null){
            throw new ApplicationException(InfoCode.LOGIN_FIRST,null);
        }
    }

}
