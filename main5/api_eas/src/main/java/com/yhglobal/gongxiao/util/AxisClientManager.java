package com.yhglobal.gongxiao.util;

import com.yhglobal.gongxiao.constant.EasConstant;
import com.yhglobal.gongxiao.login.EASLoginSoapBindingStub;
import com.yhglobal.gongxiao.login.WSContext;
import com.yhglobal.gongxiao.model.EASEnvironmentModel;
import org.apache.axis.client.Service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class AxisClientManager {

    private volatile static Service service;

    private volatile static EASLoginSoapBindingStub stub;

    private static long lastLoginTime = 0;  //stub最后登录时间

    private static long interval = 12 * 60 * 1000; //每10分钟登录一次stub

    private static EASEnvironmentModel easEnvironmentModel;

    private static String loginUrl;

    static {
        easEnvironmentModel = PropertyParseUtil.parseProperties();
        loginUrl = easEnvironmentModel.getUrl() + EasConstant.LOGIN;
    }

    /**
     * 获取调用eas webservice 需要的service client
     */
    public static Service getAxisClient() throws MalformedURLException, RemoteException {
        long currentMills = System.currentTimeMillis();
        if (service == null || stub == null || currentMills - lastLoginTime > interval) {
            synchronized (AxisClientManager.class) { //注意：需要设定登陆时长 默认大约是90秒超时，会造成业务线程在等待锁导致耗光
                if (service == null || stub == null || currentMills - lastLoginTime > interval) {
                    service = new org.apache.axis.client.Service();
                    stub = new EASLoginSoapBindingStub(new java.net.URL(loginUrl), service);
                    EASLoginSoapBindingStub stub = new EASLoginSoapBindingStub(new java.net.URL(loginUrl), service);
                    stub.setTimeout(1500); //需显式设定超时时长 单位毫秒
                    WSContext login = stub.login(easEnvironmentModel.getUser(),
                            easEnvironmentModel.getPwd(),
                            easEnvironmentModel.getSlame(),
                            easEnvironmentModel.getDcname(),
                            easEnvironmentModel.getLanguage(),
                            Integer.parseInt(easEnvironmentModel.getDbtype()));//调用ws提供的方法
                    lastLoginTime = currentMills;
                }
            }
        }
        return service;
    }


}
