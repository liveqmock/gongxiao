package com.yhglobal.gongxiao.foudation.util;

import com.yhglobal.gongxiao.foudation.basicdata.service.BasicDataSynService;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.io.IOException;

/**
 * 同步主数据工具类
 *
 * @author: 陈浩
 **/
public class SynMainDataUtil {
    public static void main(String[] args) throws IOException {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setValidating(false);
        context.load("classpath*:spring_main.xml");
        context.refresh();
        BasicDataSynService userService = context.getBean(BasicDataSynService.class);
//        userService.synProjectInfo("剃须刀");
//        userService.synSupplierInfo("飞利浦");
//        userService.synCustomerInfo("青岛怡通众合经贸发展有限公司");
        userService.synProductInfo("");
        userService.synUnitInfo("");
        System.out.println("同步完毕");

    }

}
