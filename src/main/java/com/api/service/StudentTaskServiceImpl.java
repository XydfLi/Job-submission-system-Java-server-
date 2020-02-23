package com.api.service;

import com.api.dao.BaseCRUD;
import com.api.dao.TaskDaoImpl;
import com.api.exception.InfoCode;
import com.api.model.Task;

import java.util.List;

/**
 * 实现学生进行考核操作的Service层接口
 *
 * @author 李星源
 * @version 1.0
 */
public class StudentTaskServiceImpl implements StudentTaskService {
    BaseCRUD<Task> taskBaseCRUD=new TaskDaoImpl();//调用Dao层接口
    IsTaskLegal itl=new IsTaskLegalImpl();

    /**
     * 查询全部考核
     *
     * @return Task列表
     */
    @Override
    public List<Task> getAllTask() {
        return taskBaseCRUD.readAll(null);
    }

    /**
     * 查询考核（通过考核id）的合法性
     *
     * @param taskId 考核id
     * @return InfoCode对象
     */
    @Override
    public InfoCode getByTaskIdLegal(int taskId) {
        if (!itl.taskId(taskId)){
            return InfoCode.TASK_ID_ERROR;
        }
        Task task=taskBaseCRUD.readByKey(new Object[]{taskId});
        if (task==null){
            return InfoCode.TASK_ID_NOT_EXIST;
        }
        return InfoCode.OK;
    }

    /**
     * 查询考核（通过考核id）操作
     *
     * @param taskId 考核id
     * @return Task对象
     */
    @Override
    public Task getByTaskId(int taskId) {
        return taskBaseCRUD.readByKey(new Object[]{taskId});
    }

}
