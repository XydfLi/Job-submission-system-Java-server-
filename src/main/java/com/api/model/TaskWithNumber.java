package com.api.model;

import com.api.dao.AssignmentDaoImpl;
import com.api.dao.BaseCRUD;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 带提交人数的Task类
 *
 * @author 李星源
 * @version 1.0
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

//    TODO : 前端实现"yyyy-MM-dd HH:mm:ss"格式后,使用下一行注解
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")

// FIXME : @FastJson注解 自定义JSON转换 转换中文出现乱码
//@FastJson
public class TaskWithNumber extends Task {

    private int number;//提交人数

    /**
     * 构造函数
     * 使用Task构造TaskWithNumber
     *
     * @param task Task对象
     */
    TaskWithNumber(Task task){
        this.setTaskId(task.getTaskId());
        this.setDeadline(task.getDeadline());
        this.setStartDate(task.getStartDate());
        this.setTaskContent(task.getTaskContent());
        this.setTaskName(task.getTaskName());
    }

    /**
     * 将Task列表转换为TaskWithNumber列表
     *
     * @param taskList Task列表
     * @return TaskWithNumber列表
     */
    public static List<TaskWithNumber> addNumber(List<Task> taskList){
        BaseCRUD<Assignment> assignmentBaseCRUD=new AssignmentDaoImpl();
        TaskWithNumber twn;
        List<TaskWithNumber> tl=new ArrayList<TaskWithNumber>();
        int count=taskList.size();

        for (int i=0;i<count;i++){
            twn=new TaskWithNumber(taskList.get(i));
            List<Assignment> assignments=assignmentBaseCRUD.readAll(new Object[]{twn.getTaskId()});

            if (assignments==null){
                twn.setNumber(0);
            } else {
                twn.setNumber(assignments.size());
            }
            tl.add(twn);
        }
        return tl;
    }

    /**
     * 将Task对象转换为TaskWithNumber对象
     *
     * @param task Task对象
     * @return TaskWithNumber对象
     */
    public static TaskWithNumber addNumber(Task task){
        BaseCRUD<Assignment> assignmentBaseCRUD=new AssignmentDaoImpl();
        TaskWithNumber twn=new TaskWithNumber(task);
        List<Assignment> assignments=assignmentBaseCRUD.readAll(new Object[]{task.getTaskId()});
        if (assignments==null){
            twn.setNumber(0);
        } else {
            twn.setNumber(assignments.size());
        }
        return twn;
    }

}
