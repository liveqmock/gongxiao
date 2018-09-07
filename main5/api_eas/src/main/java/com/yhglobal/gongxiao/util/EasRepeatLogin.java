package com.yhglobal.gongxiao.util;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * 每隔5秒抢一次EAS登录
 *
 * @author: 陈浩
 **/
public class EasRepeatLogin {
    public static void main(String[] args) throws MalformedURLException, RemoteException, InterruptedException {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                org.apache.axis.client.Service service = new org.apache.axis.client.Service();
                while (true){
                    try {
                        EasBuildParameter.easLogin(service);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }
}
