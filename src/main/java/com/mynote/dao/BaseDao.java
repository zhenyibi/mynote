package com.mynote.dao;

/**
 * @author zhenyiBi
 * @date 2022/3/31
 */

import com.mynote.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * 基础的JDBC操作类
 * 更新操作 (添加、修改、删除)
 * 查询操作
 * 1、查询一个字段 (只会返回一条记录且只有一个字段；常用场景：查询总数量)
 * 2、查询集合
 * 3、查询某个对象
 */

public class BaseDao {

    /**
     * 更新操作
     * 添加、修改、删除
     * 1、得到数据库连接
     * 2、定义sql语句 (添加语句、修改语句、删除语句)
     * 3、预编译
     * 4、如果有参数，则设置参数，下标从1开始 (数组或集合、循环设置参数)
     * 5、执行更新操作，返回受影响的行数
     * 6、关闭资源
     * <p>
     * 注：需要两个参数：sql语句、所需参数的集合
     *
     * @param sql    语句
     * @param params 参数
     * @return 受影响的行数
     */
    public static int executeUpdate(String sql, List<Object> params) {
        int row = 0; //受影响的行数
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //得到数据库连接
            connection = DBUtil.getConnection();
            //预编译
            preparedStatement = connection.prepareStatement(sql);
            //如果有参数，则设置参数，下标从1开始
            if (params != null && params.size() > 0) {
                //循环设置参数，设置参数类型为Object
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            row = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, preparedStatement, connection);
        }

        return row;
    }


    /**
     * 查询一个字段 (只会返回一条记录且只有一个字段；常用场景：查询总数量)
     * 1、得到数据库连接
     * 2、定义sql语句
     * 3、预编译
     * 4、如果有参数，则设置参数，下标从1开始 (数组或集合、循环设置参数)
     * 5、执行查询，返回结果集
     * 6、判断并分析结果集
     * 7、关闭资源
     * <p>
     * 注：需要两个参数：sql语句、所需参数的集合
     *
     * @param sql    语句
     * @param params 参数
     * @return object
     */
    public static Object findSingleValue(String sql, List<Object> params) {
        Object object = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //获取数据库连接
            connection = DBUtil.getConnection();
            //预编译
            preparedStatement = connection.prepareStatement(sql);
            //如果有参数，则设置参数，下标从1开始
            if (params != null && params.size() > 0) {
                //循环设置参数，设置参数类型为Object
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            //执行查询，返回结果集
            resultSet = preparedStatement.executeQuery();
            //判断并分析结果集
            if (resultSet.next()){
                object = resultSet.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            DBUtil.close(resultSet, preparedStatement, connection);
        }

        return object;
    }
}
