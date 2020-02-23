package com.api.exception;

/**
 * 自定义Security异常
 * 用于抛出Security相关的异常
 * 用于抛出受到攻击等相关异常
 *
 * @author 李星源
 * @version 1.0
 */
public class SecurityException extends ApplicationException {

    /**
     * 构造方法
     *
     * @param infoCode 系统状态
     * @param ex Throwable对象
     */
    public SecurityException(InfoCode infoCode,Throwable ex) {
        super(infoCode,ex);
    }

}
