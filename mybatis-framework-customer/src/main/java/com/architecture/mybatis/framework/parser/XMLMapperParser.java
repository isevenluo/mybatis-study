package com.architecture.mybatis.framework.parser;

import com.architecture.mybatis.framework.config.Configuration;
import com.architecture.mybatis.framework.mapping.BoundSql;
import com.architecture.mybatis.framework.mapping.ParameterMapping;
import com.architecture.mybatis.framework.statement.MappedStatement;
import javafx.beans.binding.MapExpression;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析Mapper中的sql标签为Statement对象
 * @description:
 * @outhor: chong
 * @create: 2019-06-30 14:50
 */
public class XMLMapperParser {

    private Configuration configuration;

    public XMLMapperParser(Configuration configuration) {
        this.configuration = configuration;
    }



    public void parse(Element rootElement) {
        Map<String,MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
        if (mappedStatementMap == null) {
            mappedStatementMap = new HashMap<>();
        }

        String namespace = rootElement.attributeValue("namespace");
        /**
         * 解析select标签
         */
        List<Element> selectMapper = rootElement.elements("select");

        for (Element element : selectMapper) {
            MappedStatement mappedStatement = new MappedStatement();

            try {
                String id = element.attributeValue("id");
                Class parameterClass = Class.forName(element.attributeValue("parameterType"));
                Class resultType = Class.forName(element.attributeValue("resultType"));
                String statementType = element.attributeValue("statementType");
                String sql = element.getTextTrim();
                /**解析sql语句*/
                BoundSql boundSql = parseBoundSql(sql);

                mappedStatement.setBoundSql(boundSql);
                mappedStatement.setParameterClass(parameterClass);
                mappedStatement.setResultType(resultType);
                mappedStatement.setStatementType(statementType);
                mappedStatementMap.put(namespace+"."+id,mappedStatement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(mappedStatementMap);
        configuration.setMappedStatementMap(mappedStatementMap);

    }


    public BoundSql parseBoundSql (String sql ) {
        BoundSql bound = new BoundSql();
        List<ParameterMapping> parameterMappings = new ArrayList<>();
        String r = "(\\{[^\\}]+})";
        Pattern pattern = Pattern.compile(r);
        Matcher matcher = pattern.matcher(sql);
         while (matcher.find()) {
            ParameterMapping parameterMapping = new ParameterMapping();
            String paramName = matcher.group(1);
            parameterMapping.setParameterName(paramName.substring(1,paramName.length()-1));
            parameterMappings.add(parameterMapping);
        }

        String regex = "\\#\\{[^\\}]+\\}";
        String boundSql = sql.replaceAll(regex,"?");
        bound.setBoundSql(boundSql);
        bound.setParameterMappings(parameterMappings);
        return bound;
    }

    public static void main(String[] args) {
        String sql = "SELECT * FROM user where id = #{id} and username = #{username}";

        String r = "(\\{[^\\}]+})";
        Pattern pattern = Pattern.compile(r);
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String paramName = matcher.group(1);
            System.out.println(paramName.substring(1,paramName.length()-1));
        }


    }




}
