package com.api.exception;

import com.api.model.ResponseMes;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

/**
 * 主要用于将ApplicationException异常转换为ResponseMes对象
 *
 * @author 李星源
 * @version 1.0
 */
@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {
    //日志信息
    static Logger logger=Logger.getLogger(ApplicationExceptionMapper.class.getName());

    /**
     * 用于将ApplicationException异常转换为ResponseMes对象
     *
     * @param e ApplicationException对象
     * @return ResponseMes对象
     */
    @Override
    public Response toResponse(ApplicationException e) {
        ResponseMes result=new ResponseMes();
        result.failure(HttpServletResponse.SC_OK,e.getCode().getCode(),e.getCode().getValue(),0);
        return Response.ok(result,MediaType.APPLICATION_JSON).build();
    }

}
