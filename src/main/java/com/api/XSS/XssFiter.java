package com.api.XSS;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ext.Provider;

/**
 *
 * FIXME : 存在问题
 */
@Provider
public class XssFiter implements Filter{
    //日志信息
    private static Logger logger=Logger.getLogger(XssFiter.class.getName());

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("开始");

        HttpServletRequest req = (HttpServletRequest)request;
        XssHttpServletRequest xssHttpServletRequest = new XssHttpServletRequest(req);
        chain.doFilter(xssHttpServletRequest, response);
    }

    public void destroy() {

    }

}

