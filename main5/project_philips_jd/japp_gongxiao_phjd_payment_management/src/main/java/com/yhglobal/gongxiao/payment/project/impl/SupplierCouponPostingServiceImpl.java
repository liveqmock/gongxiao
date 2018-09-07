package com.yhglobal.gongxiao.payment.project.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.YhGlobalInoutFlowConstant;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierCouponPostingServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierCouponPostingServiceStructure;
import com.yhglobal.gongxiao.payment.project.dao.supplier.SupplierCouponTransferRecordDao;

import com.yhglobal.gongxiao.payment.project.model.FrontPageFlow;

import com.yhglobal.gongxiao.payment.project.model.SupplierCouponFlow;
import com.yhglobal.gongxiao.util.RpcErrorResponseBuilder;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Service
public class SupplierCouponPostingServiceImpl extends SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(SupplierCouponPostingServiceImpl.class);

    @Autowired
    SupplierCouponTransferRecordDao supplierCouponTransferRecordDao;

    /**
     * 分页显示流水
     * */
    public void selectAllBySupplierIdHasPage(SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq request,
                                      StreamObserver<PaymentCommonGrpc.FrontPageFlowPageInfo> streamObserver) {

        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String traceId = request.getRpcHeader().getTraceId();
        String currencyCode = request.getCurrencyCode();
        Integer moneyFlow = request.getMoneyFlow()==-1?null:request.getMoneyFlow();
        Long supplierId =  request.getSupplierId();
        Long projectId = request.getProjectId();
        Long beginDateLong = request.getBeginDate();
        Long endDateLong = request.getEndDate();
        Integer pageNum = request.getPageNumber();
        Integer pageSize = request.getPageSize();

        Date beginDate = null;
        if (beginDateLong != -1) {
            beginDate = new Date(beginDateLong);
        }
        Date endDate = null;
        if (endDateLong != -1) {
            endDate = new Date(endDateLong);
        }
        String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                traceId, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
        PageHelper.startPage(pageNum, pageSize);
        List<SupplierCouponFlow> supplierCouponFlowList =  supplierCouponTransferRecordDao.selectAllBySupplierId(tablePrefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);

        List<FrontPageFlow> frontPageFlowList = new ArrayList<>();
        for (SupplierCouponFlow record: supplierCouponFlowList) {
            FrontPageFlow frontPageFlow = generateFrontPageFlow(record);
            frontPageFlowList.add(frontPageFlow);
        }
        // 用分页包装
        PageInfo<FrontPageFlow> frontPageFlowPageInfo = new PageInfo<>(frontPageFlowList);

        // 把封装好的分页数据再封装给grpc
        PaymentCommonGrpc.FrontPageFlowPageInfo.Builder pageInfo = PaymentCommonGrpc.FrontPageFlowPageInfo.newBuilder();
        for (FrontPageFlow frontPageFlow:frontPageFlowList){
            PaymentCommonGrpc.FrontPageFlow.Builder builder = PaymentCommonGrpc.FrontPageFlow.newBuilder();
            builder.setFlowNo(frontPageFlow.getFlowNo());
            builder.setType(frontPageFlow.getType());
            builder.setCurrencyCode(frontPageFlow.getCurrencyCode());
            builder.setTransactionAmount(frontPageFlow.getTransactionAmount());
            builder.setAmountAfterTransaction(frontPageFlow.getAmountAfterTransaction());
            builder.setCreateTime(frontPageFlow.getCreateTime().getTime());
            builder.setCreateByName(frontPageFlow.getCreateByName());
            builder.setRemark(frontPageFlow.getRemark()==null?"":frontPageFlow.getRemark());
            pageInfo.addFlows(builder.build());
        }

        pageInfo.setTotal(frontPageFlowPageInfo.getTotal());
        pageInfo.setPageNum(frontPageFlowPageInfo.getPageNum());
        pageInfo.setPageSize(frontPageFlowPageInfo.getPageSize());

        streamObserver.onNext(pageInfo.build());
        streamObserver.onCompleted();

    }

    /**
     * 查询流水 用于文件导出
     * */
    @Override
    public void selectAllBySupplierIdNoPage(SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageReq request,
                                                     StreamObserver<SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp> responseObserver) {

        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String traceId = request.getRpcHeader().getTraceId();
        String currencyCode = request.getCurrencyCode();
        Integer moneyFlow = request.getMoneyFlow()==-1?null:request.getMoneyFlow();
        Long supplierId =  request.getSupplierId();
        Long projectId = request.getProjectId();
        Long beginDateLong = request.getBeginDate();
        Long endDateLong = request.getEndDate();

        Date beginDate = beginDateLong==-1?null:new Date(beginDateLong);
        Date endDate = endDateLong==-1?null:new Date(endDateLong);

        String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);

         SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp.Builder respBUilder = SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp.newBuilder();

        List<SupplierCouponFlow> supplierCouponFlowList =  supplierCouponTransferRecordDao.selectAllBySupplierId(tablePrefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
                 if (supplierCouponFlowList == null) {
                logger.info("#traceId={}# [OUT] fail to selectSuplierCouponTransferRecord: currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                       traceId, rpcHeader, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
                     RpcErrorResponseBuilder.buildWithEnumError(respBUilder, SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp.getDescriptor(), ErrorCode.TARGET_DATA_NOT_EXIST);
                     //定制返回去的错误信息
                     String msg = ErrorCode.TARGET_DATA_NOT_EXIST.getMessage();
                 RpcErrorResponseBuilder.buildWithErrorCodeAndMsg(respBUilder, SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp.getDescriptor(), ErrorCode.TARGET_DATA_NOT_EXIST.getErrorCode(), msg);
                 } else {
                    // 封装成通用的流水对象
                     List<FrontPageFlow> frontPageFlowList = new ArrayList<>();
                     for (SupplierCouponFlow record: supplierCouponFlowList) {
                         FrontPageFlow frontPageFlow = generateFrontPageFlow(record);
                         frontPageFlowList.add(frontPageFlow);
                     }
                     SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp.Builder respBuilder = SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp.newBuilder();
                     for (FrontPageFlow frontPageFlow:frontPageFlowList){
                         SupplierCouponPostingServiceStructure.Result.Builder resultBuilder = SupplierCouponPostingServiceStructure.Result.newBuilder();
                         // 把Java对象转化成proto对象传递
                         resultBuilder.setFlowNo(frontPageFlow.getFlowNo());
                         resultBuilder.setType(frontPageFlow.getType());
                         resultBuilder.setTypeStr(frontPageFlow.getTypeStr());
                         resultBuilder.setCurrencyCode(frontPageFlow.getCurrencyCode());
                         resultBuilder.setTransactionAmount(frontPageFlow.getTransactionAmount());
                         resultBuilder.setTransactionAmountStr(frontPageFlow.getTransactionAmountStr());
                         resultBuilder.setAmountAfterTransaction(frontPageFlow.getAmountAfterTransaction());
                         resultBuilder.setAmountAfterTransactionStr(frontPageFlow.getAmountAfterTransactionStr());
                         resultBuilder.setCreateTime(frontPageFlow.getCreateTime().getTime());
                         resultBuilder.setCreateByName(frontPageFlow.getCreateByName());
                         resultBuilder.setRemark(frontPageFlow.getRemark());

                         SupplierCouponPostingServiceStructure.Result result = resultBuilder.build();
                         respBuilder.addResult(result);
                     }
                     responseObserver.onNext(respBuilder.build());
                     responseObserver.onCompleted();

           }

    }
    /**
     * 生成流水包装类
     * */
    FrontPageFlow generateFrontPageFlow(SupplierCouponFlow record){
        FrontPageFlow frontPageFlow = new FrontPageFlow();
        // 前台显示订单号
        frontPageFlow.setFlowNo(record.getRecordId()+"");
        frontPageFlow.setAmountAfterTransaction(record.getAmountAfterTransaction().doubleValue());
        frontPageFlow.setCreateTime(record.getCreateTime());
        frontPageFlow.setCurrencyCode(record.getCurrencyCode());
        if (record.getTransactionAmount().compareTo(new BigDecimal("0"))==-1) {
            frontPageFlow.setTransactionAmount(-record.getTransactionAmount().doubleValue());
        }else {
            frontPageFlow.setTransactionAmount(record.getTransactionAmount().doubleValue());
        }
        frontPageFlow.setCreateByName(record.getCreatedByName());
        frontPageFlow.setType(record.getFlowType());
        frontPageFlow.setRemark(record.getRemark());
        return  frontPageFlow;
    }

    /**
     * 生成统计金额的包装类
     * */
    @Override
    public void generateFrontFlowSubtotal(SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq request,
                                          StreamObserver<SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        Integer moneyFlow = request.getMoneyFlow();
        String currencyCode = request.getCurrencyCode();
        Long supplierId =  request.getSupplierId();
        Long projectId = request.getProjectId();
        Long beginDateLong = request.getBeginDate();
        Long endDateLong = request.getEndDate();

        Date beginDate = beginDateLong==-1?null:new Date(beginDateLong);
        Date endDate = endDateLong==-1?null:new Date(endDateLong);
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);

        // ProjectServiceStructure.GetProjectByIdResp getProjectByIdResp; //保存返回的值
        SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp generateFrontFlowSubtotalResp;

        // ProjectServiceStructure.GetProjectByIdResp.Builder respBuilder = ProjectServiceStructure.GetProjectByIdResp.newBuilder(); //每个proto对象都需要从builder构建出来
        SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp.Builder respBuilder = SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp.newBuilder();

        String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
        FrontPageFlowSubtotal frontPageFlowSubtotal = new FrontPageFlowSubtotal();
        if (moneyFlow == -1){ // 流水类型为空
            int incomeCount = supplierCouponTransferRecordDao.selectIncomeCount(tablePrefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            BigDecimal incomeAmount = supplierCouponTransferRecordDao.selectIncomeAmount(tablePrefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            if (incomeAmount == null) {
                incomeAmount = new BigDecimal("0");
            }
            frontPageFlowSubtotal.setIncomeQuantity(incomeCount);
            frontPageFlowSubtotal.setIncomeAmount(incomeAmount);
            int expenditureCount = supplierCouponTransferRecordDao.selectExpenditureCount(tablePrefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            BigDecimal expenditureAmount = supplierCouponTransferRecordDao.selectExpenditureAmount(tablePrefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            if (expenditureAmount == null) {
                expenditureAmount = new BigDecimal("0");
            }
            frontPageFlowSubtotal.setExpenditureQuantity(expenditureCount);
            frontPageFlowSubtotal.setExpenditureAmount(expenditureAmount.multiply(new BigDecimal("-1")));
        }else {
            if (moneyFlow == YhGlobalInoutFlowConstant.FLOW_TYPE_IN.getNum()) {
                int incomeCount = supplierCouponTransferRecordDao.selectIncomeCount(tablePrefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
                BigDecimal incomeAmount = supplierCouponTransferRecordDao.selectIncomeAmount(tablePrefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
                if (incomeAmount == null) {
                    incomeAmount =  new BigDecimal("0");
                }
                frontPageFlowSubtotal.setIncomeQuantity(incomeCount);
                frontPageFlowSubtotal.setIncomeAmount(incomeAmount);
                frontPageFlowSubtotal.setExpenditureQuantity(0);
                frontPageFlowSubtotal.setExpenditureAmount(new BigDecimal("0"));
            }
            if (moneyFlow == YhGlobalInoutFlowConstant.FLOW_TYPE_OUT.getNum()) {
                int expenditureCount = supplierCouponTransferRecordDao.selectExpenditureCount(tablePrefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
                BigDecimal expenditureAmount = supplierCouponTransferRecordDao.selectExpenditureAmount(tablePrefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
                if (expenditureAmount == null) {
                    expenditureAmount =  new BigDecimal("0");
                }

                frontPageFlowSubtotal.setExpenditureQuantity(expenditureCount);
                frontPageFlowSubtotal.setExpenditureAmount(expenditureAmount.multiply(new BigDecimal("-1")));
                frontPageFlowSubtotal.setIncomeQuantity(0);
                frontPageFlowSubtotal.setIncomeAmount(new BigDecimal("0"));
            }
        }
        // Transformer trans = new Transformer(); //用于把java bean转换为protobuf对象
        // trans.javaToMessage(frontPageFlowSubtotal, respBuilder); //把java bean转换为protobuf对象
        respBuilder.setExpenditureAmount(frontPageFlowSubtotal.getExpenditureAmount().doubleValue());
        respBuilder.setExpenditureQuantity(frontPageFlowSubtotal.getExpenditureQuantity());
        respBuilder.setIncomeAmount(frontPageFlowSubtotal.getIncomeAmount().doubleValue());
        respBuilder.setIncomeQuantity(frontPageFlowSubtotal.getIncomeQuantity());

        // respBuilder.setCreateTimeLongValue(project.getCreateTime().getTime());
        // getProjectByIdResp = respBuilder.build(); //通过build()方法来生成最终的getProjectByIdResp
        generateFrontFlowSubtotalResp = respBuilder.build();
        responseObserver.onNext(generateFrontFlowSubtotalResp);
        responseObserver.onCompleted();

       //  return frontPageFlowSubtotal;
    }

}
