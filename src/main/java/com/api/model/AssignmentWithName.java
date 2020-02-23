package com.api.model;

import com.api.dao.AccountDaoImpl;
import com.api.dao.BaseCRUD;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 作业类
 * 继承自Assignment类，多了用户名称属性
 * 便于将更多的数据返回给前台
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
public class AssignmentWithName extends Assignment {

    public String name;//提交用户名称

    /**
     * 用Assignment对象生成的构造方法
     *
     * @param assignment Assignment对象
     */
    AssignmentWithName(Assignment assignment){
        this.setTaskId(assignment.getTaskId());
        this.setAccountId(assignment.getAccountId());
        this.setJobContent(assignment.getJobContent());
        this.setSubTime(assignment.getSubTime());
        this.setMark(assignment.getMark());
    }

    /**
     * 将Assignment列表转为AssignmentWithName列表
     *
     * @param assignmentList Assignment对象列表
     * @return AssignmentWithName对象列表
     */
    public List<AssignmentWithName> addName(List<Assignment> assignmentList){
        BaseCRUD<Account> accountBaseCRUD=new AccountDaoImpl();//调用Dao层接口
        AssignmentWithName awn;
        List<AssignmentWithName> al=new ArrayList<AssignmentWithName>();
        int count=assignmentList.size();
        String name;
        for(int i=0;i<count;i++){
            awn=new AssignmentWithName(assignmentList.get(i));
            //获得相应作业提交用户的名称
            name=accountBaseCRUD.readByKey(new Object[]{awn.getAccountId()}).getAccountName();
            awn.setName(name);
            al.add(awn);
        }
        return al;
    }

    /**
     * 将Assignment对象转换为AssignmentWithName对象
     *
     * @param assignment Assignment对象
     * @return AssignmentWithName对象
     */
    public AssignmentWithName addName(Assignment assignment){
        BaseCRUD<Account> accountBaseCRUD=new AccountDaoImpl();//调用Dao层接口
        AssignmentWithName awn= new AssignmentWithName(assignment);
        //获得用户名称
        String name=accountBaseCRUD.readByKey(new Object[]{awn.getAccountId()}).getAccountName();
        awn.setName(name);
        return awn;
    }
}
