package com.api.controller;

import com.api.exception.DataException;
import com.api.exception.InfoCode;
import com.api.exception.TaskException;
import com.api.format.FastJson;
import com.api.model.ReadAllTaskData;
import com.api.model.ResponseMes;
import com.api.model.Task;
import com.api.model.TaskWithNumber;
import com.api.service.StudentTaskService;
import com.api.service.StudentTaskServiceImpl;
import lombok.NonNull;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 用于学生对考核的操作
 * TODO : 添加签发token和验证token
 *
 * @author 李星源
 * @version 1.0
 */
@Path("/students/tasks")
@PermitAll()
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@FastJson
public class StudentTaskController {
    //日志信息
    private static Logger logger=Logger.getLogger(StudentTaskController.class.getName());
    StudentTaskService st=new StudentTaskServiceImpl();//调用Service层接口
    ResponseMes responseMes=new ResponseMes();//统一响应类

    /**
     * 查询全部考核
     *
     * @return ResponseMes对象，data为ReadAllTaskData
     */
    @GET
    public ResponseMes getAllTask(){
        List<Task> taskList=st.getAllTask();
        List<TaskWithNumber> twl=TaskWithNumber.addNumber(taskList);

        ReadAllTaskData ratd=new ReadAllTaskData();
        ratd.setCount(taskList.size());
        ratd.setTaskWithNumberList(twl);
        if (taskList.size()>0){
            return responseMes.success(ratd);
        } else {
            throw new DataException(InfoCode.DATA_EMPTY,null);
        }
    }

    /**
     * 查询考核（通过考核id）
     *
     * @param taskId 考核id
     * @return ResponseMes对象，data为TaskWithNumber
     */
    @GET
    @Path("/{taskId}")
    public ResponseMes getTask(@NonNull @PathParam("taskId") int taskId){
        InfoCode infoCode=st.getByTaskIdLegal(taskId);//合法性判断

        if (infoCode!=InfoCode.OK){
            throw new TaskException(infoCode,null);
        }

        Task task=st.getByTaskId(taskId);//查询考核
        TaskWithNumber taskWithNumber=TaskWithNumber.addNumber(task);
        return responseMes.success(taskWithNumber);
    }

}
