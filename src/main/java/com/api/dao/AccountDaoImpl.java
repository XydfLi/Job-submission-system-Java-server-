package com.api.dao;

import com.api.model.Account;

import java.util.List;

/**
 * Account表的BaseCRUD接口实现
 *
 * @author 李星源
 * @version 1.0
 */
public class AccountDaoImpl implements BaseCRUD<Account> {
    BaseDao baseDao=new BaseDaoImpl();//用接口实现类使用接口BaseDao

    /**
     * 实现数据库Account表的增加数据操作
     *
     * @param dataClass Account对象
     * @return -1 无用数据
     */
    @Override
    public int create(Account dataClass) {
        String sql="insert into Account(accountId,accountName,passWord,identity) values(?,?,?,?)";
        Object[] paramsValue={dataClass.getAccountId(),dataClass.getAccountName(),dataClass.getPassWord(),dataClass.getIdentity()};
        baseDao.update(sql,paramsValue);
        return -1;
    }

    /**
     * 实现数据库Account表的删除数据操作
     *
     * @param key Objective数组，仅含有用户id
     */
    @Override
    public void delete(Object[] key) {
        String sql="delete from Account where accountId=?";
        Object[] paramsValue={key[0]};
        baseDao.update(sql,paramsValue);
    }

    /**
     * 实现数据库Account表的删除数据操作（全部）
     *
     * @param key 空值null
     */
    @Override
    public void deleteAll(Object[] key) {
        String sql="delete from Account";
        baseDao.update(sql,null);
    }

    /**
     * 实现数据库Account表的更新数据操作
     *
     * @param propertyName 需要更新属性的数组，与value数组顺序一致
     * @param value 需要更新属性的值的数组，与propertyName数组顺序一致
     */
    @Override
    public void update(String[] propertyName,Object[] value) {
        String head="update Account set ";
        String tail=" where accountId=?";
        String sql="";
        sql=sql+head;

        int count=propertyName.length;
        //填入各个属性
        for(int i=0;i<count;i++)
        {
            if(i==0){
                sql=sql+propertyName[i];
                sql=sql+"=?";
            } else {
                sql=sql+",";
                sql=sql+propertyName[i];
                sql=sql+"=?";
            }
        }

        sql=sql+tail;
        baseDao.update(sql,value);
    }

    /**
     * 实现数据库Account表的读取数据操作（全部）
     *
     * @param key 空值null
     * @return Account列表
     */
    @Override
    public List<Account> readAll(Object[] key) {
        String sql="select * from Account";
        List<Account> accountList=baseDao.query(sql,null,Account.class);
        return accountList;
    }

    /**
     * 实现数据库Account表的读取数据操作
     *
     * @param key Objective数组，仅含有用户id
     * @return Account 对象
     */
    @Override
    public Account readByKey(Object[] key) {
        String sql="select * from Account where accountId=?";
        List<Account> accountList=baseDao.query(sql,new Object[]{key[0]},Account.class);
        return  (accountList!=null&&accountList.size()>0)?accountList.get(0):null;
    }

}
