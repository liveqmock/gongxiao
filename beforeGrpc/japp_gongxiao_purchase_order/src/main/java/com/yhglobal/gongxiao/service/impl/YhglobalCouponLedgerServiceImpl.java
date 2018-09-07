package com.yhglobal.gongxiao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yhglobal.gongxiao.constant.CouponLedgerCouponStatus;
import com.yhglobal.gongxiao.constant.FXConstant;

import com.yhglobal.gongxiao.foundation.project.dao.ProjectDao;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.model.PurchaseOrder;
import com.yhglobal.gongxiao.model.YhglobalCouponLedger;
import com.yhglobal.gongxiao.purchase.dao.YhglobalToReceiveCouponLedgerDao;
import com.yhglobal.gongxiao.purchase.service.YhglobalCouponLedgerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * Yh应收返利生成Service实现类
 *
 * @Author: 王帅
 */
@Service(timeout = 1000)
public class YhglobalCouponLedgerServiceImpl implements YhglobalCouponLedgerService {

    private static Logger logger = LoggerFactory.getLogger(YhglobalCouponLedgerServiceImpl.class);


    @Autowired
    ProjectDao projectDao;

    @Autowired
    YhglobalToReceiveCouponLedgerDao yhglobalToReceiveCouponLedgerDao;//采购返利详情

    /**
     * 根据采购单及关联的项目信息自动生成应收返利
     * @param purchaseOrder     采购单对象
     * */
    @Override
    public boolean generateYhglobalCouponLedger(PurchaseOrder purchaseOrder) throws MalformedURLException, RemoteException {

        try{
            logger.info("#traceId={}# [IN][generateYhglobalCouponLedger] params:  purchaseOrder ={}", purchaseOrder.toString());
            // 根据采购单关联项目ID查出项目
            Project project = projectDao.getByProjectId(purchaseOrder.getProjectId());
            // 根据采购单金额 项目返利比例 生成多条应收返利
            // 返利金额 = 采购总金额 * 返利率
            // 应收返利数据插入  抽象为一个方法  传入参数调用四次
            List<YhglobalCouponLedger> list = yhglobalToReceiveCouponLedgerDao.getByPurchaseOrderNo(purchaseOrder.getPurchaseOrderNo());
            if (list.size()==0) {
                if (project.getMonthlyCouponGenerateRate() > 0) {
                    // 生成月度返利
                    insertOneRecord(CouponLedgerCouponStatus.COUPON_TYPE_MONTHLY.getCode(), project.getMonthlyCouponGenerateRate(), project, purchaseOrder);
                }
                if(project.getQuarterlyCouponGenerateRate() > 0) {
                    // 生成季度返利
                    insertOneRecord(CouponLedgerCouponStatus.COUPON_TYPE_QUARTERLY.getCode(), project.getQuarterlyCouponGenerateRate(), project, purchaseOrder);
                }
                if (project.getAnnualCouponGenerateRate() > 0) {
                    // 生成年度返利
                    insertOneRecord(CouponLedgerCouponStatus.COUPON_TYPE_ANNUAL.getCode(), project.getAnnualCouponGenerateRate(), project, purchaseOrder);
                }
                if (project.getCashRebateRateAfter() >0 ) {
                    // 生成后返现金返利  应收表要修改 此处后返现金返利 对应的返利类型暂为7  类型常量写在对应的constant接口中
                    insertOneRecord(CouponLedgerCouponStatus.COUPON_TYPE_CASH_REBATE_AFTER.getCode(), project.getCashRebateRateAfter(), project, purchaseOrder);
                }
            }
        }catch (Exception e) {
            logger.error("# [OUT] errorMessage: " + e.getMessage(), e);
            return false;
        }
        return true;
    }


    /**
     *  依据返利类型 返利比例 关联的项目 关联的采购单生成返利对象
     *  @param  couponType     返利类型
     *  @param  couponRatio    返利比率
     *  @param  project        项目
     *  @param  purchaseOrder  采购单
     * */
    public void insertOneRecord(byte couponType, int couponRatio, Project project, PurchaseOrder purchaseOrder ){
        try {
            // 先判断数据库是否已经存在对应的应收返利 根据采购单号和返利类型查询
           // YhglobalCouponLedger yhglobalCouponLedger1 = yhglobalToReceiveCouponLedgerDao.getByPurchaseOrderNoAndCouponType(purchaseOrder.getPurchaseOrderNo(),couponType);
          //  if (yhglobalCouponLedger1 == null) {
                // 预计生成的返利在表中不存在 则进行生成插入 否则则不插入
                YhglobalCouponLedger yhglobalCouponLedger = new YhglobalCouponLedger();
                yhglobalCouponLedger.setCouponStatus(CouponLedgerCouponStatus.COUPON_STATUS_NOT_TO_ISSUE.getCode());
                yhglobalCouponLedger.setCouponType(couponType);
                // couponmodel 返利模式 该字段不赋值
                // yhglobalCouponLedger.setCouponModel();
                yhglobalCouponLedger.setCouponRatio(couponRatio);
                yhglobalCouponLedger.setProjectId((long) project.getProjectId());
                yhglobalCouponLedger.setProjectName(project.getProjectName());
                yhglobalCouponLedger.setSupplierId((long) project.getSupplierId());
                yhglobalCouponLedger.setSupplierName(project.getSupplierName());
                // if ("1".equals(purchaseOrder.getCurrencyId())) {
                yhglobalCouponLedger.setCurrencyCode("CNY");
                // 待定 采购单和项目 缺少对应字段
                yhglobalCouponLedger.setSupplierOrderNo(purchaseOrder.getBrandOrderNo()); // 品牌商订单号
                yhglobalCouponLedger.setPurchaseOrderNo(purchaseOrder.getPurchaseOrderNo());
                yhglobalCouponLedger.setPurchaseTime(purchaseOrder.getCreateTime());
                long estimateCouponAmount = 0l;
                if (couponRatio >= 0) {
                    estimateCouponAmount = Math.round(purchaseOrder.getPurchaseShouldPayAmount() * couponRatio / FXConstant.MILLION_DOUBLE );
                    yhglobalCouponLedger.setEstimatedCouponAmount(estimateCouponAmount);
                } else {
                    throw new RuntimeException("generate new yhglobalcouponledger wrong ,the couponRatio < 0");
                }
                yhglobalCouponLedger.setToBeConfirmAmount(estimateCouponAmount);
                yhglobalCouponLedger.setConfirmedCouponAmount(0l);
                yhglobalCouponLedger.setReceivedCouponAmount(0l);
                yhglobalCouponLedger.setDataVersion(0l);
                yhglobalCouponLedger.setLastUpdateTime(new Date());
                // 预估入账时间  该字段舍弃
                // yhglobalCouponLedger.setEstimatedPostingDate();
                // yhglobalCouponLedger.setCreateTime(DateUtil.stringToDate(DateUtil.getCurrentDateTime()));
                yhglobalCouponLedger.setCreateTime(new Date());
                // 插入该应收返利数据
                yhglobalToReceiveCouponLedgerDao.insert(yhglobalCouponLedger);
         //   }

        }catch (Exception e){
            logger.error("# [OUT] errorMessage: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
