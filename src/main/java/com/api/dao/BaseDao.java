package com.api.dao;

import java.util.List;
import java.util.logging.Logger;

/**
 * 数据库更新、查询接口
 * 使用prepareStatement，防止SQL注入
 *
 * @author 李星源
 * @version 1.0
 */
public interface BaseDao {
    //日志信息
    static Logger logger=Logger.getLogger(BaseDao.class.getName());

    //数据更新接口
    public int update(String sql,Object[] paramsValue);
    //数据查询接口
    public <T> List<T> query(String sql,Object[] paramsValue,Class<T> cla);
}
