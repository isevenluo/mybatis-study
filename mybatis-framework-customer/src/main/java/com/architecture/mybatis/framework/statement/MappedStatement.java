package com.architecture.mybatis.framework.statement;

import com.architecture.mybatis.framework.mapping.BoundSql;
import lombok.*;

/**
 * @description:
 * @outhor: chong
 * @create: 2019-06-30 15:46
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MappedStatement {

    private BoundSql boundSql;

    private String statementType;

    private Class resultType;

    private Class parameterClass;


}
