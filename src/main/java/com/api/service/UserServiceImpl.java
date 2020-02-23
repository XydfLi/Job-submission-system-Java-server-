package com.api.service;

import com.api.dao.AccountDaoImpl;
import com.api.dao.BaseCRUD;
import com.api.model.Account;
import com.api.exception.InfoCode;

/**
 * 实现用户操作的Service层接口
 *
 * @author 李星源
 * @version 1.0
 */
public class UserServiceImpl implements UserService {
    BaseCRUD<Account> accountBaseCRUD=new AccountDaoImpl();//调用Dao层接口
    IsAccountLegal ial=new IsAccountLegalImpl();

    /**
     * 获取身份信息时判断信息合法性
     *
     * @param account Account对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode isIdentityLegal(Account account) {
        if (account.getAccountId()==null){
            return InfoCode.ACCOUNT_Id_EMPTY;
        } else if (account.getPassWord()==null){
            return InfoCode.PASSWORD_EMPTY;
        }

        Account acc=new Account();
        acc=accountBaseCRUD.readByKey(new Object[]{account.getAccountId()});
        if (acc!=null){
            if (acc.getPassWord().equals(account.getPassWord())){
                return InfoCode.OK;
            }
            return InfoCode.PASSWORD_ERROR;
        } else {
            return InfoCode.ACCOUNT_Id_NOT_EXIST;
        }
    }

    /**
     * 获取身份信息
     *
     * @param account Account
     * @return 1为管理员身份，2为学生身份
     */
    @Override
    public int getIdentity(Account account) {
        Account acc=new Account();
        acc=accountBaseCRUD.readByKey(new Object[]{account.getAccountId()});
        return acc.getIdentity();
    }

    /**
     * 修改密码时判断信息合法性
     *
     * @param account Account对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode isPassWordLegal(Account account,String newPassword) {
        if (account.getAccountId()==null){
            return InfoCode.ACCOUNT_Id_EMPTY;
        } else if (account.getPassWord()==null){
            return InfoCode.OLD_PASSWORD_EMPTY;
        } else if (newPassword==null){
            return InfoCode.NEW_PASSWORD_EMPTY;
        }

        Account acc=new Account();
        acc=accountBaseCRUD.readByKey(new Object[]{account.getAccountId()});
        if (acc!=null){
            if (!ial.passWord(newPassword)){
                return InfoCode.INVALID_Password;
            } else if (!account.getPassWord().equals(acc.getPassWord())){
                return InfoCode.PASSWORD_ERROR;
            }
            return InfoCode.OK;
        }
        return InfoCode.ACCOUNT_Id_NOT_EXIST;
    }

    /**
     * 修改密码
     *
     * @param account Account对象
     */
    @Override
    public void updatePassWord(Account account,String newPassword) {
        accountBaseCRUD.update(new String[]{"passWord"},
                new Object[]{newPassword,account.getAccountId()});
    }

    /**
     * 获得用户名称的合法性
     *
     * @param accountId 用户id
     * @return InfoCode对象
     */
    @Override
    public InfoCode getAccountNameLegal(String accountId) {
        if (accountId==null){
            return InfoCode.ACCOUNT_Id_EMPTY;
        } else if (!ial.accountId(accountId)) {
            return InfoCode.ACCOUNT_Id_ERROR;
        }
        Account account=accountBaseCRUD.readByKey(new Object[]{accountId});
        if (account==null){
            return InfoCode.ACCOUNT_Id_NOT_EXIST;
        }
        return InfoCode.OK;
    }

    /**
     * 获得用户名称
     *
     * @param accountId 用户id
     * @return 用户名称
     */
    @Override
    public String getAccountName(String accountId) {
        return accountBaseCRUD.readByKey(new Object[]{accountId}).getAccountName();
    }

    /**
     * 用于判断注册用户的合法性
     *
     * @param account Account对象
     * @return InfoCode对象
     */
    @Override
    public InfoCode registerLegal(Account account) {
        InfoCode infoCode=ial.isEmpty(account);
        if (infoCode!=InfoCode.OK){
            return infoCode;
        }
        infoCode=ial.isAll(account);
        if (infoCode!=InfoCode.OK){
            return infoCode;
        }
        Account account1=accountBaseCRUD.readByKey(new Object[]{account.getAccountId()});
        if (account1!=null){
            return InfoCode.ACCOUNT_ID_EXIST;
        }
        return InfoCode.OK;
    }

    /**
     * 用于进行注册用户操作
     *
     * @param account Account对象
     */
    @Override
    public void register(Account account) {
        accountBaseCRUD.create(account);
    }

}
