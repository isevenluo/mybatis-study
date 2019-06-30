package com.architecture.mybatis.framework.parser;

import com.architecture.mybatis.framework.config.Configuration;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;


/**
 * @description:
 * @outhor: chong
 * @create: 2019-06-30 14:13
 */
public class XMLConfigParser {

    private Configuration configuration;

    public XMLConfigParser(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration parseConfiguration(Element element) {
        /**
         * 解析environments
         */
        parseEnvironments(element.element("environments"));
        /**
         * 解析mappers
         */
        parseMappers(element.element("mappers"));
        return configuration;

    }

    private void parseMappers(Element mappers) {
        List<Element> elements = mappers.elements("mapper");
        for (Element element : elements) {
            parseMapper(element);
        }
    }

    private void parseMapper(Element element) {
        String resource = element.attributeValue("resource");
        /**
         * 将文件流封装为一个文档对象
         */
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        Document document = DocumentReader.createDocument(inputStream);
        XMLMapperParser xmlMapperParser = new XMLMapperParser(configuration);
        /**
         * 解析sql标签为statement
         */
        xmlMapperParser.parse(document.getRootElement());
    }

    private void parseEnvironments(Element element) {
        String defaultId = element.attributeValue("default");
        /**
         * 获取所有environment
         */
        List<Element> elements = element.elements("environment");

        for (Element envElement :elements) {
            String envId = envElement.attributeValue("id");
            if ( envId != null && envId.equals(defaultId)) {
                parseDateSource(envElement.element("dataSource"));
            }
        }
    }

    private void parseDateSource(Element dataSource) {
        String type = dataSource.attributeValue("type");
        if (type == null || type.equals("")) {
            type = "DBCP";
        }
        List<Element> elements = dataSource.elements("property");

        Properties properties = new Properties();

        for (Element element : elements) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }
        BasicDataSource basicDataSource = null;
        if (type.equals("DBCP")) {
            basicDataSource = new BasicDataSource();
            basicDataSource.setDriverClassName(properties.getProperty("driver"));
            basicDataSource.setUrl(properties.getProperty("url"));
            basicDataSource.setUsername(properties.getProperty("username"));
            basicDataSource.setPassword(properties.getProperty("password"));
        }
        configuration.setDataSource(basicDataSource);
    }
}
