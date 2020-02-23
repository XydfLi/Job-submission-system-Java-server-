package com.api.application;

import com.api.XSS.XssFiter;
import com.api.format.FastJsonBodyReader;
import com.api.format.FastJsonBodyWriter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ApplicationPath;

/**
 * jersey框架注册类
 * 自动扫描资源类、注册组件
 * MultiPartFeature注册、过滤器注册
 *
 * @author 李星源
 * @version 1.0
 */
@ApplicationPath("/api/*")//应用的虚拟目录
public class RestApplication extends ResourceConfig {

    /**
     * 构造函数
     */
    public RestApplication(){
        this.packages("com.api");//扫描路径
        this.register(MultiPartFeature.class);//文件上传，MultiPartFeature注册
        this.register(XssFiter.class);//XSS过滤器
//        this.register(RolesAllowedDynamicFeature.class);//角色权限管理动态绑定
        this.register(FastJsonBodyReader.class);
        this.register(FastJsonBodyWriter.class);
    }

}
