package com.yhglobal.gongxiao.warehouse.util;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 发送Http请求工具类
 * @author liukai
 */
public class HttpRequestUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    public static String sendPost(String url, Object request, String charsetName) throws Exception{
        logger.info("[IN][sendPost] params: url={},request={},charsetName={}", url, JSON.toJSONString(request),charsetName);
        try {
            URL linkUrl = new URL(url);
            // 将url 连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
            HttpURLConnection connection = (HttpURLConnection) linkUrl.openConnection(); // 此时cnnection只是为一个连接对象,待连接中
            //设置超时时间
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
            connection.setDoOutput(true);
            // 设置连接输入流为true
            connection.setDoInput(true);
            // 设置请求方式为post
            connection.setRequestMethod("POST");
            // post请求缓存设为false
            connection.setUseCaches(false);
            // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setInstanceFollowRedirects(true);
            // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
            connection.setRequestProperty("Accept-Charset", charsetName);
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charsetName);
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
            connection.connect();
            // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
            // 将参数输出到连接
            dataout.write(JSON.toJSONString(request).getBytes());
//            dataout.writeBytes(JSON.toJSONString(request));
            // 输出完成后刷新并关闭流
            dataout.flush();
            dataout.close();

            // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charsetName));
            String line;
            StringBuilder stringBuilder = new StringBuilder(); // 用来存储响应数据

            // 循环读取流,若不到结尾处
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(System.getProperty("line.separator"));
            }
            bufferedReader.close();     // 重要且易忽略步骤 (关闭流,切记!)
            connection.disconnect();    // 销毁连接
            logger.info("[OUT] get sendPost success: result={}", stringBuilder.toString());
            return stringBuilder.toString();
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

}
