package com.api.controller;

import com.api.exception.*;
import com.api.format.FastJson;
import com.api.interceptor.reader.Student;
import com.api.model.ResponseMes;
import com.api.model.*;
import com.api.service.AdministratorJSONService;
import com.api.service.AdministratorJSONServiceImpl;
import lombok.NonNull;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

/**
 * 用于进行管理员操作
 * 更新考核（全部）、更新考核（部分）、增加考核、读取全部考核、批量删除作业
 * 通信为JSON
 * TODO : 添加签发token和验证token
 *
 * @author 李星源
 * @version 1.0
 */
//TODO : 也可以使用方法中的@RolesAllowed("Administrator")
//@RolesAllowed("Administrator")
@Student//拦截学生操作
@Path("/administrators")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@FastJson
public class AdministratorJSONController {
    //日志信息
    private static Logger logger=Logger.getLogger(AdministratorJSONController.class.getName());
    ResponseMes responseMes=new ResponseMes();//统一相应类
    AdministratorJSONService adjs=new AdministratorJSONServiceImpl();//调用Service层接口

    /**
     * 更新考核（全部）
     *
     * @param task Task对象
     * @return ResponseMes对象，data为0
     */
    @PUT
    @Path("/tasks")
    public ResponseMes updateAllTask(@NonNull Task task){
        InfoCode infoCode=adjs.updateAllTaskLegal(task);//合法性判断
        if (infoCode!=InfoCode.OK){
            throw new TaskException(infoCode,null);//统一异常处理
        } else {
            adjs.updateAllTask(task);//更新操作
            return responseMes.success(0);
        }
    }

    /**
     * 更新考核（部分）
     *
     * @param task Task对象
     * @return ResponseMes对象，data为0
     */
    @PATCH
    @Path("/tasks")
    public ResponseMes updateTask(@NonNull Task task){
        InfoCode infoCode=adjs.updateTaskLegal(task);//合法性判断
        if (infoCode!=InfoCode.OK){
            throw new TaskException(infoCode,null);
        } else {
            adjs.updateTask(task);//更新操作
            return responseMes.success(0);
        }
    }

    /**
     * 增加考核
     *
     * @param task Task对象
     * @return ResponseMes对象，data为数据库自增主键生成的考核id
     */
    @POST
    @Path("/tasks")
    public ResponseMes createTask(Task task){
        InfoCode infoCode=adjs.createTaskLegal(task);//合法性判断
        if (infoCode!=InfoCode.OK){
            throw new TaskException(infoCode,null);
        } else {
            return responseMes.success(adjs.createTask(task));//增加考核
        }
    }

    /**
     * 读取全部考核
     *
     * @return ResponseMes对象，data为考核列表
     */
    @GET
    @Path("/tasks")
    public ResponseMes readAllTask(){
        List<Task> taskList=adjs.readAllTask();//读取数据
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
     * 批量删除作业
     *
     * @param batchAssingment BatchAssingment对象
     * @return ResponseMes对象，data为0
     */
    @PUT
    @Path("/assignments")
    public ResponseMes deleteAssignments(@NonNull BatchAssingment batchAssingment,
                                         @Context ServletContext ctx){
        InfoCode infoCode=adjs.batchDeleteAssignmentLegal(batchAssingment);
        if (infoCode!=InfoCode.OK){
            throw new AssignmentException(infoCode,null);
        } else {
            //如果文件夹不存在则创建文件夹
            File file=new File(ctx.getRealPath("/WEB-INF/Assignments"));
            if (!file.exists()){
                file.mkdir();
            }

            adjs.batchDeleteAssignment(batchAssingment,ctx.getRealPath("/WEB-INF/Assignments"));
            return responseMes.success(0);
        }
    }

}
