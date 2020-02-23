package com.api.service;

import com.api.exception.InfoCode;
import com.api.model.Task;

import java.util.List;
import java.util.logging.Logger;

/**
 * 学生进行考核操作的Service层接口
 *
 * @author 李星源
 * @version 1.0
 */
public interface StudentTaskService {
    //日志信息
    static Logger logger=Logger.getLogger(StudentTaskService.class.getName());

    //获取全部考核信息
    public List<Task> getAllTask();
    //获取单个考核信息的合法性
    public InfoCode getByTaskIdLegal(int taskId);
    //获取单个考核信息
    public Task getByTaskId(int taskId);
}
