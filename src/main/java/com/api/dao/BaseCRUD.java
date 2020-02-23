package com.api.dao;

import java.util.List;
import java.util.logging.Logger;

/**
 * 进行数据库CRUD操作接口
 * Account、Assignment、Task表实现其多态
 *
 * @param <T> 模板
 * @author 李星源
 * @version 1.0
 */
public interface BaseCRUD<T> {
    //日志信息
    static Logger logger=Logger.getLogger(BaseCRUD.class.getName());

    //增加数据接口
    public int create(T dataClass);
    //删除数据接口
    public void delete(Object[] key);
    //删除所有数据接口
    public void deleteAll(Object[] key);
    //修改数据接口
    public void update(String[] propertyName,Object[] value);
    //查询全部数据
    public List<T> readAll(Object[] key);
    //根据条件查询(主键)
    public T readByKey(Object[] key);
}
