package com.yhglobal.gongxiao.payment.flow.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.dao.DistributorCouponFlowDao;
import com.yhglobal.gongxiao.payment.flow.service.DistributorCouponTransferService;
import com.yhglobal.gongxiao.payment.model.DistributorCouponAccount;
import com.yhglobal.gongxiao.payment.model.DistributorCouponFlow;
import com.yhglobal.gongxiao.payment.model.SupplierCouponTransferRecord;
import com.yhglobal.gongxiao.payment.service.DistributorAccountService;
import com.yhglobal.gongxiao.payment.service.DistributorCouponAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToDistributorService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponTransferToDistributorFlowService;
import com.yhglobal.gongxiao.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * AD返利到账的接口实现类
 *
 * @author 王帅
 */
@Service
public class
DistributorCouponTransferServiceImpl implements DistributorCouponTransferService {

    private static Logger logger = LoggerFactory.getLogger(DistributorCouponTransferServiceImpl.class);
    @Autowired
    DistributorCouponAccountService distributorCouponAccountService;

    @Autowired
    SupplierCouponTransferToDistributorFlowService supplierCouponTransferToDistributorFlowService;

    @Autowired
    SupplierCouponBufferToDistributorService supplierCouponBufferToDistributorService;

    @Autowired
    DistributorCouponFlowDao distributorCouponFlowDao;

    @Autowired
    DistributorAccountService distributorAccountService;


    /**
     * AD返利转入的实现  葛灿封装了扣减 AD账户增加 流水生成的整个过程
     */
    @Override
    public RpcResult singleDistributorCouponTransferReceived(RpcHeader rpcHeader, long distributorId, long receivedAmount, String currencyCode,
                                                          long supplierId, String supplierName, long projectId, String distributorName, String remark) {
        logger.info("#traceId={}# [IN][updateRecipientInfo] params:  distributorId={}, receivedAmount={}, currencyCode={}," +
                        "supplierId={}, supplierName={}, projectId={}, distributorName={}, remark={} ",
                rpcHeader.traceId, distributorId,receivedAmount,currencyCode,supplierId,supplierName,projectId,distributorName,remark);
        RpcResult rpcResult=null;
        try {
            Date receivedDate = new Date();
            rpcResult = distributorAccountService.depositCouponReceived(rpcHeader, currencyCode, distributorId, distributorName,
                    projectId, receivedAmount, receivedDate, remark);
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("distributor coupon transfer error", e);
        }
       return rpcResult;

    }

    /**
     * 查询流水
     * */
    @Override
    public PageInfo<FrontPageFlow> selectCouponFlow(RpcHeader rpcHeader,String currencyCode, long distributorId, long projectId,
                                                    Integer moneyFlow, Date beginDate, Date endDate, int pageNum, int pageSize) {

        logger.info("#traceId={}# [IN][updateRecipientInfo] params:  distributorId={}, receivedAmount={}, currencyCode={}," +
                        "supplierId={}, supplierName={}, projectId={}, distributorName={}, remark={} ",
                rpcHeader.traceId, currencyCode,distributorId,projectId,moneyFlow,beginDate,endDate,pageNum,pageSize);

        PageHelper.startPage(pageNum, pageSize);
        PageInfo<FrontPageFlow> pageInfo = null;
        try {
            List<DistributorCouponFlow> distributorCouponFlowList = distributorCouponFlowDao.selectAllBydistributorId(currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
            List<FrontPageFlow> frontPageFlowList = new ArrayList<>();
            for (DistributorCouponFlow record : distributorCouponFlowList) {
                FrontPageFlow frontPageFlow = generateFrontPageFlow(rpcHeader,record);
                frontPageFlowList.add(frontPageFlow);
            }
            // 用分页包装
            pageInfo = new PageInfo<>(frontPageFlowList);
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("query distributor coupon flow error", e);
        }
        return pageInfo;
    }

    /**
     * 查询流水 导出
     * */
    @Override
    public List<FrontPageFlow> selectCouponFlow(RpcHeader rpcHeader,String currencyCode, long distributorId, long projectId,
                                                    Integer moneyFlow, Date beginDate, Date endDate) {

        logger.info("#traceId={}# [IN][updateRecipientInfo] params:  distributorId={}, receivedAmount={}, currencyCode={}," +
                        "supplierId={}, supplierName={}, projectId={}, distributorName={}, remark={} ",
                rpcHeader.traceId, currencyCode,distributorId,projectId,moneyFlow,beginDate,endDate);
        List<FrontPageFlow> frontPageFlowList = new ArrayList<>();
        try {
            List<DistributorCouponFlow>  distributorCouponFlowList = distributorCouponFlowDao.selectAllBydistributorId(currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);

            for (DistributorCouponFlow record : distributorCouponFlowList) {
                FrontPageFlow frontPageFlow = generateFrontPageFlow(rpcHeader,record);
                frontPageFlowList.add(frontPageFlow);
            }

        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("query distributor coupon flow error", e);
        }
        return frontPageFlowList;
    }
    /**
     * 生成流水包装类
     */
    FrontPageFlow generateFrontPageFlow(RpcHeader rpcHeader,DistributorCouponFlow record) {
        FrontPageFlow frontPageFlow = new FrontPageFlow();
        logger.info("#traceId={}# [IN][updateRecipientInfo] params:  distributorId={}, receivedAmount={}, currencyCode={}," +
                        "supplierId={}, supplierName={}, projectId={}, distributorName={}, remark={} ",
                rpcHeader.traceId,record.toString());
        try {
            // 前台显示订单号
            frontPageFlow.setFlowNo(record.getFlowNo());
            frontPageFlow.setAmountAfterTransaction(record.getAmountAfterTransaction() / FXConstant.HUNDRED_DOUBLE);
            frontPageFlow.setCreateTime(record.getCreateTime());
            frontPageFlow.setCurrencyCode(record.getCurrencyCode());
            if (record.getTransactionAmount() < 0) {
                frontPageFlow.setTransactionAmount(-record.getTransactionAmount() / FXConstant.HUNDRED_DOUBLE);
            } else {
                frontPageFlow.setTransactionAmount(record.getTransactionAmount() / FXConstant.HUNDRED_DOUBLE);
            }
            frontPageFlow.setType(record.getFlowType());
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("generate frontpageflow error", e);
        }
        frontPageFlow.setCreateByName(record.getCreateByName());
        return frontPageFlow;
    }


    /**
     * 统计金额的对象数据包装
     * */
    @Override
    public FrontPageFlowSubtotal getSubtotal(RpcHeader rpcHeader,String currencyCode, long supplierId, long projectId,
                                             int accountType, Integer moneyFlow, Date beginDate, Date endDate) {
        logger.info("#traceId={}# [IN][updateRecipientInfo] params: currencyCode={}," +
                        "supplierId={}, projectId={}, accountType={}, moneyFlow={}, beginDate={}, endDate={} ",
                rpcHeader.traceId, currencyCode,supplierId,projectId,accountType,moneyFlow,beginDate,endDate);

        FrontPageFlowSubtotal frontPageFlowSubtotal = new FrontPageFlowSubtotal();
        try {
            Integer incomeCount = distributorCouponFlowDao.selectIncomeCount(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            Long incomeAmount = distributorCouponFlowDao.selectIncomeAmount(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            if (incomeAmount == null) {
                incomeAmount = 0L;
            }
            Integer expenditureCount = distributorCouponFlowDao.selectExpenditureCount(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            Long expenditureAmount = distributorCouponFlowDao.selectExpenditureAmount(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            if (expenditureAmount == null) {
                expenditureAmount = 0L;
            }
            frontPageFlowSubtotal.setIncomeQuantity(incomeCount);
            frontPageFlowSubtotal.setIncomeAmount(incomeAmount / FXConstant.HUNDRED_DOUBLE);
            frontPageFlowSubtotal.setExpenditureQuantity(expenditureCount);
            frontPageFlowSubtotal.setExpenditureAmount(-expenditureAmount / FXConstant.HUNDRED_DOUBLE);
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("get account amount subtotal error", e);
        }
        return frontPageFlowSubtotal;
    }
}
