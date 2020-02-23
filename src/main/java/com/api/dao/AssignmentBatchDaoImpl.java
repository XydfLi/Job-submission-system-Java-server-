package com.api.dao;

import java.sql.*;
import java.util.List;

/**
 * Assignment表的BaseBatchCRUD接口实现
 *
 * @author 李星源
 * @version 1.0
 * TODO ： 功能拓展
 */
public class AssignmentBatchDaoImpl implements BaseBatchCRUD {

    /**
     * 进行作业批量删除操作
     *
     * @param count 作业个数
     * @param keys keys[0]为考核id，keys[1]为用户id列表
     */
    @Override
    public void delete(int count,Object[] keys){
        ConClo DbUtil=null;
        Connection conn=null;
        PreparedStatement pstmt=null;

        try {
            conn=DbUtil.getConnection();
            String sql="delete from Assignment where taskId=? and accountId=?";
            pstmt=conn.prepareStatement(sql);
            int taskId=(Integer)keys[0];
            List<String> accountIds=(List<String>)keys[1];

            //循环加入每一个SQL
            for (int i=0;i<count;i++){
                pstmt.setInt(1,taskId);
                pstmt.setString(2,accountIds.get(i));
                pstmt.addBatch();
                if (i%1000==0){//防止执行的SQL数据过多
                    pstmt.executeBatch();
                    pstmt.clearBatch();
                }
            }
            pstmt.executeBatch();
            pstmt.clearBatch();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }
    }

}
