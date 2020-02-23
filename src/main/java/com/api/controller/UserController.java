package com.api.controller;

import com.api.JWT.JwtHelper;
import com.api.JWT.JwtUser;
import com.api.exception.AccountException;
import com.api.exception.ApplicationException;
import com.api.format.FastJson;
import com.api.model.Account;
import com.api.exception.InfoCode;
import com.api.model.ResponseMes;
import com.api.model.UpPassword;
import com.api.service.UserService;
import com.api.service.UserServiceImpl;
import lombok.NonNull;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

/**
 * 用户操作
 * 登入、修改密码、获得用户名、登出、注册用户
 * TODO : 添加签发token和验证token
 *
 * @author 李星源
 * @version 1.0
 */
@Path("/users")
@PermitAll()
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@FastJson
public class UserController {
    //日志信息
    private static Logger logger=Logger.getLogger(UserController.class.getName());
    ResponseMes responseMes=new ResponseMes();//统一响应类
    UserService user=new UserServiceImpl();//通过接口实现类使用接口UserService

    /**
     * 用户登入
     *
     * @param account Account对象
     * @param req 用于获得session
     * @return ResponseMes对象，data为（0为身份错误，1为管理员身份，2为学生身份）
     */
    @POST
    @Path("/login")
    public ResponseMes login(@NonNull Account account,@Context HttpServletRequest req){
        InfoCode infoCode=user.isIdentityLegal(account);//合法性判断
        if (infoCode!=InfoCode.OK){
            throw new AccountException(infoCode,null);
        } else {
            //设置session
            HttpSession session=req.getSession(true);
            session.setAttribute("accountId",account.getAccountId());

            //TODO : 以下token验证+权限管理需要前端加上头"CSRFToken"，暂时不可用
//            int mark=user.getIdentity(account);
//            //生成token
//            JwtUser jwtUser=new JwtUser();
//            jwtUser.setId(session.getId());
//            String jwt;
//            if (mark==1){
//                jwtUser.setUname("管理员1");
//                jwt=JwtHelper.createJWT(jwtUser,"Administrator");
//            } else {
//                jwtUser.setUname("管理员1");
//                jwt=JwtHelper.createJWT(jwtUser,"Student");
//            }
//            session.setAttribute("CSRFToken",jwt);

            logger.info("登入");
            return responseMes.success(user.getIdentity(account));
        }
    }

    /**
     * 修改密码
     *
     * @param upPassword 包含原密码和新密码
     * @param req 用于获得session
     * @return ResponseMes，data为0
     */
    @PUT
    public ResponseMes updatePassWord(@NonNull UpPassword upPassword,@Context HttpServletRequest req){
        //获得accountId
        HttpSession session=req.getSession(true);
        String accountId=(String)session.getAttribute("accountId");

        Account account=new Account();
        account.setAccountId(accountId);
        account.setPassWord(upPassword.getOldPassword());

        //合法性判断
        InfoCode infoCode=user.isPassWordLegal(account,upPassword.getNewPassword());
        if (infoCode!=InfoCode.OK){
            throw new AccountException(infoCode,null);
        } else {
            user.updatePassWord(account,upPassword.getNewPassword());//密码更新
            return responseMes.success(0);
        }
    }

    /**
     * 用于获得用户名称
     *
     * @param req 用于获得session
     * @return ResponseMes对象，data为用户名称
     */
    @GET
    public ResponseMes getName(@Context HttpServletRequest req){
        //获得accountId
        HttpSession session=req.getSession(true);
        String accountId=(String)session.getAttribute("accountId");

        InfoCode infoCode=user.getAccountNameLegal(accountId);//合法性判断
        if (infoCode!=InfoCode.OK){
            throw new AccountException(infoCode,null);
        } else {
            return responseMes.success(user.getAccountName(accountId));
        }
    }

    /**
     * 登出
     *
     * @param req 用于获得session
     * @return ResponseMes对象，data为0
     */
    @DELETE
    @Path("/logout")
    public ResponseMes logout(@Context HttpServletRequest req){
        logger.info("登出");
        HttpSession session=req.getSession(true);
        String accountId=(String)session.getAttribute("accountId");
        if (accountId!=null){
            session.invalidate();
            return responseMes.success(0);
        }
        throw new ApplicationException(InfoCode.REPEAT_LOGOUT,null);
    }

    /**
     * 用于注册用户
     *
     * @param account 包含新用户id、新密码
     * @return ResponseMes对象，data为0
     */
    @POST
    @Path("/students")
    public ResponseMes registration(@NonNull Account account,@Context HttpServletRequest req){

        //HACK : 注册身份只能为管理员
        account.setIdentity(1);

        InfoCode infoCode=user.registerLegal(account);
        if (infoCode!=InfoCode.OK){
            throw new ApplicationException(infoCode,null);
        }

        //TODO : 注释以下代码，（以下三行代码无用）
        if (account.getAccountName()==null){
            account.setAccountName("管理员");
        }

        user.register(account);
        logger.info("注册成功");

        //设置session
        HttpSession session=req.getSession(true);
        session.setAttribute("accountId",account.getAccountId());

        //TODO : 以下token验证+权限管理需要前端加上头"CSRFToken"，暂时不可用
//        int mark=user.getIdentity(account);
//        //生成token
//        JwtUser jwtUser=new JwtUser();
//        jwtUser.setId(session.getId());
//        String jwt;
//        if (mark==1){
//            jwtUser.setUname("管理员1");
//            jwt=JwtHelper.createJWT(jwtUser,"Administrator");
//        } else {
//            jwtUser.setUname("管理员1");
//            jwt=JwtHelper.createJWT(jwtUser,"Student");
//        }
//        session.setAttribute("CSRFToken",jwt);

        logger.info("登入");

        return responseMes.success(0);
    }

}
