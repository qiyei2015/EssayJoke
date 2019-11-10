package com.qiyei.sdk.xml;

import com.qiyei.sdk.log.LogManager;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Created by qiyei2015 on 2017/12/31.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class Dom4jHelper {

    private static final String TAG = "DOM4J";

    /**
     * 解析xml文件
     * @param xmlFile
     */
    public static void parseXml(File xmlFile){
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(xmlFile);

            Element element = document.getRootElement();
            readeNode(element,"");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析xml
     * @param inputStream
     */
    public static void parseXml(InputStream inputStream){
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);

            Element element = document.getRootElement();
            readeNode(element,"");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    /**
     * 递归调用，即可解析xml
     * @param root
     */
    private static void readeNode(Element root,String prefix){
        if (root == null){
            return;
        }
        LogManager.i(TAG,prefix +"name:" + root.getName());
        //获取所有的属性
        List<Attribute> attrs = root.attributes();
        if (attrs != null && attrs.size() > 0){
            for (Attribute attribute : attrs){
                LogManager.i(TAG,"attribute name:" + attribute.getName() + " value:" + attribute.getValue());
            }
        }
        prefix += "\t";
        List<Element> childNodes = root.elements();
        for (Element element : childNodes){
            readeNode(element,prefix);
        }
    }

}
