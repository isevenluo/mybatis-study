package com.architecture.mybatis.framework.config;

import com.architecture.mybatis.framework.statement.MappedStatement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 全局配置
 * @author luochong
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {

    private DataSource dataSource;

    private Map<String,MappedStatement> mappedStatementMap;


}
