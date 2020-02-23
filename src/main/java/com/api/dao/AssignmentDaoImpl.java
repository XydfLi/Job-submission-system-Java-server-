package com.api.dao;

import com.api.model.Assignment;

import java.util.List;

/**
 * Assignment表的BaseCRUD接口实现
 *
 * @author 李星源
 * @version 1.0
 */
public class AssignmentDaoImpl implements BaseCRUD<Assignment> {
    BaseDao baseDao=new BaseDaoImpl();//用接口实现类使用接口BaseDao

    /**
     * 实现数据库Assignment表的增加数据操作
     *
     * @param dataClass Assignment对象
     * @return -1 无用数据
     */
    @Override
    public int create(Assignment dataClass) {
        String sql="insert into Assignment(taskId,accountId,jobContent,subTime,mark) values(?,?,?,?,?)";
        Object[] paramsValue={dataClass.getTaskId(),dataClass.getAccountId(),dataClass.getJobContent(),dataClass.getSubTime(),dataClass.getMark()};
        baseDao.update(sql,paramsValue);
        return -1;
    }

    /**
     * 实现数据库Assignment表的删除数据操作
     *
     * @param key key[0]为考核id，key[1]为用户id
     */
    @Override
    public void delete(Object[] key) {
        String sql="delete from Assignment where taskId=? and accountId=?";
        Object[] paramsValue={key[0],key[1]};
        baseDao.update(sql,paramsValue);
    }

    /**
     * 实现数据库Assignment表的删除数据操作（全部）
     *
     * @param key key[0]为考核id
     */
    @Override
    public void deleteAll(Object[] key) {
        String sql="delete from Assignment where taskId=?";
        Object[] paramsValue={key[0]};
        baseDao.update(sql,paramsValue);
    }

    /**
     * 实现数据库Assignment表的更新数据操作
     *
     * @param propertyName 需要更新属性的数组，与value数组顺序一致
     * @param value 需要更新属性的值的数组，与propertyName数组顺序一致
     */
    @Override
    public void update(String[] propertyName,Object[] value) {
        String head="update Assignment set ";
        String tail=" where taskId=? and accountId=?";
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
     * 实现数据库Assignment表的读取数据操作（全部）
     *
     * @param key key[0]为考核id
     * @return Assignment列表
     */
    @Override
    public List<Assignment> readAll(Object[] key) {
        String sql="select * from Assignment where taskId=?";
        List<Assignment> assignmentList=baseDao.query(sql,key,Assignment.class);
        return (assignmentList!=null&&assignmentList.size()>0)?assignmentList:null;
    }

    /**
     * 实现数据库Assignment表的读取数据操作
     *
     * @param key key[0]为考核id，key[1]为用户id
     * @return Assignment对象
     */
    @Override
    public Assignment readByKey(Object[] key) {
        String sql="select * from Assignment where taskId=? and accountId=?";
        List<Assignment> assignmentList=baseDao.query(sql,new Object[]{key[0],key[1]},Assignment.class);
        return  (assignmentList!=null&&assignmentList.size()>0)?assignmentList.get(0):null;
    }

}
