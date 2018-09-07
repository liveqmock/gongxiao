package com.yhglobal.gongxiao.payment.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.payment.constant.YhGlobalInoutFlowConstant;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.dao.SupplierCouponTransferRecordDao;
import com.yhglobal.gongxiao.payment.flow.service.impl.YhglobalCouponWriteroffServiceImpl;
import com.yhglobal.gongxiao.payment.model.SupplierCouponTransferRecord;
import com.yhglobal.gongxiao.payment.record.SupplierCouponPostingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
@Service
public class SupplierCouponPostingServiceImpl implements SupplierCouponPostingService{

    private static Logger logger = LoggerFactory.getLogger(SupplierCouponPostingServiceImpl.class);

    @Autowired
    SupplierCouponTransferRecordDao supplierCouponTransferRecordDao;

    /**
     * 分页显示流水
     * */
    @Override
    public PageInfo<FrontPageFlow> selectAllBySupplierId(RpcHeader rpcHeader, String currencyCode,
                                                         long supplierId,
                                                         long projectId,
                                                         Integer moneyFlow,
                                                         Date beginDate,
                                                         Date endDate,
                                                         int pageNum, int pageSize) {
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}, pageNum={}, pageSize={}",
                rpcHeader, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);

        PageHelper.startPage(pageNum, pageSize);
        List<SupplierCouponTransferRecord> supplierCouponTransferRecordList =  supplierCouponTransferRecordDao.selectAllBySupplierId(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);

        List<FrontPageFlow> frontPageFlowList = new ArrayList<>();
        for (SupplierCouponTransferRecord record:supplierCouponTransferRecordList) {
            FrontPageFlow frontPageFlow = generateFrontPageFlow(record);
            frontPageFlowList.add(frontPageFlow);
        }
        // 用分页包装
        PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlowList);
        return pageInfo;
    }

    /**
     * 查询流水 用于文件导出
     * */
    @Override
    public List<FrontPageFlow> selectAllBySupplierId(RpcHeader rpcHeader,String currencyCode,
                                                         long supplierId,
                                                         long projectId,
                                                         Integer moneyFlow,
                                                         Date beginDate,
                                                         Date endDate) {
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
        List<SupplierCouponTransferRecord> supplierCouponTransferRecordList =  supplierCouponTransferRecordDao.selectAllBySupplierId(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);

        List<FrontPageFlow> frontPageFlowList = new ArrayList<>();
        for (SupplierCouponTransferRecord record:supplierCouponTransferRecordList) {
            FrontPageFlow frontPageFlow = generateFrontPageFlow(record);
            frontPageFlowList.add(frontPageFlow);
        }
        return frontPageFlowList;
    }
    /**
     * 生成流水包装类
     * */
    FrontPageFlow generateFrontPageFlow(SupplierCouponTransferRecord record){
        FrontPageFlow frontPageFlow = new FrontPageFlow();
        // 前台显示订单号
        frontPageFlow.setFlowNo(record.getRecordId()+"");
        frontPageFlow.setAmountAfterTransaction(record.getAmountAfterTransaction()/ FXConstant.HUNDRED_DOUBLE);
        frontPageFlow.setCreateTime(record.getCreateTime());
        frontPageFlow.setCurrencyCode(record.getCurrencyCode());
        if (record.getTransactionAmount() < 0) {
            frontPageFlow.setTransactionAmount(-record.getTransactionAmount() / FXConstant.HUNDRED_DOUBLE);
        }else {
            frontPageFlow.setTransactionAmount(record.getTransactionAmount() / FXConstant.HUNDRED_DOUBLE);
        }
        frontPageFlow.setCreateByName(record.getCreatedByName());
        frontPageFlow.setType(record.getRecordType());
        return  frontPageFlow;
    }

    /**
     * 生成统计金额的包装类
     * */
    @Override
    public FrontPageFlowSubtotal generateFrontFlowSubtotal(RpcHeader rpcHeader,String currencyCode,
                                                           long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate) {
        logger.info("#traceId={}# [IN][getPrepaidInfoById] params:currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
        FrontPageFlowSubtotal frontPageFlowSubtotal = new FrontPageFlowSubtotal();
        if (moneyFlow == null){
            int incomeCount = supplierCouponTransferRecordDao.selectIncomeCount(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            Long incomeAmount = supplierCouponTransferRecordDao.selectIncomeAmount(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            if (incomeAmount == null) {
                incomeAmount = 0L;
            }
            frontPageFlowSubtotal.setIncomeQuantity(incomeCount);
            frontPageFlowSubtotal.setIncomeAmount(incomeAmount/FXConstant.HUNDRED_DOUBLE);

            int expenditureCount = supplierCouponTransferRecordDao.selectExpenditureCount(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            Long expenditureAmount = supplierCouponTransferRecordDao.selectExpenditureAmount(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            if (expenditureAmount == null) {
                expenditureAmount = 0L;
            }
            frontPageFlowSubtotal.setExpenditureQuantity(expenditureCount);
            frontPageFlowSubtotal.setExpenditureAmount(-expenditureAmount / FXConstant.HUNDRED_DOUBLE);
        }else {
            if (moneyFlow == YhGlobalInoutFlowConstant.FLOW_TYPE_IN.getNum()) {
                int incomeCount = supplierCouponTransferRecordDao.selectIncomeCount(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
                Long incomeAmount = supplierCouponTransferRecordDao.selectIncomeAmount(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
                if (incomeAmount == null) {
                    incomeAmount = 0L;
                }
                frontPageFlowSubtotal.setIncomeQuantity(incomeCount);
                frontPageFlowSubtotal.setIncomeAmount(incomeAmount / FXConstant.HUNDRED_DOUBLE);
                frontPageFlowSubtotal.setExpenditureQuantity(0);
                frontPageFlowSubtotal.setExpenditureAmount(0);
            }
            if (moneyFlow == YhGlobalInoutFlowConstant.FLOW_TYPE_OUT.getNum()) {
                int expenditureCount = supplierCouponTransferRecordDao.selectExpenditureCount(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
                Long expenditureAmount = supplierCouponTransferRecordDao.selectExpenditureAmount(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
                if (expenditureAmount == null) {
                    expenditureAmount = 0L;
                }

                frontPageFlowSubtotal.setExpenditureQuantity(expenditureCount);
                frontPageFlowSubtotal.setExpenditureAmount(-expenditureAmount / FXConstant.HUNDRED_DOUBLE);
                frontPageFlowSubtotal.setIncomeQuantity(0);
                frontPageFlowSubtotal.setIncomeAmount(0);
            }
        }
        return frontPageFlowSubtotal;
    }

}
