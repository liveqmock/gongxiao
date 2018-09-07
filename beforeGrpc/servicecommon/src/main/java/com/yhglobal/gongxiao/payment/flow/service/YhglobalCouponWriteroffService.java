package com.yhglobal.gongxiao.payment.flow.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.model.*;
import com.yhglobal.gongxiao.payment.flow.YhglobalCouponWriteoffFlow;

import java.util.Date;
import java.util.List;

/**
 * 越海返利流水表Service
 *
 * @author: 王帅
 */
public interface YhglobalCouponWriteroffService {

    // boolean generateYhglobalCouponLedger(PurchaseOrder purchaseOrder) throws Exception;
    /**
     * 通过项目id查找
     *
     * @param rpcHeader        rpc调用的header
     * @param projectId        项目ID
     * @return
     */
    RpcResult yhCouponWriteroff(RpcHeader rpcHeader, Long projectId, String projectName, Date useDateS, Integer accountType,
                                String philipDocumentNo, String confirmInfoJson) throws  Exception;

     PageInfo<PurchaseCouponFlow> selectAllByProjectId(RpcHeader rpcHeader,long projectId, int pageNumber,
                                                   int pageSize);

    PageInfo<YhglobalCouponLedgerItem> selectByManyCondiitons(RpcHeader rpcHeader, Long projectId,
                                                              String purchaseOrderNo,
                                                              String supplierOrderNo, Date dateS,
                                                              Date dateE, String couponStatus, String flowNo, Integer pageNumber,
                                                              Integer pageSize);

    List<YhglobalCouponLedgerItem> selectByManyCondiitons(RpcHeader rpcHeader, Long projectId,
                                                              String purchaseOrderNo,
                                                              String supplierOrderNo, Date dateS,
                                                              Date dateE, String couponStatus, String flowNo);
    /**
     * 批量取消确认
     * @param rpcHeader
     * @param flowCodes
     * @return
     */
     boolean receiveCancelConfirmBatch(RpcHeader rpcHeader,String[] flowCodes);

     List<YhglobalCouponLedger> selectYhglobalCouponLedgerByPurchaseCouponLedgerId(List<Long> list);

    PageInfo<YhglobalCouponWriteoffRecord> searchCouponConfirm(RpcHeader rpcHeader,
                                                               Long projectId,
                                                               String flowCode,
                                                               Integer accountType,
                                                               Date useDateStart,
                                                               Date useDateEnd,
                                                               Date dateStart,
                                                               Date dateEnd,
                                                               Integer pageNumber,
                                                               Integer pageSize);


}
