package com.api.dao;

import com.api.model.Task;

import java.util.List;

/**
 * Task表的BaseCRUD接口实现
 *
 * @author 李星源
 * @version 1.0
 */
public class TaskDaoImpl implements BaseCRUD<Task> {
    BaseDao baseDao=new BaseDaoImpl();//用接口实现类使用接口BaseDao

    /**
     * 实现数据库Task表的增加数据操作
     *
     * @param dataClass Task对象
     * @return 新生成的数据的自增主键（考核id）
     */
    @Override
    public int create(Task dataClass) {
        String sql="insert into Task(taskName,taskContent,startDate,deadline) values(?,?,?,?)";
        Object[] paramsValue={dataClass.getTaskName(),dataClass.getTaskContent(),dataClass.getStartDate(),dataClass.getDeadline()};
        return baseDao.update(sql,paramsValue);
    }

    /**
     * 实现数据库Task表的删除数据操作
     *
     * @param key Objective数组，仅含有考核id
     */
    @Override
    public void delete(Object[] key) {
        String sql="delete from Task where taskId=?";
        Object[] paramsValue={key[0]};
        baseDao.update(sql,paramsValue);
    }

    /**
     * 实现数据库Task表的删除数据操作（全部）
     *
     * @param key 值为null
     */
    @Override
    public void deleteAll(Object[] key) {
        String sql="delete from Task";
        baseDao.update(sql,null);
    }

    /**
     * 实现数据库Task表的更新数据操作
     *
     * @param propertyName 需要更新属性的数组，与value数组顺序一致
     * @param value 需要更新属性的值的数组，与propertyName数组顺序一致
     */
    @Override
    public void update(String[] propertyName,Object[] value) {
        String head="update Task set ";
        String tail=" where taskId=?";
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
     * 实现数据库Task表的读取数据操作（全部）
     *
     * @param key 值为null
     * @return Task列表
     */
    @Override
    public List<Task> readAll(Object[] key) {
        String sql="select * from Task";
        List<Task> taskList=baseDao.query(sql,null,Task.class);
        return taskList;
    }

    /**
     * 实现数据库Task表的读取数据操作（全部）
     *
     * @param key Objective数组，仅含有考核id
     * @return Task对象
     */
    @Override
    public Task readByKey(Object[] key) {
        String sql = "select * from Task where taskId=?";
        List<Task> taskList=baseDao.query(sql,new Object[]{key[0]},Task.class);
        return  (taskList!=null&&taskList.size()>0)?taskList.get(0):null;
    }

}
