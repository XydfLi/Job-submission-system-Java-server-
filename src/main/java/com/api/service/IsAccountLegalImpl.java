package com.api.service;

import com.api.exception.InfoCode;
import com.api.model.Account;

/**
 * 实现Account属性合法性的判断
 *
 * @author 李星源
 * @version 1.0
 */
public class IsAccountLegalImpl implements IsAccountLegal {

    /**
     * 判断用户id的合法性
     *
     * @param ai 用户id
     * @return true为合法，false为非法
     */
    @Override
    public boolean accountId(String ai) {
        if ((ai.length()>0)&&(ai.length()<=10)&&digitalLegal(ai)){
            return true;
        }
        return false;
    }

    /**
     * 用于判断用户密码的合法性
     *
     * @param pw 用户密码
     * @return true为合法，false为非法
     */
    @Override
    public boolean passWord(String pw) {
        if(pw.length()>0&&pw.length()<=10&&letterLegal(pw)){
            return true;
        }
        return false;
    }

    /**
     * 用于判断用户名称的合法性
     *
     * @param an 用户名称
     * @return true为合法，false为非法
     */
    @Override
    public boolean accountName(String an) {
        if(an.length()>0&&an.length()<=15&&nameLegal(an)){
            return true;
        }
        return false;
    }

    /**
     * 用户id、密码、名称不能为空
     *
     * @param account Account对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode isEmpty(Account account) {
        if (account.getAccountId()==null){
            return InfoCode.ACCOUNT_Id_EMPTY;
        } else if (account.getPassWord()==null){
            return InfoCode.PASSWORD_EMPTY;
        } else if (account.getAccountName()==null){
            //TODO : 解除注释，（以下一行代码有用）
//            return InfoCode.ACCOUNT_NAME_EMPTY;
        }
        return InfoCode.OK;
    }

    /**
     * 用于判断注册用户Account是否均合法
     *
     * @param account Account对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode isAll(Account account) {
        if (!accountId(account.getAccountId())){
            return InfoCode.ACCOUNT_Id_ERROR;
        } else if (!passWord(account.getPassWord())){
            return InfoCode.INVALID_Password;
            //TODO : 解除注释，（以下两行代码有用）
//        } else if (!accountName(account.getAccountName())){
//            return InfoCode.ACCOUNT_NAME_ERROR;
        }
        return InfoCode.OK;
    }

    /**
     * 用于判断字符串的合法性
     * 字符串是否只含数字、字母、下划线
     *
     * @param s 字符串
     * @return true为合法，false为非法
     */
    public boolean letterLegal(String s){
        int leng=s.length();
        char c;
        for(int i=0;i<leng;i++){
            c=s.charAt(i);
            if (!((c>='0'&&c<='9')||(c>='a'&&c<='z')||(c>='A'&&c<='Z')||(c=='_'))){
                return false;
            }
        }
        return true;
    }

    /**
     * 用于判断姓名的合法性
     * 不能含有数字
     *
     * @param s 字符串
     * @return true为合法，false为非法
     */
    public boolean nameLegal(String s){
        int leng=s.length();
        char c;
        for(int i=0;i<leng;i++){
            c=s.charAt(i);
            if (c>='0'&&c<='9'){
                return false;
            }
        }
        return true;
    }

    /**
     * 用于判断字符串是否只含有数字
     *
     * @param s 字符串
     * @return true为合法，false为非法
     */
    private boolean digitalLegal(String s){
        int leng=s.length();
        char c;
        for(int i=0;i<leng;i++){
            c=s.charAt(i);
            if (!(c>='0'&&c<='9')){
                return false;
            }
        }
        return true;
    }

}
