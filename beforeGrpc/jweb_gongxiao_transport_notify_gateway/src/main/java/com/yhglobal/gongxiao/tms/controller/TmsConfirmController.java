package com.yhglobal.gongxiao.tms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.spring.ApplicationContextProvider;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.sales.service.SalesScheduleDeliveryService;
import com.yhglobal.gongxiao.tms.config.PortalConfig;
import com.yhglobal.gongxiao.tms.dao.SignInfoDao;
import com.yhglobal.gongxiao.tms.dao.SignedPictrueMsgDao;
import com.yhglobal.gongxiao.tms.dto.TmsRequest;
import com.yhglobal.gongxiao.tms.dto.TmsSignInfo;
import com.yhglobal.gongxiao.tms.dto.TmsSignedPictrueMsg;
import com.yhglobal.gongxiao.tms.model.SignInfo;
import com.yhglobal.gongxiao.tms.model.SignedPictrueMsg;
import com.yhglobal.gongxiao.tms.task.SyncTmsSignInfo;
import com.yhglobal.gongxiao.tms.util.TransportPortalTraceIdGenerator;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.warehouse.service.OutboundService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 接收WMS确认信息Controller
 *
 * @author 葛灿
 */
@Controller
@RequestMapping("/transport")
public class TmsConfirmController {

    private static Logger logger = LoggerFactory.getLogger(TmsConfirmController.class);

    private static final String SIGN = "sign";
    private static final String RECEIPT = "receipt";
    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    SignedPictrueMsgDao signedPictrueMsgDao;

    @Autowired
    SignInfoDao signInfoDao;

    @Reference
    OutboundService outboundService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolExecutor;

    @Reference
    private SalesScheduleDeliveryService salesScheduleDeliveryService;



    @RequestMapping("/tmsNotification")
    @ResponseBody
    public GongxiaoResult notifyFromTms(HttpServletRequest request, @RequestBody String requestJson) {
        String traceId = null;
        RpcHeader rpcHeader;
        GongxiaoResult result = new GongxiaoResult(0, null);
        try {
            traceId = TransportPortalTraceIdGenerator.genTraceId("0", TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, "1", "WMS系统");
            logger.info("#traceId={}# [IN][notifyFromTms] params:  tmsRequest={}",
                    traceId, requestJson);
            if (StringUtils.isEmpty(request)) {
                result = new GongxiaoResult(101, "空请求");
                logger.error("#traceId={}# [OUT] no params!",traceId);
                return result;
            }
            TmsRequest tmsRequest = null;
            try {
                tmsRequest = JSON.parseObject(requestJson, TmsRequest.class);
            } catch (JSONException e) {
                result = new GongxiaoResult(101, "JSON格式错误");
                logger.error("#traceId={}# [OUT] params invalid!",traceId);
                return result;
            }
            String custOrdNo = tmsRequest.getCustOrdNo();
            String data = tmsRequest.getData();
            String notificationType = tmsRequest.getNotificationType();
            String remark = tmsRequest.getRemark();
            String tmsOrdNo = tmsRequest.getTmsOrdNo();
//            logger.info("#traceId={}# [IN][notifyFromTms] params:  CustOrdNo={}, NotificationType={}, Data={}, Remark={}",
//                    traceId, custOrdNo, notificationType, data, remark);
            //数据校验
            TmsSignInfo tmsSignInfo = null;
            List<TmsSignedPictrueMsg> tmsSignedPictrueMsgs = null;
            if (StringUtils.isEmpty(custOrdNo)) {
                result = new GongxiaoResult(101, "<供销平台订单出库单号>未提交");
                return result;
            }
            if (StringUtils.isEmpty(tmsOrdNo)) {
                result = new GongxiaoResult(101, "<TMS系统出库单号>未提交");
                return result;
            }
            if (StringUtils.isEmpty(data)) {
                result = new GongxiaoResult(101, "<结果对象JSON>未提交");
                return result;
            }
            if (SIGN.equals(notificationType)) {
                try {
                    tmsSignInfo = JSON.parseObject(data, TmsSignInfo.class);
                } catch (JSONException e) {
                    result = new GongxiaoResult(101, "SignInfo格式错误");
                    logger.error("#traceId={}# [OUT] params invalid!");
                    return result;
                }
                if (StringUtils.isEmpty(tmsSignInfo.getPostedBy()) && StringUtils.isEmpty(tmsSignInfo.getSignedBy())) {
                    result = new GongxiaoResult(101, "<实际签收人>和<签收时间维护人>均未提交");
                }
                if (StringUtils.isEmpty(tmsSignInfo.getTransporter()) && StringUtils.isEmpty(tmsSignInfo.getTransporter())) {
                    result = new GongxiaoResult(101, "<运输商>未提交");
                }
                if (StringUtils.isEmpty(tmsSignInfo.getSignedTime())) {
                    result = new GongxiaoResult(101, "<收件时间>未提交");
                } else {
                    try {
                        Date signedTime = DateUtils.parseDate(tmsSignInfo.getSignedTime(), DATE_FORMATTER);
                    } catch (ParseException e) {
                        result = new GongxiaoResult(101, "<收件时间>格式错误");
                        logger.error("#traceId={}# [OUT] params invalid!",traceId);
                        return result;
                    }
                }
            } else if (RECEIPT.equals(notificationType)) {
                try {
                    tmsSignedPictrueMsgs = JSON.parseArray(data, TmsSignedPictrueMsg.class);
                } catch (JSONException e) {
                    result = new GongxiaoResult(101, "List格式错误");

                }
                if (tmsSignedPictrueMsgs.size() == 0) {
                    result = new GongxiaoResult(101, "<回单照片信息>未提交");
                }
                for (TmsSignedPictrueMsg pictrueMsg : tmsSignedPictrueMsgs) {
                    if (StringUtils.isEmpty(pictrueMsg.getPictureData())) {
                        result = new GongxiaoResult(101, "<图片数据>未提交");
                    }
                    if (StringUtils.isEmpty(pictrueMsg.getPictureType())) {
                        result = new GongxiaoResult(101, "<图片类型>未提交");
                    }
                    if (StringUtils.isEmpty(pictrueMsg.getPictureTime())) {
                        result = new GongxiaoResult(101, "<拍照时间>未提交");
                    } else {
                        try {
                            Date pictureTime = DateUtils.parseDate(pictrueMsg.getPictureTime(), DATE_FORMATTER);
                        } catch (ParseException e) {
                            result = new GongxiaoResult(101, "<拍照时间>格式错误");
                        }
                    }
                }
            } else {
                result = new GongxiaoResult(101, "<通知类型>不符合要求");
            }
            if (result.getReturnCode() != 0) {
                //数据校验未通过
                logger.error("#traceId={}# [OUT] params invalid!",traceId);
                return result;
            }
            //数据校验通过,开始对数据进行操作
            // 如果是签收回告
            if (SIGN.equals(notificationType)) {
                SignInfo byOrderNo = signInfoDao.getByOrderNo(custOrdNo);
                SignInfo signInfo = new SignInfo();
                signInfo.setCustOrdNo(custOrdNo);
                signInfo.setRemark(remark);
                signInfo.setTmsOrdNo(tmsOrdNo);
                signInfo.setPostedBy(tmsSignInfo.getPostedBy());
                signInfo.setSignedPhone(tmsSignInfo.getSignedPhone());
                signInfo.setSignedBy(tmsSignInfo.getSignedBy());
                signInfo.setTransporter(tmsSignInfo.getTransporter());
                signInfo.setSignedTime(DateUtils.parseDate(tmsSignInfo.getSignedTime(), DATE_FORMATTER));
                //第一次回告
                signInfoDao.insert(signInfo);
                if (byOrderNo == null) {
                    SyncTmsSignInfo task = new SyncTmsSignInfo(ApplicationContextProvider.getApplicationContext(),
                            salesScheduleDeliveryService,
                            rpcHeader,
                            signInfo
                    );
                    threadPoolExecutor.submit(task);
                }
            }
            //如果是回单回告
            if (RECEIPT.equals(notificationType)) {
                for (TmsSignedPictrueMsg tmsSignedPictrueMsg : tmsSignedPictrueMsgs) {
                    SignedPictrueMsg pictrueMsg = new SignedPictrueMsg();
                    pictrueMsg.setCustOrdNo(custOrdNo);
                    pictrueMsg.setRemark(remark);
                    pictrueMsg.setPictureData(tmsSignedPictrueMsg.getPictureData());
                    pictrueMsg.setPictureType(tmsSignedPictrueMsg.getPictureType());
                    pictrueMsg.setPictureTime(DateUtils.parseDate(tmsSignedPictrueMsg.getPictureTime(), DATE_FORMATTER));
                    signedPictrueMsgDao.insert(pictrueMsg);
                }
            }
            logger.info("#traceId={}# [OUT] tms message received.");
            return result;
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            result = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return result;
    }


    @RequestMapping(value = "/isOnline", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String isOnline() { //用于探测当前接受wms通知的服务是否在线
        return "true";
    }

    @RequestMapping("/test")
    public void test(){
        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader("zzz", "1", "WMS系统");
//        salesScheduleDeliveryService.submitOutboundOrderToTms(rpcHeader,)
    }
}
