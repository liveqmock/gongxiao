package com.yhglobal.gongxiao.edi.bootstrap;

import com.yhglobal.gongxiao.edi.service.impl.FtpSchduledTask;
import com.yhglobal.gongxiao.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 葛灿
 */
public class EdiClientBootstrapServer {

    private static final Logger logger = LoggerFactory.getLogger(EdiClientBootstrapServer.class);

    private static ApplicationContext applicationContext = null;

    public static void main(String[] args) throws InterruptedException {
        String serverMode = CommonUtil.getServerMode();
        logger.info("serverMode={}", serverMode);
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        FtpSchduledTask bean = applicationContext.getBean(FtpSchduledTask.class);
        synchronized (EdiClientBootstrapServer.class) {  // 避免spring容器退出
            EdiClientBootstrapServer.class.wait();
        }
    }
}
