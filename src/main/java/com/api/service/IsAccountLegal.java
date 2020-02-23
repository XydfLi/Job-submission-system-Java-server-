package com.api.service;

import com.api.exception.InfoCode;
import com.api.model.Account;

import java.util.logging.Logger;

/**
 * 用于判断Account类数据的合法性
 *
 * @author 李星源
 * @version 1.0
 */
public interface IsAccountLegal {
    //日志信息
    static Logger logger=Logger.getLogger(IsAccountLegal.class.getName());

    //用于判断用户id是否合法
    public boolean accountId(String ai);
    //用于判断密码是否合法
    public boolean passWord(String pw);
    //用于判断用户名称是否合法
    public boolean accountName(String an);
    //用于注册用户判断Account是否均为空
    public InfoCode isEmpty(Account account);
    //用于注册用户判断Account是否均合法
    public InfoCode isAll(Account account);
}
