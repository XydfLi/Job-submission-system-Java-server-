package com.api.XSS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.logging.Logger;

/**
 *
 * FIXME : 存在问题
 */
public class XssHttpServletRequest extends HttpServletRequestWrapper{
    //日志信息
    private static Logger logger=Logger.getLogger(XssHttpServletRequest.class.getName());
    private HttpServletRequest request;

    //需要重写构造方法
    public XssHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    public String getParameter(String name) {
        String value = request.getParameter(name);
        System.out.println("没有转义之前:value="+value);
        if(StringUtils.isNotBlank(value)){
            value = StringEscapeUtils.escapeHtml4(value);
        }
        return value;
    }

}
