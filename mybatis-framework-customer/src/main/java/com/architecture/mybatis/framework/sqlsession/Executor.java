package com.architecture.mybatis.framework.sqlsession;

import com.architecture.mybatis.framework.config.Configuration;
import com.architecture.mybatis.framework.statement.MappedStatement;

import java.util.List;



public interface Executor {

	<T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object param);
}
