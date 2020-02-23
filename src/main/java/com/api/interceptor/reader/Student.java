package com.api.interceptor.reader;

import javax.ws.rs.NameBinding;
import java.lang.annotation.*;

/**
 * 用于进行学生权限拦截器的名称绑定
 *
 * @author 李星源
 * @version 1.0
 */
@NameBinding//名称绑定
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Student {
}
