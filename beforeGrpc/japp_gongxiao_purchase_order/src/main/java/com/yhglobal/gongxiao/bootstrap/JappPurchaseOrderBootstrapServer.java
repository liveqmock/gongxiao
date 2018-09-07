package com.yhglobal.gongxiao.bootstrap;

import com.yhglobal.gongxiao.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class JappPurchaseOrderBootstrapServer {

    private static final Logger logger = LoggerFactory.getLogger(JappPurchaseOrderBootstrapServer.class);

    private static ApplicationContext applicationContext = null;

    public static void main(String[] args) throws InterruptedException {
        String serverMode = CommonUtil.getServerMode();
        logger.info("serverMode={}", serverMode);
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        synchronized (JappPurchaseOrderBootstrapServer.class) {  // 避免spring容器退出
            JappPurchaseOrderBootstrapServer.class.wait();
        }
    }
}
