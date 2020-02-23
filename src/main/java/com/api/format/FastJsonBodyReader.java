package com.api.format;

import com.alibaba.fastjson.JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * 自定义请求JSON格式
 *
 * @author 李星源
 * @version 1.0
 */
@Provider//代表这是一个Provider类，Jersey会自动发现并注册
@FastJson
public class FastJsonBodyReader implements MessageBodyReader<Object> {

    /**
     * 判断给定的目标资源方法是否能够使用我们的MessageBodyReader接口实施转化
     *
     * @param aClass 当前资源方法返回的类型
     * @param type 当前资源方法返回的类型，这个类型可以获取泛型类型
     * @param annotations 当前资源方法返回的类型上面的注解实例
     * @param mediaType 当前HTTP方法的生产MIME类型
     * @return true为可以，false为不行
     */
    @Override
    //辅助匹配方法，要求对应资源方法的@Consumes，必须包含MediaType.APPLICATION_JSON类型，才能够实施我们的MessageBodyReader
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean isReadable(Class<?> aClass,
            Type type,
            Annotation[] annotations,
            MediaType mediaType) {
        //只有返回的目标对象类型上面有我们的@FastJson标签，才实施转化
        return aClass.isAnnotationPresent(FastJson.class);
    }

    /**
     * 把请求体中的JSON字符串转化成目标的对象
     *
     * @param aClass 当前资源方法返回的类型
     * @param type 当前资源方法返回的类型，这个类型可以获取泛型类型
     * @param annotations 当前资源方法返回的类型上面的注解实例
     * @param mediaType 当前HTTP方法的生产MIME类型
     * @param multivaluedMap 本次HTTP请求的请求头列表
     * @param inputStream 本次请求实体的输入流
     * @return 转化的对象
     */
    @Override
    public Object readFrom(Class<Object> aClass,
            Type type,
            Annotation[] annotations,
            MediaType mediaType,
            MultivaluedMap<String, String> multivaluedMap,
            InputStream inputStream) {
        BufferedInputStream bis=new BufferedInputStream(inputStream);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();

        //把inputStream中的内容读取到一个byte[]中
        try {
            byte[] buffer=new byte[1024];
            int bytesRead=-1;
            while((bytesRead=bis.read(buffer))!=-1){
                baos.write(buffer,0,bytesRead);
            }
            baos.flush();
        } catch (IOException e){
            e.printStackTrace();
        } catch (WebApplicationException e){
            e.printStackTrace();
        }

        //把读入的byte[]转变成对应的对象类型，目标类型就是传入的type参数
        Object o= JSON.parseObject(baos.toByteArray(),type);
        return o;
    }

}
