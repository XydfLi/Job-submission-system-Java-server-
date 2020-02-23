package com.api.service;

import com.api.model.Assignment;
import com.api.exception.InfoCode;

import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 * 用于判断Assignment类数据的合法性
 *
 * @author 李星源
 * @version 1.0
 */
public interface IsAssignmentLegal {
    //日志信息
    static Logger logger=Logger.getLogger(IsAssignmentLegal.class.getName());
    static String minDate="2000-01-01 00:00:00";//日期设置的最小日期
    static String maxDate="3000-01-01 00:00:00";//日期设置的最大日期
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //用于判断taskId是否合法
    public boolean taskId(int ti);
    //用于判断用户id是否合法
    public boolean accountId(String ai);
    //用于判断提交内容是否合法
    public boolean jobContent(String jc);
    //用于判断提交时间是否合法
    public boolean subTime(Assignment assignment);
    //用于判断标记是否合法
    public boolean mark(int mark);
    //用于判断是否全部合法
    public InfoCode all(Assignment assignment);
    //用于判断关键信息是否为空
    public InfoCode isEmpty(Assignment assignment);
}
