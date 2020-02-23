package com.api.service;

import com.api.dao.AccountDaoImpl;
import com.api.dao.AssignmentDaoImpl;
import com.api.dao.BaseCRUD;
import com.api.dao.TaskDaoImpl;
import com.api.model.Account;
import com.api.exception.InfoCode;
import com.api.model.Assignment;
import com.api.model.Task;

import java.util.Date;

/**
 * 实现文件操作的Service层接口
 *
 * @author 李星源
 * @version 1.0
 */
public class FileServiceImpl implements FileService {

    /**
     * 上传文件合法性判断
     *
     * @param assignment 上传作业信息
     * @param ext 文件后缀名
     * @return InfoCode对象
     */
    @Override
    public InfoCode uploadFileLegal(Assignment assignment,String ext) {
        IsAssignmentLegal ial=new IsAssignmentLegalImpl();
        if (ial.all(assignment)!=InfoCode.OK){
            return InfoCode.TASK_ID_ERROR;
        }

        BaseCRUD<Task> taskBaseCRUD=new TaskDaoImpl();
        BaseCRUD<Account> accountBaseCRUD=new AccountDaoImpl();
        Task task=taskBaseCRUD.readByKey(new Object[]{assignment.getTaskId()});
        Account account=accountBaseCRUD.readByKey(new Object[]{assignment.getAccountId()});
        if (task==null){
            return InfoCode.TASK_ID_NOT_EXIST;
        } else if(account==null){
            return InfoCode.ACCOUNT_Id_NOT_EXIST;
        } else if (!ext.equals("zip")){
            return InfoCode.FILE_FORMAT_ERROR;
        }

        BaseCRUD<Assignment> assignmentBaseCRUD=new AssignmentDaoImpl();
        Assignment assignment0=assignmentBaseCRUD.readByKey(new Object[]{assignment.getTaskId(),assignment.getAccountId()});
        if (assignment0!=null){
            return InfoCode.FILE_EXIST;
        }

        return InfoCode.OK;
    }

}
