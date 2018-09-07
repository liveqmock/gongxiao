package com.yhglobal.gongxiao.payment.bootstrap;

import com.yhglobal.gongxiao.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 *
 */
    public class JappPaymentManagementBootstrapServer {

    private static final Logger logger = LoggerFactory.getLogger(JappPaymentManagementBootstrapServer.class);

    private static ApplicationContext applicationContext = null;

    public static void main(String[] args) throws InterruptedException {
        String serverMode = CommonUtil.getServerMode();
        logger.info("serverMode={}", serverMode);
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        synchronized (JappPaymentManagementBootstrapServer.class) {  // 避免spring容器退出
            JappPaymentManagementBootstrapServer.class.wait();
        }
    }

}
