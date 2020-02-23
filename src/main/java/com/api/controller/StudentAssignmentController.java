package com.api.controller;

import com.api.exception.AssignmentException;
import com.api.format.FastJson;
import com.api.model.Assignment;
import com.api.model.AssignmentWithName;
import com.api.exception.InfoCode;
import com.api.model.ResponseMes;
import com.api.service.StudentAssignmentService;
import com.api.service.StudentAssignmentServiceImpl;
import lombok.NonNull;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

/**
 * 用于学生进行作业操作
 * TODO : 添加签发token和验证token
 *
 * @author 李星源
 * @version 1.0
 */
@Path("/students")
@PermitAll()
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@FastJson
public class StudentAssignmentController {
    //日志信息
    private static Logger logger=Logger.getLogger(StudentAssignmentController.class.getName());
    ResponseMes responseMes=new ResponseMes();//统一响应类
    StudentAssignmentService sas=new StudentAssignmentServiceImpl();//调用Service层接口

    /**
     * 提交作业
     *
     * @param assignment Assignment对象
     * @param req 用于获得session
     * @return ResponseMes对象，data为0
     */
    @POST
    public ResponseMes submit(@NonNull Assignment assignment,@Context HttpServletRequest req) {
        //获得accountId
        HttpSession session=req.getSession(true);
        String accountId=(String)session.getAttribute("accountId");

        assignment.setAccountId(accountId);
        assignment.setMark(1);//默认提交的作业标记为审核中
        InfoCode infoCode=sas.submitLegal(assignment);//合法性判断
        if (infoCode!=InfoCode.OK){
            throw new AssignmentException(infoCode,null);
        }
        sas.submit(assignment);//提交作业
        return responseMes.success(0);
    }

    /**
     * 更新作业（全部）
     *
     * @param assignment Assignment对象
     * @param req 用于获得session
     * @return ResponseMes对象，data为0
     */
    @PUT
    public ResponseMes updateAllSubmit(@NonNull Assignment assignment,@Context HttpServletRequest req) {
        //获得accountId
        HttpSession session=req.getSession(true);
        String accountId=(String)session.getAttribute("accountId");

        assignment.setAccountId(accountId);
        InfoCode infoCode=sas.updateAllSubmitLegal(assignment);//合法性判断
        if (infoCode!=InfoCode.OK){
            throw new AssignmentException(infoCode,null);
        }
        sas.updateAllSubmit(assignment);//更新作业
        return responseMes.success(0);
    }

    /**
     * 更新作业（部分）
     * 只有非空部分会被修改
     *
     * @param assignment Assignment对象
     * @param req 用于获得session
     * @return ResponseMes对象，data为0
     */
    @PATCH
    public ResponseMes updateSubmit(@NonNull Assignment assignment,@Context HttpServletRequest req) {
        //获得accountId
        HttpSession session=req.getSession(true);
        String accountId=(String)session.getAttribute("accountId");

        assignment.setAccountId(accountId);
        InfoCode infoCode=sas.updateSubmitLegal(assignment);//合法性判断
        if (infoCode!=InfoCode.OK){
            throw new AssignmentException(infoCode,null);
        }
        sas.updateSubmit(assignment);//更新操作
        return responseMes.success(0);
    }

    /**
     * 查询作业
     *
     * @param taskId 考核id
     * @param req 用于获得session
     * @return ResponseMes对象，data为AssignmentWithName
     */
    @GET
    @Path("/{taskId}")
    public ResponseMes readSubmit(@NonNull @PathParam("taskId") int taskId,
                                  @Context HttpServletRequest req){
        //获得accountId
        HttpSession session=req.getSession(true);
        String accountId=(String)session.getAttribute("accountId");

        InfoCode infoCode=sas.readSubmitLegal(taskId,accountId);//合法性判断
        if (infoCode!=InfoCode.OK){
            throw new AssignmentException(infoCode,null);
        }
        AssignmentWithName awn=new AssignmentWithName();
        awn=awn.addName(sas.readSubmit(taskId,accountId));//类型转换
        return responseMes.success(awn);
    }

}
