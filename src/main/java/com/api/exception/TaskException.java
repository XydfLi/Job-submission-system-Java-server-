package com.api.exception;

/**
 * 自定义Account异常
 * 用于抛出Account相关的异常
 *
 * @author 李星源
 * @version 1.0
 */
public class TaskException extends ApplicationException {

    /**
     * 构造方法
     *
     * @param code 系统状态
     * @param cause Throwable对象
     */
    public TaskException(InfoCode code, Throwable cause) {
        super(code, cause);
    }

}
