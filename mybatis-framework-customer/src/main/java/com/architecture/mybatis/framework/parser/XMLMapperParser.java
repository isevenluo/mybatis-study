package com.architecture.mybatis.framework.parser;

import com.architecture.mybatis.framework.config.Configuration;
import com.architecture.mybatis.framework.mapping.BoundSql;
import com.architecture.mybatis.framework.mapping.ParameterMapping;
import com.architecture.mybatis.framework.mapping.SqlSource;
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
 *
 * @description:
 * @outhor: chong
 * @create: 2019-06-30 14:50
 */
public class XMLMapperParser {

    private Configuration configuration;
    String namespace;

    public XMLMapperParser(Configuration configuration) {
        this.configuration = configuration;
    }


    public void parse(Element rootElement) {


        namespace = rootElement.attributeValue("namespace");

        // 将解析出来的MappedStatement对象放入Configuration对象中的map集合
        parseStatements(rootElement.elements("select"));

        System.out.println("configuration = " + configuration);

    }

    private void parseStatements(List<Element> selectMapper) {
        for (Element element : selectMapper) {
            parseStatement(element);
        }


    }

    private void parseStatement(Element element) {

        String id = element.attributeValue("id");
        id = namespace + "." + id;
        Class parameterClass = getClassByType(element.attributeValue("parameterType"));

        Class resultType = getClassByType(element.attributeValue("resultType"));

        String statementType = element.attributeValue("statementType");

        String sql = element.getTextTrim();
        /**
         * 将未解析占位符的sql放到对象中，解析sql在调用时在加载解析
         */
        SqlSource sqlSource = new SqlSource(sql);

        // 封装MappedStatement
        MappedStatement mappedStatement = new MappedStatement(sqlSource,statementType,resultType,parameterClass);
        configuration.addMappedStatement(id,mappedStatement);


    }

    public Class getClassByType(String parameterType) {
        if (parameterType == null || parameterType.equals("")) {
            return null;
        }
        try {
            Class parameterClass = Class.forName(parameterType);
            return parameterClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;


    }


    public BoundSql parseBoundSql(String sql) {
        BoundSql bound = new BoundSql();
        List<ParameterMapping> parameterMappings = new ArrayList<>();
        String r = "(\\{[^\\}]+})";
        Pattern pattern = Pattern.compile(r);
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            ParameterMapping parameterMapping = new ParameterMapping();
            String paramName = matcher.group(1);
            parameterMapping.setParameterName(paramName.substring(1, paramName.length() - 1));
            parameterMappings.add(parameterMapping);
        }

        String regex = "\\#\\{[^\\}]+\\}";
        String boundSql = sql.replaceAll(regex, "?");
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
            System.out.println(paramName.substring(1, paramName.length() - 1));
        }


    }


}
