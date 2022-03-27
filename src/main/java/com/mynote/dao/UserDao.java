package com.mynote.dao;

import com.mynote.po.User;
import com.mynote.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author zhenyiBi
 * @date 2022/3/27
 */
public class UserDao {

    /**
     * 通过用户名查询用户对象，返回用户对象
     * 1.获取数据库连接
     * 2.定义sql语句
     * 3.预编译
     * 4.设置参数
     * 5.执行查询，返回结果集
     * 6.判断并分析结果集
     * 7.关闭资源
     *
     * @param userName 用户名
     * @return 用户
     */
    public User queryUserByName(String userName) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //1.获取数据库连接
            connection = DBUtil.getConnection();
            //2.定义sql语句
            String sql = "select * from tb_user where uname = ?";
            //3.预编译
            preparedStatement = connection.prepareStatement(sql);
            //4.设置参数
            preparedStatement.setString(1, userName);
            //5.执行查询，返回结果集
            resultSet = preparedStatement.executeQuery();
            //6.判断并分析结果集
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUname(userName);
                user.setHead(resultSet.getString("head"));
                user.setMood(resultSet.getString("mood"));
                user.setNick(resultSet.getString("nick"));
                user.setUpwd(resultSet.getString("upwd"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //7.关闭资源
            DBUtil.close(resultSet, preparedStatement, connection);
        }

        return user;
    }

}
