package com.yhglobal.gongxiao.grossProfit.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.coupon.constant.RollbackStatus;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grossProfit.Service.GrossProfitService;
import com.yhglobal.gongxiao.grossProfit.constant.DifferenceAdjustPatternConstant;
import com.yhglobal.gongxiao.grossProfit.constant.GrossProfitLedgerConfirmStatus;
import com.yhglobal.gongxiao.grossProfit.constant.GrossProfitTransferPatternConstant;
import com.yhglobal.gongxiao.grossProfit.dao.YhglobalToReceiveGrossProfitLedgerDao;
import com.yhglobal.gongxiao.grossProfit.dao.YhglobalToReceiveGrossProfitLedgerWriteoffFlowDao;
import com.yhglobal.gongxiao.grossProfit.dao.YhglobalToReceiveGrossProfitLedgerWriteoffRecordDao;
import com.yhglobal.gongxiao.grossProfit.model.*;
import com.yhglobal.gongxiao.grossProfit.utils.GetConfirmStatusUtil;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.model.WriteOffReturnObject;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Service
public class GrossProfitServiceImpl implements GrossProfitService {

    static Logger logger = LoggerFactory.getLogger(GrossProfitServiceImpl.class);

    @Autowired
    YhglobalToReceiveGrossProfitLedgerDao yhglobalToReceiveGrossProfitLedgerDao;

    @Autowired
    YhglobalToReceiveGrossProfitLedgerWriteoffFlowDao yhglobalToReceiveGrossProfitLedgerWriteoffFlowDao;

    @Autowired
    YhglobalToReceiveGrossProfitLedgerWriteoffRecordDao yhglobalToReceiveGrossProfitLedgerWriteoffRecordDao;

    /**
     * 根据项目ID获取 对应的表的前缀名 所有支持多项目的update select insert delete部分都需要
     * @param projectId
     * @return
     */
    public static String getTablePrefix(Long projectId){
        return TablePrefixUtil.getTablePrefixByProjectId(projectId);
    }

    /**
     * 生成毛利
     * @param rpcHeader
     * @param projectId
     * @param currencyCode
     * @param purchaseOrderNo
     * @param estimatedGrossProfitAmount
     * @param confirmedAmount
     * @param projectName
     * @param receivedAmount
     * @param salesOrderNo
     * @param salesTime
     * @param tablePrefix
     * @param toBeConfirmAmount
     * @return
     */
    @Override
    public RpcResult generateGrossProfitLedger(GongxiaoRpc.RpcHeader rpcHeader, Long projectId,
                                                  String currencyCode ,
                                                  String purchaseOrderNo ,
                                                  BigDecimal estimatedGrossProfitAmount,
                                                  BigDecimal confirmedAmount ,
                                                  String projectName ,
                                                  BigDecimal receivedAmount,
                                                  String salesOrderNo ,
                                                  Date salesTime ,
                                                  String tablePrefix,
                                                  BigDecimal toBeConfirmAmount) {

        logger.info("#traceId={}# [IN][generateYhglobalGrossProfitLedger] params:  projectId={}, currencyCode={}, purchaseOrderNo={}," +
                        "  estimatedGrossProfitAmount={}, confirmedAmount={}, confirmStatus={}, projectName={}, receivedAmount={}, projectName={}," +
                        "  salesOrderNo={}, salesTime={}, toBeConfirmAmount={}", projectId, currencyCode,purchaseOrderNo, estimatedGrossProfitAmount, confirmedAmount,
                projectName, receivedAmount, salesOrderNo, salesTime, toBeConfirmAmount);
        try {
            YhglobalToReceiveGrossProfitLedger ledger = new YhglobalToReceiveGrossProfitLedger();
            ledger.setEstimatedGrossProfitAmount(estimatedGrossProfitAmount);
            ledger.setConfirmedAmount(confirmedAmount);
            ledger.setReceivedAmount(receivedAmount);
            ledger.setToBeConfirmAmount(toBeConfirmAmount);
            ledger.setProjectId(projectId);
            ledger.setProjectName(projectName);
            ledger.setCreateTime(new Date());
            ledger.setCurrencyCode(currencyCode);
            ledger.setPurchaseOrderNo(purchaseOrderNo);
            ledger.setSalesOrderNo(salesOrderNo);
            ledger.setSalesTime(salesTime);
            ledger.setDataVersion(0L);
            ledger.setTablePrefix(tablePrefix);

            // 设置确认状态
            Long estimatedAmountLong = estimatedGrossProfitAmount.multiply(new BigDecimal("100")).longValue();
            Long tobeConfirmAmountLong = toBeConfirmAmount.multiply(new BigDecimal("100")).longValue();
            Byte confirmStatus = GetConfirmStatusUtil.updateCouponStatus(tobeConfirmAmountLong, estimatedAmountLong);
            ledger.setConfirmStatus(confirmStatus);
            int success = yhglobalToReceiveGrossProfitLedgerDao.insertLedger(ledger);
            if (success == 1) {
                return new RpcResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());
            } else {
                return new RpcResult(ErrorCode.INSERT_FAIL.getErrorCode(), ErrorCode.INSERT_FAIL.getMessage());
            }
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    /**
     * 毛利核销
     * @param rpcHeader
     * @param confirmInfoJson
     * @param currencyCode
     * @param differenceAmountAdjustPattern
     * @param projectId
     * @param transferIntoPattern
     * @param useDate
     * @return
     */
    @Override
    public RpcResult yhGrossProfitWriteroff(GongxiaoRpc.RpcHeader rpcHeader,  String confirmInfoJson ,
                                       String currencyCode ,
                                       Byte differenceAmountAdjustPattern ,
                                       Long projectId,
                                       Byte transferIntoPattern ,
                                       String useDate, String flowNo, List<WriteOffReturnObject> list) {

        logger.info("#traceId={}# [IN][receiveConfirm] params:projectId={}, projectName={}, useDate={},  accountType={},  confirmInfo={}",
                rpcHeader.getTraceId(), projectId, currencyCode, useDate, differenceAmountAdjustPattern, transferIntoPattern, confirmInfoJson);
        RpcResult rpcResult = null;
        // 部分数据转换获取
        // 根据json串 遍历应收
        // 修改账户
        // 修改应收
        // 插入核销流水
        // 插入核销记录
        try {
            Date now = new Date();
            //根据项目得表前缀
            String tablePrefix = getTablePrefix(projectId);
            // 解析前端封装的json格式数据
            List<YhglobalToReceiveGrossProfitLedger> receiveList
                    = JSON.parseObject(confirmInfoJson, new TypeReference<List<YhglobalToReceiveGrossProfitLedger>>() {
            });

            //查询帐户余额 根据账户类型选择不同的账户
            Long accountTotalAmount = 0L;
            GrossProfitTransferPatternConstant grossProfitTransferPatternConstant = GrossProfitTransferPatternConstant.getGrossProfitTransferPatternConstantByCode(transferIntoPattern.intValue());

            // 获得货币编码
            // String currencyCode = "";
            Integer i = 0; // 成功条数
            Integer length = receiveList.size();// 总条数
            Integer failNo = 0;
            // 生成流水号 给流水,记录以及 调用账户接口时使用
            String flowCode = DateTimeIdGenerator.nextId(tablePrefix, BizNumberType.YHGLOBAL_GROSS_PROFIT_WRITEROFF_FLOW);
            for (int j = 0;j<receiveList.size(); j++) { // 根据选择的多条核销数据进行逐条账户扣减
                YhglobalToReceiveGrossProfitLedger receive = receiveList.get(j);
                WriteOffReturnObject writeOffReturnObject = list.get(j);
                if (writeOffReturnObject.getReturnCode() != 0|| writeOffReturnObject == null){
                    continue; // 当条核销数据 交易失败或返回数据为空时 跳出该条数据  执行下一条
                }
                BigDecimal transferAmount = receive.getReceivedAmount(); // 每笔核销扣减的额度
                failNo = length - i;
//                switch (grossProfitTransferPatternConstant) { //根据确认的账户类型加载对应的账户余额
//                    case COUPON_RECEIVED_ACCOUNT:
//                        // 账户额度查询使用grpc调用葛灿接口
//                        YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest rpcRequest = YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest.newBuilder()
//                                .setRpcHeader(rpcHeader)
//                                .setProjectId(projectId)
//                                .build();
//                        YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
//                        PaymentCommonGrpc.AccountAmountResponse rpcResponse = yhglobalAccountService.getYhglobalReceivedAccountAmount(rpcRequest);
//                        BigDecimal accountAmount = new BigDecimal(rpcResponse.getCouponAmount() / FXConstant.HUNDRED_DOUBLE + "");
//                        if (accountAmount.compareTo(transferAmount) == -1) { // 说明账户额度比交易额度小 及账户额度不足
//                            // 账户额度不足
//                            logger.error("accountTotalAmount insufficient: GrossProfitId={}",  receive.getGrossProfitId());
//                            return new RpcResult(ErrorCode.YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), "账户额度不足,共选择了"+length+"条毛利,已成功核销"+i+"条, 核销失败"+failNo+"条");
//                        }
//                        break;
//                    case SALES_DIFFERENCE_ACCOUNT:
//                        // 账户额度查询使用grpc调用葛灿接口
//                        SupplierAccountServiceStructure.GetSellHighAccountRequest rpcRequest1 = SupplierAccountServiceStructure.GetSellHighAccountRequest.newBuilder()
//                                .setRpcHeader(rpcHeader)
//                                .setProjectId(projectId)
//                                .build();
//                        SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
//                        SupplierAccountServiceStructure.GetSellHighAccountResponse rpcResponse2 = rpcStub.getSellHighAccount(rpcRequest1);
//                        BigDecimal accountAmount2 = new BigDecimal(rpcResponse2.getTotalAmount() / FXConstant.HUNDRED_DOUBLE + "");
//                        if (accountAmount2.compareTo(transferAmount) == -1) {
//                            // 差价账户余额不足
//                            logger.error("accountTotalAmount insufficient: GrossProfitId={}", receive.getGrossProfitId());
//                            return new RpcResult(ErrorCode.SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), "账户额度不足,共选择了"+length+"条毛利,已成功核销"+i+"条, 核销失败"+failNo+"条");
//                        }
//                        break;
//                    case PREPAID_RECEIVED_ACCOUNT:
//                        //  调用葛灿代垫实收账户接口
//                        YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest rpcRequest3 = YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest.newBuilder()
//                                .setRpcHeader(rpcHeader)
//                                .setProjectId(projectId)
//                                .build();
//                        YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService3 = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
//                        PaymentCommonGrpc.AccountAmountResponse rpcResponse3 = yhglobalAccountService3.getYhglobalReceivedAccountAmount(rpcRequest3);
//                        BigDecimal accountAmount3 = new BigDecimal(rpcResponse3.getPrepaidAmount() / FXConstant.HUNDRED_DOUBLE + "");
//                        if (accountAmount3.compareTo(transferAmount) == -1){
//                            // 账户额度不足
//                            logger.error("accountTotalAmount insufficient: GrossProfitId={}", receive.getGrossProfitId());
//                            return new RpcResult(ErrorCode.ACCOUNT_PREPAID_RECEIPT_NOT_SUFFICIENT_FUNDS.getErrorCode(),"账户额度不足,共选择了"+length+"条毛利,已成功核销"+i+"条, 核销失败"+failNo+"条");
//                        }
//                        break;
//                    case PURCHASE_RESERVED_ACCOUNT:
//                        // TODO 调用采购预留账户修改接口(账户表以表名前缀区分不同项目)
//                        return new RpcResult(ErrorCode.SUCCESS.getErrorCode(),"暂不支持该账户类型");
//
//                    case UNIT_PRICE_DISCOUNT_RESERVED_ACCOUNT:
//                        // TODO 调用单价折扣预留账户修改接口(账户表以表名前缀区分不同项目)
//                        return new RpcResult(ErrorCode.SUCCESS.getErrorCode(),"暂不支持该账户类型");
//                    case SUPPLIER_CASH_TRANSFER_IN:
//                        // TODO 供应商现金转入 (账户表以表名前缀区分不同项目) 是供应商现金账户?
//                        return new RpcResult(ErrorCode.SUCCESS.getErrorCode(),"暂不支持该账户类型");
//                    default:
//                        throw new IllegalArgumentException("unknown grossProfitTransferPatternConstant: " + grossProfitTransferPatternConstant.getAccountName());
//                }


                //Long amount = - Math.round(transferAmount.doubleValue() * FXConstant.HUNDRED); // 交易金额类型转换
                // 调用根据账户类型修改账户额度的方法  有返回值
                // WriteOffReturnObject writeOffReturnObject = updateAccount(rpcHeader, projectId,amount , now, flowCode, grossProfitTransferPatternConstant);
                //返利模块只是调用扣多少款 由支付模块返回交易前后的余额,
                Long amountBeforeTransaction = writeOffReturnObject.getAmountBeforeTrans();//流水发生前的余额
                Long amountAfterTransaction = writeOffReturnObject.getAmountAfterTrans();//流水发生后的余额
                //查询应收记录
                YhglobalToReceiveGrossProfitLedger receiveInfo = yhglobalToReceiveGrossProfitLedgerDao.getForWriteoff(tablePrefix, receive.getGrossProfitId());
                if ("".equals(currencyCode) || currencyCode == null) {
                    currencyCode = receiveInfo.getCurrencyCode();
                }
                //写一条核销记录
                YhglobalToReceiveGrossProfitLedgerWriteoffRecord record = new YhglobalToReceiveGrossProfitLedgerWriteoffRecord();
                record.setGrossProfitId(receive.getGrossProfitId());
                record.setConfirmAmount(receive.getConfirmedAmount());
                record.setReceiptAmount(receive.getReceivedAmount());
                record.setConfirmCurrencyCode(currencyCode);
                record.setReceivedCurrencyCode(currencyCode);
                record.setFlowNo(flowCode);
                record.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                record.setDataVersion(0L);
                record.setCreatedByName(rpcHeader.getUsername());
                record.setCreateTime(now);
                record.setTransferIntoPattern(grossProfitTransferPatternConstant.getCode().byteValue());
                record.setProjectId(projectId);
                record.setUseDate(DateUtil.stringToDate(useDate));
                record.setReceivedCurrencyCode(currencyCode);
                record.setIsRollback(RollbackStatus.ROLLBACK_STATUS_NOES.getCode());
                record.setDifferenceAmount(receive.getConfirmedAmount().subtract(receive.getReceivedAmount()));
                //保存核销记录
                // yhglobalPrepaidLedgerWriteoffRecordDao.addWriteoffRecord(record);
                record.setTablePrefix(tablePrefix);
                yhglobalToReceiveGrossProfitLedgerWriteoffRecordDao.insertRecord(record);

                //计算应收返利的确认额度 实收额度 未确认额度
                BigDecimal confirmAmount ; //该条应收返利总共已确认的金额
                if (receiveInfo.getConfirmedAmount() == null) {
                    confirmAmount = receive.getConfirmedAmount();
                } else { //非空表示历史有已确认金额,则累加
                    confirmAmount = receiveInfo.getConfirmedAmount().add(receive.getConfirmedAmount());
                }
                receiveInfo.setConfirmedAmount(confirmAmount);

                BigDecimal receiptAmount ; //该条应收返利总共实收的金额
                if (receiveInfo.getReceivedAmount() == null) {
                    receiptAmount = receive.getReceivedAmount();
                } else {
                    receiptAmount = receiveInfo.getReceivedAmount().add(receive.getReceivedAmount());
                }
                receiveInfo.setReceivedAmount(receiptAmount);

                BigDecimal diff = receiveInfo.getEstimatedGrossProfitAmount().subtract(confirmAmount); //计算待核销金额
                receiveInfo.setToBeConfirmAmount(diff);

                // 调用设置应收返利的方法
                Byte targetGrossProfitStatus = updateStatus(receiveInfo.getToBeConfirmAmount(), receiveInfo.getEstimatedGrossProfitAmount());
                receiveInfo.setConfirmStatus(targetGrossProfitStatus);

                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "毛利确认");
                String appendTraceLog = TraceLogUtil.appendTraceLog(receiveInfo.getTracelog(), traceLog);

                // 更新应收毛利
                yhglobalToReceiveGrossProfitLedgerDao.updateLedger(tablePrefix, receiveInfo.getGrossProfitId(),targetGrossProfitStatus,diff,
                        confirmAmount, receiptAmount, receiveInfo.getDataVersion(), appendTraceLog, new Date(),"" );
                DifferenceAdjustPatternConstant differenceAdjustPatternConstant = DifferenceAdjustPatternConstant.getDifferenceAdjustPatternConstant(differenceAmountAdjustPattern);
                //记录流水  供应商及经销商信息为预留字段 目前不用存储
                try {
                    addWriteoffFlow(tablePrefix, rpcHeader, grossProfitTransferPatternConstant,flowCode,
                            projectId,  currencyCode,
                            new BigDecimal(amountBeforeTransaction / FXConstant.HUNDRED_DOUBLE +""),
                            transferAmount.multiply(new BigDecimal("-1")),
                            new BigDecimal(amountAfterTransaction / FXConstant.HUNDRED_DOUBLE + ""),
                            now,
                            null,
                            null,
                            receiveInfo.getSalesOrderNo(),
                            null,
                            null,
                            grossProfitTransferPatternConstant.getAccountName(),differenceAdjustPatternConstant.getMeaning());
                } catch (Exception ex) {
                    logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + ex.getMessage(), ex);
                }

                i++; // 统计核销成功的条数
            }
            //构建repcresult 返回给controller
            rpcResult = new RpcResult(ErrorCode.SUCCESS.getErrorCode(), "全部核销成功,共选择了"+length+"条毛利,已成功核销"+i+"条, 核销失败"+(length - i)+"条");

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            rpcResult = new RpcResult(ErrorCode.GROSS_PROFIT_CONFIRM_FAIL.getErrorCode(), ErrorCode.GROSS_PROFIT_CONFIRM_FAIL.getMessage());
        }

        return rpcResult;
    }

    /**
     * 生成流水
     */
    public void addWriteoffFlow(String tablePrefix, GongxiaoRpc.RpcHeader rpcHeader,GrossProfitTransferPatternConstant grossProfitTransferPatternConstant,
                                String flowCode,
                                Long projectId,
                                String currencyCode,
                                BigDecimal amountBeforeTransaction,
                                BigDecimal transactionAmount,
                                BigDecimal amountAfterTransaction,
                                Date transactionTime,
                                Integer supplierId,
                                String supplierName,
                                String salesOrderId,
                                Long distributorId,
                                String distributorName,
                                String transferPattern,
                                String differenceAmountAdjustPattern) {

        logger.info("#traceId={}# [IN][receiveConfirm] params:projectId={}, projectName={},  amountBeforeTransaction={},  transactionAmount={}, " +
                        " amountAfterTransaction={},  transactionTime={}, supplierId={}, supplierName={}, salesOrderId={}, distributorId={}, distributorName={}, differenceAmountAdjustPattern={}",
                rpcHeader.getTraceId(), projectId, amountBeforeTransaction, transactionAmount, amountAfterTransaction, transactionTime, supplierId, supplierName,
                salesOrderId, distributorId, distributorName, differenceAmountAdjustPattern, transferPattern);
        YhglobalToReceiveGrossProfitLedgerWriteoffFlow flow = new YhglobalToReceiveGrossProfitLedgerWriteoffFlow();

        try {
            flow.setFlowNo(flowCode);
            flow.setProjectId(projectId);
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
            flow.setTransferPattern(grossProfitTransferPatternConstant.getAccountName());
            if (transactionAmount.compareTo(new BigDecimal("0")) == -1){
                flow.setFlowType(305);
            }else {
                flow.setFlowType(306);
            }
            flow.setTablePrefix(tablePrefix);
            int count = yhglobalToReceiveGrossProfitLedgerWriteoffFlowDao.insertFlow(flow);
            if (count != 1) {
                logger.error("#traceId={}# [OUT]: insert transportation writeroff flow fail flowCode={}", rpcHeader.getTraceId(), flowCode);
            }
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("add transportation writeroff flow error!", e);
        }

    }

    /**
     * 根据毛利ID查询毛利
     * @param projectId
     * @param idList
     * @return
     */
    @Override
    public List<YhglobalToReceiveGrossProfitLedger> selectGrossProfitLedgerByIds(Long projectId, List<String> idList) {

        logger.info("#traceId={}# [IN][selectGrossProfitLedgerByIds] params: ids={} ",idList.toString());
//        List<String> idList = Arrays.asList(ids);
        //  拼接ID 一次查出所有对象
        StringBuffer sb = new StringBuffer();
        boolean flag = false;
        for (String id : idList){
            if (flag){
                sb.append(",");
            }else {
                flag = true;
            }
            sb.append(id);
        }
        RpcResult rpcResult = null;
        //todo 前台需要传projectID
        String tablePrefix = getTablePrefix(projectId);
        // String tablePrefix = "shaver";
        try {
            List<YhglobalToReceiveGrossProfitLedger> yhglobalGrossProfitLedgerList = yhglobalToReceiveGrossProfitLedgerDao.selectByIds(tablePrefix, sb.toString());
            // 根据选中的采购返利ID 查询出对应的返利详情
            for (YhglobalToReceiveGrossProfitLedger yhglobalToReceiveGrossProfitLedger : yhglobalGrossProfitLedgerList) {
                // 毛利状态
                GrossProfitLedgerConfirmStatus status = GrossProfitLedgerConfirmStatus.getGrossProfitLedgerConfirmStatusByCode(yhglobalToReceiveGrossProfitLedger.getConfirmStatus());
                yhglobalToReceiveGrossProfitLedger.setConfirmStatusStr(status.getMeaning());

            }
            return yhglobalGrossProfitLedgerList;
        }catch (Exception e){
            logger.error("# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectYhglobalGrossProfitLedgerById fail");
        }

    }

    /**
     * 毛利台账
     * @param rpcHeader
     * @param accountType
     * @param flowCode
     * @param projectId
     * @param pageNumber
     * @param pageSize
     * @param dateS
     * @param dateE
     * @param useDateS
     * @param useDateE
     * @return
     */
    @Override
    public  PageInfo<YhglobalToReceiveGrossProfitLedgerWriteoffRecord> searchGrossProfitConfirm(GongxiaoRpc.RpcHeader rpcHeader, Integer accountType ,
                                                                                        String flowCode ,
                                                                                        Long projectId,
                                                                                        Integer pageNumber,
                                                                                        Integer pageSize ,
                                                                                        Date dateS ,
                                                                                        Date dateE,
                                                                                        Date useDateS,
                                                                                        Date useDateE) {

        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, flowCode={}, accountType={}, useDateS={}, useDateE={}, dateS={}, " +
                "dateE={}", rpcHeader, projectId, flowCode, accountType, pageNumber, pageSize, useDateS, useDateE, dateS, dateE);

        try {
            //todo 根据项目得表前缀
            String tablePrefix = getTablePrefix(projectId);

            PageInfo<YhglobalToReceiveGrossProfitLedgerWriteoffRecord> resultPage;
            PageHelper.startPage(pageNumber, pageSize);
            List<YhglobalToReceiveGrossProfitLedgerWriteoffRecord> recordList = yhglobalToReceiveGrossProfitLedgerWriteoffRecordDao.selectForBook(tablePrefix,
                    projectId, flowCode, accountType, useDateS, useDateE, dateS, dateE);
            for (YhglobalToReceiveGrossProfitLedgerWriteoffRecord record : recordList) {
                // 交易账户名称赋值
                GrossProfitTransferPatternConstant grossProfitTransferPatternConstant = GrossProfitTransferPatternConstant.getGrossProfitTransferPatternConstantByCode(record.getTransferIntoPattern().intValue());
                record.setTransferIntoPatternStr(grossProfitTransferPatternConstant.getAccountName());

                record.setUseDateStr(DateUtil.dateToString(record.getUseDate()));
                record.setCreateTimeStr(DateUtil.dateToString(record.getCreateTime()));
            }
            resultPage = new PageInfo<>(recordList);
            return resultPage;

        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("searchGrossProfitConfirm fail");
        }

    }

    /**
     * 毛利多条件查询
     * @param rpcHeader
     * @param grossProfitStatus
     * @param flowNo
     * @param projectId
     * @param purchaseOrderNo
     * @param pageNumber
     * @param pageSize
     * @param salesOrderNo
     * @param dateS
     * @param dateE
     * @return
     */
    @Override
    public  PageInfo<GrossProfitItem> selectByManyConditionsHasPage(GongxiaoRpc.RpcHeader rpcHeader, String grossProfitStatus ,
                                                                    String flowNo ,
                                                                    Long projectId ,
                                                                    String purchaseOrderNo ,
                                                                    Integer pageNumber ,
                                                                    Integer pageSize,
                                                                    String salesOrderNo ,
                                                                    Date dateS ,
                                                                    Date dateE) {
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, flowCode={}, accountType={}, useDateStart={}," +
                        " useDateEnd={}, dateStart={}, dateEnd={}", rpcHeader, grossProfitStatus, projectId, purchaseOrderNo,  dateS, dateE,salesOrderNo,
                 flowNo, pageNumber, pageSize);

        //todo 根据项目得表前缀
        String tablePrefix = getTablePrefix(projectId);
        try {
            PageHelper.startPage(pageNumber, pageSize);
            // 条件查询
            List<GrossProfitItem> grossProfitItemList = yhglobalToReceiveGrossProfitLedgerDao.selectByManyConditions(tablePrefix, projectId, purchaseOrderNo, salesOrderNo,
                    flowNo, dateS, dateE, grossProfitStatus);
            // 填充部分前端显示的字段
            for (GrossProfitItem grossProfitItem :grossProfitItemList) {

                // 从枚举中获取常量赋值给confirmStatusStr字段
                GrossProfitLedgerConfirmStatus grossProfitLedgerConfirmStatus = GrossProfitLedgerConfirmStatus.getGrossProfitLedgerConfirmStatusByCode(grossProfitItem.getConfirmStatus().byteValue());
                grossProfitItem.setConfirmStatusStr(grossProfitLedgerConfirmStatus.getMeaning());

                if (grossProfitItem.getUseDate() != null) grossProfitItem.setUseDateStr(DateUtil.dateToString(grossProfitItem.getUseDate()));
            }

            PageInfo<GrossProfitItem> resultPage  = new PageInfo<>(grossProfitItemList);
            return resultPage;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectGrossProfitByManyCondiitons fail");
        }

    }

    @Override
    public List<GrossProfitItem> selectByManyConditionsNoPage( GongxiaoRpc.RpcHeader rpcHeader,String grossProfitStatus ,
                                                        String flowNo ,
                                                        Long projectId ,
                                                        String purchaseOrderNo ,
                                                        String salesOrderNo ,
                                                        Date dateS ,
                                                        Date dateE){
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, flowCode={}, accountType={}, useDateStart={}," +
                        " useDateEnd={}, dateStart={}, dateEnd={}", rpcHeader, grossProfitStatus, projectId, purchaseOrderNo,  dateS, dateE,salesOrderNo,
                flowNo);

        //todo 根据项目得表前缀
        String tablePrefix = getTablePrefix(projectId);
        try {
            // 条件查询
            List<GrossProfitItem> grossProfitItemList = yhglobalToReceiveGrossProfitLedgerDao.selectByManyConditions(tablePrefix, projectId, purchaseOrderNo, salesOrderNo,
                    flowNo, dateS, dateE, grossProfitStatus);
            // 填充部分前端显示的字段
            for (GrossProfitItem grossProfitItem :grossProfitItemList) {

                // 从枚举中获取常量赋值给confirmStatusStr字段
                GrossProfitLedgerConfirmStatus grossProfitLedgerConfirmStatus = GrossProfitLedgerConfirmStatus.getGrossProfitLedgerConfirmStatusByCode(grossProfitItem.getConfirmStatus().byteValue());
                grossProfitItem.setConfirmStatusStr(grossProfitLedgerConfirmStatus.getMeaning());

                if (grossProfitItem.getUseDate() != null) grossProfitItem.setUseDateStr(DateUtil.dateToString(grossProfitItem.getUseDate()));
            }
            return grossProfitItemList;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectGrossProfitByManyCondiitons fail");
        }


    }


    /**
     * 毛利取消确认
     * @param rpcHeader
     * @param projectId
     * @param flowCodes
     * @return
     */
    @Override
    public RpcResult receiveCancelConfirmBatch(GongxiaoRpc.RpcHeader rpcHeader, Long projectId, String flowCodes, WriteOffReturnObject object , String flow) {

        //List<String> flowCodeList = Arrays.asList(flowCodes.split(","));
        //logger.info("#traceId={}# [IN][receiveCancelConfirmBatch] params:flowCodeList={}", rpcHeader, flowCodeList.toString());
        //todo 根据项目得表前缀
        String tablePrefix = getTablePrefix(projectId);
        //for (String flowCode : flowCodeList) {
            try {
                receiveCancelConfirm(tablePrefix, rpcHeader, flowCodes, object, flow);
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
                return new RpcResult(ErrorCode.GROSS_PROFIT_CANCEL_CONFIRM_FAIL.getErrorCode(), ErrorCode.GROSS_PROFIT_CANCEL_CONFIRM_FAIL.getMessage());
            }
        //}

        return new RpcResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());

    }

    /**
     * 对每个台账对象进行取消确认
     * */
    void receiveCancelConfirm(String tablePrefix, GongxiaoRpc.RpcHeader rpcHeader, String flowCode, WriteOffReturnObject object , String flow) {
        logger.info("#traceId={}# [IN][receiveCancelConfirm] params:flowCode={}", rpcHeader, flowCode);
        try {
            BigDecimal transactionAmount = new BigDecimal("0"); //累加待取消的返利确认总额
            Long projectId = 0L;
            String  projectName = "";
            Long supplierId = 0L;
            Byte accountType = 0;
            String currencyCode = "";
            // 按流水号查确认列表 根据确认记录 修改每条应收返利
            List<YhglobalToReceiveGrossProfitLedgerWriteoffRecord> writeoffRecordList = yhglobalToReceiveGrossProfitLedgerWriteoffRecordDao.selectByFlowNo(tablePrefix,flowCode);
            for (YhglobalToReceiveGrossProfitLedgerWriteoffRecord record : writeoffRecordList) {
                // 按应收ID查应收记录
                YhglobalToReceiveGrossProfitLedger receive = yhglobalToReceiveGrossProfitLedgerDao.getById(tablePrefix,record.getGrossProfitId());
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
                    accountType = record.getTransferIntoPattern();
                }
                if ("".equals(currencyCode)){
                    currencyCode = receive.getCurrencyCode();
                }
                transactionAmount = record.getReceiptAmount().add(transactionAmount); //合计金额累加 核销记录 按实收计
                //修改应收记录（确认金额、未收金额、状态）
                // 更新应收返利数据
                receive.setConfirmedAmount(receive.getConfirmedAmount().subtract(record.getConfirmAmount()));// 确认
                receive.setReceivedAmount(receive.getReceivedAmount().subtract(record.getReceiptAmount()));// 实收
                receive.setToBeConfirmAmount(receive.getToBeConfirmAmount().add(record.getConfirmAmount()));// 未收


                // 调用判断确认状态的方法
                Byte targetGrossProfitStatus = updateStatus(receive.getToBeConfirmAmount(), receive.getEstimatedGrossProfitAmount());
                receive.setConfirmStatus(targetGrossProfitStatus);

                //添加操作日志 更新应收记录
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消毛利确认");
                String appendTraceLog = TraceLogUtil.appendTraceLog(receive.getTracelog(), traceLog);
                receive.setTracelog(appendTraceLog);
                yhglobalToReceiveGrossProfitLedgerDao.updateLedger(tablePrefix,receive.getGrossProfitId(), targetGrossProfitStatus, receive.getToBeConfirmAmount(),
                        receive.getConfirmedAmount(), receive.getReceivedAmount(), receive.getDataVersion(),appendTraceLog, new Date(), "");
                // 修改确认记录为已回滚
                yhglobalToReceiveGrossProfitLedgerWriteoffRecordDao.updateRecord(tablePrefix, RollbackStatus.ROLLBACK_STATUS_YES.getCode(),record.getConfirmId());
            }
            GrossProfitTransferPatternConstant transferPatternConstant = GrossProfitTransferPatternConstant.getGrossProfitTransferPatternConstantByCode(accountType.intValue());
            Date now = new Date();  // 该流水生成时间 也在调用账户中使用
            // 生成流水号
            //String flow = DateTimeIdGenerator.nextId(tablePrefix, BizNumberType.YHGLOBAL_GROSS_PROFIT_CANCEL_WRITEROFF_FLOW);
            long amount = Math.round(transactionAmount.doubleValue() * FXConstant.HUNDRED);// 类型转换
            // 调用根据账户类型修改账户额度的方法
            //WriteOffReturnObject object = updateAccount(rpcHeader, projectId, amount, now, flow, transferPatternConstant);

            // 流水中需要的账户额度从 修改账户 的接口返回值中获取
            Long accountTotalAmount = 0l;
            Long amountBeforeTransaction = object.getAmountBeforeTrans();//流水发生前的余额
            Long amountAfterTransaction = object.getAmountAfterTrans();//流水发生后的余额
            // 取消确认时 金额合并 无法确定此时的该字段
            // DifferenceAdjustPatternConstant differenceAdjustPatternConstant = DifferenceAdjustPatternConstant.getDifferenceAdjustPatternConstant();
            //生成应收确认对冲流水
            try {
                //按帐户类型查询帐户余额supplierId==null?0:supplierId.intValue()
                addWriteoffFlow(tablePrefix,rpcHeader,transferPatternConstant,flow,
                        projectId, currencyCode, new BigDecimal(amountBeforeTransaction / FXConstant.HUNDRED_DOUBLE +""),
                        transactionAmount ,new BigDecimal(amountAfterTransaction/ FXConstant.HUNDRED_DOUBLE +""), now,
                        null, "", "", null,
                        null, "", "");
            } catch (Exception ex) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + ex.getMessage(), ex);
            }

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("receiveCancelConfirm error!", e);
        }
    }



    /**
     * 设置应收毛利的 毛利状态的方法
     * @param toBeConfirmAmount 返利的未确认额度
     * @param estimatedAmount 返利的应收额度
     * @return
     */
    Byte updateStatus(BigDecimal toBeConfirmAmount, BigDecimal estimatedAmount){
        Byte targetGrossProfitStatus; //保存目标应收状态
        BigDecimal zero = new BigDecimal("0");
        /**
         * 应收毛利为正的状态判定:
         * (a) 若diff小于等于0 表示返利已全部确认
         * (b) 若diff大于0, 且待核销金额和初始预估的核销值一致 则表示从未有过返利核销确认
         * (c) 其他情况认为是 部分核销
         */
        if (toBeConfirmAmount.compareTo(zero) == 0) { // 未收为0,已全部发放
            targetGrossProfitStatus = GrossProfitLedgerConfirmStatus.STATUS_ALL_ISSUE_NOT_USING.getCode();
        }  else if (toBeConfirmAmount.compareTo(estimatedAmount) == 0){ //未收等于应收 从未有过核销确认
            targetGrossProfitStatus =GrossProfitLedgerConfirmStatus.STATUS_NOT_TO_ISSUE.getCode();
        } else { //其他情况为部分确认
            // 当应收为正时,未收 为 非正时 均为全部发放
            // 未收为正且小于 应收 或者 大于应收(即此时确认额度为负数) 则为部分发放
            targetGrossProfitStatus = GrossProfitLedgerConfirmStatus.STATUS_PART_OF_ISSUE.getCode();//部分发放
        }

        return targetGrossProfitStatus;

    }
}
