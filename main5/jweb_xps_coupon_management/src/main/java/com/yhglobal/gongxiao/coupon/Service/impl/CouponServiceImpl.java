package com.yhglobal.gongxiao.coupon.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;

import com.yhglobal.gongxiao.coupon.Service.CouponService;
import com.yhglobal.gongxiao.coupon.constant.*;
import com.yhglobal.gongxiao.coupon.dao.YhglobalCouponWriteoffFlowDao;
import com.yhglobal.gongxiao.coupon.dao.YhglobalCouponWriteoffRecordDao;
import com.yhglobal.gongxiao.coupon.dao.YhglobalToReceiveCouponLedgerDao;
import com.yhglobal.gongxiao.coupon.model.*;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yhglobal.gongxiao.model.WriteOffReturnObject;

import java.util.*;

/**
 * @author 王帅
 */
@Service
public class CouponServiceImpl implements CouponService{


     static Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Autowired
    YhglobalToReceiveCouponLedgerDao yhglobalToReceiveCouponLedgerDao;//采购返利详情

    @Autowired
    YhglobalCouponWriteoffFlowDao yhglobalCouponWriteoffFlowDao;

    @Autowired
    YhglobalCouponWriteoffRecordDao yhglobalCouponWriteoffRecordDao;

    /**
     * 根据项目ID获取 对应的表的前缀名 所有支持多项目的update select insert delete部分都需要
     * @param projectId
     * @return
     */
    public static String getTablePrefix(Long projectId){
        return TablePrefixUtil.getTablePrefixByProjectId(projectId);
    }

    /**
     * 触发时间: 当采购订单完成时 采购模块主动调用该接口生成应收返利
     * 根据采购单及关联的项目信息自动生成应收返利
     * */
    @Override
    public RpcResult generateYhglobalCouponLedger(RpcHeader rpcHeader,
                                                  Long projectId ,
                                                    String currencyCode,
                                                    String brandOrderNo ,
                                                    String purchaseOrderNo,
                                                    String purchaseTime ,
                                                    Long  purchaseShouldPayAmount ,
                                                    Integer couponType ,
                                                    long couponRatio  ,
                                                    String projectName ,
                                                    long supplierId ,
                                                    String supplierName) {

        logger.info("#traceId={}# [IN][generateYhglobalCouponLedger] params:  projectId={}, currencyCode={}, brandOrderNo={}," +
                        " purchaseOrderNo={}, purchaseTime={}, purchaseShouldPayAmount={}, couponType={}, couponRatio={}, projectName={}," +
                        "  supplierId={}, supplierName={}", projectId, currencyCode, brandOrderNo, purchaseOrderNo,
                purchaseTime, purchaseShouldPayAmount, couponType, couponRatio, projectName,  supplierId, supplierName);
        RpcResult rpcResult = null;
        try{

            //todo 根据项目得表前缀
            String tablePrefix = getTablePrefix(projectId);
            // 返利金额 = 采购总金额 * 返利率
            // 应收返利数据插入
            YhglobalCouponLedger yhglobalCouponLedger = yhglobalToReceiveCouponLedgerDao.getByPurchaseOrderNoAndCouponType(tablePrefix,purchaseOrderNo, couponType.byteValue()); //查出该采购单现有的应收返利

            if (couponRatio > 0 && yhglobalCouponLedger == null) {
                // 利率大于零且该返利不存在则生成返利
                boolean flag = insertOneRecord(couponType.byteValue(), couponRatio, currencyCode, projectId, projectName, tablePrefix,
                        supplierId, supplierName, brandOrderNo, purchaseOrderNo, purchaseTime, purchaseShouldPayAmount);
                if (flag) {
                    rpcResult = new RpcResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());
                }else {
                    rpcResult = new RpcResult(ErrorCode.GENERATE_COUPON_FAIL.getErrorCode(),ErrorCode.GENERATE_COUPON_FAIL.getMessage());
                }
            }else {
                rpcResult = new RpcResult(ErrorCode.TO_RECEIVE_COUPON_LEDGER_EXISTED.getErrorCode(), ErrorCode.TO_RECEIVE_COUPON_LEDGER_EXISTED.getMessage());
            }
        } catch (Exception e) {
            rpcResult = new RpcResult(ErrorCode.GENERATE_COUPON_FAIL.getErrorCode(),ErrorCode.GENERATE_COUPON_FAIL.getMessage());
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
        }
        return rpcResult;
    }



    /**
     *  依据返利类型 返利比例 关联的采购单生成返利对象
     *  @param  couponType     返利类型
     *  @param  couponRatio    返利比率
     * */
    public boolean insertOneRecord(Byte couponType, Long couponRatio, String currencyCode, Long projectId, String projectName, String tablePrefix,
                                long supplierId , String supplierName, String brandOrderNo,String purchaseOrderNo,String purchaseTime,Long purchaseShouldPayAmount ){
        // Byte couponType, Long couponRatio project.getProjectName() project.getSupplierId()  project.getSupplierName()
        if (couponRatio < 0) {
            throw new RuntimeException("generate new yhglobalcouponledger wrong ,the couponRatio < 0");
        }
        try {
            YhglobalCouponLedger yhglobalCouponLedger = new YhglobalCouponLedger(); //目标写入db的对象
            yhglobalCouponLedger.setCouponStatus(CouponLedgerCouponStatus.COUPON_STATUS_NOT_TO_ISSUE.getCode());
            yhglobalCouponLedger.setCouponType(couponType);
            // couponmodel 返利模式 该字段不赋值
            // yhglobalCouponLedger.setCouponModel();
            yhglobalCouponLedger.setCouponRatio(couponRatio);
            yhglobalCouponLedger.setProjectId(projectId);
            yhglobalCouponLedger.setProjectName(projectName);
            yhglobalCouponLedger.setSupplierId(supplierId);
            yhglobalCouponLedger.setSupplierName(supplierName);
            yhglobalCouponLedger.setCurrencyCode(currencyCode);
            // 待定 采购单和项目 缺少对应字段
            yhglobalCouponLedger.setSupplierOrderNo(brandOrderNo); // 品牌商订单号
            yhglobalCouponLedger.setPurchaseOrderNo(purchaseOrderNo);
            yhglobalCouponLedger.setPurchaseTime(DateUtil.stringToDate(purchaseTime, DateUtil.DATE_FORMAT));
            long estimateCouponAmount =  Math.round(purchaseShouldPayAmount * couponRatio / FXConstant.MILLION_DOUBLE );
            yhglobalCouponLedger.setEstimatedCouponAmount(estimateCouponAmount);

            yhglobalCouponLedger.setToBeConfirmAmount(estimateCouponAmount);
            yhglobalCouponLedger.setConfirmedCouponAmount(0L);
            yhglobalCouponLedger.setReceivedCouponAmount(0L);
            yhglobalCouponLedger.setDataVersion(0L);
            yhglobalCouponLedger.setLastUpdateTime(new Date());
            yhglobalCouponLedger.setLastUpdateTime(new Date());
            // 预估入账时间  该字段舍弃
            // yhglobalCouponLedger.setEstimatedPostingDate();
            yhglobalCouponLedger.setCreateTime(DateUtil.stringToDate(DateUtil.getCurrentDateTime()));
            // 插入该应收返利数据
            yhglobalCouponLedger.setTablePrefix(tablePrefix);
            yhglobalToReceiveCouponLedgerDao.insert(yhglobalCouponLedger);
            return true;
        }catch (Exception e){
            logger.error("# [OUT] errorMessage: " + e.getMessage(), e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * " 收付款管理 > 返利确认处理 > 返利确认列表" 页面 点击"确认"按钮的业务处理入口
     *
     *  备注 :应收返利有已过期状态 目前获取的 预计到账时间 字段为空
     *  返利核销功能需要判断返利是否过期(在前端选择返利时也要过滤)
     */
    @Override
    public RpcResult yhCouponWriteroff( GongxiaoRpc.RpcHeader rpcHeader, Long projectId,
                                         String useDate ,
                                         Integer accountType,
                                         String confirmInfoJson ,
                                         String projectName,
                                         String philipDocumentNo, String flowCode, List<WriteOffReturnObject> list) {
        logger.info("#traceId={}# [IN][receiveConfirm] params:projectId={}, projectName={}, useDate={},  accountType={},  confirmInfo={}",
                rpcHeader.getTraceId(), projectId, projectName, useDate, accountType, confirmInfoJson);
        RpcResult rpcResult = null;
        try {

            Date now = new Date();
            //根据项目得表前缀
            String tablePrefix = getTablePrefix(projectId);
            // 解析前端封装的json格式数据
            List<YhglobalCouponLedger> receiveList
                    = JSON.parseObject(confirmInfoJson, new TypeReference<List<YhglobalCouponLedger>>() {
            });
//            long totalToConfirmAmount = 0;  //待确认的返利总和
//            for (YhglobalCouponLedger receive : receiveList) { // 统计核销确认的总额度
//                totalToConfirmAmount += Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED);
//            }
            //查询帐户余额 根据账户类型选择不同的账户
            Long accountTotalAmount = 0L;
            CouponConfirmAccountType couponConfirmAccountType = CouponConfirmAccountType.getCouponConfirmAccountTypeByCode(accountType);

            // 获得货币编码
            String currencyCode = "";
            //Integer i = 0; // 成功条数
            //Integer length = receiveList.size();// 总条数
            Integer failNo = 0;
            // 生成流水号 给流水,记录以及 调用账户接口时使用
            //String flowCode = DateTimeIdGenerator.nextId(tablePrefix, BizNumberType.YHGLOBAL_COUPON_WRITEROFF_FLOW);
            for (int j = 0; j < receiveList.size(); j++) { // 根据选择的多条核销数据进行逐条账户扣减
                WriteOffReturnObject writeOffReturnObject = list.get(j);
                if (writeOffReturnObject.getReturnCode() != 0 || writeOffReturnObject == null){
                    continue; // 当条核销数据 交易失败或返回数据为空时 跳出该条数据  执行下一条
                }
                YhglobalCouponLedger receive = receiveList.get(j);
                Long transferAmount = Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED); // 每笔核销扣减的额度
               // failNo = length - i;
//                switch (couponConfirmAccountType) { //根据确认的账户类型加载对应的账户余额
//                    case COUPON_RECEIVED_ACCOUNT:
//                        //todo 账户额度查询使用grpc调用葛灿接口
//                        YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest rpcRequest = YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest.newBuilder()
//                                .setRpcHeader(rpcHeader)
//                                .setProjectId(projectId)
//                                .build();
//                        YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub yhglobalAccountService = RpcStubStore.getRpcStub(YhglobalAccountServiceGrpc.YhglobalAccountServiceBlockingStub.class);
//                        PaymentCommonGrpc.AccountAmountResponse rpcResponse = yhglobalAccountService.getYhglobalReceivedAccountAmount(rpcRequest);
//                        if (rpcResponse.getCouponAmount() < transferAmount) {
//                            // 账户额度不足
//                            logger.error("accountTotalAmount insufficient: philipDocumentNo={}", philipDocumentNo, receive.getPurchaseCouponLedgerId());
//                            return new RpcResult(ErrorCode.YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), "账户额度不足,共选择了"+length+"条返利,已成功核销"+i+"条, 核销失败"+failNo+"条");
//                        }
//                        break;
//                    case SALES_DIFFERENCE_ACCOUNT:
//                        //todo 账户额度查询使用grpc调用葛灿接口
//                        SupplierAccountServiceStructure.GetSellHighAccountRequest rpcRequest1 = SupplierAccountServiceStructure.GetSellHighAccountRequest.newBuilder()
//                                .setRpcHeader(rpcHeader)
//                                .setProjectId(projectId)
//                                .build();
//                        SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
//                        SupplierAccountServiceStructure.GetSellHighAccountResponse rpcResponse1 = rpcStub.getSellHighAccount(rpcRequest1);
//                        if (rpcResponse1.getTotalAmount() < transferAmount) {
//                            // 差价账户余额不足
//                            logger.error("accountTotalAmount insufficient: philipDocumentNo={}", philipDocumentNo, receive.getPurchaseCouponLedgerId());
//                            return new RpcResult(ErrorCode.SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode(), "账户额度不足,共选择了"+length+"条返利,已成功核销"+i+"条, 核销失败"+failNo+"条");
//                        }
//                        break;
//                    default:
//                        throw new IllegalArgumentException("unknown couponConfirmAccountType: " + couponConfirmAccountType.getAccountName());
//                }


                Long transactionAmount = -transferAmount;//交易金额
                // 调用根据账户类型修改账户额度的方法  有返回值


                //返利模块只是调用扣多少款 由支付模块返回交易前后的余额,
                Long amountBeforeTransaction = writeOffReturnObject.getAmountBeforeTrans();//流水发生前的余额
                Long amountAfterTransaction = writeOffReturnObject.getAmountAfterTrans();//流水发生后的余额
                //查询应收记录
                YhglobalCouponLedger receiveInfo = yhglobalToReceiveCouponLedgerDao.selectCouponLedgerForWriteoff(tablePrefix, receive.getPurchaseCouponLedgerId());
                if ("".equals(currencyCode)) {
                    currencyCode = receiveInfo.getCurrencyCode();
                }
                //写一条核销记录
                YhglobalCouponWriteoffRecord record = new YhglobalCouponWriteoffRecord();
                record.setToReceiveCouponId(receive.getPurchaseCouponLedgerId());
                record.setConfirmAmount(Math.round(receive.getConfirmAmountDouble() * FXConstant.HUNDRED));
                record.setReceiptAmount(Math.round(receive.getReceiptAmountDouble() * FXConstant.HUNDRED));
                record.setConfirmCurrencyCode(currencyCode);
                record.setReceivedCurrencyCode(currencyCode);
                record.setFlowNo(flowCode);
                record.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                record.setDataVersion(0L);
                record.setCreatedByName(rpcHeader.getUsername());
                record.setCreateTime(now);
                record.setAccountType(couponConfirmAccountType.getCode());
                record.setProjectId(projectId);
                record.setUseDate(DateUtil.stringToDate(useDate));
                // 设置飞利浦单据号
                record.setPhilipDocumentNo(philipDocumentNo);
                record.setReceivedCurrencyCode(currencyCode);
                record.setIsRollback(RollbackStatus.ROLLBACK_STATUS_NOES.getCode());
                //保存核销记录
                // yhglobalPrepaidLedgerWriteoffRecordDao.addWriteoffRecord(record);
                record.setTablePrefix(tablePrefix);
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
                yhglobalToReceiveCouponLedgerDao.updateCouponLedgerForWriteoff(tablePrefix, receiveInfo.getPurchaseCouponLedgerId(),
                        confirmAmount, receiptAmount, diff, targetCouponStatus, receiveInfo.getDataVersion(), appendTraceLog, new Date());

                //记录流水  供应商及经销商信息为预留字段 目前不用存储
                try {
                    addWriteoffFlow(tablePrefix, rpcHeader, flowCode, couponConfirmAccountType, CouponWriteoffFlowType.CONFIRM_COUPON_WRITEOFF.getCode(),
                            projectId, projectName, currencyCode,
                            amountBeforeTransaction,
                            transactionAmount,
                            amountAfterTransaction,
                            now,
                            null,
                            "",
                            "",
                            null,
                            null,
                            "");
                } catch (Exception ex) {
                    logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + ex.getMessage(), ex);
                }

                //i++; // 统计核销成功的条数
            }
            //构建repcresult 返回给controller
            rpcResult = new RpcResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            rpcResult = new RpcResult(ErrorCode.COUPON_CONFIRM_FAIL.getErrorCode(), ErrorCode.COUPON_CONFIRM_FAIL.getMessage());
        }

        return rpcResult;

    }

    /**
     * 生成流水
     */
    public void addWriteoffFlow(String tablePrefix, GongxiaoRpc.RpcHeader rpcHeader,
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
                rpcHeader.getTraceId(), projectId, projectName, amountBeforeTransaction, transactionAmount, amountAfterTransaction, transactionTime, supplierId, supplierName, salesOrderId, distributorId, distributorName, differenceAmountAdjustPattern);
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

            flow.setTablePrefix(tablePrefix);
            int count = yhglobalCouponWriteoffFlowDao.insert( flow);
            if (count != 1) {
                logger.error("#traceId={}# [OUT]: insert transportation writeroff flow fail flowCode={}", rpcHeader.getTraceId(), flowCode);
            }
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("add transportation writeroff flow error!", e);
        }

    }

    /**
     * 根据选中的返利主键查询返利详情 以便加载"收付款管理 > 返利确认处理 > 返利确认列表"页面时填写默认值
     */
    @Override
    public List<YhglobalCouponLedger> selectYhglobalCouponLedgerByPurchaseCouponLedgerId(Long projectId, List<String> idList) {

        logger.info("#traceId={}# [IN][selectYhglobalCouponLedgerByPurchaseCouponLedgerId] params: ids={} ",idList.toString());
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
            List<YhglobalCouponLedger> yhglobalCouponLedgerList = yhglobalToReceiveCouponLedgerDao.searchByIds(tablePrefix, sb.toString());
            // 根据选中的采购返利ID 查询出对应的返利详情
            for (YhglobalCouponLedger yhglobalCouponLedger : yhglobalCouponLedgerList) {
                //Long id = Long.valueOf(idStr);
                //YhglobalCouponLedger yhglobalCouponLedger = yhglobalToReceiveCouponLedgerDao.selectByPrimaryKey(id);
                yhglobalCouponLedger.setConfirmAmountDouble(yhglobalCouponLedger.getToBeConfirmAmount() != null ? yhglobalCouponLedger.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
                yhglobalCouponLedger.setReceiptAmountDouble(yhglobalCouponLedger.getToBeConfirmAmount() != null ? yhglobalCouponLedger.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
                yhglobalCouponLedger.setEstimatedAmountDouble(yhglobalCouponLedger.getEstimatedCouponAmount() != null ? yhglobalCouponLedger.getEstimatedCouponAmount() / FXConstant.HUNDRED_DOUBLE : null);
                yhglobalCouponLedger.setToBeConfirmAmountDouble(yhglobalCouponLedger.getToBeConfirmAmount() != null ? yhglobalCouponLedger.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE : null);
                // 返利状态
                CouponLedgerCouponStatus couponStatus = CouponLedgerCouponStatus.getCouponLedgerCouponStatusByCode(yhglobalCouponLedger.getCouponStatus());
                yhglobalCouponLedger.setCouponStatusString(couponStatus.getMeaning());
                // 返利类型
                CouponLedgerCouponType couponTypeByCode = CouponLedgerCouponType.getCouponLedgerCouponTypeByCode(yhglobalCouponLedger.getCouponType());
                yhglobalCouponLedger.setCouponTypeString(couponTypeByCode.getMeaning());
                // 从枚举中获取常量赋值给CouponStatusString字段
//                CouponLedgerCouponStatus couponLedgerCouponStatus = CouponLedgerCouponStatus.getCouponLedgerCouponStatusByCode(yhglobalCouponLedger.getCouponType());
//                yhglobalCouponLedger.setCouponStatusString(couponLedgerCouponStatus.getMeaning());
//                CouponConfirmAccountType couponConfirmAccountType = CouponConfirmAccountType.getCouponConfirmAccountTypeByCode(yhglobalCouponLedger.getAccountType());
//                yhglobalCouponLedger.setAccountTypeString(couponConfirmAccountType.getAccountName());

            }
            // rpcResult = new RpcResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage(),yhglobalCouponLedgerList);
            return yhglobalCouponLedgerList;
        }catch (Exception e){
            logger.error("# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectYhglobalCouponLedgerByPurchaseCouponLedgerId fail");
        }

    }

    /**
     * 返利台账流水查询
     */
    @Override
    public PageInfo<YhglobalCouponWriteoffRecord> searchCouponConfirm(RpcHeader rpcHeader,
                                                                      Long projectId,
                                                                      String flowNo,
                                                                      Integer accountType,
                                                                      Date useDateStart,
                                                                      Date useDateEnd,
                                                                      Date dateStart,
                                                                      Date dateEnd,
                                                                      Integer pageNumber,
                                                                      Integer pageSize) {

        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, flowCode={}, accountType={}, useDateStart={}, useDateEnd={}, dateStart={}, dateEnd={}", rpcHeader, projectId, flowNo, accountType, useDateStart, useDateEnd, dateStart, dateEnd);

        try {
            //todo 根据项目得表前缀
            String tablePrefix = getTablePrefix(projectId);

            PageInfo<YhglobalCouponWriteoffRecord> resultPage;
            PageHelper.startPage(pageNumber, pageSize);
            List<YhglobalCouponWriteoffRecord> recordList = yhglobalCouponWriteoffRecordDao.searchCouponConfirm(tablePrefix, projectId, flowNo, accountType, useDateStart, useDateEnd, dateStart, dateEnd);
            for (YhglobalCouponWriteoffRecord record : recordList) {
                record.setConfirmAmountDouble(record.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                record.setReceiptAmountDouble(record.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE);
                record.setDifferenceAmountDouble(record.getConfirmAmountDouble() - record.getReceiptAmountDouble());

                // 交易账户名称赋值
                CouponConfirmAccountType couponConfirmAccountType = CouponConfirmAccountType.getCouponConfirmAccountTypeByCode(record.getAccountType());
                record.setAccountTypeName(couponConfirmAccountType.getAccountName());
            }
            resultPage = new PageInfo<>(recordList);
            return resultPage;

        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("searchCouponConfirm fail");
        }

    }

    /**
     * 条件查询返利
     * */
    @Override
    public PageInfo<YhglobalCouponLedgerItem> selectByManyConditionsHasPage(RpcHeader rpcHeader, Long projectId, String purchaseOrderNo,
                                                                     String supplierOrderNo, Date dateS, Date dateE, String couponStatus,String couponTypes,
                                                                     String flowNo, Integer pageNumber,
                                                                     Integer pageSize){

        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, flowCode={}, accountType={}, useDateStart={}," +
                        " useDateEnd={}, dateStart={}, dateEnd={}", rpcHeader, projectId, purchaseOrderNo, supplierOrderNo, dateS, dateE,
                couponStatus, flowNo, pageNumber, pageSize);

        //todo 根据项目得表前缀
        String tablePrefix = getTablePrefix(projectId);
        try {
            PageHelper.startPage(pageNumber, pageSize);
            // 条件查询
            List<YhglobalCouponLedgerItem> yhglobalCouponLedgerItemList = yhglobalToReceiveCouponLedgerDao.searchByManyCondition(tablePrefix, projectId, purchaseOrderNo, supplierOrderNo
                    , flowNo, dateS, dateE, couponStatus, couponTypes);
            // 填充部分前端显示的字段
            for (YhglobalCouponLedgerItem yhglobalCouponLedgerItem : yhglobalCouponLedgerItemList) {

                // 从枚举中获取常量赋值给CouponStatusString字段
                CouponLedgerCouponStatus couponLedgerCouponStatus = CouponLedgerCouponStatus.getCouponLedgerCouponStatusByCode(yhglobalCouponLedgerItem.getCouponStatus());
                yhglobalCouponLedgerItem.setCouponStatusString(couponLedgerCouponStatus.getMeaning());
                // 返利类型字符串
                CouponLedgerCouponType couponLedgerCouponType = CouponLedgerCouponType.getCouponLedgerCouponTypeByCode(yhglobalCouponLedgerItem.getCouponType());
                yhglobalCouponLedgerItem.setCouponTypeString(couponLedgerCouponType.getMeaning());
                // 账户类型字符串
                if (yhglobalCouponLedgerItem.getAccountType() != null) {
                    CouponConfirmAccountType couponConfirmAccountType = CouponConfirmAccountType.getCouponConfirmAccountTypeByCode(yhglobalCouponLedgerItem.getAccountType());
                    yhglobalCouponLedgerItem.setAccountTypeString(couponConfirmAccountType.getAccountName());
                }
                yhglobalCouponLedgerItem.setEstimatedCouponAmountDouble(yhglobalCouponLedgerItem.getEstimatedCouponAmount() == null ? 0 : yhglobalCouponLedgerItem.getEstimatedCouponAmount() / FXConstant.HUNDRED_DOUBLE);
                yhglobalCouponLedgerItem.setToBeConfirmAmountDouble(yhglobalCouponLedgerItem.getToBeConfirmAmount() == null ? 0 : yhglobalCouponLedgerItem.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                if (yhglobalCouponLedgerItem.getConfirmAmount() != null) {
                    yhglobalCouponLedgerItem.setConfirmAmountDouble(yhglobalCouponLedgerItem.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                }
                if (yhglobalCouponLedgerItem.getReceiptAmount() != null) {
                    yhglobalCouponLedgerItem.setReceiptAmountDouble(yhglobalCouponLedgerItem.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE);
                }
            }

            PageInfo<YhglobalCouponLedgerItem> resultPage  = new PageInfo<>(yhglobalCouponLedgerItemList);
            return resultPage;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectCouponByManyCondiitons fail");
        }

    }


    /**
     * 条件查询返利 导出数据
     * */
    @Override
    public List<YhglobalCouponLedgerItem> selectByManyConditionsNoPage(RpcHeader rpcHeader, Long projectId, String purchaseOrderNo,
                                                                 String supplierOrderNo, Date dateS, Date dateE, String couponStatus,String couponTypes,
                                                                 String flowNo){

        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:projectId={}, flowCode={}, accountType={}, useDateStart={}," +
                        " useDateEnd={}, dateStart={}, dateEnd={}", rpcHeader, projectId, purchaseOrderNo, supplierOrderNo, dateS, dateE,
                couponStatus, flowNo);
        List<YhglobalCouponLedgerItem> yhglobalCouponLedgerItemList;
        //todo 根据项目得表前缀
        String tablePrefix = getTablePrefix(projectId);
        try {
            // 条件查询
            yhglobalCouponLedgerItemList = yhglobalToReceiveCouponLedgerDao.searchByManyCondition(tablePrefix, projectId, purchaseOrderNo, supplierOrderNo
                    , flowNo, dateS, dateE, couponStatus, couponTypes);
            //填充部分前端显示的字段
            for (YhglobalCouponLedgerItem yhglobalCouponLedgerItem : yhglobalCouponLedgerItemList) {
                // 从枚举中获取常量赋值给CouponStatusString字段
                CouponLedgerCouponStatus couponLedgerCouponStatus = CouponLedgerCouponStatus.getCouponLedgerCouponStatusByCode(yhglobalCouponLedgerItem.getCouponType());
                yhglobalCouponLedgerItem.setCouponStatusString(couponLedgerCouponStatus.getMeaning());
                // 返利类型字符串
                yhglobalCouponLedgerItem.setCouponTypeString(couponLedgerCouponStatus.getMeaning());
                // 账户类型字符串
                CouponConfirmAccountType couponConfirmAccountType = CouponConfirmAccountType.getCouponConfirmAccountTypeByCode(yhglobalCouponLedgerItem.getAccountType());
                yhglobalCouponLedgerItem.setAccountTypeString(couponConfirmAccountType.getAccountName());

                yhglobalCouponLedgerItem.setEstimatedCouponAmountDouble(yhglobalCouponLedgerItem.getEstimatedCouponAmount() == null ? 0 : yhglobalCouponLedgerItem.getEstimatedCouponAmount() / FXConstant.HUNDRED_DOUBLE);
                yhglobalCouponLedgerItem.setToBeConfirmAmountDouble(yhglobalCouponLedgerItem.getToBeConfirmAmount() == null ? 0 : yhglobalCouponLedgerItem.getToBeConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                if (yhglobalCouponLedgerItem.getConfirmAmount() != null) {
                    yhglobalCouponLedgerItem.setConfirmAmountDouble(yhglobalCouponLedgerItem.getConfirmAmount() / FXConstant.HUNDRED_DOUBLE);
                }
                if (yhglobalCouponLedgerItem.getReceiptAmount() != null) {
                    yhglobalCouponLedgerItem.setReceiptAmountDouble(yhglobalCouponLedgerItem.getReceiptAmount() / FXConstant.HUNDRED_DOUBLE);
                }

                yhglobalCouponLedgerItem.setEstimatedCouponAmountStr(yhglobalCouponLedgerItem.getEstimatedCouponAmountStr());
                yhglobalCouponLedgerItem.setToBeConfirmAmountStr(yhglobalCouponLedgerItem.getToBeConfirmAmountStr());
                yhglobalCouponLedgerItem.setReceiptAmountStr(yhglobalCouponLedgerItem.getReceiptAmountStr());
                yhglobalCouponLedgerItem.setConfirmAmountStr(yhglobalCouponLedgerItem.getConfirmAmountStr());
            }
            return yhglobalCouponLedgerItemList;

        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("selectByManyCondiitons fail");
        }


    }

    /**
     * 实现返利确认的取消功能
     * @param flowCode 台账界面显示的流水号  查询核销记录用
     * @param flow 修改账户额度时产生的流水号  插入取消确认对应的流水用
     * */
    @Override
    public RpcResult receiveCancelConfirmBatch(GongxiaoRpc.RpcHeader rpcHeader,Long projectId, String flowCode, WriteOffReturnObject object,
                                               String flow) {

        //List<String> flowCodeList = Arrays.asList(flowCodes.split(","));
        //logger.info("#traceId={}# [IN][receiveCancelConfirmBatch] params:flowCodeList={}", rpcHeader, flowCodeList.toString());
        //todo 根据项目得表前缀
        String tablePrefix = getTablePrefix(projectId);
        //List<Map<Integer, Long>> list = new ArrayList<>();
        //for (String flowCode : flowCodeList) {
            try {
                receiveCancelConfirm(tablePrefix, rpcHeader, flowCode, object, flow);

            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
                return new RpcResult(ErrorCode.COUPON_CANCEL_CONFIRM_FAIL.getErrorCode(), ErrorCode.COUPON_CANCEL_CONFIRM_FAIL.getMessage());
            }
        //}

        return new RpcResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());
    }

    /**
     * 对每个台账对象进行取消确认
     * */
     void receiveCancelConfirm(String tablePrefix, GongxiaoRpc.RpcHeader rpcHeader, String flowCode, WriteOffReturnObject object,
                               String flow) {
        logger.info("#traceId={}# [IN][receiveCancelConfirm] params:flowCode={}", rpcHeader, flowCode);
        try {
            Long transactionAmount = 0L; //累加待取消的返利确认总额
            Long projectId = 0L;
            String  projectName = "";
            Long supplierId = 0L;
            Integer accountType = 0;
            String currencyCode = "";
            // 按流水号查确认列表 根据确认记录 修改每条应收返利
            List<YhglobalCouponWriteoffRecord> yhglobalCouponWriteoffRecordList = yhglobalCouponWriteoffRecordDao.selectByFlowCode(tablePrefix,flowCode);
            for (YhglobalCouponWriteoffRecord record : yhglobalCouponWriteoffRecordList) {
                // 按应收ID查应收记录
                YhglobalCouponLedger receive = yhglobalToReceiveCouponLedgerDao.selectByPrimaryKey(tablePrefix,record.getToReceiveCouponId());
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
                transactionAmount += record.getReceiptAmount(); //合计金额累加 核销记录
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
                yhglobalToReceiveCouponLedgerDao.updateCouponLedgerForWriteoff(tablePrefix,receive.getPurchaseCouponLedgerId(),receive.getConfirmedCouponAmount(),
                        receive.getReceivedCouponAmount(),receive.getToBeConfirmAmount(),targetCouponStatus,receive.getDataVersion(),appendTraceLog, new Date());
                // 修改确认记录为已回滚
                // record.setIsRollback(1);
                yhglobalCouponWriteoffRecordDao.updateRollbackStatus(tablePrefix, RollbackStatus.ROLLBACK_STATUS_YES.getCode(),record.getConfirmId());
            }
            CouponConfirmAccountType couponConfirmAccountType = CouponConfirmAccountType.getCouponConfirmAccountTypeByCode(accountType);
            Date now = new Date();  // 该流水生成时间 也在调用账户中使用
            // 生成流水号
            //String flow = DateTimeIdGenerator.nextId(tablePrefix, BizNumberType.YHGLOBAL_COUPON_WRITEROFF_FLOW);

            // 调用根据账户类型修改账户额度的方法
//            WriteOffReturnObject object = updateAccount(rpcHeader, projectId, transactionAmount, now, flow, couponConfirmAccountType);

            // 流水中需要的账户额度从 修改账户 的接口返回值中获取
            Long accountTotalAmount = 0l;
            Long amountBeforeTransaction = object.getAmountBeforeTrans();//流水发生前的余额
            Long amountAfterTransaction = object.getAmountAfterTrans();//流水发生后的余额

            //生成应收确认对冲流水
            try {
                //按帐户类型查询帐户余额
                addWriteoffFlow(tablePrefix,rpcHeader, flow,couponConfirmAccountType, CouponWriteoffFlowType.CANCEL_COUPON_WRITEOFF.getCode(),
                        projectId, projectName,currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, now,
                        new Long(supplierId).intValue(), "", "", null,
                        null, "");
            } catch (Exception ex) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + ex.getMessage(), ex);
            }

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("receiveCancelConfirm error!", e);
        }
    }



    /**
     * 返利确认和取消确认都要调用的 设置应收返利的 返利状态的方法
     * @param toBeConfirmAmount 返利的未确认额度
     * @param estimatedCouponAmount 返利的应收额度
     * @return
     */
     Byte updateCouponStatus(Long toBeConfirmAmount, Long estimatedCouponAmount){ //TODO 拆成具体的业务参数
        Byte targetCouponStatus = 0; //保存目标应收状态
//        if (estimatedCouponAmount > 0) { //应收返利为正 比如:正常采购
//            /**
//             * 应收返利为正的状态判定:
//             * (a) 若diff小于等于0 表示返利已全部确认
//             * (b) 若diff大于0, 且待核销金额和初始预估的核销值一致 则表示从未有过返利核销确认
//             * (c) 其他情况认为是 部分核销
//             */
            if (toBeConfirmAmount == 0) { //全部确认
                targetCouponStatus = CouponLedgerCouponStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getCode();
            }  else if (toBeConfirmAmount.longValue() == estimatedCouponAmount.longValue()){ //从未有过核销确认
                targetCouponStatus = CouponLedgerCouponStatus.COUPON_STATUS_NOT_TO_ISSUE.getCode();
            } else { //其他情况为部分确认
                targetCouponStatus = CouponLedgerCouponStatus.COUPON_STATUS_PART_OF_ISSUE.getCode();//部分发放
            }
//        } else { //应收返利为负: 比如采购退货
//            /**
//             * 应收返利为负的状态判定:
//             * (a) 若diff大于等于0 表示返利已全部确认
//             * (b) 若diff小于0, 且待核销金额和初始预估的核销值一致 则表示从未有过返利核销确认
//             * (c) 其他情况认为是 部分核销
//             */
//            if (toBeConfirmAmount >= 0) { //应收已全部发放
//                targetCouponStatus = CouponLedgerCouponStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getCode();
//            } else if (toBeConfirmAmount.longValue() == estimatedCouponAmount.longValue()){ //从未有过返利核销确认
//                targetCouponStatus = CouponLedgerCouponStatus.COUPON_STATUS_NOT_TO_ISSUE.getCode();
//            } else { //其他情况为部分确认
//                targetCouponStatus = CouponLedgerCouponStatus.COUPON_STATUS_PART_OF_ISSUE.getCode();//部分发放
//            }
//        }

        return targetCouponStatus;

    }




}
