package com.api.service;

import com.api.exception.InfoCode;
import com.api.model.Task;

import java.text.ParseException;
import java.util.Date;

/**
 * 实现Task属性合法性的判断
 *
 * @author 李星源
 * @version 1.0
 */
public class IsTaskLegalImpl implements IsTaskLegal {

    /**
     * 用于判断考核id的合法性
     *
     * @param ti 考核id
     * @return true为合法，false为非法
     */
    @Override
    public boolean taskId(int ti) {
        if(ti<1){
            return false;
        }
        return true;
    }

    /**
     * 用于判断考核名称的合法性
     *
     * @param tn 考核名称
     * @return true为合法，false为非法
     */
    @Override
    public boolean taskName(String tn) {
        if(tn!=null){
            if(tn.length()>0&&tn.length()<=15){
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * 用于判断考核内容的合法性
     *
     * @param tc 考核内容
     * @return true为合法，false为非法
     */
    @Override
    public boolean taskContent(String tc) {
        if(tc!=null){
            if(tc.length()<=30000){
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * 用于判断考核开始日期的合法性
     *
     * @param sd 开始日期
     * @param dl 截止日期
     * @return true为合法，false为非法
     */
    @Override
    public boolean startDate(Date sd, Date dl){

        if(sd!=null&&dl!=null){
            try {
                Date min=format.parse(minDate);
                Date max=format.parse(maxDate);

                if(sd.after(dl)||sd.before(min)||sd.after(max)){
                    return false;
                }
            } catch (ParseException e){
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 判断截止日期的合法性
     *
     * @param sd 开始日期
     * @param dl 截止日期
     * @return true为合法，false为非法
     */
    @Override
    public boolean deadline(Date sd, Date dl){
        if(sd!=null&&dl!=null){
            try {
                Date min=format.parse(minDate);
                Date max=format.parse(maxDate);

                if(dl.before(sd)||dl.before(min)||dl.after(max)){
                    return false;
                }
            } catch (ParseException e){
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 判断Task属性是否均合法
     *
     * @param task Task对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode all(Task task){
        if (!this.taskId(task.getTaskId())) {
            return InfoCode.TASK_ID_ERROR;
        } else if (!this.taskName(task.getTaskName())) {
            return InfoCode.TASK_NAME_ERROR;
        } else if (!this.taskContent(task.getTaskContent())) {
            return InfoCode.TASK_CONTENT_ERROR;
        } else if (!this.startDate(task.getStartDate(), task.getDeadline())) {
            return InfoCode.START_DATE_ERROR;
        } else if (!this.deadline(task.getStartDate(), task.getDeadline())) {
            return InfoCode.DEADLINE_ERROR;
        }
        return InfoCode.OK;
    }

    /**
     * 用于判断Task属性是否为空
     *
     * @param task Task对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode isEmpty(Task task) {
        if (task.getTaskName()==null){
            return InfoCode.TASK_NAME_EMPTY;
        } else if (task.getTaskContent()==null){
            return InfoCode.TASK_CONTENT_EMPTY;
        } else if (task.getStartDate()==null){
            return InfoCode.START_DATE_EMPTY;
        } else if (task.getDeadline()==null){
            return InfoCode.DEADLINE_EMPTY;
        }
        return InfoCode.OK;
    }

}
