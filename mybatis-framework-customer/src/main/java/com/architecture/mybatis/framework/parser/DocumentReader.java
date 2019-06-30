package com.architecture.mybatis.framework.parser;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * 创建Document对象
 * @description:
 * @outhor: chong
 * @create: 2019-06-30 14:46
 */
public class DocumentReader {

    public static Document createDocument(InputStream inputStream) {
        Document document = null;
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(inputStream);
            return document;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

}
