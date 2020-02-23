package com.api.service;

import com.api.exception.InfoCode;
import com.api.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * 用于判断Task类数据的合法性
 *
 * @author 李星源
 * @version 1.0
 */
public interface IsTaskLegal {
    //日志信息
    static Logger logger=Logger.getLogger(IsTaskLegal.class.getName());
    static String minDate="2000-01-01 00:00:00";//最小日期
    static String maxDate="3000-01-01 00:00:00";//最大日期
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //用于判断taskId是否合法
    public boolean taskId(int ti);
    //用于判断考核名称是否合法
    public boolean taskName(String tn);
    //用于判断考核内容是否合法
    public boolean taskContent(String tc);
    //用于判断开始日期是否合法
    public boolean startDate(Date sd,Date dl);
    //用于判断截止日期是否合法
    public boolean deadline(Date sd,Date dl);
    //用户判断是否全部合法
    public InfoCode all(Task task);
    //用于判断是否存在空值
    public InfoCode isEmpty(Task task);
}
