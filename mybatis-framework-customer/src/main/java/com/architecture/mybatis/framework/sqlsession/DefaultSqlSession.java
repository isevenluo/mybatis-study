package com.architecture.mybatis.framework.sqlsession;

import com.architecture.mybatis.framework.config.Configuration;
import com.architecture.mybatis.framework.mapping.BoundSql;
import com.architecture.mybatis.framework.mapping.ParameterMapping;
import com.architecture.mybatis.framework.statement.MappedStatement;
import com.architecture.mybatis.framework.statement.StatementType;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statementId, Object param) {

        List<Object> results = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            DataSource dataSource = configuration.getDataSource();
            connection = dataSource.getConnection();

            MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
            // 获取boundSq，里面存储了将#{}替换为? 后的sql文件，以及替换前的参数信息
            BoundSql boundSql = mappedStatement.getBoundSql();
            String sql = boundSql.getBoundSql();

            /**
             * 判断statementType
             */
            if (mappedStatement.getStatementType().equalsIgnoreCase(StatementType.PREPARED.toString())) {
                // 预编译
                preparedStatement = connection.prepareStatement(sql);
                // 设置参数
                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                // 参数类型
                Class parameterClass = mappedStatement.getParameterClass();
                // 如果是基本参数类型直接设置
                if (parameterClass == Integer.class || parameterClass == String.class) {
                    preparedStatement.setObject(1, param);
                } else {
                    if (parameterMappings != null) {
                        for (ParameterMapping parameterMapping : parameterMappings) {
                            int i = 1;
                            /**
                             * 利用反射设置
                             */
                            Field field = parameterClass.getDeclaredField(parameterMapping.getParameterName());
                            field.setAccessible(true);
                            Object value = field.get(param);
                            preparedStatement.setObject(i, value);
                            i++;
                        }
                    }
                }

                /**
                 * 查询结果集
                 */
                rs = preparedStatement.executeQuery();
                // 遍历结果集
                Class resultClass = mappedStatement.getResultType();

                while (rs.next()) {
                    Object resultObj = resultClass.newInstance();
                    // 通过反射去给实例赋值
                    ResultSetMetaData metaData = rs.getMetaData();

                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);

                        // 前提是列名和属性名称要一周
                        Field declaredField = resultClass.getDeclaredField(columnName);
                        // 暴力访问
                        declaredField.setAccessible(true);
                        // rs的下标是从1开始的
                        declaredField.set(resultObj, rs.getObject(i));
                    }

                    results.add(resultObj);
                }


            } else if (mappedStatement.getStatementType().equalsIgnoreCase(StatementType.CALLABLE.toString())) {


            }

            return (T) results.get(0);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs !=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection !=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
