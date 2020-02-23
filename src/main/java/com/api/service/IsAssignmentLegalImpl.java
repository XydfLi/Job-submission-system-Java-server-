package com.api.service;

import com.api.dao.BaseCRUD;
import com.api.dao.TaskDaoImpl;
import com.api.model.Assignment;
import com.api.exception.InfoCode;
import com.api.model.Task;

import java.text.ParseException;
import java.util.Date;

/**
 * 实现Assignment属性合法性的判断
 *
 * @author 李星源
 * @version 1.0
 */
public class IsAssignmentLegalImpl implements IsAssignmentLegal {

    /**
     * 用于判断考核id的合法性
     *
     * @param ti 考核id
     * @return true为合法，false为非法
     */
    @Override
    public boolean taskId(int ti) {
        IsTaskLegal isTaskLegal=new IsTaskLegalImpl();
        return isTaskLegal.taskId(ti);
    }

    /**
     * 用于判断用户id的合法性
     *
     * @param ai 用户id
     * @return true为合法，false为非法
     */
    @Override
    public boolean accountId(String ai) {
        if (ai!=null){
            IsAccountLegal isAccountLegal=new IsAccountLegalImpl();
            return isAccountLegal.accountId(ai);
        }
        return true;
    }

    /**
     * 用于判断作业内容的合法性
     * 长度小于等于30000
     *
     * @param jc 作业内容
     * @return true为合法，false为非法
     */
    @Override
    public boolean jobContent(String jc) {
        if (jc!=null){
            if(jc.length()<=30000){
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * 用于判断提交时间的合法性
     *
     * @param assignment Assignment对象
     * @return true为合法，false为非法
     */
    @Override
    public boolean subTime(Assignment assignment){
        if (assignment.getSubTime()!=null){
            try {
                Date min=format.parse(minDate);
                Date max=format.parse(maxDate);
                BaseCRUD<Task> tbc=new TaskDaoImpl();
                Task task=tbc.readByKey(new Object[]{assignment.getTaskId()});

                if(assignment.getSubTime().before(min)||assignment.getSubTime().after(max)){
                    return false;
                }
                if (task!=null){
                    if (assignment.getSubTime().after(task.getDeadline())
                            ||assignment.getSubTime().before(task.getStartDate())){
                        return false;
                    }
                }
            } catch (ParseException e){
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 用于判断身份标记的合法性
     *
     * @param mark 1为管理员，2为学生
     * @return true为合法，false为非法
     */
    @Override
    public boolean mark(int mark) {
        if (mark>0&&mark<4){
            return true;
        }
        return false;
    }

    /**
     * 判断Assignment属性是否均合法
     *
     * @param assignment Assignment对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode all(Assignment assignment){
        if (!this.taskId(assignment.getTaskId())){
            return InfoCode.TASK_ID_ERROR;
        } else if (!this.accountId(assignment.getAccountId())){
            return InfoCode.ACCOUNT_Id_ERROR;
        } else if (!this.jobContent(assignment.getJobContent())){
            return InfoCode.JOB_CONTENT_ERROR;
        } else if (!this.subTime(assignment)){
            return InfoCode.SUB_TIME_ERROR;
        } else if (!this.mark(assignment.getMark())){
            return InfoCode.MARK_ERROR;
        }
        return InfoCode.OK;
    }

    /**
     * 用于判断Assignment属性是否为空
     *
     * @param assignment Assignment对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode isEmpty(Assignment assignment) {
        if (assignment.getAccountId()==null){
            return InfoCode.ACCOUNT_Id_EMPTY;
        } else if (assignment.getSubTime()==null){
            return InfoCode.SUB_TIME_EMPTY;
        }
        return InfoCode.OK;
    }

}
