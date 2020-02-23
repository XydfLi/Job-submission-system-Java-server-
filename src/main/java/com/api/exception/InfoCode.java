package com.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统状态码infoCode的枚举
 * 进行Jersey统一异常处理
 * 增加程序的可维护性和可移植性
 *
 * @author 李星源
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum InfoCode {
    OK(1000,"OK"),

    ACCOUNT_Id_NOT_EXIST(1101,"用户ID不存在"),
    ACCOUNT_Id_EMPTY(1102,"用户ID为空"),
    ACCOUNT_Id_ERROR(1103,"用户ID不合法"),
    ACCOUNT_ID_EXIST(1104,"用户ID已存在"),
    PASSWORD_ERROR(1105,"密码错误"),
    PASSWORD_EMPTY(1106,"密码为空"),
    OLD_PASSWORD_EMPTY(1107,"原密码为空"),
    NEW_PASSWORD_EMPTY(1108,"新密码为空"),
    INVALID_Password(1109,"密码不合法"),
    ACCOUNT_NAME_EMPTY(1110,"用户名称为空"),
    ACCOUNT_NAME_ERROR(1111,"用户名称不合法"),

    TASK_NAME_EMPTY(1201,"考核名称为空"),
    TASK_NAME_ERROR(1202,"考核名称不合法"),
    TASK_CONTENT_EMPTY(1203,"考核内容为空"),
    TASK_CONTENT_ERROR(1204,"考核内容不合法"),
    START_DATE_EMPTY(1205,"开始日期为空"),
    START_DATE_ERROR(1206,"开始日期不合法"),
    DEADLINE_EMPTY(1207,"截止日期为空"),
    DEADLINE_ERROR(1208,"截止日期不合法"),
    TASK_ID_ERROR(1209,"考核ID不合法"),
    TASK_ID_NOT_EXIST(1210,"考核ID不存在"),

    JOB_CONTENT_ERROR(1301,"作业内容不合法"),
    SUBMIT_ONLY_ONCE(1302,"作业只能提交一次"),
    SUB_TIME_EMPTY(1303,"提交日期为空"),
    SUB_TIME_ERROR(1304,"提交日期在规定时间之外"),
    MARK_ERROR(1305,"作业标记不合法"),

    FILE_FORMAT_ERROR(1401,"文件格式错误"),
    FILE_NOT_EXIST(1402,"文件不存在"),
    FILE_EXIST(1403,"文件已存在"),
    FILE_EMPTY(1404,"文件为空"),

    XSS_ATTACK(1501,"服务端受到XSS攻击"),
    CSRF_ATTACK(1502,"客户端身份错误"),
    ILLEGAL_CHARACTERS(1503,"存在非法字符"),

    DATA_EMPTY(0,"数据为空"),
    UNKNOWN_MISTAKE(1,"未知错误"),
    REPEAT_LOGOUT(2,"重复登出"),
    LOGIN_FIRST(3,"请先登录"),
    NO_PERMISSION(4,"没有权限");

    private int code;
    private String value;
}
