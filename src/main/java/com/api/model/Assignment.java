package com.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * 作业类
 * 对应数据库中的Assignment表
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
public class Assignment {
    protected int taskId;//考核id
    protected String accountId;//用户id
    protected String jobContent;//作业内容

//    TODO : 前端实现"yyyy-MM-dd HH:mm:ss"格式后，使用下一行注解
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    protected Date subTime;//提交时间
    protected int mark;//1为审核中，2为通过，3为未通过
}
