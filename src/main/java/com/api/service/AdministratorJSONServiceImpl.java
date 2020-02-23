package com.api.service;

import com.api.dao.*;
import com.api.model.Assignment;
import com.api.model.BatchAssingment;
import com.api.exception.InfoCode;
import com.api.model.Task;

import java.util.List;

/**
 * 实现管理员操作Service层接口（JSON）
 *
 * @author 李星源
 * @version 1.0
 */
public class AdministratorJSONServiceImpl implements AdministratorJSONService {
    BaseCRUD<Task> taskBaseCRUD=new TaskDaoImpl();//调用Task的Dao层接口
    BaseCRUD<Assignment> assignmentBaseCRUD=new AssignmentDaoImpl();//调用Assignment的Dao层接口
    IsTaskLegal itl=new IsTaskLegalImpl();//合法性判断接口

    /**
     * 判断更新考核（全部）的合法性
     *
     * @param task Task对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode updateAllTaskLegal(Task task){
        InfoCode infoCode=itl.isEmpty(task);
        if (infoCode!=InfoCode.OK){
            return infoCode;
        }
        infoCode=itl.all(task);
        if (infoCode!=InfoCode.OK){
            return infoCode;
        }
        Task task1=taskBaseCRUD.readByKey(new Object[]{task.getTaskId()});
        if (task1==null){
            return InfoCode.TASK_ID_NOT_EXIST;
        }
        return InfoCode.OK;
    }

    /**
     * 更新考核（全部）操作
     *
     * @param task Task对象
     */
    @Override
    public void updateAllTask(Task task){
        taskBaseCRUD.update(new String[]{"taskName","taskContent","startDate","deadline"},
                new Object[]{task.getTaskName(),task.getTaskContent(),
                task.getStartDate(),task.getDeadline(),
                task.getTaskId()});
    }

    /**
     * 判断更新考核（部分）的合法性
     *
     * @param task Task对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode updateTaskLegal(Task task){
        Task task1=taskBaseCRUD.readByKey(new Object[]{task.getTaskId()});
        InfoCode infoCode=itl.all(task);
        if (infoCode!=InfoCode.OK){
            return infoCode;
        } else if (task1==null){
            return InfoCode.TASK_ID_NOT_EXIST;
        }
        return InfoCode.OK;
    }

    /**
     * 更新考核（部分）
     * 数据非空则要更新
     *
     * @param task Task对象
     */
    @Override
    public void updateTask(Task task) {
        if (task.getTaskName()!=null){
            taskBaseCRUD.update(new String[]{"taskName"},
                    new Object[]{task.getTaskName(),task.getTaskId()});
        }
        if (task.getTaskContent()!=null){
            taskBaseCRUD.update(new String[]{"taskContent"},
                    new Object[]{task.getTaskContent(),task.getTaskId()});
        }
        if (task.getStartDate()!=null){
            taskBaseCRUD.update(new String[]{"startDate"},
                    new Object[]{task.getStartDate(),task.getTaskId()});
        }
        if (task.getDeadline()!=null){
            taskBaseCRUD.update(new String[]{"deadline"},
                    new Object[]{task.getDeadline(),task.getTaskId()});
        }
    }

    /**
     * 用于判断增加考核的合法性
     *
     * @param task Task对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode createTaskLegal(Task task){
        InfoCode infoCode=itl.isEmpty(task);
        if (infoCode!=InfoCode.OK){
            return infoCode;
        }
        task.setTaskId(2);
        infoCode=itl.all(task);
        return infoCode;
    }

    /**
     * 增加考核操作
     *
     * @param task Task对象
     * @return 考核id（数据库自增主键）
     */
    @Override
    public int createTask(Task task) {
        return taskBaseCRUD.create(task);
    }

    /**
     * 获取全部考核的合法性
     * TODO : 暂时为正确
     *
     * @return InfoCode.OK
     */
    @Override
    public InfoCode readAllTaskLegal() {
        return InfoCode.OK;
    }

    /**
     * 读取全部考核操作
     *
     * @return Task列表
     */
    @Override
    public List<Task> readAllTask() {
        return taskBaseCRUD.readAll(null);
    }

    /**
     * 批量删除作业的合法性
     *
     * @param batchAssingment BatchAssingment对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode batchDeleteAssignmentLegal(BatchAssingment batchAssingment) {
        Task task=taskBaseCRUD.readByKey(new Object[]{batchAssingment.getTaskId()});
        if (task==null){//考核是否存在
            return InfoCode.TASK_ID_NOT_EXIST;
        }
        int count=batchAssingment.getCount();
        String accountId="";
        for (int i=0;i<count;i++){//用户是否存在
            accountId=batchAssingment.getAccountIdList().get(i);
            Assignment assignment=assignmentBaseCRUD.readByKey(new Object[]{batchAssingment.getTaskId(),accountId});
            if (assignment==null){
                return InfoCode.ACCOUNT_Id_NOT_EXIST;
            }
        }
        return InfoCode.OK;
    }

    /**
     * 批量删除作业操作
     *
     * @param batchAssingment BatchAssingment对象
     */
    @Override
    public void batchDeleteAssignment(BatchAssingment batchAssingment,String path0){
        BaseBatchCRUD abdi=new AssignmentBatchDaoImpl();
        DeleteFiles.deleteFiles(batchAssingment,path0);
        abdi.delete(batchAssingment.getCount(),
                new Object[]{batchAssingment.getTaskId(),batchAssingment.getAccountIdList()});
    }

}
