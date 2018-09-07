package com.yhglobal.gongxiao.util;

import com.yhglobal.gongxiao.model.EASEnvironmentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class PropertyParseUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertyParseUtil.class);

    public static EASEnvironmentModel parseProperties() {
        String env = getServerMode();
        logger.info("env:{}",env);
        System.out.println(env);
        InputStream fis = null;
        Properties properties = new Properties();
        try {
            String configFile = "eas_" + env + ".properties";
            fis = PropertyParseUtil.class.getClassLoader().getResourceAsStream(configFile);
            if (fis == null) {
                logger.error("fail to load config file:  {}", configFile);
            } else {
                logger.info("load config file success: {}", configFile);
            }
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        EASEnvironmentModel easEnvironmentModel = new EASEnvironmentModel();
        String url= properties.getProperty("eas.url");
        logger.info("--->url={}", url);
        String user= properties.getProperty("eas.user");
        String pwd= properties.getProperty("eas.pwd");
        String slname= properties.getProperty("eas.slname");
        String dcname= properties.getProperty("eas.dcname");
        String language= properties.getProperty("eas.language");
        String dbtype= properties.getProperty("eas.dbtype");
        easEnvironmentModel.setUrl(url);
        easEnvironmentModel.setUser(user);
        easEnvironmentModel.setPwd(pwd);
        easEnvironmentModel.setSlame(slname);
        easEnvironmentModel.setDcname(dcname);
        easEnvironmentModel.setLanguage(language);
        easEnvironmentModel.setDbtype(dbtype);
        logger.info("easEnvironmentModel: {}",easEnvironmentModel.toString());
        return easEnvironmentModel;
    }

    public static String getServerMode() {
        String env = System.getProperty("server.mode");
        if(env == null || env.equals(""))    env = System.getenv("server.mode");
        if(env == null || env.equals("")) {
            System.out.println("server.mode is not set properly...exit");
            System.exit(1); //强制性设置server.mode参数，不设的话则系统直接退出
        }
        return env;
    }

    public static void main(String[] args) {
        parseProperties();
    }

}
