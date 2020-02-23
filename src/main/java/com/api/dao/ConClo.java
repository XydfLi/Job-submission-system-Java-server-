package com.api.dao;

import java.sql.*;

/**
 * 用于进行数据库的连接和关闭
 *
 * @author 李星源
 * @version 1.0
 */
public class ConClo {

    /**
     * 用于进行数据库的连接
     *
     * @return Connection对象
     */
    public static Connection getConnection(){
        Connection conn=null;
        try {
            // HACK : mysql-connector-java 6及6之后的，注意匹配的MySQL版本
            Class.forName("com.mysql.cj.jdbc.Driver");
            // HACK : mysql-connector-java 6及6之后的，注意匹配的MySQL版本
            // HACK : 数据库名称、用户、密码
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/JobSubmissionSystem?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true","root", "123456");
        } catch (ClassNotFoundException e){
            e.printStackTrace();;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 重载close方法
     * 用于关闭PreparedStatement
     *
     * @param pstmt PreparedStatement对象
     */
    public static void close(PreparedStatement pstmt){
        if(pstmt!=null){
            try {
                pstmt.close();
            } catch (SQLException e){
                e.printStackTrace();;
            }
        }
    }

    /**
     * 重载close方法
     * 用于关闭Connection
     *
     * @param conn Connection对象
     */
    public static void close(Connection conn){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e){
                e.printStackTrace();;
            }
        }
    }

    /**
     * 重载close方法
     * 用于关闭ResultSet
     *
     * @param res ResultSet对象
     */
    public static void close(ResultSet res){
        if(res!=null){
            try {
                res.close();
            } catch (SQLException e){
                e.printStackTrace();;
            }
        }
    }

}
