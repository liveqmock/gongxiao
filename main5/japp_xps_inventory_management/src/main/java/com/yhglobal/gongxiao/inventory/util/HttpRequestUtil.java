package com.yhglobal.gongxiao.inventory.util;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.coreutil.OkHttpManager;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 发送Http请求工具类
 * @author liukai
 */
public class HttpRequestUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    public static String sendPost(String url, String requestJson, String charsetName) throws Exception{
        logger.info("[IN][sendPost] params: url={},request={},charsetName={}", url, JSON.toJSONString(requestJson),charsetName);
        try {
            MediaType jsonMediaType = MediaType.parse("application/json");
            RequestBody requestBody = RequestBody.create(jsonMediaType, requestJson);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#transportation response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return content;
            } else {
                String msg = String.format("got error http status code from transportation: statusCode=%d", statusCode);
                logger.error(msg);
                throw new RuntimeException(msg);
            }
        }catch (Exception e){
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }

}
