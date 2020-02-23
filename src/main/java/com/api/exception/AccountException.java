package com.api.exception;

/**
 * 自定义Account异常
 * 用于抛出Account相关的异常
 *
 * @author 李星源
 * @version 1.0
 */
public class AccountException extends ApplicationException {

    /**
     * 构造方法
     *
     * @param infoCode 系统状态
     * @param ex Throwable对象
     */
    public AccountException(InfoCode infoCode,Throwable ex) {
        super(infoCode,ex);
    }

}
