package com.api.controller;

import javax.ws.rs.HttpMethod;
import java.lang.annotation.*;

/**
 * 自定义PATCH请求头
 *
 * @author 李星源
 * @version 1.0
 */
@Target({ElementType.METHOD})//用在方法上
@Retention(RetentionPolicy.RUNTIME)//在程序运行中存在
@HttpMethod(value="PATCH")//添加PATCH请求头
@Documented
public @interface PATCH {
}
