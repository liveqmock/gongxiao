package com.yhglobal.gongxiao.payment.service.impl;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.payment.service.YhglobalCouponTransferIntoReceivedService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedCouponAccountService;
import com.yhglobal.gongxiao.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 越海返利自动存入实收表接口的实现类
 *
 * @author 王帅
 */
@Service
public class YhglobalCouponTransferIntoReceivedServiceImpl implements YhglobalCouponTransferIntoReceivedService {

    // 业务逻辑
    // 1实收表额度增加 2生成实收流水

    @Autowired
    YhglobalReceivedCouponAccountService yhglobalReceivedCouponAccountService;

    /**
     * 越海实收返利账户接口实现了额度的增加和流水生成 这里调用接口即可
     *
     * @param rpcHeader       包含了操作员ID和name
     * @param currencyCode    货币编码
     * @param supplierId      供应商ID
     * @param projectId       项目ID
     * @param couponAmount    返利额度
     * @param purchaseOrderNo 关联的采购单号
     * @return true            业务成功
     */
    public boolean yhglobalCouponTransferIntoReceived(RpcHeader rpcHeader, String currencyCode,
                                                      long supplierId, long projectId, long couponAmount,
                                                      String purchaseOrderNo) {
        Date transactionDate = DateUtil.stringToDate(DateUtil.getCurrentDateTime());
        yhglobalReceivedCouponAccountService.updateYhglobalReceivedCouponAccount(rpcHeader, currencyCode,
                projectId, couponAmount, transactionDate, purchaseOrderNo, null,null);
        return true;
    }
}
