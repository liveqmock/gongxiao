package com.yhglobal.gongxiao.transportataion.eventnotification;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.coreutil.OkHttpManager;
import com.yhglobal.gongxiao.transportataion.sendtotransportation.sales.model.SignInfo;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 负责组装报文 并把发送请求到TMS
 */
public class XpsTransportationNotifyManager {

    private static final String SIGNED_URL = "/salesorder/outboundSigned";

    private static Logger logger = LoggerFactory.getLogger(XpsTransportationNotifyManager.class);

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * 运输模块通知销售模块
     * 出库单签收
     *
     * @param projectUrl 项目url
     * @param signInfo   签收人信息
     * @return
     */
    public static GongxiaoResult salesOutboundSigned(String projectUrl,
                                                     SignInfo signInfo) {
        logger.info("[salesOutboundSigned] params: projectUrl={}, signInfo={}", projectUrl, JSON.toJSONString(signInfo));
        GongxiaoResult gongxiaoResult = null;

        try {
            String jsonString = JSON.toJSONString(signInfo);
            MediaType jsonMediaType = MediaType.parse("application/json");
            RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
            String ulr = projectUrl+SIGNED_URL;
            Request request = new Request.Builder()
                    .url(ulr)
                    .post(requestBody)
                    .build();

            try {
                Response httpResponse = OkHttpManager.execute(request);
                String content = httpResponse.body().string();
                logger.info("#project response#: {}", content);

                int statusCode = httpResponse.code();
                if (statusCode == 200) {
                    //请求成功
                    logger.error("#[OUT] http request finished.");
                    gongxiaoResult = JSON.parseObject(content, GongxiaoResult.class);
                } else {
                    //http请求错误
                    String msg = String.format("got error http status code from transportation: statusCode=%d", statusCode);
                    logger.error(msg);
                    throw new RuntimeException(msg);
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error("# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    public static void main(String[] args){
        SignInfo signInfo = new SignInfo();
        signInfo.setCustOrdNo("XPS_shaver_SOOUT2018090418060400");
        signInfo.setTmsOrdNo("SZ180801000Y-01");
        signInfo.setPostedBy("gecan");
        signInfo.setSignedTime(new Date());
        signInfo.setTransporter("transporter");
        signInfo.setProjectId(146798161L);
//        salesOutboundSigned("http://47.104.154.165:12010",signInfo);
        salesOutboundSigned("http://127.0.0.1:12010",signInfo);
    }


}
