package com.architecture.mybatis.framework.sqlsession;

import com.architecture.mybatis.framework.config.Configuration;
import com.architecture.mybatis.framework.statement.MappedStatement;

import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statementId, Object param) {

        List<Object> list = selectList(statementId, param);
        if (list != null && list.size() == 1) {
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statementId, Object param) {
        // TODO Auto-generated method stub
        // 真正和数据库进行CRUD操作的类
        // 去执行statement，缓存执行器，基本执行器
        Executor executor = new SimpleExecutor();
        //根据statementId获取MappedStatement
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return executor.query(configuration,mappedStatement, param);
    }
}
