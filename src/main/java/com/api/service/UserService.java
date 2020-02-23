package com.api.service;

import com.api.model.Account;
import com.api.exception.InfoCode;

import java.util.logging.Logger;

/**
 * 用户操作的Service层接口
 *
 * @author 李星源
 * @version 1.0
 */
public interface UserService {
    //日志信息
    static Logger logger=Logger.getLogger(UserService.class.getName());

    //获取身份信息时判断信息合法性
    public InfoCode isIdentityLegal(Account account);
    //获得身份信息
    public int getIdentity(Account account);
    //修改密码时判断信息合法性
    public InfoCode isPassWordLegal(Account account,String newPassword);
    //修改密码
    public void updatePassWord(Account account,String newPassword);
    //获得用户名称的合法性
    public InfoCode getAccountNameLegal(String accountId);
    //获得用户名称
    public String getAccountName(String accountId);
    //用于判断注册用户的合法性
    public InfoCode registerLegal(Account account);
    //用于进行注册用户操作
    public void register(Account account);
}
