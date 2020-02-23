package com.api.service;

import com.api.dao.AssignmentDaoImpl;
import com.api.dao.BaseCRUD;
import com.api.dao.TaskDaoImpl;
import com.api.model.Assignment;
import com.api.exception.InfoCode;
import com.api.model.Task;

import java.util.List;

/**
 * 实现管理员操作Service层接口（非JSON）接口
 *
 * @author 李星源
 * @version 1.0
 */
public class AdministratorParamServiceImpl implements AdministratorParamService {
    IsTaskLegal itl=new IsTaskLegalImpl();
    IsAssignmentLegal ial=new IsAssignmentLegalImpl();
    BaseCRUD<Task> taskBaseCRUD=new TaskDaoImpl();//调用Dao层接口
    BaseCRUD<Assignment> assignmentBaseCRUD=new AssignmentDaoImpl();//调用Dao层接口

    /**
     * 判断删除考核的合法性
     *
     * @param taskId 考核id
     * @return InfoCode对象
     */
    @Override
    public InfoCode deleteTaskLegal(int taskId) {
        Task task=taskBaseCRUD.readByKey(new Object[]{taskId});

        if (!itl.taskId(taskId)){
            return InfoCode.TASK_ID_ERROR;
        } else if (task==null){
            return InfoCode.TASK_ID_NOT_EXIST;
        }
        return InfoCode.OK;
    }

    /**
     * 删除考核操作
     *
     * @param taskId 考核id
     */
    @Override
    public void deleteTask(int taskId) {
        assignmentBaseCRUD.deleteAll(new Object[]{taskId});//删除考核对应的所有作业
        taskBaseCRUD.delete(new Object[]{taskId});
    }

    /**
     * 读取考核（通过考核id）的合法性
     *
     * @param taskId 考核id
     * @return InfoCode对象
     */
    @Override
    public InfoCode readTaskByTaskIdLegal(int taskId) {
        Task task=taskBaseCRUD.readByKey(new Object[]{taskId});

        if (!itl.taskId(taskId)){
            return InfoCode.TASK_ID_ERROR;
        } else if (task==null){
            return InfoCode.TASK_ID_NOT_EXIST;
        }
        return InfoCode.OK;
    }

    /**
     * 读取考核（通过考核id）
     *
     * @param taskId 考核id
     * @return Task对象
     */
    @Override
    public Task readTaskByTaskId(int taskId) {
        return taskBaseCRUD.readByKey(new Object[]{taskId});
    }

    /**
     * 读取所有作业（通过考核id）的合法性
     *
     * @param taskId 考核id
     * @return InfoCode对象
     */
    @Override
    public InfoCode readAllAssignmentLegal(int taskId) {
        if (!ial.taskId(taskId)){
            return InfoCode.TASK_ID_ERROR;
        }
        List<Assignment> assignmentList=assignmentBaseCRUD.readAll(new Object[]{taskId});
        if (assignmentList.size()>0){
            return InfoCode.OK;
        }
        return InfoCode.DATA_EMPTY;
    }

    /**
     * 读取所有作业（通过考核id）操作
     *
     * @param taskId 考核id
     * @return Assignment列表
     */
    @Override
    public List<Assignment> readAllAssignment(int taskId) {
        return assignmentBaseCRUD.readAll(new Object[]{taskId});
    }

    /**
     * 读取作业（通过考核id、用户id）的合法性
     *
     * @param taskId 考核id
     * @param accountId 用户id
     * @return InfoCode对象
     */
    @Override
    public InfoCode readAssignmentLegal(int taskId, String accountId) {
        if (!ial.taskId(taskId)){
            return InfoCode.TASK_ID_ERROR;
        } else if (accountId==null){
            return InfoCode.ACCOUNT_Id_EMPTY;
        } else if (!ial.accountId(accountId)){
            return InfoCode.ACCOUNT_Id_ERROR;
        }
        Assignment assignment=assignmentBaseCRUD.readByKey(new Object[]{taskId,accountId});
        if (assignment==null){
            return InfoCode.DATA_EMPTY;
        }
        return InfoCode.OK;
    }

    /**
     * 读取作业（通过考核id、用户id）操作
     *
     * @param taskId 考核id
     * @param accountId 用户id
     * @return Assignment对象
     */
    @Override
    public Assignment readAssignment(int taskId, String accountId) {
        return assignmentBaseCRUD.readByKey(new Object[]{taskId,accountId});
    }

    /**
     * 更新作业状态（1为审核中，2为通过，3为未通过）的合法性
     *
     * @param taskId 考核id
     * @param accountId 用户id
     * @param mark 审核状态（1为审核中，2为通过，3为未通过）
     * @return InfoCode对象
     */
    @Override
    public InfoCode updateMarkLegal(int taskId, String accountId, int mark) {
        if (!ial.taskId(taskId)){
            return InfoCode.TASK_ID_ERROR;
        } else if (accountId==null){
            return InfoCode.ACCOUNT_Id_EMPTY;
        } else if (!ial.accountId(accountId)){
            return InfoCode.ACCOUNT_Id_ERROR;
        } else if (!ial.mark(mark)){
            return InfoCode.MARK_ERROR;
        }
        Assignment assignment=assignmentBaseCRUD.readByKey(new Object[]{taskId,accountId});
        if (assignment==null){
            return InfoCode.DATA_EMPTY;
        }
        return InfoCode.OK;
    }

    /**
     * 更新作业状态（1为审核中，2为通过，3为未通过）操作
     *
     * @param taskId 考核id
     * @param accountId 用户id
     * @param mark 审核状态（1为审核中，2为通过，3为未通过）
     */
    @Override
    public void updateMark(int taskId, String accountId, int mark) {
        assignmentBaseCRUD.update(new String[]{"mark"},new Object[]{mark,taskId,accountId});
    }

}
