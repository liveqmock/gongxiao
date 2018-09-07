package com.yhglobal.gongxiao.transportation.controller;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.sales.SalesOrderSyncEnum;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceGrpc;
import com.yhglobal.gongxiao.foundation.channel.microservice.ChannelServiceStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.transportataion.eventnotification.sales.model.CreateDispatchOrderRequest;
import com.yhglobal.gongxiao.transportataion.sendtotransportation.sales.model.CancelDispatchOrderRequest;
import com.yhglobal.gongxiao.transportation.dao.CancelledOutboundOrderDao;
import com.yhglobal.gongxiao.transportation.dao.TransportationOutboundOrderDao;
import com.yhglobal.gongxiao.transportation.model.TransportConfig;
import com.yhglobal.gongxiao.transportation.model.TransportationOutboundOrder;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * 出库单状态
 *
 * @author 葛灿
 */
@Controller
@RequestMapping("/transportation")
public class OutboundOrderController {

    private static Logger logger = LoggerFactory.getLogger(OutboundOrderController.class);
    @Autowired
    private TransportationOutboundOrderDao transportationOutboundOrderDao;

    @Autowired
    private CancelledOutboundOrderDao cancelledOutboundOrderDao;

    @Autowired
    private TransportConfig transportConfig;


    @ResponseBody
    @RequestMapping("/cancelOutbound")
    public GongxiaoResult tmsSendCar(ServletRequest request) throws IOException {
        CancelDispatchOrderRequest cancelDispatchOrderRequest;
        ServletInputStream inputStream = request.getInputStream();
        String jsonStr = IOUtils.toString(inputStream, "utf-8");
        try {
//            List<StockInOrderItem> orderItemList = JSON.parseArray(request.getParameter("orderItemList"), StockInOrderItem.class);
            logger.info("in");
            cancelDispatchOrderRequest = JSON.parseObject(jsonStr, CancelDispatchOrderRequest.class);
            String custOrdNo = cancelDispatchOrderRequest.getCustOrdNo();
            long projectId = cancelDispatchOrderRequest.getProjectId();
            //获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);


            jsonStr = JSON.toJSONString(cancelDispatchOrderRequest);

            //通过单号查询
            int insert = cancelledOutboundOrderDao.insert(prefix, custOrdNo, jsonStr, SalesOrderSyncEnum.UNHANDLED.getStatus(), 0);
            GongxiaoResult gongxiaoResult;
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.SUCCESS);
            //如果单号存在,返回重复请求
            if (insert == 0) {
                logger.info("repeated request. outboundOrderNo={}", custOrdNo);
                gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResultByCode(ErrorCode.DUPLICATED_REQUEST.getErrorCode());
            }
            return gongxiaoResult;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/sendCar")
    public GongxiaoResult cancel(ServletRequest request) throws IOException {
        CreateDispatchOrderRequest createDispatchOrderRequest;
        ServletInputStream inputStream = request.getInputStream();
        String jsonStr = IOUtils.toString(inputStream, "utf-8");
        try {
//            List<StockInOrderItem> orderItemList = JSON.parseArray(request.getParameter("orderItemList"), StockInOrderItem.class);
            logger.info("cancelOutbound");
            createDispatchOrderRequest = JSON.parseObject(jsonStr, CreateDispatchOrderRequest.class);
            long projectId = createDispatchOrderRequest.getProjectId();

            String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            String channelId = createDispatchOrderRequest.getChannelId();
            String custOrdNo = createDispatchOrderRequest.getCustOrdNo();
            String channelToken = createDispatchOrderRequest.getChannelToken();
            //调用基础模块的SourceChannel服务
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(httpServletRequest.getServletPath(), "0", "jweb-transportation");
            ChannelServiceGrpc.ChannelServiceBlockingStub channelService = GlobalRpcStubStore.getRpcStub(ChannelServiceGrpc.ChannelServiceBlockingStub.class);
            ChannelServiceStructure.GetChannelByChannelIdReq getChannelByChannelIdReq = ChannelServiceStructure.GetChannelByChannelIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setXpsChannelId(channelId)
                    .build();
            ChannelServiceStructure.GetChannelByChannelIdResp getChannelByChannelIdResp = channelService.getChannelByChannelId(getChannelByChannelIdReq);
            ChannelServiceStructure.FoundationXpsSourceChannel sourceChannel = getChannelByChannelIdResp.getSourceChannel();

            createDispatchOrderRequest.setCustomerName(sourceChannel.getTmsCustomerName());
            createDispatchOrderRequest.setCustomerCode(sourceChannel.getTmsCustomerCode());
            jsonStr = JSON.toJSONString(createDispatchOrderRequest);

            //通过单号查询
            TransportationOutboundOrder outboundOrder = transportationOutboundOrderDao.getOrderByOrderNo(prefix, custOrdNo);
            GongxiaoResult gongxiaoResult;
            //如果单号存在,返回重复请求
            if (outboundOrder != null) {
                logger.info("repeated request. outboundOrderNo={}", custOrdNo);
                gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResultByCode(ErrorCode.DUPLICATED_REQUEST.getErrorCode());
            } else {
                //把数据存表
                int insert = transportationOutboundOrderDao.insert(prefix, projectId, custOrdNo, channelId, channelToken, jsonStr, SalesOrderSyncEnum.UNHANDLED.getStatus());
                logger.info("save on database success. outboundOrderNo={}", custOrdNo);
                //返回成功
                gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResultByCode(ErrorCode.SUCCESS.getErrorCode());
            }
            return gongxiaoResult;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
