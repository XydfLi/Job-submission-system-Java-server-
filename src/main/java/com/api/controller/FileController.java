package com.api.controller;

import com.api.exception.ApplicationException;
import com.api.exception.FileException;
import com.api.exception.InfoCode;
import com.api.format.FastJson;
import com.api.model.Assignment;
import com.api.model.ResponseMes;
import com.api.model.Task;
import com.api.service.*;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.glassfish.jersey.jaxb.internal.XmlJaxbElementProvider;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 进行文件操作
 * 文件上传、文件下载
 * TODO : 添加签发token和验证token
 *
 * @author 李星源
 * @version 1.0
 */
@PermitAll()
@Path("/assignments")
@FastJson
public class FileController {
    //日志信息
    private static Logger logger=Logger.getLogger(FileController.class.getName());
    FileService fileService=new FileServiceImpl();//调用Service层接口
    ResponseMes responseMes=new ResponseMes();

    /**
     * 文件上传
     *
     * @param fileInputStream 提交内容
     * @param disposition 获得文件信息，content-disposition的封装
     * @param ctx servlet上下文
     * @param taskId 考核id
     * @param accountId 用户id
     * @return ResponseMes对象，data为0
     */
    @POST
    @Path("/upload/{taskId}/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ResponseMes upload(@FormDataParam("file") InputStream fileInputStream,
                              @FormDataParam("file") FormDataContentDisposition disposition,
                              @Context ServletContext ctx,
                              @NonNull @PathParam("taskId") int taskId,
                              @NonNull @PathParam("accountId") String accountId) {
        if (fileInputStream==null||disposition==null||ctx==null){
            throw new FileException(InfoCode.FILE_EMPTY,null);
        }

        Date now=new Date();
        Assignment assignment=new Assignment();
        assignment.setAccountId(accountId);
        assignment.setMark(1);
        assignment.setJobContent("请下载作业文件查看");
        assignment.setTaskId(taskId);
        assignment.setSubTime(now);

        //合法性判断
        InfoCode infoCode=fileService.uploadFileLegal(assignment,FilenameUtils.getExtension(disposition.getFileName()));
        if (infoCode!=InfoCode.OK&&infoCode!=InfoCode.FILE_EXIST){
            throw new FileException(infoCode,null);
        }

        StudentAssignmentService sas=new StudentAssignmentServiceImpl();
        if (infoCode!=InfoCode.FILE_EXIST){
            //增加数据库记录
            sas.submit(assignment);
        } else {
            //修改数据库记录
            sas.updateAllSubmit(assignment);
        }

        //如果文件夹不存在则创建文件夹
        File file=new File(ctx.getRealPath("/WEB-INF/Assignments"));
        if (!file.exists()){
            file.mkdir();
        }

        //创建文件
        String name=taskId+"_"+accountId;
        File upload=new File(ctx.getRealPath("/WEB-INF/Assignments"),
                name+".zip");

        try {
            //把文件拷贝过去
            FileUtils.copyInputStreamToFile(fileInputStream, upload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMes.success(0);
    }

    /**
     * 下载文件
     *
     * @param taskId 考核id
     * @param ctx servlet上下文
     * @param req 用于获得session
     * @return Response对象
     */
    @GET
    @Path("/download/{taskId}/{accountId}")
    public Response download(@NonNull @PathParam("taskId") int taskId,
                             @NonNull @PathParam("accountId") String accountId,
                             @Context ServletContext ctx,
                             @Context HttpServletRequest req){

        //如果文件夹不存在则创建文件夹
        File file=new File(ctx.getRealPath("/WEB-INF/Assignments"));
        if (!file.exists()){
            file.mkdir();
        }

        String path=taskId+"_"+accountId+".zip";
        File assignmentFile=new File(ctx.getRealPath("/WEB-INF/Assignments"),path);

        if (!assignmentFile.exists()) {
            throw new FileException(InfoCode.FILE_NOT_EXIST,null);
        } else {
            try {
                UserService user=new UserServiceImpl();
                String name=user.getAccountName(accountId);
                StudentTaskService st=new StudentTaskServiceImpl();
                Task task=st.getByTaskId(taskId);
                //重新设置文件名
                String fileName=name+"_"+task.getTaskName()+".zip";
                fileName=URLEncoder.encode(fileName,"UTF-8");
                return Response.ok(assignmentFile).header("Content-disposition","attachment;filename="+fileName)
                        .header("Cache-Control", "no-cache").build();
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }
        //出现其他未知错误
        throw new ApplicationException(InfoCode.UNKNOWN_MISTAKE,null);
    }

}
