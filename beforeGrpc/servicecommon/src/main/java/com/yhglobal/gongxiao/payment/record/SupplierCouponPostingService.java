package com.yhglobal.gongxiao.payment.record;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierCouponTransferRecord;

import java.util.Date;
import java.util.List;

/**
 * @author 王帅
 */
public interface SupplierCouponPostingService {

    /**
     * 查询供应商返利流水 包括收入和支出
     * @param supplierId    供应商ID
     **/
    PageInfo<FrontPageFlow> selectAllBySupplierId(RpcHeader rpcHeader,String currencyCode,
                                                  long supplierId,
                                                  long projectId,
                                                  Integer moneyFlow,
                                                  Date beginDate,
                                                  Date endDate, int pageNum, int pageSize);

    FrontPageFlowSubtotal generateFrontFlowSubtotal(RpcHeader rpcHeader,String currencyCode, long supplierId, long projectId, Integer moneyFlow, Date beginDate, Date endDate);

    List<FrontPageFlow> selectAllBySupplierId(RpcHeader rpcHeader,String currencyCode,
                                                     long supplierId,
                                                     long projectId,
                                                     Integer moneyFlow,
                                                     Date beginDate,
                                                     Date endDate);

}
