package com.api.controller;

import com.api.exception.AssignmentException;
import com.api.exception.InfoCode;
import com.api.exception.TaskException;
import com.api.format.FastJson;
import com.api.interceptor.reader.Student;
import com.api.model.ResponseMes;
import com.api.model.*;
import com.api.service.AdministratorParamService;
import com.api.service.AdministratorParamServiceImpl;
import lombok.NonNull;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

/**
 * 用于进行管理员操作
 * 删除考核、查询考核、查询全部作业、查询作业、更新作业审核状态
 * 通信不全为JSON
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
public class AdministratorParamController {
    //日志信息
    private static Logger logger=Logger.getLogger(AdministratorParamController.class.getName());
    AdministratorParamService aps=new AdministratorParamServiceImpl();//调用Service层接口
    ResponseMes responseMes=new ResponseMes();//统一响应类

    /**
     * 删除考核（通过考核id）
     *
     * @param taskId 考核id
     * @return ResponseMes对象，data为0
     */
    @DELETE
    @Path("/tasks/{taskId}")
    public ResponseMes deleteTask(@NonNull @PathParam("taskId") int taskId){
        InfoCode infoCode=aps.deleteTaskLegal(taskId);//合法性判断
        if (infoCode!=InfoCode.OK){
            throw new TaskException(infoCode,null);
        } else {
            aps.deleteTask(taskId);//进行删除操作
            return responseMes.success(0);
        }
    }

    /**
     * 查询考核（通过考核id）
     *
     * @param taskId 考核id
     * @return ResponseMes对象，data为taskWithNumber对象
     */
    @GET
    @Path("/tasks/{taskId}")
    public ResponseMes readTask(@NonNull @PathParam("taskId") int taskId){
        InfoCode infoCode=aps.readTaskByTaskIdLegal(taskId);//合法性判断
        if (infoCode!=InfoCode.OK){
            throw new TaskException(infoCode,null);
        } else {
            Task task=aps.readTaskByTaskId(taskId);//查询考核
            TaskWithNumber taskWithNumber=TaskWithNumber.addNumber(task);
            return responseMes.success(taskWithNumber);
        }
    }

    /**
     * 查询所有作业（通过考核id）
     *
     * @param taskId 考核id
     * @return ResponseMes对象，data为AssignmentWithName列表
     */
    @GET
    @Path("/assignments/{taskId}")
    public ResponseMes readAllAssignments(@NonNull @PathParam("taskId") int taskId){
        InfoCode infoCode=aps.readAllAssignmentLegal(taskId);//合法性判断
        if (infoCode!=InfoCode.OK){
            throw new AssignmentException(infoCode,null);
        } else {
            ReadAllAssignmentData raa=new ReadAllAssignmentData();
            List<Assignment> assignmentList=aps.readAllAssignment(taskId);
            AssignmentWithName awn=new AssignmentWithName();
            List<AssignmentWithName> awnl=awn.addName(assignmentList);//转换类型
            raa.setCount(awnl.size());
            raa.setAssignmentList(awnl);
            return responseMes.success(raa);
        }
    }

    /**
     * 查询作业（通过考核id、用户id）
     *
     * @param taskId 考核id
     * @param accountId 用户id
     * @return ResponseMes对象，data为AssignmentWithName对象
     */
    @GET
    @Path("/assignments/{taskId}/{accountId}")
    public ResponseMes readAssignment(@NonNull @PathParam("taskId") int taskId,
                                      @NonNull @PathParam("accountId") String accountId){
        InfoCode infoCode=aps.readAssignmentLegal(taskId,accountId);//合法性判断
        if (infoCode!=InfoCode.OK){
            throw new AssignmentException(infoCode,null);
        } else {
            AssignmentWithName awn=new AssignmentWithName();
            awn=awn.addName(aps.readAssignment(taskId, accountId));//转换对象
            return responseMes.success(awn);
        }
    }

    /**
     * 更新作业审核状态（1为审核中，2为通过，3为未通过）（通过考核id、用户id）
     *
     * @param taskId 考核id
     * @param accountId 用户id
     * @param mark 审核状态（1为审核中，2为通过，3为未通过）
     * @return ResponseMes对象，data为0
     */
    @PUT
    @Path("/assignments/{taskId}/{accountId}/{mark}")
    public ResponseMes updateMark(@NonNull @PathParam("taskId") int taskId,
                                  @NonNull @PathParam("accountId") String accountId,
                                  @NonNull @PathParam("mark") int mark){
        InfoCode infoCode=aps.updateMarkLegal(taskId, accountId, mark);//合法性判断
        if (infoCode!=InfoCode.OK){
            throw new AssignmentException(infoCode,null);
        } else {
            aps.updateMark(taskId, accountId, mark);//更新操作
            return responseMes.success(0);
        }
    }

}
