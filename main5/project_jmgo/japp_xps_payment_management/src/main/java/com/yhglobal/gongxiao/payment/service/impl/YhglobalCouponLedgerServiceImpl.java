package com.yhglobal.gongxiao.payment.service.impl;


import com.yhglobal.gongxiao.accountmanage.microservice.YhglobalCouponServiceGrpc;
import com.yhglobal.gongxiao.accountmanage.microservice.YhglobalCouponServiceStructure;
import com.yhglobal.gongxiao.constant.CouponLedgerCouponStatus;
import com.yhglobal.gongxiao.constant.CouponLedgerCouponType;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.payment.project.bean.YhglobalCouponLedger;
import com.yhglobal.gongxiao.payment.project.dao.yhglobal.YhglobalToReceiveCouponLedgerDao;
import com.yhglobal.gongxiao.utils.DateUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Yh应收返利生成Service实现类
 *
 * @Author: 王帅
 */
@Service
public class YhglobalCouponLedgerServiceImpl extends YhglobalCouponServiceGrpc.YhglobalCouponServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(YhglobalCouponLedgerServiceImpl.class);

    @Autowired
    YhglobalToReceiveCouponLedgerDao yhglobalToReceiveCouponLedgerDao;//采购返利详情

    /**
     * 触发时间: 当采购订单完成时 采购模块主动调用该接口生成应收返利
     * 根据采购单及关联的项目信息自动生成应收返利
     * */
    @Override
    public void generateYhglobalCouponLedger(YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerReq req,
                                             StreamObserver<YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerResp> respStreamObserver) {

        Long projectId = req.getProjectId();
        String currencyCode = req.getCurrencyCode();
        String brandOrderNo = req.getBrandOrderNo();
        String purchaseOrderNo = req.getPurchaseOrderNo();
        String purchaseTime = req.getPurchaseTime();
        Long  purchaseShouldPayAmount = req.getPurchaseShouldPayAmount();
        Integer couponType = req.getCouponType();
        long couponRatio = req.getCouponRatio();
        String projectName = req.getProjectName();
        String tablePrefix = req.getTablePrefix();
        long supplierId = req.getSupplierId();
        String supplierName = req.getSupplierName();

        logger.info("#traceId={}# [IN][generateYhglobalCouponLedger] params:  projectId={}, currencyCode={}, brandOrderNo={}," +
                        " purchaseOrderNo={}, purchaseTime={}, purchaseShouldPayAmount={}, couponType={}, couponRatio={}, projectName={}," +
                        " tablePrefix={}, supplierId={}, supplierName={}", projectId, currencyCode, brandOrderNo, purchaseOrderNo,
                purchaseTime, purchaseShouldPayAmount, couponType, couponRatio, projectName, tablePrefix, supplierId, supplierName);
        try{
            // 返利金额 = 采购总金额 * 返利率
            // 应收返利数据插入
            YhglobalCouponLedger yhglobalCouponLedger = yhglobalToReceiveCouponLedgerDao.getByPurchaseOrderNoAndCouponType(purchaseOrderNo, couponType.byteValue()); //查出该采购单现有的应收返利

            if (couponRatio > 0 && yhglobalCouponLedger == null) {
                // 利率大于零且该返利不存在则生成返利
                insertOneRecord(CouponLedgerCouponType.COUPON_TYPE_MONTHLY.getCode(), couponRatio,currencyCode, projectId, projectName, tablePrefix,
                                supplierId , supplierName, brandOrderNo, purchaseOrderNo, purchaseTime, purchaseShouldPayAmount);
                YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerResp.Builder respBuilder = YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerResp.newBuilder();
                respBuilder.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                respBuilder.setMsg(ErrorCode.SUCCESS.getMessage());
                respStreamObserver.onNext(respBuilder.build());
                respStreamObserver.onCompleted();
            }else {
                YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerResp.Builder respBuilder = YhglobalCouponServiceStructure.GenerateYhglobalCouponLedgerResp.newBuilder();
                respBuilder.setReturnCode(ErrorCode.TO_RECEIVE_COUPON_LEDGER_EXISTED.getErrorCode());
                respBuilder.setMsg(ErrorCode.TO_RECEIVE_COUPON_LEDGER_EXISTED.getMessage());
                respStreamObserver.onNext(respBuilder.build());
                respStreamObserver.onCompleted();
            }

        } catch (Exception e) {
            logger.error("# [OUT] errorMessage: " + e.getMessage(), e);
        }

    }


    /**
     *  依据返利类型 返利比例 关联的采购单生成返利对象
     *  @param  couponType     返利类型
     *  @param  couponRatio    返利比率
     * */
    public void insertOneRecord(Byte couponType, Long couponRatio, String currencyCode, Long projectId, String projectName, String tablePrefix,
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
                yhglobalCouponLedger.setPurchaseTime(DateUtil.stringToDate(purchaseTime));
                long estimateCouponAmount =  Math.round(purchaseShouldPayAmount * couponRatio / FXConstant.MILLION_DOUBLE );
                yhglobalCouponLedger.setEstimatedCouponAmount(estimateCouponAmount);

                yhglobalCouponLedger.setToBeConfirmAmount(estimateCouponAmount);
                yhglobalCouponLedger.setConfirmedCouponAmount(0L);
                yhglobalCouponLedger.setReceivedCouponAmount(0L);
                yhglobalCouponLedger.setDataVersion(0L);
                yhglobalCouponLedger.setLastUpdateTime(new Date());
                // 预估入账时间  该字段舍弃
                // yhglobalCouponLedger.setEstimatedPostingDate();
                yhglobalCouponLedger.setCreateTime(DateUtil.stringToDate(DateUtil.getCurrentDateTime()));
                // 插入该应收返利数据
                yhglobalToReceiveCouponLedgerDao.insert(yhglobalCouponLedger);

        }catch (Exception e){
            logger.error("# [OUT] errorMessage: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
