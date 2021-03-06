package com.api.exception;

/**
 * 自定义数据异常
 * 用于抛出数据相关的异常
 *
 * @author 李星源
 * @version 1.0
 */
public class DataException extends ApplicationException {

    /**
     * 构造方法
     *
     * @param code 系统状态
     * @param cause Throwable对象
     */
    public DataException(InfoCode code, Throwable cause) {
        super(code, cause);
    }

}
