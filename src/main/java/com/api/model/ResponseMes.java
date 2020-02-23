package com.api.model;

import com.api.exception.InfoCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 统一响应类
 * 对响应的内容进行JSON规范化
 *
 * @author 李星源
 * @version 1.0
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
// FIXME : @FastJson注解 自定义JSON转换 转换中文出现乱码
//@FastJson
public class ResponseMes {
    private int status;//状态码
    private int infoCode;//系统状态码
    private String info;//系统状态信息

//    TODO : 前端实现"yyyy-MM-dd HH:mm:ss"格式后，使用下一行注解
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Object data;//返回的数据信息

    /**
     * 前台请求操作成功时运行该方法
     *
     * @param data 需要返回给前台的数据
     * @return ResponseMes对象
     */
    public ResponseMes success(Object data){
        this.status=HttpServletResponse.SC_OK;
        this.infoCode= InfoCode.OK.getCode();
        this.info=InfoCode.OK.getValue();
        this.data=data;
        return this;
    }

    /**
     * 前台请求操作失败时运行该方法
     *
     * @param status 需要返回给前台的状态码
     * @param infoCode 需要返回给前台的系统状态码
     * @param info 需要返回给前天的系统状态信息
     * @param data 需要返回给前台的数据
     * @return ResponseMes对象
     */
    public ResponseMes failure(int status,int infoCode,String info,Object data){
        this.status=status;
        this.infoCode=infoCode;
        this.info=info;
        this.data=data;
        return this;
    }
}
