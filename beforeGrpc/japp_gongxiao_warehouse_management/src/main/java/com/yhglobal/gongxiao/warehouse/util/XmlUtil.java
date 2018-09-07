package com.yhglobal.gongxiao.warehouse.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * XML文件工具类
 *
 * @author liukai
 */
public class XmlUtil {

    private static Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    /**
     * 将指定路径下的XML文件转化成对象
     * @param xmlPath  目标文件路径
     * @param c       对象Class类型
     * @return 对象实例
     */
    public static <T> T xml2Object(String xmlPath, Class<T> c) {
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            T t = (T) unmarshaller.unmarshal(new File(xmlPath));

            return t;

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 将对象转换成XML文件，存放到指定目录下
     * @param sourceParth  生成文件的路径
     * @param object      对象
     * @return 返回xml文件
     */
    public static void object2Xml(String sourceParth, Object object) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshal = context.createMarshaller();

            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);    // 格式化输出
            marshal.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");         // 编码格式,默认为utf-8
            marshal.setProperty(Marshaller.JAXB_FRAGMENT, false);           // 是否省略xml头信息
            marshal.setProperty("jaxb.encoding", "utf-8");
            marshal.marshal(object, writer);

            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(sourceParth)));
                bufferedWriter.write(writer.toString());
                bufferedWriter.close();
            } catch (IOException e) {
                logger.error("将XML字符流写入XML文件的时候报错", e.toString());
                e.printStackTrace();
            }
        } catch (Exception e) {
            logger.error("将{}转化成XML文件的时候报错:{}", object.getClass().getName(), e.toString());
            e.printStackTrace();
        }

    }

}
