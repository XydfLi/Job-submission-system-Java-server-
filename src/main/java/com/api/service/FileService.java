package com.api.service;

import com.api.exception.InfoCode;
import com.api.model.Assignment;

import java.util.Date;
import java.util.logging.Logger;

/**
 * 文件操作的Service层接口
 *
 * @author 李星源
 * @version 1.0
 */
public interface FileService {
    //日志信息
    static Logger logger=Logger.getLogger(FileService.class.getName());

    //上传文件的合法性
    public InfoCode uploadFileLegal(Assignment assignment, String ext);
}
