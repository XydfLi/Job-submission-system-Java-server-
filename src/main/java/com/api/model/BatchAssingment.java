package com.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.List;

/**
 * 作业类（批量）
 * 用于处理批量作业
 *
 * @author 李星源
 * @version 1.0
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

//    TODO : 前端实现"yyyy-MM-dd HH:mm:ss"格式后，使用下一行注解
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")

// FIXME : @FastJson注解 自定义JSON转换 转换中文出现乱码
//@FastJson
public class BatchAssingment {
    int count;//列表的个数
    int taskId;//考核id
    List<String> accountIdList;//用户id的列表
}
