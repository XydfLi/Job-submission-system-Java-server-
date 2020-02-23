package com.api.format;

import com.alibaba.fastjson.JSONObject;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * 自定义响应JSON格式
 *
 * @author 李星源
 * @version 1.0
 */
@Provider//代表这是一个Provider类，Jersey会自动发现并注册
@FastJson
public class FastJsonBodyWriter implements MessageBodyWriter<Object> {

    /**
     * 判断针对一个结果，是否能够使用该MessageBodyWriter来处理
     *
     * @param aClass 当前资源方法返回的类型
     * @param type 当前资源方法返回的类型，这个类型可以获取泛型类型
     * @param annotations 当前资源方法返回的类型上面的注解实例
     * @param mediaType 当前HTTP方法的生产MIME类型
     * @return true为可以，false为不行
     */
    @Override
    //要求该FastJsonBodyWriter至少作用于标记了@Produces标签中，包含MediaType.APPLICATION_JSON类型的方法
    @Produces(MediaType.APPLICATION_JSON)
    public boolean isWriteable(Class<?> aClass,
            Type type,
            Annotation[] annotations,
            MediaType mediaType) {
        //判断对应的资源方法返回的对象类型上是否有@FastJson标签
        return aClass.isAnnotationPresent(FastJson.class);
    }

    /**
     * 用于标记响应的Content-Length属性的，在JAX-RS2.0中已经作废
     * 只需要返回-1
     *
     * @param o
     * @param aClass
     * @param type
     * @param annotations
     * @param mediaType
     * @return
     */
    @Override
    public long getSize(Object o,
            Class<?> aClass,
            Type type,
            Annotation[] annotations,
            MediaType mediaType) {
        return -1;
    }

    /**
     * 写入操作
     *
     * @param o 资源方法返回的对象
     * @param aClass 当前资源方法返回的类型
     * @param type 当前资源方法返回的类型，这个类型可以获取泛型类型
     * @param annotations 当前资源方法返回的类型上面的注解实例
     * @param mediaType 当前HTTP方法的生产MIME类型
     * @param multivaluedMap 本次HTTP请求的请求头列表
     * @param outputStream 响应实体内容的输出流
     */
    @Override
    public void writeTo(Object o,
            Class<?> aClass,
            Type type,
            Annotation[] annotations,
            MediaType mediaType,
            MultivaluedMap<String,Object> multivaluedMap,
            OutputStream outputStream) {
        try {
            outputStream.write(JSONObject.toJSONString(o).getBytes());
        } catch (IOException e){
            e.printStackTrace();
        } catch (WebApplicationException e){
            e.printStackTrace();
        }
    }

}
