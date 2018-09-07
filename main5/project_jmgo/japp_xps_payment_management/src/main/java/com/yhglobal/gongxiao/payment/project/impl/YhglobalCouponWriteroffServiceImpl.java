package com.yhglobal.gongxiao.payment.project.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.accountmanage.microservice.YhglobalCouponWriteoffServiceGrpc;
import com.yhglobal.gongxiao.accountmanage.microservice.YhglobalCouponWriteoffServiceStructure;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.constant.*;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.bo.WriteOffReturnObject;
import com.yhglobal.gongxiao.payment.dao.SupplierSellHeightTransferAccountDao;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedCouponAccountDao;
import com.yhglobal.gongxiao.payment.project.bean.YhglobalCouponLedger;
import com.yhglobal.gongxiao.payment.project.bean.YhglobalCouponLedgerItem;
import com.yhglobal.gongxiao.payment.project.bean.YhglobalCouponWriteoffFlow;
import com.yhglobal.gongxiao.payment.project.bean.YhglobalCouponWriteoffRecord;
import com.yhglobal.gongxiao.payment.project.constant.CouponConfirmAccountType;
import com.yhglobal.gongxiao.payment.project.constant.CouponWriteoffFlowType;
import com.yhglobal.gongxiao.payment.project.dao.yhglobal.YhglobalCouponWriteoffFlowDao;
import com.yhglobal.gongxiao.payment.project.dao.yhglobal.YhglobalCouponWriteoffRecordDao;
import com.yhglobal.gongxiao.payment.project.dao.yhglobal.YhglobalToReceiveCouponLedgerDao;
import com.yhglobal.gongxiao.payment.service.SupplierSellHeightTransferAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedCouponAccountService;
import com.yhglobal.gongxiao.utils.TraceLogUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 越海返利流水核销Service实现类
 *
 * @author : 王帅
 */
@Service
public class YhglobalCouponWriteroffServiceImpl extends YhglobalCouponWriteoffServiceGrpc.YhglobalCouponWriteoffServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(YhglobalCouponWriteroffServiceImpl.class);


    @Autowired
    YhglobalToReceiveCouponLedgerDao yhglobalToReceiveCouponLedgerDao;

    @Autowired
    YhglobalCouponWriteoffFlowDao yhglobalCouponWriteoffFlowDao;

    @Autowired
    YhglobalCouponWriteoffRecordDao yhglobalCouponWriteoffRecordDao;

    @Autowired
    YhglobalReceivedCouponAccountDao yhglobalReceivedCouponAccountDao;

    @Autowired
    SupplierSellHeightTransferAccountDao supplierSellHeightTransferAccountDao;

    @Autowired
    YhglobalReceivedCouponAccountService yhglobalReceivedCouponAccountService;

    @Autowired
    SupplierSellHeightTransferAccountService supplierSellHeightTransferAccountService;

    /**
     * 根据项目ID获取 对应的表的前缀名 所有支持多项目的update select insert delete部分都需要
     * @param projectId
     * @return
     */
    public static String getTablePrefix(Long projectId){
        ProjectStructure.GetByProjectIdReq.Builder reqBuilder = ProjectStructure.GetByProjectIdReq.newBuilder();
        reqBuilder.setProjectId(projectId.toString());
        ProjectServiceGrpc.ProjectServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
        ProjectStructure.GetByProjectIdResp resp = blockingStub.getByProjectId(reqBuilder.build());
        return resp.getProject().getProjectTablePrefix();
    }

    /**
     * " 收付款管理 > 返利确认处理 > 返利确认列表" 页面 点击"确认"按钮的业务处理入口
     *
     *  备注 :应收返利有已过期状态 目前获取的 预计到账时间 字段为空
     *  返利核销功能需要判断返利是否过期(在前端选择返利时也要过滤)
     */
    @Override
    public void yhCouponWriteroff(YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffReq req,
                                  StreamObserver<YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp> respStreamObserver) {

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        Long projectId = req.getProjectId();
        Date useDate = req.getUseDateS()==-1?null:new Date(req.getUseDateS());
        Integer accountType = req.getAccountType();
        String confirmInfoJson = req.getConfirmInfoJson();
        String projectName = req.getProjectName();
        String philipDocumentNo = req.getPhilipDocumentNo();

        logger.info("#traceId={}# [IN][receiveConfirm] params:projectId={}, projectName={}, useDate={},  accountType={},  confirmInfo={}",
                rpcHeader.getTraceId(), projectId, projectName, useDate, accountType, confirmInfoJson);
        try {
            Date now = new Date();

            // 解析前端封装的json格式数据
            List<YhglobalCouponLedger> receiveList
                    = JSON.parseObject(confirmInfoJson, new TypeReference<List<YhglobalCouponLedger>>() {
            });
            long totalToConfirmAmount = 0;  //待确认的返利总和
            for (YhglobalCouponLedger receive : receiveList) { // 统计核销确认的总额度
                totalToConfirmAmount += Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED);
            }
            //查询帐户余额 根据账户类型选择不同的账户
            Long accountTotalAmount = 0L;
            CouponConfirmAccountType couponConfirmAccountType = CouponConfirmAccountType.getCouponConfirmAccountTypeByCode(accountType);
//            switch (couponConfirmAccountType) { //根据确认的账户类型加载对应的账户余额
//                case COUPON_RECEIVED_ACCOUNT:
//                    accountTotalAmount = yhglobalReceivedCouponAccountDao.getAccount(projectId).getTotalAmount();
//                    //判断本次确认金额是否超出  超出则返回错误信息
//                    if (accountTotalAmount == null || accountTotalAmount < totalToConfirmAmount) {
//                        //已超出
//                        logger.error("accountTotalAmount insufficient: philipDocumentNo={}", philipDocumentNo);
//                        //TODO: 返回错误码给调用方
//                        YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp.Builder respBuilder = YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp.newBuilder();
//                        respBuilder.setReturnCode(ErrorCode.YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode());
//                        respBuilder.setMsg(ErrorCode.YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
//                        respStreamObserver.onNext(respBuilder.build());
//                        respStreamObserver.onCompleted();
//                        return ;
//                    }
//                    break;
//                case SALES_DIFFERENCE_ACCOUNT:
//                    accountTotalAmount = supplierSellHeightTransferAccountDao.getAccount(projectId).getTotalAmount();
//                    //判断本次确认金额是否超出  超出则返回错误信息
//                    if (accountTotalAmount == null || accountTotalAmount < totalToConfirmAmount) {
//                        //已超出
//                        logger.error("accountTotalAmount insufficient: philipDocumentNo={}", philipDocumentNo);
//                        //TODO: 返回错误码给调用方
//                        YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp.Builder respBuilder = YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp.newBuilder();
//                        respBuilder.setReturnCode(ErrorCode.SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode());
//                        respBuilder.setMsg(ErrorCode.SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH.getMessage());
//                        respStreamObserver.onNext(respBuilder.build());
//                        respStreamObserver.onCompleted();
//                        return ;
//                    }
//                    break;
//                default:
//                    throw  new IllegalArgumentException("unknown couponConfirmAccountType: " + couponConfirmAccountType.getAccountName());
//            }
//            String flowCode = DateTimeIdGenerator.nextId(BizNumberType.YHGLOBAL_COUPON_WRITEROFF_FLOW);
            Long transactionAmount = -totalToConfirmAmount;//交易金额
            // 调用根据账户类型修改账户额度的方法  有返回值
            //WriteOffReturnObject writeOffReturnObject = updateAccount(rpcHeader, projectId, transactionAmount, now,flowCode,couponConfirmAccountType);

            //TODO: 返利模块只是调用扣多少款 由支付模块返回交易前后的余额,
//            Long amountBeforeTransaction = writeOffReturnObject.getAmountBeforeTrans();//流水发生前的余额
//            Long amountAfterTransaction = writeOffReturnObject.getAmountAfterTrans();//流水发生后的余额


            // 获得货币编码
            String currencyCode = "";
            for (YhglobalCouponLedger receive : receiveList) {
                //查询应收记录
                YhglobalCouponLedger receiveInfo = yhglobalToReceiveCouponLedgerDao.selectCouponLedgerForWriteoff(receive.getPurchaseCouponLedgerId());
                if ("".equals(currencyCode)){
                    currencyCode = receiveInfo.getCurrencyCode();
                }
                //写一条核销记录
                YhglobalCouponWriteoffRecord record = new YhglobalCouponWriteoffRecord();
                record.setToReceiveCouponId(receive.getPurchaseCouponLedgerId());
                record.setConfirmAmount(Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED));
                record.setReceiptAmount(Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED));
                record.setConfirmCurrencyCode(receiveInfo.getCurrencyCode());
                record.setReceivedCurrencyCode(receiveInfo.getCurrencyCode());
                //record.setFlowNo(flowCode);
                record.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                record.setDataVersion(0L);
                record.setCreatedByName(rpcHeader.getUsername());
                record.setCreateTime(now);
                record.setAccountType(couponConfirmAccountType.getCode());
                record.setProjectId(projectId);
                record.setUseDate(useDate);
                // 设置飞利浦单据号
                record.setPhilipDocumentNo(philipDocumentNo);
                //保存核销记录
                // yhglobalPrepaidLedgerWriteoffRecordDao.addWriteoffRecord(record);
                yhglobalCouponWriteoffRecordDao.insert(record);

                //计算应收返利的确认额度 实收额度 未确认额度
                Long confirmAmount = 0L; //该条应收返利总共已确认的金额
                if (receiveInfo.getConfirmedCouponAmount() == null) {
                    confirmAmount = Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED);
                } else { //非空表示历史有已确认金额,则累加
                    confirmAmount = receiveInfo.getConfirmedCouponAmount() + Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED);
                }
                 receiveInfo.setConfirmedCouponAmount(confirmAmount);

                Long receiptAmount = 0L; //该条应收返利总共实收的金额
                if (receiveInfo.getReceivedCouponAmount() == null) {
                    receiptAmount = Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED);
                } else {
                    receiptAmount = receiveInfo.getReceivedCouponAmount() + Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED);
                }
                receiveInfo.setReceivedCouponAmount(receiptAmount);

                long diff = receiveInfo.getEstimatedCouponAmount() - confirmAmount; //计算待核销金额
                receiveInfo.setToBeConfirmAmount(diff);

                // 调用设置应收返利的方法
                Byte targetCouponStatus = updateCouponStatus(receiveInfo.getToBeConfirmAmount(), receiveInfo.getEstimatedCouponAmount());
                receiveInfo.setCouponStatus(targetCouponStatus);

                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "返利确认");
                String appendTraceLog = TraceLogUtil.appendTraceLog(receiveInfo.getTracelog(), traceLog);
                // 更新ConfirmedCouponAmount ReceivedCouponAmount ToBeConfirmAmount CouponStatus DataVersion Tracelog 字段
                yhglobalToReceiveCouponLedgerDao.updateCouponLedgerForWriteoff(receiveInfo.getPurchaseCouponLedgerId(),
                        confirmAmount,receiptAmount,diff,targetCouponStatus,receiveInfo.getDataVersion(),appendTraceLog, new Date() ); //TODO: update需要拆分成具体的参数,不要传对象
            }


            //记录流水  供应商及经销商信息为预留字段 目前不用存储
//            try {
//                addWriteoffFlow(rpcHeader,flowCode, couponConfirmAccountType, CouponWriteoffFlowType.CONFIRM_COUPON_WRITEOFF.getCode(),
//                        projectId, projectName,currencyCode,
//                        amountBeforeTransaction,
//                        transactionAmount,
//                        amountAfterTransaction,
//                        now,
//                        null,
//                        "",
//                        "",
//                        null,
//                        null,
//                        "");
//            } catch (Exception ex) {
//                logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + ex.getMessage(), ex);
//            }
//            if (writeOffReturnObject.getReturnCode() == ErrorCode.SUCCESS.getErrorCode()) {
//                // 构建respStreamObserver 返回给controller
//                YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp.Builder respBuilder = YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp.newBuilder();
//                respBuilder.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
//                respBuilder.setMsg(ErrorCode.SUCCESS.getMessage());
//                respStreamObserver.onNext(respBuilder.build());
//                respStreamObserver.onCompleted();
//            }else {
//                // 构建respStreamObserver 返回给controller
//                logger.info("update account wrong ,philipDocumentNo={}, accountType={}, confirmInfoJson={}",philipDocumentNo, accountType, confirmInfoJson);
//                YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp.Builder respBuilder = YhglobalCouponWriteoffServiceStructure.YhCouponWriteroffResp.newBuilder();
//                respBuilder.setReturnCode(ErrorCode.UPDATE_ACCOUNT_WRONG.getErrorCode());
//                respBuilder.setMsg(ErrorCode.UPDATE_ACCOUNT_WRONG.getMessage());
//                respStreamObserver.onNext(respBuilder.build());
//                respStreamObserver.onCompleted();
//            }

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("receiveConfirm error!", e);
        }

    }

    /**
     * 生成流水
     */
    public String addWriteoffFlow(GongxiaoRpc.RpcHeader rpcHeader,
                                  String flowCode,
                                  CouponConfirmAccountType couponConfirmAccountType,
                                  Integer flowType,
                                  Long projectId,
                                  String projectName,
                                  String currencyCode,
                                  Long amountBeforeTransaction,
                                  Long transactionAmount,
                                  Long amountAfterTransaction,
                                  Date transactionTime,
                                  Integer supplierId,
                                  String supplierName,
                                  String salesOrderId,
                                  Long distributorId,
                                  String distributorName,
                                  String differenceAmountAdjustPattern) {
        logger.info("#traceId={}# [IN][receiveConfirm] params:projectId={}, projectName={},  amountBeforeTransaction={},  transactionAmount={}, " +
                        " amountAfterTransaction={},  transactionTime={}, supplierId={}, supplierName={}, salesOrderId={}, distributorId={}, distributorName={}, differenceAmountAdjustPattern={}",
                rpcHeader, projectId, projectName, amountBeforeTransaction, transactionAmount, amountAfterTransaction, transactionTime, supplierId, supplierName, salesOrderId, distributorId, distributorName, differenceAmountAdjustPattern);
        YhglobalCouponWriteoffFlow flow = new YhglobalCouponWriteoffFlow();

        try {
            flow.setFlowNo(flowCode);
            flow.setFlowType(flowType);
            flow.setProjectId(projectId);
            flow.setProjectName(projectName);
            flow.setAmountBeforeTransaction(amountBeforeTransaction);
            flow.setTransactionAmount(transactionAmount);
            flow.setAmountAfterTransaction(amountAfterTransaction);
            flow.setTransactionTime(transactionTime);
            flow.setSupplierId(supplierId);
            flow.setSupplierName(supplierName);
            flow.setSalesOrderId(salesOrderId);
            flow.setDistributorId(distributorId);
            flow.setDistributorName(distributorName);
            flow.setDifferenceAmountAdjustPattern(differenceAmountAdjustPattern);
            flow.setCreateTime(new Date());
            flow.setCurrencyCode(currencyCode);
            flow.setTransferPattern(couponConfirmAccountType.getAccountName());

            int count = yhglobalCouponWriteoffFlowDao.insert(flow);
            if (count != 1) {
                logger.error("#traceId={}# [OUT]: insert transportation writeroff flow fail flowCode={}", rpcHeader.getTraceId(), flowCode);
                return null;
            }
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("add transportation writeroff flow error!", e);
        }
        return flowCode;
    }



    /**
     * 根据选中的返利主键查询返利详情 以便加载"收付款管理 > 返利确认处理 > 返利确认列表"页面时填写默认值
     */
    @Override
    public void selectYhglobalCouponLedgerByPurchaseCouponLedgerId(YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdReq req,
                                                                                         StreamObserver<YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp> respStreamObserver) {
        List<String> idList = req.getCouponIdList();

        List<YhglobalCouponLedger> yhglobalCouponLedgerList = new ArrayList<>();
        // 根据选中的采购返利ID 查询出对应的返利详情
        for (String idStr : idList) {
            Long id = Long.valueOf(idStr);
            YhglobalCouponLedger yhglobalCouponLedger = yhglobalToReceiveCouponLedgerDao.selectByPrimaryKey(id);
            yhglobalCouponLedger.setConfirmAmountDouble(yhglobalCouponLedger.getToBeConfirmAmount() != null ? yhglobalCouponLedger.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
            yhglobalCouponLedger.setReceiptAmountDouble(yhglobalCouponLedger.getToBeConfirmAmount() != null ? yhglobalCouponLedger.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
            yhglobalCouponLedger.setEstimatedAmountDouble(yhglobalCouponLedger.getEstimatedCouponAmount() != null ? yhglobalCouponLedger.getEstimatedCouponAmount() / FXConstant.HUNDRED_DOUBLE : null);
            yhglobalCouponLedger.setToBeConfirmAmountDouble(yhglobalCouponLedger.getToBeConfirmAmount() != null ? yhglobalCouponLedger.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
            CouponLedgerCouponStatus couponStatus = CouponLedgerCouponStatus.getCouponLedgerCouponStatusByCode(yhglobalCouponLedger.getCouponStatus());
            yhglobalCouponLedger.setCouponStatusString(couponStatus.getMeaning());

            // 从枚举中获取常量赋值给CouponStatusString字段
            CouponLedgerCouponType couponLedgerCouponType = CouponLedgerCouponType.getCouponLedgerCouponTypeByCode(yhglobalCouponLedger.getCouponType());
            yhglobalCouponLedger.setCouponTypeString(couponLedgerCouponType.getMeaning());

            yhglobalCouponLedgerList.add(yhglobalCouponLedger);
        }
        // 把yhglobalCouponLedgerList数据转换为proto对象 然后返回
        YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp.Builder respBuilder = YhglobalCouponWriteoffServiceStructure.SelectYhglobalCouponLedgerByPurchaseCouponLedgerIdResp.newBuilder();
        for (YhglobalCouponLedger yhglobalCouponLedger:yhglobalCouponLedgerList){
            YhglobalCouponWriteoffServiceStructure.YhglobalCouponLedger.Builder ledger = YhglobalCouponWriteoffServiceStructure.YhglobalCouponLedger.newBuilder();

            //Transformer trans = new Transformer();
            //YhglobalCouponWriteoffServiceStructure.YhglobalCouponLedger ledger = trans.javaToMessage(yhglobalCouponLedger,ledgerBuilder).build();
            ledger.setPurchaseCouponLedgerId(yhglobalCouponLedger.getPurchaseCouponLedgerId());
            ledger.setCouponStatus(String.valueOf(yhglobalCouponLedger.getCouponStatus()));
            ledger.setCouponType(String.valueOf(yhglobalCouponLedger.getCouponType()));
            ledger.setCouponModel(yhglobalCouponLedger.getCouponModel()==null?"":yhglobalCouponLedger.getCouponModel());
            ledger.setCouponRatio(yhglobalCouponLedger.getCouponRatio().intValue());
            ledger.setProjectId(yhglobalCouponLedger.getProjectId());
            ledger.setProjectName(yhglobalCouponLedger.getProjectName());
            ledger.setSupplierId(yhglobalCouponLedger.getSupplierId());
            ledger.setSupplierName(yhglobalCouponLedger.getSupplierName());
            ledger.setCurrencyCode(yhglobalCouponLedger.getCurrencyCode()==null?"":yhglobalCouponLedger.getCurrencyCode());
            ledger.setSupplierOrderNo(yhglobalCouponLedger.getSupplierOrderNo());
            ledger.setPurchaseOrderNo(yhglobalCouponLedger.getPurchaseOrderNo());
            ledger.setPurchaseTime(yhglobalCouponLedger.getPurchaseTime().getTime());
            ledger.setEstimatedCouponAmount(yhglobalCouponLedger.getEstimatedCouponAmount());
            ledger.setConfirmedCouponAmount(yhglobalCouponLedger.getConfirmedCouponAmount());
            ledger.setToBeConfirmAmount(yhglobalCouponLedger.getToBeConfirmAmount());
            ledger.setReceivedCouponAmount(yhglobalCouponLedger.getReceivedCouponAmount());
            ledger.setDataVersion(yhglobalCouponLedger.getDataVersion());
            ledger.setCreateTime(yhglobalCouponLedger.getCreateTime().getTime());
            ledger.setLastUpdateTime(yhglobalCouponLedger.getLastUpdateTime().getTime());
            ledger.setTracelog(yhglobalCouponLedger.getTracelog()==null?"":yhglobalCouponLedger.getTracelog());

            ledger.setEstimatedAmountDouble(yhglobalCouponLedger.getEstimatedAmountDouble()==null?0:yhglobalCouponLedger.getEstimatedAmountDouble());
            ledger.setToBeConfirmAmountDouble(yhglobalCouponLedger.getToBeConfirmAmountDouble()==null?0:yhglobalCouponLedger.getToBeConfirmAmountDouble());
            ledger.setConfirmAmountDouble(yhglobalCouponLedger.getConfirmAmountDouble()==null?0:yhglobalCouponLedger.getConfirmAmountDouble());
            ledger.setReceiptAmountDouble(yhglobalCouponLedger.getReceiptAmountDouble()==null?0:yhglobalCouponLedger.getReceiptAmountDouble());

            ledger.setCouponStatusString(yhglobalCouponLedger.getCouponStatusString());
            ledger.setCouponTypeString(yhglobalCouponLedger.getCouponTypeString());

            respBuilder.addList(ledger.build());
        }
        respStreamObserver.onNext(respBuilder.build());
        respStreamObserver.onCompleted();

    }



    /**
     * "付款管理 > 返利台帐"页面  获取台账数据
     */
    @Override
    public void searchCouponConfirm(YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmReq req,
                                                                      StreamObserver<YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp> respStreamObserver) {

        GongxiaoRpc.RpcHeader rpcHeader =  req.getRpcHeader();
        Long projectId = req.getProjectId();
        String flowNo = req.getFlowCode();
        Integer accountType = req.getAccountType()>0? req.getAccountType() : null;
        Date useDateStart = req.getUseDateStart()==-1?null:new Date(req.getUseDateStart());
        Date useDateEnd = req.getUseDateEnd()==-1?null:new Date(req.getUseDateEnd());
        Date dateStart = req.getDateStart()==-1?null:new Date(req.getDateStart());
        Date dateEnd = req.getDateEnd()==-1?null:new Date(req.getDateEnd());
        Integer pageNumber = req.getPageNumber();
        Integer pageSize = req.getPageSize();
        // PageInfo<YhglobalCouponWriteoffRecord> pageInfo = null;
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, flowCode={}, accountType={}, useDateStart={}, useDateEnd={}, dateStart={}, dateEnd={}", rpcHeader, projectId, flowNo, accountType, useDateStart, useDateEnd, dateStart, dateEnd);
        PageInfo<YhglobalCouponWriteoffRecord> resultPage;
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<YhglobalCouponWriteoffRecord> recordList = yhglobalCouponWriteoffRecordDao.searchCouponConfirm(projectId, flowNo, accountType, useDateStart, useDateEnd, dateStart, dateEnd);
            for (YhglobalCouponWriteoffRecord record : recordList) {
                record.setConfirmAmountDouble(record.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                record.setReceiptAmountDouble(record.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE);
                record.setDifferenceAmountDouble(record.getConfirmAmountDouble() - record.getReceiptAmountDouble());

                // 交易账户名称赋值
                CouponConfirmAccountType couponConfirmAccountType = CouponConfirmAccountType.getCouponConfirmAccountTypeByCode(record.getAccountType());
                record.setAccountTypeName(couponConfirmAccountType.getAccountName());

            }
            resultPage = new PageInfo<>(recordList);
            // 把数据封装到proto中
            YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp.Builder respBuilder = YhglobalCouponWriteoffServiceStructure.SearchCouponConfirmResp.newBuilder();

            for (YhglobalCouponWriteoffRecord yhglobalCouponWriteoffRecord : recordList){
                YhglobalCouponWriteoffServiceStructure.YhglobalCouponWriteoffRecord.Builder record = YhglobalCouponWriteoffServiceStructure.YhglobalCouponWriteoffRecord.newBuilder();
                //Transformer trans = new Transformer();
                //YhglobalCouponWriteoffServiceStructure.YhglobalCouponWriteoffRecord yhglobalCouponWriteoffRecord = trans.javaToMessage(record,recordBuilder).build();
                // record.setConfirmId(yhglobalCouponWriteoffRecord.getConfirmId());
                record.setToReceiveCouponId(yhglobalCouponWriteoffRecord.getToReceiveCouponId());
                record.setConfirmAmount(yhglobalCouponWriteoffRecord.getConfirmAmount());
                record.setReceiptAmount(yhglobalCouponWriteoffRecord.getReceiptAmount());
                record.setFlowNo(yhglobalCouponWriteoffRecord.getFlowNo());
                record.setCreateTime(yhglobalCouponWriteoffRecord.getCreateTime().getTime());
                //record.setLastUpdateTime(yhglobalCouponWriteoffRecord.getLastUpdateTime().getTime());
                record.setReceivedCurrencyCode(yhglobalCouponWriteoffRecord.getReceivedCurrencyCode());
                record.setConfirmCurrencyCode(yhglobalCouponWriteoffRecord.getConfirmCurrencyCode());
                record.setCreatedById(yhglobalCouponWriteoffRecord.getCreatedById());
                record.setCreatedByName(yhglobalCouponWriteoffRecord.getCreatedByName());
                //record.setIsRollback(yhglobalCouponWriteoffRecord.getIsRollback());
                //record.setProjectId(yhglobalCouponWriteoffRecord.getProjectId());
                record.setAccountType(yhglobalCouponWriteoffRecord.getAccountType());
                //record.setDataVersion(yhglobalCouponWriteoffRecord.getDataVersion());
                record.setAccountTypeName(yhglobalCouponWriteoffRecord.getAccountTypeName());
                record.setConfirmAmountDouble(yhglobalCouponWriteoffRecord.getConfirmAmountDouble());
                record.setReceiptAmountDouble(yhglobalCouponWriteoffRecord.getReceiptAmountDouble());
                record.setDifferenceAmountDouble(yhglobalCouponWriteoffRecord.getDifferenceAmountDouble());
                record.setUseDate(yhglobalCouponWriteoffRecord.getUseDate()!=null?yhglobalCouponWriteoffRecord.getUseDate().getTime():-1);
                record.setPhilipDocumentNo(yhglobalCouponWriteoffRecord.getPhilipDocumentNo()==null?"":yhglobalCouponWriteoffRecord.getPhilipDocumentNo());

                respBuilder.addList(record.build());
            }
            respBuilder.setTotal(resultPage.getTotal());
            respBuilder.setPageNum(resultPage.getPageNum());
            respBuilder.setPageSize(resultPage.getPageSize());
            respBuilder.setSize(resultPage.getSize());
            respBuilder.setStartRow(resultPage.getStartRow());
            respBuilder.setEndRow(resultPage.getEndRow());
            respBuilder.setPages(resultPage.getPages());

            respStreamObserver.onNext(respBuilder.build());
            respStreamObserver.onCompleted();

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("search transportation Confirm error!", e);
        }
        // return resultPage;
    }

    /**
     * "收付款管理 > 返利确认处理" 条件查询的入口
     * */
    @Override
    public void selectByManyCondiitonsHasPage(YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageReq req,
                                                                     StreamObserver<YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp> respStreamObserver) {

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        Long projectId = req.getProjectId();
        String purchaseOrderNo = req.getPurchaseOrderNo();
        String supplierOrderNo = req.getSupplierOrderNo();
        Date dateS = 0<req.getDateS()? new Date(req.getDateS()) : null;
        Date dateE = req.getDateE()==-1?null:new Date(req.getDateE());
        String couponStatus = req.getCouponStatus();
        String flowNo = req.getFlowNo();
        Integer pageNumber = req.getPageNumber();
        Integer pageSize = req.getPageSize();
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, flowCode={}, accountType={}, useDateStart={}," +
                " useDateEnd={}, dateStart={}, dateEnd={}", rpcHeader, projectId, purchaseOrderNo, supplierOrderNo, dateS, dateE,
                couponStatus, flowNo, pageNumber, pageSize);
        PageInfo<YhglobalCouponLedgerItem> resultPage;
        try {
            PageHelper.startPage(pageNumber, pageSize);
            // 条件查询
            List<YhglobalCouponLedgerItem> yhglobalCouponLedgerItemList = yhglobalToReceiveCouponLedgerDao.searchByManyCondition(projectId, purchaseOrderNo, supplierOrderNo
                    , flowNo, dateS, dateE, couponStatus);
            // 填充部分前端显示的字段
            for (YhglobalCouponLedgerItem yhglobalCouponLedgerItem:yhglobalCouponLedgerItemList) {

                // 从枚举中获取常量赋值给CouponStatusString字段
                CouponLedgerCouponStatus couponLedgerCouponStatus = CouponLedgerCouponStatus.getCouponLedgerCouponStatusByCode(yhglobalCouponLedgerItem.getCouponStatus());
                yhglobalCouponLedgerItem.setCouponStatusString(couponLedgerCouponStatus.getMeaning());
                // 返利类型字符串
                CouponLedgerCouponType couponLedgerCouponType = CouponLedgerCouponType.getCouponLedgerCouponTypeByCode(yhglobalCouponLedgerItem.getCouponType());
                yhglobalCouponLedgerItem.setCouponTypeString(couponLedgerCouponType.getMeaning());

                // 账户类型字符串
                CouponConfirmAccountType couponConfirmAccountType = CouponConfirmAccountType.getCouponConfirmAccountTypeByCode(yhglobalCouponLedgerItem.getAccountType());
                yhglobalCouponLedgerItem.setAccountTypeString(couponConfirmAccountType==null?"":couponConfirmAccountType.getAccountName());

                yhglobalCouponLedgerItem.setEstimatedCouponAmountDouble(yhglobalCouponLedgerItem.getEstimatedCouponAmount()==null?0:yhglobalCouponLedgerItem.getEstimatedCouponAmount() / FXConstant.HUNDRED_DOUBLE);
                yhglobalCouponLedgerItem.setToBeConfirmAmountDouble(yhglobalCouponLedgerItem.getToBeConfirmAmount()==null?0:yhglobalCouponLedgerItem.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                if (yhglobalCouponLedgerItem.getConfirmAmount()!=null) {
                    yhglobalCouponLedgerItem.setConfirmAmountDouble(yhglobalCouponLedgerItem.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                }
                if (yhglobalCouponLedgerItem.getReceiptAmount()!=null) {
                    yhglobalCouponLedgerItem.setReceiptAmountDouble(yhglobalCouponLedgerItem.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE);
                }
            }

            resultPage = new PageInfo<>(yhglobalCouponLedgerItemList);

//            List<Long> integerList = new ArrayList<>();
//            for (YhglobalCouponLedgerItem yhglobalCouponLedgerItem:yhglobalCouponLedgerItemList){
//                if (!integerList.contains(yhglobalCouponLedgerItem.getPurchaseCouponLedgerId())){
//                    integerList.add(yhglobalCouponLedgerItem.getPurchaseCouponLedgerId());
//                }
//            }
//            resultPage.setTotal(integerList.size());
            // 把数据封装到proto
            YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp.Builder respBuilder = YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsHasPageResp.newBuilder();
            for (YhglobalCouponLedgerItem item:yhglobalCouponLedgerItemList){
                YhglobalCouponWriteoffServiceStructure.YhglobalCouponLedgerItem.Builder yhglobalCouponLedgerItem = YhglobalCouponWriteoffServiceStructure.YhglobalCouponLedgerItem.newBuilder();
                //Transformer trans = new Transformer();
                //YhglobalCouponWriteoffServiceStructure.YhglobalCouponLedgerItem yhglobalCouponLedgerItem1 = trans.javaToMessage(yhglobalCouponLedgerItem,itemBuilder).build();
                yhglobalCouponLedgerItem.setPurchaseCouponLedgerId(item.getPurchaseCouponLedgerId());
                yhglobalCouponLedgerItem.setCouponStatus(String.valueOf(item.getCouponStatus()));
                yhglobalCouponLedgerItem.setPurchaseOrderNo(item.getPurchaseOrderNo());
                yhglobalCouponLedgerItem.setYlCreateTime(item.getYlCreateTime().getTime());
                yhglobalCouponLedgerItem.setCouponType(String.valueOf(item.getCouponType()));
                yhglobalCouponLedgerItem.setYlCurrencyCode(item.getYlCurrencyCode()==null?"":item.getYlCurrencyCode());
                yhglobalCouponLedgerItem.setEstimatedCouponAmount(item.getEstimatedCouponAmount());
                yhglobalCouponLedgerItem.setToBeConfirmAmount(item.getToBeConfirmAmount()==null?0:item.getToBeConfirmAmount());
                yhglobalCouponLedgerItem.setConfirmCurrencyCode(item.getConfirmCurrencyCode()==null?"":item.getConfirmCurrencyCode());
                yhglobalCouponLedgerItem.setConfirmAmount(item.getConfirmAmount()==null?0:item.getConfirmAmount());
                yhglobalCouponLedgerItem.setReceivedCurrencyCode(item.getReceivedCurrencyCode()==null?"":item.getReceivedCurrencyCode());
                yhglobalCouponLedgerItem.setReceiptAmount(item.getReceiptAmount()==null?0:item.getReceiptAmount());
                yhglobalCouponLedgerItem.setAccountType(item.getAccountType()==null?-1:item.getAccountType());
                yhglobalCouponLedgerItem.setYwCreateTime(item.getYwCreateTime()==null?-1:item.getYwCreateTime().getTime());
                yhglobalCouponLedgerItem.setFlowNo(item.getFlowNo()==null?"":item.getFlowNo());
                yhglobalCouponLedgerItem.setSupplierOrderNo(item.getSupplierOrderNo());

                yhglobalCouponLedgerItem.setPhilipDocumentNo(item.getPhilipDocumentNo()==null?"":item.getPhilipDocumentNo());
                yhglobalCouponLedgerItem.setFlowNo(item.getFlowNo()==null?"":item.getFlowNo());
                yhglobalCouponLedgerItem.setAccountTypeString(item.getAccountTypeString());
                yhglobalCouponLedgerItem.setYwCreateTime(item.getYwCreateTime()==null?-1:item.getYwCreateTime().getTime());

                yhglobalCouponLedgerItem.setEstimatedCouponAmountDouble(item.getEstimatedCouponAmountDouble()==null?0:item.getEstimatedCouponAmountDouble());
                yhglobalCouponLedgerItem.setToBeConfirmAmountDouble(item.getToBeConfirmAmountDouble()==null?0:item.getToBeConfirmAmountDouble());
                yhglobalCouponLedgerItem.setConfirmAmountDouble(item.getConfirmAmountDouble()==null?0:item.getConfirmAmountDouble());
                yhglobalCouponLedgerItem.setReceiptAmountDouble(item.getReceiptAmountDouble()==null?0:item.getReceiptAmountDouble());

                yhglobalCouponLedgerItem.setCouponStatusString(item.getCouponStatusString());
                yhglobalCouponLedgerItem.setCouponTypeString(item.getCouponTypeString());

                respBuilder.addList(yhglobalCouponLedgerItem.build());
            }
            respBuilder.setTotal(resultPage.getTotal());
            respBuilder.setPageNum(resultPage.getPageNum());
            respBuilder.setPageSize(resultPage.getPageSize());
            respBuilder.setSize(resultPage.getSize());
            respBuilder.setStartRow(resultPage.getStartRow());
            respBuilder.setEndRow(resultPage.getEndRow());
            respBuilder.setPages(resultPage.getPages());

            respStreamObserver.onNext(respBuilder.build());
            respStreamObserver.onCompleted();

        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectByManyCondiitons error!", e);
        }

    }


    /**
     * 条件查询返利 导出数据
     * */
    @Override
    public void selectByManyCondiitonsNoPage(YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageReq req,
                                       StreamObserver<YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp> respStreamObserver) {

        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        Long projectId = req.getProjectId();
        String purchaseOrderNo = req.getPurchaseOrderNo();
        String supplierOrderNo = req.getSupplierOrderNo();
        Date dateS = new Date(req.getDateS());
        Date dateE = new Date(req.getDateE());
        String couponStatus = req.getCouponStatus();
        String flowNo = req.getFlowNo();
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, flowCode={}, accountType={}, useDateStart={}," +
                        " useDateEnd={}, dateStart={}, dateEnd={}", rpcHeader, projectId, purchaseOrderNo, supplierOrderNo, dateS, dateE,
                couponStatus, flowNo);
        List<YhglobalCouponLedgerItem> yhglobalCouponLedgerItemList;
        try {
            // 条件查询
            yhglobalCouponLedgerItemList = yhglobalToReceiveCouponLedgerDao.searchByManyCondition(projectId, purchaseOrderNo, supplierOrderNo
                    , flowNo, dateS, dateE, couponStatus);
            //填充部分前端显示的字段
            for (YhglobalCouponLedgerItem yhglobalCouponLedgerItem:yhglobalCouponLedgerItemList) {
                // 从枚举中获取常量赋值给CouponStatusString字段
                CouponLedgerCouponStatus couponLedgerCouponStatus = CouponLedgerCouponStatus.getCouponLedgerCouponStatusByCode(yhglobalCouponLedgerItem.getCouponType());
                yhglobalCouponLedgerItem.setCouponStatusString(couponLedgerCouponStatus.getMeaning());
                // 返利类型字符串
                yhglobalCouponLedgerItem.setCouponTypeString(couponLedgerCouponStatus.getMeaning());
                // 账户类型字符串
                CouponConfirmAccountType couponConfirmAccountType = CouponConfirmAccountType.getCouponConfirmAccountTypeByCode(yhglobalCouponLedgerItem.getAccountType());
                yhglobalCouponLedgerItem.setAccountTypeString(couponConfirmAccountType.getAccountName());

                yhglobalCouponLedgerItem.setEstimatedCouponAmountDouble(yhglobalCouponLedgerItem.getEstimatedCouponAmount()==null?0:yhglobalCouponLedgerItem.getEstimatedCouponAmount() / FXConstant.HUNDRED_DOUBLE);
                yhglobalCouponLedgerItem.setToBeConfirmAmountDouble(yhglobalCouponLedgerItem.getToBeConfirmAmount()==null?0:yhglobalCouponLedgerItem.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                if (yhglobalCouponLedgerItem.getConfirmAmount()!=null) {
                    yhglobalCouponLedgerItem.setConfirmAmountDouble(yhglobalCouponLedgerItem.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                }
                if (yhglobalCouponLedgerItem.getReceiptAmount()!=null) {
                    yhglobalCouponLedgerItem.setReceiptAmountDouble(yhglobalCouponLedgerItem.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE);
                }

                yhglobalCouponLedgerItem.setEstimatedCouponAmountStr(yhglobalCouponLedgerItem.getEstimatedCouponAmountStr());
                yhglobalCouponLedgerItem.setToBeConfirmAmountStr(yhglobalCouponLedgerItem.getToBeConfirmAmountStr());
                yhglobalCouponLedgerItem.setReceiptAmountStr(yhglobalCouponLedgerItem.getReceiptAmountStr());
                yhglobalCouponLedgerItem.setConfirmAmountStr(yhglobalCouponLedgerItem.getConfirmAmountStr());
            }

            // 把数据封装到proto
            YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp.Builder respBuilder = YhglobalCouponWriteoffServiceStructure.SelectByManyCondiitonsNoPageResp.newBuilder();
            for (YhglobalCouponLedgerItem item:yhglobalCouponLedgerItemList){
                YhglobalCouponWriteoffServiceStructure.YhglobalCouponLedgerItem.Builder yhglobalCouponLedgerItem = YhglobalCouponWriteoffServiceStructure.YhglobalCouponLedgerItem.newBuilder();
                //Transformer trans = new Transformer();
                //YhglobalCouponWriteoffServiceStructure.YhglobalCouponLedgerItem yhglobalCouponLedgerItem1 = trans.javaToMessage(yhglobalCouponLedgerItem,itemBuilder).build();
                yhglobalCouponLedgerItem.setPurchaseCouponLedgerId(item.getPurchaseCouponLedgerId());
                yhglobalCouponLedgerItem.setCouponStatus(String.valueOf(item.getCouponStatus()));
                yhglobalCouponLedgerItem.setPurchaseOrderNo(item.getPurchaseOrderNo());
                yhglobalCouponLedgerItem.setYlCreateTime(item.getYlCreateTime().getTime());
                yhglobalCouponLedgerItem.setCouponType(String.valueOf(item.getCouponType()));
                yhglobalCouponLedgerItem.setYlCurrencyCode(item.getYlCurrencyCode()==null?"":item.getYlCurrencyCode());
                yhglobalCouponLedgerItem.setEstimatedCouponAmount(item.getEstimatedCouponAmount());
                yhglobalCouponLedgerItem.setToBeConfirmAmount(item.getToBeConfirmAmount()==null?0:item.getToBeConfirmAmount());
                yhglobalCouponLedgerItem.setConfirmCurrencyCode(item.getConfirmCurrencyCode()==null?"":item.getConfirmCurrencyCode());
                yhglobalCouponLedgerItem.setConfirmAmount(item.getConfirmAmount()==null?0:item.getConfirmAmount());
                yhglobalCouponLedgerItem.setReceivedCurrencyCode(item.getReceivedCurrencyCode()==null?"":item.getReceivedCurrencyCode());
                yhglobalCouponLedgerItem.setReceiptAmount(item.getReceiptAmount()==null?0:item.getReceiptAmount());
                yhglobalCouponLedgerItem.setAccountType(item.getAccountType()==null?-1:item.getAccountType());
                yhglobalCouponLedgerItem.setYwCreateTime(item.getYwCreateTime()==null?-1:item.getYwCreateTime().getTime());
                yhglobalCouponLedgerItem.setFlowNo(item.getFlowNo()==null?"":item.getFlowNo());

                respBuilder.addList(yhglobalCouponLedgerItem.build());
            }

            respStreamObserver.onNext(respBuilder.build());
            respStreamObserver.onCompleted();

        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectByManyCondiitons error!", e);
        }

    }
    /**
     * 实现返利确认的取消功能
     * */
    @Override
    public void couponReceiveCancelConfirmBatch(YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchReq req,
                                             StreamObserver<YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp> respStreamObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = req.getRpcHeader();
        List<String> flowCodeList = req.getFlowCodeList();
        logger.info("#traceId={}# [IN][receiveCancelConfirmBatch] params:flowCodeList={}", rpcHeader, flowCodeList.toString());
        boolean flag = false;
        for (String flowCode : flowCodeList) {
            try {
                flag = receiveCancelConfirm(rpcHeader, flowCode);
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            }
            if (!flag){
                // 取消确认出现错误 直接返回
                YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp.Builder respBuilder = YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp.newBuilder();
                respBuilder.setReturnCode(ErrorCode.CANCEL_COUPON_CONFIRM_WRONG.getErrorCode());
                respBuilder.setMsg(ErrorCode.CANCEL_COUPON_CONFIRM_WRONG.getMessage());
                respStreamObserver.onNext(respBuilder.build());
                respStreamObserver.onCompleted();
            }
        }
        if (flag){
            // 取消返利成功 构建respStreamObserver 返回给controller
            YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp.Builder respBuilder = YhglobalCouponWriteoffServiceStructure.CouponReceiveCancelConfirmBatchResp.newBuilder();
            respBuilder.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
            respBuilder.setMsg(ErrorCode.SUCCESS.getMessage());
            respStreamObserver.onNext(respBuilder.build());
            respStreamObserver.onCompleted();
        }


    }

    /**
     * 对每个台账对象进行取消确认
     * */
    private boolean receiveCancelConfirm(GongxiaoRpc.RpcHeader rpcHeader, String flowCode) {
        logger.info("#traceId={}# [IN][receiveCancelConfirm] params:flowCode={}", rpcHeader, flowCode);
        try {
            Long transactionAmount = 0L; //累加待取消的返利确认总额
            Long projectId = 0L;
            String  projectName = "";
            Long supplierId = 0L;
            Integer accountType = 0;
            String currencyCode = "";
            // 按流水号查确认列表 根据确认记录 修改每条应收返利
            List<YhglobalCouponWriteoffRecord> yhglobalCouponWriteoffRecordList = yhglobalCouponWriteoffRecordDao.selectByFlowCode(flowCode);
            for (YhglobalCouponWriteoffRecord record : yhglobalCouponWriteoffRecordList) {
                // 按应收ID查应收记录
                YhglobalCouponLedger receive = yhglobalToReceiveCouponLedgerDao.selectByPrimaryKey(record.getToReceiveCouponId());
                // projectId  projectName supplierId accountType 获取这四个参数用于流水生成 一条台账对应的多条记录 这四个参数均相同
                // 因此只需赋值一次 用if判断 避免重复赋值
                if (projectId == 0) {
                    projectId = receive.getProjectId();
                }
                if (projectName == "") {
                    projectName = receive.getProjectName();
                }
                if (supplierId == 0) {
                    supplierId = receive.getSupplierId();
                }
                if (accountType == 0) {
                    accountType = record.getAccountType();
                }
                if ("".equals(currencyCode)){
                    currencyCode = receive.getCurrencyCode();
                }
                transactionAmount += receive.getConfirmedCouponAmount(); //合计金额累加
                //修改应收记录（确认金额、未收金额、状态）
                // 更新应收返利数据
                receive.setConfirmedCouponAmount(receive.getConfirmedCouponAmount() - record.getConfirmAmount());
                receive.setReceivedCouponAmount(receive.getReceivedCouponAmount() - record.getReceiptAmount());
                receive.setToBeConfirmAmount(receive.getToBeConfirmAmount() + record.getConfirmAmount());

                long diff = receive.getToBeConfirmAmount(); //计算待核销金额
                receive.setToBeConfirmAmount(diff);

                // 调用赋值返利状态的方法
                Byte targetCouponStatus = updateCouponStatus(receive.getToBeConfirmAmount(), receive.getEstimatedCouponAmount());
                receive.setCouponStatus(targetCouponStatus);

                //更新应收记录
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消返利确认");
                String appendTraceLog = TraceLogUtil.appendTraceLog(receive.getTracelog(), traceLog);
                receive.setTracelog(appendTraceLog);
                yhglobalToReceiveCouponLedgerDao.updateCouponLedgerForWriteoff(receive.getPurchaseCouponLedgerId(),receive.getConfirmedCouponAmount(),
                        receive.getReceivedCouponAmount(),receive.getToBeConfirmAmount(),targetCouponStatus,receive.getDataVersion(),appendTraceLog, new Date());
                // 修改确认记录为已回滚
                // record.setIsRollback(1);
                yhglobalCouponWriteoffRecordDao.updateRollbackStatus(RollbackStatus.ROLLBACK_STATUS_YES.getCode(),record.getConfirmId());
            }
            CouponConfirmAccountType couponConfirmAccountType = CouponConfirmAccountType.getCouponConfirmAccountTypeByCode(accountType);
            Date now = new Date();  // 该流水生成时间 也在调用账户中使用
            //String flow = DateTimeIdGenerator.nextId(BizNumberType.YHGLOBAL_COUPON_WRITEROFF_FLOW);
            String flow = "";
            // 调用根据账户类型修改账户额度的方法
            WriteOffReturnObject writeOffReturnObject =updateAccount(rpcHeader, projectId, transactionAmount, now,  flow,couponConfirmAccountType);

            // 流水中需要的账户额度从 修改账户 的接口返回值中获取
            Long amountBeforeTransaction = writeOffReturnObject.getAmountBeforeTrans();//流水发生前的余额
            Long amountAfterTransaction = writeOffReturnObject.getAmountAfterTrans();//流水发生后的余额

            //生成应收确认对冲流水
            try {
                //按帐户类型查询帐户余额
                addWriteoffFlow(rpcHeader, flow,couponConfirmAccountType, CouponWriteoffFlowType.CANCEL_COUPON_WRITEOFF.getCode(),
                        projectId, projectName,currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, now,
                        new Long(supplierId).intValue(), "", "", null,
                        null, "");
            } catch (Exception ex) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + ex.getMessage(), ex);
                return false;
            }
            if (writeOffReturnObject.getReturnCode() == ErrorCode.SUCCESS.getErrorCode()) {
                return true;
            }else {
                logger.info("update account wrong ,accountType={}, flowCode={}", accountType, flowCode);
                return false;
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("receiveCancelConfirm error!", e);
        }
    }

    /**
     * 返利确认和取消确认都要调用的根据账户类型修改 账户额度的方法
     * @param transactionAmount 正值表示增加 负值表示扣款
     * @param couponConfirmAccountType
     */
    private WriteOffReturnObject updateAccount(GongxiaoRpc.RpcHeader rpcHeader, Long projectId, long transactionAmount,
                                               Date now, String sourceFlowNo, CouponConfirmAccountType couponConfirmAccountType){

        WriteOffReturnObject writeOffReturnObject = null;
        switch (couponConfirmAccountType) {
            case COUPON_RECEIVED_ACCOUNT:
                //扣减实收帐户余额 TODO: 需要判断调用是否成功
                writeOffReturnObject = yhglobalReceivedCouponAccountService.updateYhglobalReceivedCouponAccount("",rpcHeader, "CNY",projectId ,
                        transactionAmount, now,"", sourceFlowNo); // 确认的多条返利的采购单号不同 此处该参数不传
                // if (writeOffReturnObject.get)
                break;
            case SALES_DIFFERENCE_ACCOUNT:
                // 扣减销售差价账户
                writeOffReturnObject = supplierSellHeightTransferAccountService.updateSupplierSellHeightTransferAccount("",rpcHeader, "CNY", projectId, transactionAmount,"", now);
                break;
            default:
                logger.error("unsupported account type: {}", couponConfirmAccountType);
        }
        return writeOffReturnObject;
    }

    /**
     * 返利确认和取消确认都要调用的 设置应收返利的 返利状态的方法
     * @param toBeConfirmAmount 返利的未确认额度
     * @param estimatedCouponAmount 返利的应收额度
     * @return
     */
    private Byte updateCouponStatus(Long toBeConfirmAmount, Long estimatedCouponAmount){ //TODO 拆成具体的业务参数
        Byte targetCouponStatus = 0; //保存目标应收状态
        if (estimatedCouponAmount > 0) { //应收返利为正 比如:正常采购
            /**
             * 应收返利为正的状态判定:
             * (a) 若diff小于等于0 表示返利已全部确认
             * (b) 若diff大于0, 且待核销金额和初始预估的核销值一致 则表示从未有过返利核销确认
             * (c) 其他情况认为是 部分核销
             */
            if (toBeConfirmAmount <= 0) { //应收已全部发放
                targetCouponStatus = CouponLedgerCouponStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getCode();
            }  else if (toBeConfirmAmount.longValue() == estimatedCouponAmount.longValue()){ //从未有过返利核销确认
                targetCouponStatus = CouponLedgerCouponStatus.COUPON_STATUS_NOT_TO_ISSUE.getCode();
            } else { //其他情况为部分确认
                // 当应收返利为正时,未收 为 非正时 均为全部发放
                // 未收为正且小于 应收 或者 大于应收(即此时确认额度为负数) 则为部分发放
                targetCouponStatus = CouponLedgerCouponStatus.COUPON_STATUS_PART_OF_ISSUE.getCode();//部分发放
            }
        } else { //应收返利为负: 比如采购退货
            /**
             * 应收返利为负的状态判定:
             * (a) 若diff大于等于0 表示返利已全部确认
             * (b) 若diff小于0, 且待核销金额和初始预估的核销值一致 则表示从未有过返利核销确认
             * (c) 其他情况认为是 部分核销
             */
            if (toBeConfirmAmount >= 0) { //应收已全部发放
                targetCouponStatus = CouponLedgerCouponStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getCode();
            } else if (toBeConfirmAmount.longValue() == estimatedCouponAmount.longValue()){ //从未有过返利核销确认
                targetCouponStatus = CouponLedgerCouponStatus.COUPON_STATUS_NOT_TO_ISSUE.getCode();
            } else { //其他情况为部分确认
                targetCouponStatus = CouponLedgerCouponStatus.COUPON_STATUS_PART_OF_ISSUE.getCode();//部分发放
            }
        }

        return targetCouponStatus;


    }

}
