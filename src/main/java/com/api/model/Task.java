package com.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * 考核类
 * 对应数据库中的Task表
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
public class Task {
    private int taskId;//考核id
    private String taskName;//考核名称
    private String taskContent;//考核内容

//    TODO : 前端实现"yyyy-MM-dd HH:mm:ss"格式后，使用下一行注解
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date startDate;//开始日期

//    TODO : 前端实现"yyyy-MM-dd HH:mm:ss"格式后，使用下一行注解
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date deadline;//截止日期
}
