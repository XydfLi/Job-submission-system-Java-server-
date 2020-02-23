package com.api.controller;

import com.api.format.FastJson;
import com.api.model.Hello;
import lombok.Getter;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.logging.Logger;

/**
 * 测试（无用）
 *
 * @author 李星源
 * @version 1.0
 */
@Path("/hello")
@FastJson
public class sayhello {
    //日志信息
    private static Logger logger=Logger.getLogger(sayhello.class.getName());

    @GET
    @Path("/s")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Administrator")
    public Hello s(){
        Hello hello=new Hello();
        hello.setName("中文");
        hello.setNow(new Date());
        return hello;
    }

}
