package com.architecture.mybatis.framework.sqlsession;

import com.architecture.mybatis.framework.config.Configuration;
import com.architecture.mybatis.framework.parser.DocumentReader;
import com.architecture.mybatis.framework.parser.XMLConfigParser;
import org.dom4j.Document;

import java.io.InputStream;
import java.io.Reader;

/**
 * @description:
 * @outhor: chong
 * @create: 2019-06-30 15:16
 */
public class SqlSessionFactoryBuilder {

    /**
     * 封装全局配置文件信息和所有映射文件信息
     */
    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream inputStream) {

        /**
         * 解析全局文件，封装为Configuration
         * 通过InputStream 流对象，去创建Document（dom4j） -- 此时还没有对xml文件中的语义进行解析
         * DocumentReader -- 去加载InputStream创建Document对象
         */
        Document document = DocumentReader.createDocument(inputStream);

        /**
         * 进行Mybatis语义解析（全局配置文件语义解析，映射文件语义解析）
         */
        XMLConfigParser xmlConfigParser = new XMLConfigParser(configuration);

        configuration = xmlConfigParser.parseConfiguration(document.getRootElement());

        return build();
    }



    private SqlSessionFactory build() {
        return new DefaultSqlSessionFactory(configuration);
    }


}
