package com.architecture.mybatis.framework.config;

import com.architecture.mybatis.framework.statement.MappedStatement;
import lombok.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置
 * @author luochong
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Configuration {

    private DataSource dataSource;

    private Map<String,MappedStatement> mappedStatementMap = new HashMap<>();

    public void addMappedStatement(String statementId, MappedStatement mappedStatement) {
        this.mappedStatementMap.put(statementId, mappedStatement);
    }

}
