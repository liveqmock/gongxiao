package com.yhglobal.gongxiao.payment.project.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.yhglobal.gongxiao.accountmanage.microservice.DistributorCouponTransferServiceGrpc;
import com.yhglobal.gongxiao.accountmanage.microservice.DistributorCouponTransferServiceStructure;
import com.yhglobal.gongxiao.constant.FXConstant;

import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.dao.DistributorCouponFlowDao;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;


import com.yhglobal.gongxiao.payment.model.DistributorCouponFlow;
import com.yhglobal.gongxiao.payment.project.model.FrontPageFlow;
import com.yhglobal.gongxiao.payment.project.model.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * AD返利到账的接口实现类
 *
 * @author 王帅
 */
@Service
public class DistributorCouponTransferServiceImpl extends DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceImplBase{

    private static Logger logger = LoggerFactory.getLogger(DistributorCouponTransferServiceImpl.class);

    @Autowired
    DistributorCouponFlowDao distributorCouponFlowDao;



    /**
     * AD返利转入的实现  葛灿封装了扣减 AD账户增加 流水生成的整个过程
     */
    @Override
    public void singleDistributorCouponTransferReceived(DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedReq req,
                                                             StreamObserver<DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedResp> respStreamObserver) {

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        Long distributorId = req.getDistributorId();
        Long receivedAmount = req.getReceivedAmount();
        String currencyCode = req.getCurrencyCode();
        Long supplierId = req.getSupplierId();
        String supplierName = req.getSupplierName();
        Long projectId = req.getProjectId();
        String distributorName = req.getDistributorName();
        String remark = req.getRemark();
        logger.info("#traceId={}# [IN][updateRecipientInfo] params:  distributorId={}, receivedAmount={}, currencyCode={}," +
                        "supplierId={}, supplierName={}, projectId={}, distributorName={}, remark={} ",
                rpcHeader.getTraceId(), distributorId,receivedAmount,currencyCode,supplierId,supplierName,projectId,distributorName,remark);

        try {
            Date receivedDate = new Date();
            // rpcResult = distributorAccountService.depositCouponReceived(rpcHeader, currencyCode, distributorId, distributorName,
             //       projectId, receivedAmount, receivedDate, remark);
            // 使用grpc调葛灿的经销商账户接口
            // DistributorAccountServiceStructure.DepositCouponReceivedResponse.Builder respBuilder = DistributorAccountServiceStructure.DepositCouponReceivedResponse.newBuilder();
            DistributorAccountServiceStructure.DepositCouponReceivedRequest.Builder reqBuilder = DistributorAccountServiceStructure.DepositCouponReceivedRequest.newBuilder();
            reqBuilder.setRpcHeader(rpcHeader);
            reqBuilder.setCurrencyCode(currencyCode);
            reqBuilder.setDistributorId(distributorId);
            reqBuilder.setProjectId(projectId);
            reqBuilder.setTransactionTime(receivedDate.getTime());
            reqBuilder.setRemark(remark);
            reqBuilder.setAmount(receivedAmount);
            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
            GongxiaoRpc.RpcResult response = blockingStub.depositCouponReceived(reqBuilder.build());

            DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedResp.Builder response1 = DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedResp.newBuilder();
            response1.setReturnCode(response.getReturnCode());
            response1.setMsg(response.getMsg());

            respStreamObserver.onNext(response1.build());
            respStreamObserver.onCompleted();



        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("distributor transportation transfer error", e);
        }


    }

    /**
     * 查询流水
     * */
    @Override
    public void selectCouponFlowHasPage(DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq req,
                                 StreamObserver<PaymentCommonGrpc.FrontPageFlowPageInfo> streamObserver) {

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String currencyCode = req.getCurrencyCode();
        Long distributorId = req.getSupplierId()==-1?null:req.getSupplierId();
        Long projectId = req.getProjectId();
//        Integer moneyFlow = req.getMoneyFlow()==-1?null:req.getMoneyFlow();
        Date beginDate = req.getBeginDate()==-1?null:new Date(req.getBeginDate());
        Date endDate = req.getEndDate()==-1?null:new Date(req.getEndDate());
        Integer pageNum = req.getPageNumber();
        Integer pageSize = req.getPageSize();
        logger.info("#traceId={}# [IN][updateRecipientInfo] params:  distributorId={}, receivedAmount={}, currencyCode={}," +
                        "supplierId={}, supplierName={}, projectId={}, distributorName={}, remark={} ",
                rpcHeader.getTraceId(), currencyCode,distributorId,projectId,req.getMoneyFlow(),beginDate,endDate,pageNum,pageSize);
        String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
        PageHelper.startPage(pageNum, pageSize);

        try {
            List<DistributorCouponFlow> distributorCouponFlowList = distributorCouponFlowDao.selectAllBydistributorId(tablePrefix, currencyCode, distributorId, projectId, req.getMoneyFlow(), beginDate, endDate);
            List<FrontPageFlow> frontPageFlowList = new ArrayList<>();
            for (DistributorCouponFlow record : distributorCouponFlowList) {
                FrontPageFlow frontPageFlow = generateFrontPageFlow(rpcHeader,record);
                frontPageFlowList.add(frontPageFlow);
            }
            // 用分页包装
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlowList);
            // 把数据封装到proto
            PaymentCommonGrpc.FrontPageFlowPageInfo.Builder pageInfoBuilder = PaymentCommonGrpc.FrontPageFlowPageInfo.newBuilder();
            for (FrontPageFlow frontPageFlow:frontPageFlowList){
                PaymentCommonGrpc.FrontPageFlow.Builder builder = PaymentCommonGrpc.FrontPageFlow.newBuilder();
                builder.setFlowNo(frontPageFlow.getFlowNo());
                builder.setType(frontPageFlow.getType());
                builder.setCurrencyCode(frontPageFlow.getCurrencyCode());
                builder.setTransactionAmount(frontPageFlow.getTransactionAmount());
                builder.setAmountAfterTransaction(frontPageFlow.getAmountAfterTransaction());
                builder.setCreateTime(frontPageFlow.getCreateTime().getTime());
                builder.setCreateByName(frontPageFlow.getCreateByName());
                pageInfoBuilder.addFlows(builder);
            }

            pageInfoBuilder.setTotal(pageInfo.getTotal());
            pageInfoBuilder.setPageNum(pageInfo.getPageNum());
            pageInfoBuilder.setPageSize(pageInfo.getPageSize());

            streamObserver.onNext(pageInfoBuilder.build());
            streamObserver.onCompleted();


        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("query distributor transportation flow error", e);
        }

    }

    /**
     * 查询流水 导出
     * */
    @Override
    public void selectCouponFlowNoPage(DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageReq req,
                                                      StreamObserver<DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageResp> respStreamObserver) {

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String currencyCode = req.getCurrencyCode();
        Long distributorId = req.getSupplierId();
        Long projectId = req.getProjectId();
        Integer moneyFlow = req.getMoneyFlow();
        Date beginDate = req.getBeginDate()==-1?null:new Date(req.getBeginDate());
        Date endDate = req.getEndDate()==-1?null:new Date(req.getEndDate());
        logger.info("#traceId={}# [IN][updateRecipientInfo] params:  distributorId={}, receivedAmount={}, currencyCode={}," +
                        "supplierId={}, supplierName={}, projectId={}, distributorName={}, remark={} ",
                rpcHeader.getTraceId(), currencyCode,distributorId,projectId,moneyFlow,beginDate,endDate);
        List<FrontPageFlow> frontPageFlowList = new ArrayList<>();
        try {
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            List<DistributorCouponFlow>  distributorCouponFlowList = distributorCouponFlowDao.selectAllBydistributorId(tablePrefix, currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);

            for (DistributorCouponFlow record : distributorCouponFlowList) {
                FrontPageFlow frontPageFlow = generateFrontPageFlow(rpcHeader,record);
                frontPageFlowList.add(frontPageFlow);
            }
            // 数据封装到proto
            DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageResp.Builder respBuilder = DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageResp.newBuilder();
            for (FrontPageFlow frontPageFlow:frontPageFlowList){
                DistributorCouponTransferServiceStructure.ResultResp.Builder flowBuilder = DistributorCouponTransferServiceStructure.ResultResp.newBuilder();
                // Transformer trans = new Transformer();
                // DistributorCouponTransferServiceStructure.ResultResp flow = trans.javaToMessage(frontPageFlow, flowBuilder).build();
                DistributorCouponTransferServiceStructure.ResultResp.Builder flow = DistributorCouponTransferServiceStructure.ResultResp.newBuilder();
                flow.setAmountAfterTransaction(frontPageFlow.getAmountAfterTransaction());
                flow.setFlowNo(frontPageFlow.getFlowNo());
                flow.setType(frontPageFlow.getType());
                flow.setCurrencyCode(frontPageFlow.getCurrencyCode()==null?"":frontPageFlow.getCurrencyCode());
                flow.setTransactionAmount(frontPageFlow.getTransactionAmount());
                flow.setCreateTime(frontPageFlow.getCreateTime().getTime());
                flow.setCreateByName(frontPageFlow.getCreateByName()==null?"":frontPageFlow.getCreateByName());
                flow.setRemark(frontPageFlow.getRemark()==null?"":frontPageFlow.getRemark());
                respBuilder.addResult(flow.build());
            }

            respStreamObserver.onNext(respBuilder.build());
            respStreamObserver.onCompleted();

        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("query distributor transportation flow error", e);
        }

    }
    /**
     * 生成流水包装类
     */
    FrontPageFlow generateFrontPageFlow(GongxiaoRpc.RpcHeader rpcHeader, DistributorCouponFlow record) {
        FrontPageFlow frontPageFlow = new FrontPageFlow();
        logger.info("#traceId={}# [IN][updateRecipientInfo] params:  distributorId={}, receivedAmount={}, currencyCode={}," +
                        "supplierId={}, supplierName={}, projectId={}, distributorName={}, remark={} ",
                rpcHeader.getTraceId(),record.toString());
        try {
            // 前台显示订单号
            frontPageFlow.setFlowNo(record.getFlowNo());
            frontPageFlow.setAmountAfterTransaction(record.getAmountAfterTransaction() / FXConstant.HUNDRED_DOUBLE);
            frontPageFlow.setCreateTime(record.getCreateTime());
            frontPageFlow.setCurrencyCode(record.getCurrencyCode());
//            if (record.getTransactionAmount() < 0) {
//                frontPageFlow.setTransactionAmount(-record.getTransactionAmount() / FXConstant.HUNDRED_DOUBLE);
//            } else {
            frontPageFlow.setTransactionAmount(record.getTransactionAmount() / FXConstant.HUNDRED_DOUBLE);
       //     }
            frontPageFlow.setType(record.getFlowType());
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("generate frontpageflow error", e);
        }
        frontPageFlow.setCreateByName(record.getCreateByName());
        return frontPageFlow;
    }


    /**
     * 统计金额的对象数据包装
     * */
    @Override
    public void getSubtotal(DistributorCouponTransferServiceStructure.GetSubtotalReq req,
                            StreamObserver<DistributorCouponTransferServiceStructure.GetSubtotalResp> respStreamObserver) {

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        String currencyCode = req.getCurrencyCode();
        Long supplierId = req.getSupplierId()==-1?null:req.getSupplierId();
        Long projectId = req.getProjectId();
        Integer moneyFlow = req.getMoneyFlow()==-1?null:req.getMoneyFlow();
        Date beginDate = req.getBeginDate()==-1?null:new Date(req.getBeginDate());
        Date endDate = req.getEndDate()==-1?null:new Date(req.getEndDate());
        logger.info("#traceId={}# [IN][updateRecipientInfo] params: currencyCode={}," +
                        "supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={} ",
                rpcHeader.getTraceId(), currencyCode,supplierId,projectId,moneyFlow,beginDate,endDate);
        String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
        FrontPageFlowSubtotal frontPageFlowSubtotal = new FrontPageFlowSubtotal();
        try {
            Integer incomeCount = distributorCouponFlowDao.selectIncomeCount(tablePrefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            Long incomeAmount = distributorCouponFlowDao.selectIncomeAmount(tablePrefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            if (incomeAmount == null) {
                incomeAmount = 0L;
            }
            Integer expenditureCount = distributorCouponFlowDao.selectExpenditureCount(tablePrefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            Long expenditureAmount = distributorCouponFlowDao.selectExpenditureAmount(tablePrefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            if (expenditureAmount == null) {
                expenditureAmount = 0L;
            }
            frontPageFlowSubtotal.setIncomeQuantity(incomeCount);
            frontPageFlowSubtotal.setIncomeAmount(incomeAmount / FXConstant.HUNDRED_DOUBLE);
            frontPageFlowSubtotal.setExpenditureQuantity(expenditureCount);
            frontPageFlowSubtotal.setExpenditureAmount(-expenditureAmount / FXConstant.HUNDRED_DOUBLE);

            // 封装数据到proto
            DistributorCouponTransferServiceStructure.GetSubtotalResp.Builder respBuilder =   DistributorCouponTransferServiceStructure.GetSubtotalResp.newBuilder();
            // Transformer trans = new Transformer();
            // DistributorCouponTransferServiceStructure.GetSubtotalResp resp = trans.javaToMessage(frontPageFlowSubtotal, respBuilder).build(); //把java bean转换为protobuf对象
            respBuilder.setExpenditureAmount(frontPageFlowSubtotal.getExpenditureAmount());
            respBuilder.setExpenditureQuantity(frontPageFlowSubtotal.getExpenditureQuantity());
            respBuilder.setIncomeAmount(frontPageFlowSubtotal.getIncomeAmount());
            respBuilder.setIncomeQuantity(frontPageFlowSubtotal.getIncomeQuantity());

            respStreamObserver.onNext(respBuilder.build());
            respStreamObserver.onCompleted();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("get account amount subtotal error", e);
        }

    }
}
