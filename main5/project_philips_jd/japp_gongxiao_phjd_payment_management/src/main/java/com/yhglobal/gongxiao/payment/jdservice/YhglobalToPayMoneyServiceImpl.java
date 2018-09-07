package com.yhglobal.gongxiao.payment.jdservice;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.AccountManageDao.YhglobalToPayMoneyDao;
import com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.YhglobalToPayMoneyServiceStructure;
import com.yhglobal.gongxiao.payment.model.YhglobalToPayMoney;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Service
public class YhglobalToPayMoneyServiceImpl extends YhglobalToPayMoneyServiceGrpc.YhglobalToPayMoneyServiceImplBase{

    private static final Logger logger = LoggerFactory.getLogger(YhglobalToPayMoneyServiceImpl.class);

    @Autowired
    YhglobalToPayMoneyDao yhglobalToPayMoneyDao;



    /**
     * 新增付款申请单
     * @param req
     * @param responseObserver
     */
    @Override
    public void insertNew(YhglobalToPayMoneyServiceStructure.InsertNewReq req, StreamObserver<YhglobalToPayMoneyServiceStructure.InsertNewResp> responseObserver){
        // 先从grpc请求中获取参数并转化成Java类型
        YhglobalToPayMoneyServiceStructure.InsertNewResp.Builder builder = YhglobalToPayMoneyServiceStructure.InsertNewResp.newBuilder();

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        YhglobalToPayMoneyServiceStructure.ToPayMoneyApply toPayMoneyApply = req.getToPayMoneyApply();
        // 请求参数需要修改 为 计划付款日期 结算方式 付款方式 申请付款金额 采购计划单号
        // 利用采购计划单号查询采购计划单 获取其他字段信息给付款申请单
        // 或者采购模块 直接把参数封装成付款单对象 进行传递 否则此处还需要调用 采购计划单接口进行传递参数查询

        YhglobalToPayMoney yhglobalToPayMoney = GrpcObjectToJavaBean(toPayMoneyApply);
        yhglobalToPayMoney.setDataVersion(0l);
        // 付款申请单的部分字段数据需要从查询的采购计划单中获取
        logger.info("#traceId={}# [IN][insertNew] params: yhglobalToPayMoney={}",
                rpcHeader.getTraceId(), yhglobalToPayMoney.toString());
        try {
            int i = yhglobalToPayMoneyDao.insert(yhglobalToPayMoney);
            if (i!=1){
                logger.info("fail to insert new yhglobalToPayMoney apply");
                throw new RuntimeException("fail to insert new yhglobalToPayMoney ");
            }else {
                builder.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                builder.setMsg(ErrorCode.SUCCESS.getMessage());
                responseObserver.onNext(builder.build());
                responseObserver.onCompleted();
            }
        }catch (Exception e){
            builder.setReturnCode(ErrorCode.INSERT_NEW_TO_PAY_MONEY_APPLY_FAIL.getErrorCode());
            builder.setMsg(ErrorCode.INSERT_NEW_TO_PAY_MONEY_APPLY_FAIL.getMessage());
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }


    }

    /**
     * 更新付款申请单数据
     * @param req
     * @param responseObserver
     */
    @Override
    public void updateData(YhglobalToPayMoneyServiceStructure.UpdateDataReq req,StreamObserver<YhglobalToPayMoneyServiceStructure.UpdateDataResp> responseObserver){
        // 先从grpc请求中获取参数并转化成Java类型
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        YhglobalToPayMoneyServiceStructure.ToPayMoneyApply toPayMoneyApply = req.getToPayMoneyApply();
        YhglobalToPayMoney yhglobalToPayMoney = GrpcObjectToJavaBean(toPayMoneyApply);
        logger.info("#traceId={}# [IN][updateData] params: yhglobalToPayMoney={}",
                rpcHeader.getTraceId(), yhglobalToPayMoney.toString());
        YhglobalToPayMoneyServiceStructure.UpdateDataResp.Builder builder = YhglobalToPayMoneyServiceStructure.UpdateDataResp.newBuilder();
        try {
            int i = yhglobalToPayMoneyDao.update(yhglobalToPayMoney);
            if (i!=1){
                logger.info("fail to update yhglobalToPayMoney apply");
                throw new RuntimeException("fail to update yhglobalToPayMoney ");
            }else {
                builder.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                builder.setMsg(ErrorCode.SUCCESS.getMessage());
                responseObserver.onNext(builder.build());
                responseObserver.onCompleted();
            }
        }catch (Exception e){
            builder.setReturnCode(ErrorCode.UPDATE_TO_PAY_MONEY_APPLY_FAIL.getErrorCode());
            builder.setMsg(ErrorCode.UPDATE_TO_PAY_MONEY_APPLY_FAIL.getMessage());
            responseObserver.onNext(builder.build());
            responseObserver.onCompleted();
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 分页条件查询付款申请单
     * @param req
     * @param responseObserver
     */
    @Override
    public void queryToPayMoney(YhglobalToPayMoneyServiceStructure.QueryReq req,StreamObserver<YhglobalToPayMoneyServiceStructure.QueryResp> responseObserver){
        // 条件查询,先从请求中获取查询参数
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
       String paymentApplyNo = req.getPaymentApplyNo();
       String purchasePlanNo = req.getPurchasePlanNo();
       Long supplierId = (req.getSupplierId()==-1l)?null:req.getSupplierId();
       Byte receiveStatus = (req.getReceiveStatus()=="")?null:Byte.valueOf(req.getReceiveStatus());
       Byte settlementType = req.getSettlementType()==""?null:Byte.valueOf(req.getSettlementType());
       Byte paymentType = req.getPaymentType()==""?null:Byte.valueOf(req.getPaymentType());
       Date dateS = req.getDateStart()==-1?null:new Date(req.getDateStart());
       Date dateE = req.getDateEnd()==-1?null:new Date(req.getDateEnd());
        Integer pageNumber = req.getPageNumber();
        Integer pageSize = req.getPageSize();

        PageInfo<YhglobalToPayMoney> resultPage;
        PageHelper.startPage(pageNumber, pageSize);
        logger.info("#traceId={}# [IN][updateData] params:paymentApplyNo={},purchasePlanNo={},supplierId={},receiveStatus={},settlementType={}" +
                        ",paymentType={},dateS={},dateE={}",
                rpcHeader.getTraceId(),paymentApplyNo,purchasePlanNo,supplierId,receiveStatus,settlementType,paymentType,dateS,dateE );
        YhglobalToPayMoneyServiceStructure.QueryResp.Builder respBuilder = YhglobalToPayMoneyServiceStructure.QueryResp.newBuilder();

        try {
            List<YhglobalToPayMoney> list = yhglobalToPayMoneyDao.queryData(paymentApplyNo, purchasePlanNo, supplierId, receiveStatus, settlementType, paymentType, dateS, dateE);
            // 设置分页并把数据转换成grpc
            YhglobalToPayMoneyServiceStructure.ToPayMoneyApply.Builder builder = YhglobalToPayMoneyServiceStructure.ToPayMoneyApply.newBuilder();
            for (YhglobalToPayMoney yhglobalToPayMoney:list){
                YhglobalToPayMoneyServiceStructure.ToPayMoneyApply toPayMoneyApply = JavaBeanToGrpcObject(yhglobalToPayMoney);
                respBuilder.addToPayList(toPayMoneyApply);
            }
            resultPage = new PageInfo<>(list);
            respBuilder.setTotal(resultPage.getTotal());
            respBuilder.setPageNumber(resultPage.getPageNum());
            respBuilder.setPageSize(resultPage.getPageSize());
            respBuilder.setSize(resultPage.getSize());
            respBuilder.setStartRow(resultPage.getStartRow());
            respBuilder.setEndRow(resultPage.getEndRow());
            respBuilder.setPages(resultPage.getPages());
            respBuilder.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            respBuilder.setMsg(ErrorCode.SUCCESS.getMessage());
            responseObserver.onNext(respBuilder.build());
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    // 把grpc中的对象转换成Java对象
    public YhglobalToPayMoney GrpcObjectToJavaBean(YhglobalToPayMoneyServiceStructure.ToPayMoneyApply toPayMoneyApply){

        YhglobalToPayMoney yhglobalToPayMoney = new YhglobalToPayMoney();
        yhglobalToPayMoney.setPaymentId(toPayMoneyApply.getPaymentId());
        yhglobalToPayMoney.setConfirmStatus(Byte.valueOf(toPayMoneyApply.getConfirmStatus()));
        yhglobalToPayMoney.setBackStatus(Byte.valueOf(toPayMoneyApply.getStatus()));
        yhglobalToPayMoney.setPaymentApplyNo(toPayMoneyApply.getPaymentApplyNo());
        yhglobalToPayMoney.setCreateTime(new Date(toPayMoneyApply.getCreateTime()));
        yhglobalToPayMoney.setEstimatedPaymentTime(new Date(toPayMoneyApply.getEstimatedPaymentTime()));
        yhglobalToPayMoney.setSupplierId(toPayMoneyApply.getSupplierId());
        yhglobalToPayMoney.setSupplierName(toPayMoneyApply.getSupplierName());
        yhglobalToPayMoney.setSettlementType(Byte.valueOf(toPayMoneyApply.getSettlementType()));
        yhglobalToPayMoney.setCreditPaymentDays(toPayMoneyApply.getCreditPaymentDays());
        yhglobalToPayMoney.setPaymentType(Byte.valueOf(toPayMoneyApply.getPaymentType()));
        yhglobalToPayMoney.setBankAcceptancePeriod(toPayMoneyApply.getBankAcceptancePeriod());
        yhglobalToPayMoney.setPurchaseNo(toPayMoneyApply.getPurchasePlanNo());
        yhglobalToPayMoney.setOrderTime(new Date(toPayMoneyApply.getOrderTime()));
        yhglobalToPayMoney.setCurrencyCode(toPayMoneyApply.getCurrencyCode());
        yhglobalToPayMoney.setReceiveAmount(BigDecimal.valueOf(toPayMoneyApply.getToPayAmount()));
        yhglobalToPayMoney.setToBeConfirmAmount(BigDecimal.valueOf(toPayMoneyApply.getToBePayAmount()));
        yhglobalToPayMoney.setConfirmAmount(BigDecimal.valueOf(toPayMoneyApply.getConfirmAmount()));
        yhglobalToPayMoney.setReceiptAmount(BigDecimal.valueOf(toPayMoneyApply.getReceiptAmount()));
        yhglobalToPayMoney.setReceiverName(toPayMoneyApply.getReceiverName());
        yhglobalToPayMoney.setProjectId(toPayMoneyApply.getProjectId());
        yhglobalToPayMoney.setProjectName(toPayMoneyApply.getProjectName());
        yhglobalToPayMoney.setDataVersion(toPayMoneyApply.getDataVersion());
        //yhglobalToPayMoney.setPlanOrderAmount(BigDecimal.valueOf(toPayMoneyApply.getPlanOrderAmount()));
        return yhglobalToPayMoney;
    }
    // Java对象转换成grpc对象
    public YhglobalToPayMoneyServiceStructure.ToPayMoneyApply JavaBeanToGrpcObject(YhglobalToPayMoney toPayMoneyApply){
        YhglobalToPayMoneyServiceStructure.ToPayMoneyApply.Builder yhglobalToPayMoney = YhglobalToPayMoneyServiceStructure.ToPayMoneyApply.newBuilder();

        yhglobalToPayMoney.setPaymentId(toPayMoneyApply.getPaymentId());
        yhglobalToPayMoney.setConfirmStatus(String.valueOf(toPayMoneyApply.getConfirmStatus()));
        yhglobalToPayMoney.setStatus(String.valueOf(toPayMoneyApply.getBackStatus()));
        yhglobalToPayMoney.setPaymentApplyNo(toPayMoneyApply.getPaymentApplyNo());
        yhglobalToPayMoney.setCreateTime(toPayMoneyApply.getCreateTime().getTime());
        yhglobalToPayMoney.setEstimatedPaymentTime(toPayMoneyApply.getEstimatedPaymentTime().getTime());
        yhglobalToPayMoney.setSupplierId(toPayMoneyApply.getSupplierId());
        yhglobalToPayMoney.setSupplierName(toPayMoneyApply.getSupplierName());
        yhglobalToPayMoney.setSettlementType(String.valueOf(toPayMoneyApply.getSettlementType()));
        yhglobalToPayMoney.setCreditPaymentDays(toPayMoneyApply.getCreditPaymentDays());
        yhglobalToPayMoney.setPaymentType(String.valueOf(toPayMoneyApply.getPaymentType()));
        yhglobalToPayMoney.setBankAcceptancePeriod(toPayMoneyApply.getBankAcceptancePeriod());
        yhglobalToPayMoney.setPurchasePlanNo(toPayMoneyApply.getPurchaseNo());
        yhglobalToPayMoney.setOrderTime(toPayMoneyApply.getOrderTime().getTime());
        yhglobalToPayMoney.setCurrencyCode(toPayMoneyApply.getCurrencyCode());
        yhglobalToPayMoney.setToPayAmount(toPayMoneyApply.getReceiveAmount().doubleValue());
        yhglobalToPayMoney.setToBePayAmount(toPayMoneyApply.getToBeConfirmAmount().doubleValue());
        yhglobalToPayMoney.setConfirmAmount(toPayMoneyApply.getConfirmAmount().doubleValue());
        yhglobalToPayMoney.setReceiptAmount(toPayMoneyApply.getReceiptAmount().doubleValue());
        yhglobalToPayMoney.setReceiverName(toPayMoneyApply.getReceiverName());
        yhglobalToPayMoney.setProjectId(toPayMoneyApply.getProjectId());
        yhglobalToPayMoney.setProjectName(toPayMoneyApply.getProjectName());
        yhglobalToPayMoney.setDataVersion(toPayMoneyApply.getDataVersion());
        //yhglobalToPayMoney.setPlanOrderAmount(toPayMoneyApply.getPlanOrderAmount().doubleValue());
        return yhglobalToPayMoney.build();
    }




}
