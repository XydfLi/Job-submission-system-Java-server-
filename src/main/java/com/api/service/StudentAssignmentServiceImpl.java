package com.api.service;

import com.api.dao.AccountDaoImpl;
import com.api.dao.AssignmentDaoImpl;
import com.api.dao.BaseCRUD;
import com.api.dao.TaskDaoImpl;
import com.api.model.Account;
import com.api.model.Assignment;
import com.api.exception.InfoCode;
import com.api.model.Task;

import java.text.ParseException;

/**
 * 实现学生进行作业操作的Service层接口
 *
 * @author 李星源
 * @version 1.0
 */
public class StudentAssignmentServiceImpl implements StudentAssignmentService {
    IsAssignmentLegal ial=new IsAssignmentLegalImpl();
    BaseCRUD<Assignment> assignmentBaseCRUD=new AssignmentDaoImpl();//调用Dao层接口
    BaseCRUD<Task> tbc=new TaskDaoImpl();//调用Dao层接口
    BaseCRUD<Account> abc=new AccountDaoImpl();//调用Dao层接口

    /**
     * 提交作业的合法性
     *
     * @param assignment Assignment对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode submitLegal(Assignment assignment){
        InfoCode infoCode=ial.isEmpty(assignment);
        if (infoCode!=InfoCode.OK){
            return infoCode;
        }
        infoCode=ial.all(assignment);
        if (infoCode!=InfoCode.OK){
            return infoCode;
        }
        Task task=tbc.readByKey(new Object[]{assignment.getTaskId()});
        if (task==null){
            return InfoCode.TASK_ID_NOT_EXIST;
        }
        Account account=abc.readByKey(new Object[]{assignment.getAccountId()});
        if (account==null){
            return InfoCode.ACCOUNT_Id_NOT_EXIST;
        }
        Assignment assignment1=assignmentBaseCRUD.readByKey(new Object[]{assignment.getTaskId(),assignment.getAccountId()});
        if (assignment1!=null){
            return InfoCode.SUBMIT_ONLY_ONCE;
        }
        return InfoCode.OK;
    }

    /**
     * 提交作业操作
     *
     * @param assignment Assignment对象
     */
    @Override
    public void submit(Assignment assignment) {
        assignment.setMark(1);
        assignmentBaseCRUD.create(assignment);
    }

    /**
     * 更新作业（全部）的合法性
     *
     * @param assignment Assignment对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode updateAllSubmitLegal(Assignment assignment){
        InfoCode infoCode=ial.isEmpty(assignment);
        if (infoCode!=InfoCode.OK){
            return infoCode;
        }
        infoCode=ial.all(assignment);
        if (infoCode!=InfoCode.OK){
            return infoCode;
        }
        Task task=tbc.readByKey(new Object[]{assignment.getTaskId()});
        if (task==null){
            return InfoCode.TASK_ID_NOT_EXIST;
        }
        Account account=abc.readByKey(new Object[]{assignment.getAccountId()});
        if (account==null){
            return InfoCode.ACCOUNT_Id_NOT_EXIST;
        }
        return InfoCode.OK;
    }

    /**
     * 更新作业（全部）操作
     *
     * @param assignment Assignment对象
     */
    @Override
    public void updateAllSubmit(Assignment assignment) {
        assignmentBaseCRUD.update(new String[]{"jobContent","subTime"},
                new Object[]{assignment.getJobContent(),assignment.getSubTime(),
                assignment.getTaskId(),assignment.getAccountId()});
    }

    /**
     * 更新作业（部分）的合法性
     *
     * @param assignment Assignment对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode updateSubmitLegal(Assignment assignment){
        InfoCode infoCode=ial.isEmpty(assignment);
        if (infoCode!=InfoCode.OK){
            return infoCode;
        }
        infoCode=ial.all(assignment);
        if (infoCode!=InfoCode.OK){
            return infoCode;
        }
        Task task=tbc.readByKey(new Object[]{assignment.getTaskId()});
        if (task==null){
            return InfoCode.TASK_ID_NOT_EXIST;
        }
        Account account=abc.readByKey(new Object[]{assignment.getAccountId()});
        if (account==null){
            return InfoCode.ACCOUNT_Id_NOT_EXIST;
        }
        return InfoCode.OK;
    }

    /**
     * 更新作业（部分）操作
     * 只有非空数据需要更新
     *
     * @param assignment Assignment对象
     */
    @Override
    public void updateSubmit(Assignment assignment) {
        if (assignment.getJobContent()!=null){
            assignmentBaseCRUD.update(new String[]{"jobContent"},
                    new Object[]{assignment.getJobContent(),
                            assignment.getTaskId(),assignment.getAccountId()});
        }
        if (assignment.getSubTime()!=null){
            assignmentBaseCRUD.update(new String[]{"subTime"},
                    new Object[]{assignment.getSubTime(),
                            assignment.getTaskId(),assignment.getAccountId()});
        }
    }

    /**
     * 读取作业的合法性
     *
     * @param taskId 考核id
     * @param accountId 用户id
     * @return InfoCode对象
     */
    @Override
    public InfoCode readSubmitLegal(int taskId, String accountId) {
        if (!ial.taskId(taskId)){
            return InfoCode.TASK_ID_ERROR;
        } else if (accountId==null){
            return InfoCode.ACCOUNT_Id_EMPTY;
        } else if (!ial.accountId(accountId)){
            return InfoCode.ACCOUNT_Id_ERROR;
        }
        Task task=tbc.readByKey(new Object[]{taskId});
        if (task==null){
            return InfoCode.TASK_ID_NOT_EXIST;
        }
        Account account=abc.readByKey(new Object[]{accountId});
        if (account==null){
            return InfoCode.ACCOUNT_Id_NOT_EXIST;
        }
        Assignment assignment=assignmentBaseCRUD.readByKey(new Object[]{taskId,accountId});
        if (assignment==null){
            return InfoCode.DATA_EMPTY;
        }
        return InfoCode.OK;
    }

    /**
     * 读取作业操作
     *
     * @param taskId 考核id
     * @param accountId 用户id
     * @return Assignment对象
     */
    @Override
    public Assignment readSubmit(int taskId, String accountId) {
        return assignmentBaseCRUD.readByKey(new Object[]{taskId,accountId});
    }

}
