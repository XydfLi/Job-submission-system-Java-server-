package com.api.model;

import lombok.*;

/**
 * 用户类
 * 对应数据库中的Account表
 *
 * @author 李星源
 * @version 1.0
 */
@Setter//生成set函数
@Getter//生成get函数
@ToString//生成toString函数
@NoArgsConstructor//生成无参构造函数
@AllArgsConstructor//生成全参构造函数
// FIXME : @FastJson注解 自定义JSON转换 转换中文出现乱码
//@FastJson
public class Account {
    private String accountId;//用户id
    private String accountName;//用户名称
    private String passWord;//用户密码
    private int identity;//0为身份错误，1为管理员身份，2为学生身份
}
