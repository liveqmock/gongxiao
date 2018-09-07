package com.yhglobal.gongxiao.transportataion.sendtotransportation;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.coreutil.OkHttpManager;
import com.yhglobal.gongxiao.transportataion.eventnotification.sales.model.CreateDispatchOrderRequest;
import com.yhglobal.gongxiao.transportataion.eventnotification.sales.model.Receiver;
import com.yhglobal.gongxiao.transportataion.eventnotification.sales.model.Sender;
import com.yhglobal.gongxiao.transportataion.eventnotification.sales.model.StockInOrderItem;
import com.yhglobal.gongxiao.transportataion.sendtotransportation.sales.model.CancelDispatchOrderRequest;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责组装报文 并把发送请求到TMS
 */
public class XpsTransportationManager {

    private static final String SEND_CAR_URL = "/transportation/sendCar";

    private static final String CALCEL_OUTBOUND = "/transportation/cancelOutbound";

    private static Logger logger = LoggerFactory.getLogger(XpsTransportationManager.class);

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * [销售模块] 通知 [运输模块]
     * 创建TMS运单
     *
     * @param url             运输web中的派车url
     * @param custOrdNo       必选 客户订单号
     * @param orderCreateTime 必选 订单创建时间 格式yyyy-MM-dd HH:mm:ss
     * @param workDt          可选 作业时间 格式yyyy-MM-dd HH:mm:ss 注:经验证可不传
     * @param reqDt           必选 要求到达时间 格式yyyy-MM-dd HH:mm:ss
     * @param orderType       必选 订单类型
     * @param workType        必选 送货方式
     * @param customerName    可选 客户名称
     * @param customerCode    可选 客户代码
     * @param po              可选 PO号
     * @param invNo           可选 发票号
     * @param property        可选 运输性质
     * @param attributes      可选 运输属性
     * @param billingType     可选 计费类型
     * @param hold            可选 是否控货: 1是 0否
     * @param projectCode     可选 项目代码
     * @param projectName     可选 项目名称
     * @param lineName        可选 线路
     * @param ordSource       必选 来源
     * @param receiver        必选 收货方信息
     * @param sender          必选 发货方信息
     * @param orderItemList   必选 订单商品信息
     * @param remark          可选 备注
     * @return CreateDispatchOrderResponse 从TMS返回的结果对象
     * <p>
     */
    public static GongxiaoResult createTmsOrder(String url,
                                                String channelId,
                                                String channelToken,
                                                long projectId,
                                                String custOrdNo,
                                                String orderCreateTime,
                                                String workDt,
                                                String reqDt,
                                                Integer orderType,
                                                Integer workType,
                                                String customerName,
                                                String customerCode,
                                                String po,
                                                String invNo,
                                                Integer property,
                                                Integer attributes,
                                                Integer billingType,
                                                Integer hold,
                                                String projectCode,
                                                String projectName,
                                                String lineName,
                                                Integer ordSource,
                                                Receiver receiver,
                                                Sender sender,
                                                List<StockInOrderItem> orderItemList,
                                                String remark) {
        logger.info("[salesOutboundSigned] params: tmsUrl={}, custOrdNo={}, orderCreateTime={}, workDt={}, reqDt={}, orderType={}, workType={}, customerName={}, customerCode={}, po={}, invNo={}, property={}, attributes={}, billingType={}, old={}, projectCode={}, projectName={}, lineName={}, ordSource={}, receiver={}, sender={}, orderItemList={}, remark={}", url, custOrdNo, orderCreateTime, workDt, reqDt, orderType, workType, customerName, customerCode, po, invNo, property, attributes, billingType, hold, projectCode, projectName, lineName, ordSource, JSON.toJSONString(receiver), JSON.toJSONString(sender), JSON.toJSONString(orderItemList), remark);

        //1. 参数校验
        String receiverProvince = receiver.getReceiverProvince();
        if (receiverProvince == null || receiverProvince.trim().length() == 0) { //收件人省份不能为空
            String msg = String.format("receiver province can not be empty: custOrdNo=%s receiver=%s", custOrdNo, JSON.toJSONString(receiver));
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        String receiverCity = receiver.getReceiverCity();
        if (receiverCity == null || receiverCity.trim().length() == 0) { //收件人城市不能为空
            String msg = String.format("receiver city can not be empty: custOrdNo=%s receiver=%s", custOrdNo, JSON.toJSONString(receiver));
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }


        //2. 装配request类
        CreateDispatchOrderRequest dispatchOrderRequest = new CreateDispatchOrderRequest();
        //2.1 装配必选参数
        dispatchOrderRequest.custOrdNo = custOrdNo;
        dispatchOrderRequest.orderCreateTime = orderCreateTime;
        dispatchOrderRequest.reqDt = reqDt;
        dispatchOrderRequest.orderType = orderType;
        dispatchOrderRequest.workType = workType;
        dispatchOrderRequest.ordSource = ordSource;
        dispatchOrderRequest.receiver = receiver;
        dispatchOrderRequest.sender = sender;
        dispatchOrderRequest.custOrdNo = custOrdNo;
        dispatchOrderRequest.orderItemList = orderItemList;
        dispatchOrderRequest.setChannelId(channelId);
        dispatchOrderRequest.setChannelToken(channelToken);
        dispatchOrderRequest.setProjectId(projectId);
        //2.2 装配可选参数
        if (workDt != null) dispatchOrderRequest.workDt = workDt;
        if (customerName != null) dispatchOrderRequest.customerName = customerName;
        if (customerCode != null) dispatchOrderRequest.customerCode = customerCode;
        if (po != null) dispatchOrderRequest.po = po;
        if (invNo != null) dispatchOrderRequest.invNO = invNo;
        if (property != null) dispatchOrderRequest.property = property;
        if (attributes != null) dispatchOrderRequest.attributes = attributes;
        if (billingType != null) dispatchOrderRequest.billingType = billingType;
        if (hold != null) dispatchOrderRequest.hold = hold;
        if (projectCode != null) dispatchOrderRequest.projectCode = projectCode;
        if (projectName != null) dispatchOrderRequest.projectName = projectName;
        if (lineName != null) dispatchOrderRequest.lineName = lineName;
        if (remark != null) dispatchOrderRequest.remark = remark;

        String jsonString = JSON.toJSONString(dispatchOrderRequest);
        logger.info("sending request to transport: {}", jsonString);
        MediaType jsonMediaType = MediaType.parse("application/json-patch+json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(url + SEND_CAR_URL)
                .post(requestBody)
                .build();

        try {
            Response httpResponse = OkHttpManager.execute(request);
            String content = httpResponse.body().string();
            logger.info("#transportation response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, GongxiaoResult.class);
            } else {
                String msg = String.format("got error http status code from transportation: statusCode=%d", statusCode);
                logger.error(msg);
                throw new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 取消TMS运单
     *
     * @param tmsUrl    tms的服务url
     * @param custOrdNo 必选 客户订单号
     * @param projectId 必选 项目id
     * @return 是否取消成功
     */
    public static GongxiaoResult cancelOutboundOrder(String tmsUrl, long projectId, String custOrdNo) {
        CancelDispatchOrderRequest cancelRequest = new CancelDispatchOrderRequest();
        cancelRequest.setCustOrdNo(custOrdNo);
        cancelRequest.setOrdSource(5);
        cancelRequest.setProjectId(projectId);

        String jsonString = JSON.toJSONString(cancelRequest);
        logger.info("[CancelDispatchOrder] sending request to TMS: {}", jsonString);
        MediaType jsonMediaType = MediaType.parse("application/json-patch+json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(tmsUrl + CALCEL_OUTBOUND)
                .post(requestBody)
                .build();

        try {
            Response httpResponse = OkHttpManager.execute(request);
            String responseStr = httpResponse.body().string();
            logger.info("#TMS response#: {}", responseStr);
            if (httpResponse.code() == 200) {
                return JSON.parseObject(responseStr, GongxiaoResult.class);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return GongxiaoResultUtil.createGongxiaoErrorResultByCode(ErrorCode.UNKNOWN_ERROR.getErrorCode());
    }


    public static void main(String[] args) throws IOException {

        int OrderType = 4; //运单类型  入库和出库都硬编码为4

        int WorkType = 1; //送货方式: 是否自提  1表示非自提

        int OrdSource = 5; //订单来源 Expresso分配的值为5

        String tmsUrl = "http://localhost:8089/";

        String CustOrdNo = "SAL20180612162724022";
        LocalDateTime current = LocalDateTime.now();
        String OrderCreateTime = current.format(formatter);
        String WorkDt = null; //可选
        String ReqDt = current.plusDays(7).format(formatter);

        String CustomerName = "Expresso"; //可选
        String CustomerCode = "YH18061301"; //可选
        String PO = null; //可选
        String InvNO = null; //可选
        Integer Property = 1384; //可选
        Integer Attributes = null; //可选
        Integer BillingType = null; //可选
        Integer Hold = null; //可选
        String ProjectCode = "02029901";
        String ProjectName = "飞利浦剃小家电线下批发项目";
        String LineName = null;

        Sender sender = new Sender("江苏省", "江苏省", "南京市", "黄村镇", "望亭国际物流园", "fromYesl", "18511111112");
        Receiver receiver = new Receiver("广西省", "广西省", "桂林市", "黄村镇", "望亭国际物流园", "toYesl", "18511111111");
        List<StockInOrderItem> orderItemList = new ArrayList<>();
        StockInOrderItem stockInOrderItem = new StockInOrderItem("1", "04.01.01.01.025", "飞利浦电动剃须刀S300/02", 30, 0.6f, 0.8f, "SQ20180329002");
        orderItemList.add(stockInOrderItem);
        String Remark = "zz"; //可选

        createTmsOrder(tmsUrl, "22", "testToken", 1, CustOrdNo, OrderCreateTime, WorkDt, ReqDt, OrderType, WorkType, CustomerName, CustomerCode, PO, InvNO, Property, Attributes, BillingType, Hold,
                ProjectCode, ProjectName, LineName, OrdSource, receiver, sender, orderItemList, Remark);
    }


}
