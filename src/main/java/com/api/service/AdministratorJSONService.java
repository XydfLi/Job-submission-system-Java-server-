package com.api.service;

import com.api.model.BatchAssingment;
import com.api.exception.InfoCode;
import com.api.model.Task;

import java.util.List;
import java.util.logging.Logger;

/**
 * 管理员操作Service层接口（JSON）
 *
 * @author 李星源
 * @version 1.0
 */
public interface AdministratorJSONService {
    //日志信息
    static Logger logger=Logger.getLogger(AdministratorJSONService.class.getName());

    //修改全部考核的合法性
    public InfoCode updateAllTaskLegal(Task task);
    //修改全部考核
    public void updateAllTask(Task task);
    //修改部分考核的合法性
    public InfoCode updateTaskLegal(Task task);
    //修改部分考核
    public void updateTask(Task task);
    //增加考核的合法性
    public InfoCode createTaskLegal(Task task);
    //增加考核
    public int createTask(Task task);
    //查询全部考核的合法性
    public InfoCode readAllTaskLegal();
    //查询全部考核
    public List<Task> readAllTask();
    //批量删除作业的合法性
    public InfoCode batchDeleteAssignmentLegal(BatchAssingment batchAssingment);
    //批量删除作业
    public void batchDeleteAssignment(BatchAssingment batchAssingment,String path0);
}
