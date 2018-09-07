package com.yhglobal.gongxiao.payment.flow.service.impl;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.TransferAccountTypeConstant;
import com.yhglobal.gongxiao.payment.service.*;
import com.yhglobal.gongxiao.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


/**
 *  供应商返利过账接口的实现类 分为过账给YH 以及过账给AD
 *  @author 王帅
 * */
public class SupplierCouponTransferServiceImpl implements SupplierCouponTransferService {


    // 过账部分主要业务逻辑  过账给YH与AD的为两个方法
    // 该部分主要为内部调用 在采购单完成后的某一节点调用 用来完成供应商账户的修改 生成过账记录


    @Autowired
    SupplierCouponTransferRecordService supplierCouponTransferRecordService;

    @Autowired
    SupplierCouponAccountService supplierCouponAccountService;

    @Autowired
    SupplierCouponBufferToYhglobalService supplierCouponBufferToYhglobalService;

    @Autowired
    YhglobalReceivedCouponAccountService yhglobalReceivedCouponAccountService;

    @Autowired
    YhglobalCouponTransferIntoReceivedService yhglobalCouponTransferIntoReceivedService;

    @Override
    public boolean supplierCouponTransferToYh(long supplierId, String supplierName, long projectId,
                                          String projectName, String currrencyCode, long totalAmount, String purchaseOrderNo,
                                          String remark,RpcHeader rpcHeader) {
        // 代码实现步骤如下
        // 1修改供应商账户余额 2生成供应商过账流水
        long couponAmount = 0 - totalAmount;
        Date transactionDate = DateUtil.stringToDate(DateUtil.getCurrentDateTime());
//        supplierCouponAccountService.updateSupplierCouponAccount(rpcHeader,currrencyCode,supplierId,projectId,couponAmount,purchaseOrderNo,
//                transactionDate,remark, TransferAccountTypeConstant.TRANSFER_ACCOUNT_TYPE_1.getType());
        // 3生成buffer表数据  生成对应流水
//        String flowNo = supplierCouponBufferToYhglobalService.updateCouponBufferToYhglobal(rpcHeader,currrencyCode,supplierId,projectId,couponAmount,
//                purchaseOrderNo,transactionDate,remark);
        // 4过账记录的流水号返给对应的2,3数据,修改数据  添加流水号  在返回流水号的接口内部完成了
        // 5修改YH账户余额 6生成YH的过账记录buffer到实收  写成另外的接口在此处调用 buffer额度清空
//       yhglobalCouponTransferIntoReceivedService.yhglobalCouponTransferIntoReceived(rpcHeader,currrencyCode,supplierId,projectId,couponAmount,
//               purchaseOrderNo);

        return  true;

    }


    @Autowired
    SupplierCouponBufferToDistributorService supplierCouponBufferToDistributorService;

    @Override
    public boolean supplierCouponTransferToAd(long supplierId, String supplierName ,long projectId,
                                          String projectName, String currrencyCode, long totalAmount, String purchaseOrderNo,
                                          String remark,RpcHeader rpcHeader) {
        // 5, 依据供应商过账给AD的额度 生成过账记录  修改供应商返利余额
        long couponAmount = 0 - totalAmount;
        Date transactionDate = DateUtil.stringToDate(DateUtil.getCurrentDateTime());
//        supplierCouponAccountService.updateSupplierCouponAccount(rpcHeader,currrencyCode,supplierId,projectId,couponAmount,purchaseOrderNo,
//                transactionDate,remark, TransferAccountTypeConstant.TRANSFER_ACCOUNT_TYPE_2.getType());

        // 6, 把过账额度存在supplier到AD的buffer表中 一次过账即产生一条数据
        long distributorId = 0;
//        String distributorName = null;
//        String flowNo = supplierCouponBufferToDistributorService.updateCouponBufferToDistributorAccount(rpcHeader,currrencyCode,
//                projectId,distributorId,distributorName,couponAmount,purchaseOrderNo,transactionDate,remark);

        // 把生成的流水号返回给 供应商的过账记录表 以及 对应的 buffer表 更新数据  已在返回流水号的接口内部实现
        // buffer表额度清空 放在手工分配该次返利之后进行  用该流水号进行表关联  待定
        // AD的具体额度分配由另外单独接口实现

        return true;
    }


}
