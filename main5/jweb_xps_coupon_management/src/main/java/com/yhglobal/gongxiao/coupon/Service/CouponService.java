package com.yhglobal.gongxiao.coupon.Service;


import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.coupon.model.RpcHeader;
import com.yhglobal.gongxiao.coupon.model.YhglobalCouponLedger;
import com.yhglobal.gongxiao.coupon.model.YhglobalCouponLedgerItem;
import com.yhglobal.gongxiao.coupon.model.YhglobalCouponWriteoffRecord;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.model.WriteOffReturnObject;

import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
public interface CouponService {

    // 生成返利的接口
    RpcResult generateYhglobalCouponLedger(RpcHeader rpcHeader,Long projectId ,
                                          String currencyCode ,
                                          String brandOrderNo  ,
                                          String purchaseOrderNo ,
                                          String purchaseTime,
                                          Long  purchaseShouldPayAmount,
                                           Integer couponType ,
                                           long couponRatio  ,
                                           String projectName ,
                                           long supplierId ,
                                           String supplierName);
    // 核销
    RpcResult yhCouponWriteroff(  GongxiaoRpc.RpcHeader rpcHeader,Long projectId,
                                 String useDate ,
                                 Integer accountType,
                                 String confirmInfoJson ,
                                 String projectName,
                                 String philipDocumentNo, String flowNo, List<WriteOffReturnObject> list);
    // 根据ID查询
    List<YhglobalCouponLedger> selectYhglobalCouponLedgerByPurchaseCouponLedgerId(Long projectId, List<String> idList);

    // 台账
    PageInfo<YhglobalCouponWriteoffRecord> searchCouponConfirm(RpcHeader rpcHeader,
                                                               Long projectId,
                                                               String flowNo,
                                                               Integer accountType,
                                                               Date useDateStart,
                                                               Date useDateEnd,
                                                               Date dateStart,
                                                               Date dateEnd,
                                                               Integer pageNumber,
                                                               Integer pageSize);


    // 条件查询返利
    PageInfo<YhglobalCouponLedgerItem> selectByManyConditionsHasPage(RpcHeader rpcHeader, Long projectId, String purchaseOrderNo,
                                                              String supplierOrderNo, Date dateS, Date dateE, String couponStatus,String couponTypes,
                                                              String flowNo, Integer pageNumber,
                                                              Integer pageSize);
    // 导出
    List<YhglobalCouponLedgerItem> selectByManyConditionsNoPage(RpcHeader rpcHeader, Long projectId, String purchaseOrderNo,
                                                                 String supplierOrderNo, Date dateS, Date dateE, String couponStatus,String couponTypes,
                                                                 String flowNo);

    // 取消确认
    RpcResult receiveCancelConfirmBatch(GongxiaoRpc.RpcHeader rpcHeader,Long projectId, String flowCodes, WriteOffReturnObject object, String flow);


}
