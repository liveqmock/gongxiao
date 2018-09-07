package com.yhglobal.gongxiao.util;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.XpsCouponManager;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.coreutil.OkHttpManager;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author 王帅
 */
public class SendRequestAndGetResponseUtil {

    private static Logger logger = LoggerFactory.getLogger(XpsCouponManager.class);

    public static  GongxiaoResult sendRequestGetResponse(Object data,String url,String methodName){
        GongxiaoResult gongxiaoResult = null;

        // 发送请求
        String dataJson = JSON.toJSONString(data);
        logger.info("send request to jweb_coupon, dataJson :{}",dataJson);
        MediaType jsonMediaType = MediaType.parse("application/json-patch+json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, dataJson);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        // 获取响应
        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#"+methodName+" response#: {}", content);
            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from jweb_coupon: statusCode=%d", statusCode);
                logger.error(msg);
                throw new RuntimeException(msg);
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

        return gongxiaoResult;

    }
}
