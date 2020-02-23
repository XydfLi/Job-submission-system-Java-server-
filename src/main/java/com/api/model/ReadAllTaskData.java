package com.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.List;

/**
 * 考核类
 * 用户与返回Task列表
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
public class ReadAllTaskData {
    int count;//列表对象个数

//    TODO : 前端实现"yyyy-MM-dd HH:mm:ss"格式后，使用下一行注解
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    List<TaskWithNumber> taskWithNumberList;//TaskWithNumber列表
}
