package com.architecture.mybatis.framework.sqlsession;

import com.architecture.mybatis.framework.config.Configuration;

/**
 * @description:
 * @outhor: chong
 * @create: 2019-06-30 15:26
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 真正实现SqlSession 接口的代码
     * @return
     */
    @Override
    public SqlSession openSession() {

        return new DefaultSqlSession(configuration);
    }
}
