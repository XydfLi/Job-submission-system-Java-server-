package com.api.dao;

import java.util.logging.Logger;

/**
 * 进行批量数据操作接口
 *
 * @author 李星源
 * @version 1.0
 * TODO ： 功能拓展
 */
public interface BaseBatchCRUD {
    //日志信息
    static Logger logger=Logger.getLogger(BaseBatchCRUD.class.getName());

    //批量删除
    public void delete(int count,Object[] keys);
}
