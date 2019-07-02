package com.architecture.mybatis.framework.sqlsession;

import com.architecture.mybatis.framework.config.Configuration;
import com.architecture.mybatis.framework.mapping.BoundSql;
import com.architecture.mybatis.framework.mapping.ParameterMapping;
import com.architecture.mybatis.framework.mapping.SqlSource;
import com.architecture.mybatis.framework.statement.MappedStatement;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;



public class SimpleExecutor implements Executor {

	@Override
	public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object param) {
		/*
		 * a).获取连接 //读取配置文件，获取数据源对象，根据数据源获取连接 通过Configuration对象获取DataSource对象
		 * 通过DataSource对象，获取Connection
		 */
		Connection connection = null;
		List<Object> results = new ArrayList<>();
		try {
			DataSource dataSource = configuration.getDataSource();
			connection = dataSource.getConnection();

			// 获取SQL语句
			SqlSource sqlSource = mappedStatement.getSqlSource();
			BoundSql boundSql = sqlSource.getBoundSql();
			String sql = boundSql.getBoundSql();

			// 获取statementType
			String statementType = mappedStatement.getStatementType();
			if ("prepared".equals(statementType)) {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);

				// 设置参数
				List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
				// 获取入参类型
				Class<?> parameterTypeClass = mappedStatement.getParameterClass();

				// 八种基本类型都可以如此处理
				if (parameterTypeClass == Integer.class || parameterTypeClass == String.class) {
					// 根本不关心#{}中的名称到底是什么
					preparedStatement.setObject(1, param);
				} else {// Map和List的我们暂不处理
						// 我们此处主要解决POJO类型

					for (ParameterMapping parameterMapping : parameterMappings) {
						int i = 1;
						/**
						 * 利用反射设置
						 */
						Field field = parameterTypeClass.getDeclaredField(parameterMapping.getParameterName());
						field.setAccessible(true);
						Object value = field.get(param);
						preparedStatement.setObject(i, value);
						i++;
					}

				}

				ResultSet resultSet = preparedStatement.executeQuery();
				Class<?> resultTypeClass = mappedStatement.getResultType();


				while (resultSet.next()) {
					Object resultObj = resultTypeClass.newInstance();
					// 通过反射去给实例赋值
					ResultSetMetaData metaData = resultSet.getMetaData();

					int columnCount = metaData.getColumnCount();
					for (int i = 1; i <= columnCount; i++) {
						String columnName = metaData.getColumnName(i);

						// 前提是列名和属性名称要一致
						Field declaredField = resultTypeClass.getDeclaredField(columnName);
						// 暴力访问
						declaredField.setAccessible(true);
						// rs的下标是从1开始的
						declaredField.set(resultObj, resultSet.getObject(i));
					}

					results.add(resultObj);
				}
			} else {
				// ....
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (List<T>) results;
	}

}
