package com.api.service;

import com.api.model.Assignment;
import com.api.exception.InfoCode;

import java.util.logging.Logger;

/**
 * 学生进行作业操作的Service层接口
 *
 * @author 李星源
 * @version 1.0
 */
public interface StudentAssignmentService {
    //日志信息
    static Logger logger=Logger.getLogger(StudentAssignmentService.class.getName());

    //提交作业的合法性
    public InfoCode submitLegal(Assignment assignment);
    //提交作业
    public void submit(Assignment assignment);
    //修改作业（全部）的合法性
    public InfoCode updateAllSubmitLegal(Assignment assignment);
    //修改作业（全部）
    public void updateAllSubmit(Assignment assignment);
    //修改作业（部分）的合法性
    public InfoCode updateSubmitLegal(Assignment assignment);
    //修改作业（部分）
    public void updateSubmit(Assignment assignment);
    //查询作业的合法性
    public InfoCode readSubmitLegal(int taskId,String accountId);
    //查询作业
    public Assignment readSubmit(int taskId,String accountId);
}
