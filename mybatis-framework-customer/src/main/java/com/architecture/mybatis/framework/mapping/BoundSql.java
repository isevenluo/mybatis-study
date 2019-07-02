package com.architecture.mybatis.framework.mapping;

import lombok.*;

import java.util.List;

/**
 * @description:
 * @outhor: chong
 * @create: 2019-06-30 16:24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoundSql {
    /**
     * 解析之后的sql
     */
    private String boundSql;
    private List<ParameterMapping> parameterMappings;

    public void addParameterMapping(ParameterMapping parameterMapping) {
        this.parameterMappings.add(parameterMapping);
    }

}
