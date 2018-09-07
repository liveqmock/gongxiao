//package com.yhglobal.gongxiao.sales.microservice.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.yhglobal.gongxiao.constant.sales.SalesOrderStatusEnum;
//import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
//import com.yhglobal.gongxiao.model.TraceLog;
//import com.yhglobal.gongxiao.sales.dao.SalesOrderDao;
//import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceGrpc;
//import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure;
//import com.yhglobal.gongxiao.sales.model.SalesOrder;
//import com.yhglobal.gongxiao.util.RpcHeaderUtil;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.Date;
//
///**
// * 使用junit4进行测试
// *
// * @author 葛灿
// */
////@RunWith(SpringJUnit4ClassRunner.class)  //启动spring进行测试
////@ContextConfiguration("classpath:spring.xml")
//public class SalesOrderServiceImplTest {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    SalesOrderDao salesOrderDao;
//
//    @Test
//    public void test(){
//        logger.info("{}",JSON.toJSONString(131));
//    }
//
//    @Test
//    public void getOrderByOrderNo() throws Exception {
//        RpcHeaderUtil.createRpcHeader("1", "1", "gecan");
//        //新建request
//        SalesOrderServiceStructure.GetListSelectivelyRequest getListSelectivelyRequest =
//                SalesOrderServiceStructure.GetListSelectivelyRequest.newBuilder().
//                        setPageNum(1).setPageSize(10).setProjectId(146798161).build();
//        //从Store获取到服务的stub
//        SalesOrderServiceGrpc.SalesOrderServiceBlockingStub rpcStub =
//                RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
//
//        //调用
//        SalesOrderServiceStructure.GetListSelectivelyResponse listSelectively = rpcStub.getListSelectively(getListSelectivelyRequest);
//        System.out.println(listSelectively);
//    }
//
//    @Test
//    public void testUpdateSql() {
//        int update;
//        TraceLog traceLog;
//        String tracelog;
//        SalesOrder salesOrder;
//        //修改订单状态
//        traceLog = new TraceLog(System.currentTimeMillis(), "1", "gecan", "修改订单状态");
//        tracelog = JSON.toJSONString(traceLog);
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        update = salesOrderDao.updateSalesOrderStatus(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), SalesOrderStatusEnum.REJECTED.getStatus(), tracelog);
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("修改订单状态 status = {}, tracelog={}", salesOrder.getSalesOrderStatus(), salesOrder.getTracelog());
//
//
//        //修改结算模式
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        traceLog = new TraceLog(System.currentTimeMillis(), "1", "gecan", "修改结算模式");
//        tracelog = JSON.toJSONString(traceLog);
//        salesOrderDao.updateSettlementMode(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), 0, 2, 10, "remark", tracelog);
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("修改结算模式 status={}, settlementMode={}, paymentDays={}, remark={}, tracelog={}", salesOrder.getSalesOrderStatus(), salesOrder.getSettlementMode(), salesOrder.getPaymentDays(), salesOrder.getCreditRemark(), salesOrder.getTracelog());
//
//        //修改同步eas状态
//        salesOrderDao.updateSyncEasStatus(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), 99);
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("修改同步eas状态 syncEas={}", salesOrder.getSyncEas());
//
//        //修改收件地址
//        salesOrderDao.updateRecipientInfo(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), "收件人",
//                "110", "11", "我省",
//                "22", "我市", "33",
//                "我区", "详细地址", "公司",
//                "修改地址");
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("修改收件地址 {} ", salesOrder);
//
//        //修改审批信息
//        salesOrderDao.updateApprovalInfo(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), 77,
//                100L, 200L, 300L, new Date(), "审批");
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("审批 {} ", salesOrder);
//
//        //修改返利代垫流水号
//        salesOrderDao.updateCouponPrepaidFlowNo(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), "couponFlow", "PrepaidFlow");
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("修改返利代垫流水号  {}, {}", salesOrder.getCouponFlowNo(), salesOrder.getPrepaidFlowNo());
//
//        //更细销售单取消现金流水号
//        salesOrderDao.updateReturnCashFlowNo(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), "returnCashFlow");
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("更细销售单取消现金流水号 {}", salesOrder.getReturnCashFlowNo());
//
//        //取消订单
//        salesOrderDao.updateSalesOrderStatusAndRejectTime(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), 55, "取消订单", new Date());
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("取消订单  {}, {}, {}", salesOrder.getSalesOrderStatus(),salesOrder.getTracelog(),salesOrder.getRejectTime());
//
//
//        //更新取消订单对应的流水号信息
//        salesOrderDao.updateReturnFlowNos(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), "returnCashFlow", "returnCouponFlow", "returnPrepaidFlow");
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("更新取消订单对应的流水号信息 {}, {}, {}", salesOrder.getReturnCashFlowNo(),salesOrder.getReturnCouponFlowNo(),salesOrder.getReturnPrepaidFlowNo());
//
//
//        //更新销售单状态及支付时间
//        salesOrderDao.updateSalesOrderStatusAndPaidTime(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), 11, "更新销售单状态及支付时间", new Date());
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("更新销售单状态及支付时间  {}, {}, {}", salesOrder.getSalesOrderStatus(),salesOrder.getTracelog(),salesOrder.getPaidTime());
//
//
//        //预约发货后,修改订单状态
//        salesOrderDao.updateWhenScheduleDelivery(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), 12, new Date(), 6, 10000, "ongoingInfo", "预约发货后,修改订单状态");
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("预约发货后,修改订单状态 {}, {}, {}, {}, {}, {}", salesOrder.getSalesOrderStatus(),salesOrder.getOutBoundTime(),salesOrder.getSyncEas(),salesOrder.getUnhandledQuantity(),salesOrder.getOngoingOutboundOrderInfo(),salesOrder.getTracelog());
//
//
//        // 当有出库单出库完成后,修改订单的状态
//        salesOrderDao.updateWhenOutbound(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), 15, new Date(), "ongoing", "finished", 666, "预约发货后,修改订单状态");
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("当有出库单出库完成后,修改订单的状态 {}, {}, {}, {}, {} ", salesOrder.getSalesOrderStatus(),salesOrder.getOngoingOutboundOrderInfo(),salesOrder.getFinishedOutboundOrderInfo(),salesOrder.getInTransitQuantity(),salesOrder.getTracelog());
//
//        // 当有出库单签收后,修改订单的状态
//        salesOrderDao.updateWhenOutboundSigned(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), 78, 32, 7, new Date(), "当有出库单签收后,修改订单的状态");
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("当有出库单签收后,修改订单的状态  {}, {}, {}, {}, {}", salesOrder.getDeliveredQuantity(),salesOrder.getInTransitQuantity(),salesOrder.getSalesOrderStatus(),salesOrder.getSignTime(),salesOrder.getTracelog());
//
//        // 更新"同步eas状态"
//        salesOrderDao.updateSyncEasStatus(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), 61);
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("更新\"同步eas状态\" {} ", salesOrder.getSyncEas());
//
//        // 更新"同步eas状态",更新eas回告的订单号和id
//        salesOrderDao.updateEasInfo(salesOrder.getSalesOrderId(), salesOrder.getDataVersion(), 44, "easOrderNo", "easId");
//        salesOrder = salesOrderDao.getByOrderNo("test");
//        logger.info("更新\"同步eas状态\",更新eas回告的订单号和id  {}, {}, {}", salesOrder.getSyncEas(),salesOrder.getEasOrderNo(),salesOrder.getEasOrderId());
//    }
//}