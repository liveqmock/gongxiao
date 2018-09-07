package com.yhglobal.gongxiao.edi.util;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.edi.entity.po.PurchaseOrder;
import com.yhglobal.gongxiao.edi.entity.poa.PurchaseConfirmOrder;
import com.yhglobal.gongxiao.edi.entity.ro.MainWarehouseReturnOrder;
import com.yhglobal.gongxiao.edi.entity.ro.SpareWarehouseReturnOrder;
import com.yhglobal.gongxiao.edi.entity.sc.OutBoundOrder;
import com.yhglobal.gongxiao.edi.entity.sn.ShipmentConfirmOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;

/**
 * XML文件工具类
 *
 * @author liukai
 */
public class XmlUtil {

    private static Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    /**
     * 将指定路径下的XML文件转化成对象
     *
     * @param xmlPath 目标文件路径
     * @param c       对象Class类型
     * @return 对象实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T xml2Object(String xmlPath, Class<T> c) throws JAXBException {
//        try {
        JAXBContext context = JAXBContext.newInstance(c);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        T t = (T) unmarshaller.unmarshal(new File(xmlPath));

        return t;
//
//        } catch (JAXBException e) {
//            logger.error("{}", e);
//            e.printStackTrace();
//            return null;
//        }

    }

    /**
     * 将对象转换成XML文件，存放到指定目录下
     *
     * @param sourceParth 生成文件的路径
     * @param object      对象
     * @return 返回xml文件
     */
    public static void object2Xml(String sourceParth, Object object) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshal = context.createMarshaller();
//            marshal.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);    // 格式化输出
            marshal.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");         // 编码格式,默认为utf-8
            marshal.setProperty(Marshaller.JAXB_FRAGMENT, false);           // 是否省略xml头信息
            marshal.setProperty("jaxb.encoding", "utf-8");
            marshal.marshal(object, writer);

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(sourceParth)));
            bufferedWriter.write(writer.toString());
            bufferedWriter.close();

        } catch (Exception e) {
            logger.error("将{}转化成XML文件的时候报错:{}", object.getClass().getName(), e.toString());
            e.printStackTrace();
        }

    }

    public static void main(String[] aa) throws JAXBException {

        // xml -> object
        String path = "E:\\test\\ediTestTemplate\\";
        //PO
        Class clazz = PurchaseOrder.class;
        Object object = xml2Object(path + "po.xml.bak", clazz);
        String jsonString = JSON.toJSONString(object);
        PurchaseOrder purchaseOrder = (PurchaseOrder) xml2Object(path + "po.xml.bak", clazz);
        //POA
        PurchaseConfirmOrder purchaseConfirmOrder = xml2Object(path + "poa.xml", PurchaseConfirmOrder.class);
        //RO
        MainWarehouseReturnOrder mainWarehouseReturnOrder = xml2Object(path + "JD_szyh_RO_4216861_20180629145716.xml", MainWarehouseReturnOrder.class);
        //ROS
        SpareWarehouseReturnOrder spareWarehouseReturnOrder = xml2Object(path + "ros.xml", SpareWarehouseReturnOrder.class);
        //SC
        OutBoundOrder outBoundOrder = xml2Object(path + "sc.xml", OutBoundOrder.class);
        //SN
        ShipmentConfirmOrder shipmentConfirmOrder = new ShipmentConfirmOrder();
//
////         object -> xml
//        object2Xml(path+"po2.xml",purchaseOrder);
//        object2Xml(path+"poa2.xml",purchaseConfirmOrder);
//        object2Xml(path+"新ro.xml",mainWarehouseReturnOrder);
//        object2Xml(path+"ros2.xml",spareWarehouseReturnOrder);
//        object2Xml(path+"sc2.xml",outBoundOrder);
    }

}
