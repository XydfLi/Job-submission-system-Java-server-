package com.api.service;

import com.api.model.Assignment;
import com.api.exception.InfoCode;
import com.api.model.Task;

import java.util.List;
import java.util.logging.Logger;

/**
 * 管理员操作Service层接口（非JSON）
 *
 * @author 李星源
 * @version 1.0
 */
public interface AdministratorParamService {
    //日志信息
    static Logger logger=Logger.getLogger(AdministratorParamService.class.getName());

    //删除考核的合法性
    public InfoCode deleteTaskLegal(int taskId);
    //删除考核
    public void deleteTask(int taskId);
    //查询单个考核的合法性
    public InfoCode readTaskByTaskIdLegal(int taskId);
    //查询单个考核
    public Task readTaskByTaskId(int taskId);
    //查询考核全部提交信息的合法性
    public InfoCode readAllAssignmentLegal(int taskId);
    //查询考核全部提交信息
    public List<Assignment> readAllAssignment(int taskId);
    //查询单个提交信息的合法性
    public InfoCode readAssignmentLegal(int taskId,String accountId);
    //查询单个提交信息
    public Assignment readAssignment(int taskId,String accountId);
    //修改作业标记的合法性
    public InfoCode updateMarkLegal(int taskId,String accountId,int mark);
    //修改作业标记
    public void updateMark(int taskId,String accountId,int mark);
}
