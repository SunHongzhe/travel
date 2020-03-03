package com.zuikc.travel.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @program: February
 * @description:
 * @author: Sun
 * @create: 2020/02/14 23:45
 * @version: 1.0
 */
public class BeanFactory {

    public static<T> T getBean(String id,Class<T> c){

        //生产对象---根据清单生产----配置文件----将每一个bean对象的生产的细节配到配置文件中
        //使用dom4j的xml解析技术

        try {
            //1、创建解析器
            SAXReader reader = new SAXReader();
            //2、解析文档---bean.xml在src下
            String path = BeanFactory.class.getClassLoader().getResource("bean.xml").getPath();
            Document doc = reader.read(path);
            //3、获得元素---参数是xpath规则
            Element element = (Element) doc.selectSingleNode("//bean[@id='"+id+"']");
            //<bean id="userService" class="com.zuikc.service.impl.UserServiceImpl"></bean>
            String className = element.attributeValue("class");
            //com.zuikc.service.impl.UserServiceImpl
            //使用反射创建对象
            Class clazz = Class.forName(className);
            T t = (T) clazz.newInstance();

            return t;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}