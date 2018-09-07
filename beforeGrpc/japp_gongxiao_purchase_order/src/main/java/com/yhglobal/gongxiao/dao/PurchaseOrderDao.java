package com.yhglobal.gongxiao.dao;

import com.yhglobal.gongxiao.dao.mapper.PurchaseOrderMapper;
import com.yhglobal.gongxiao.model.PurchaseOrder;
import com.yhglobal.gongxiao.purchase.dto.PurchaseOrderBackWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 采购单DAO
 *
 * @author: 陈浩
 * @create: 2018-02-06 16:50
 **/
@Repository
public class PurchaseOrderDao {

    @Autowired
    PurchaseOrderMapper purchaseOrderMapper;

    /**
     * 获取所有的采购单数据,按照创建时间倒序排列
     * @return
     */
    public List<PurchaseOrder> selectAll(){
        return purchaseOrderMapper.selectAll();
    }

    /**
     * 通过采购单号获取采购单信息
     * @param purchaseOrderNo
     * @return
     */
    public PurchaseOrder selectByPurchaseNo(String purchaseOrderNo){
        return purchaseOrderMapper.selectByPurchaseNo(purchaseOrderNo);
    }

    /**
     * 通过唯一号获取采购单信息
     * @param uniqueNumber
     * @return
     */
    public PurchaseOrder getByUniqueNumber(String uniqueNumber){
        return purchaseOrderMapper.getByUniqueNumber(uniqueNumber);
    }

    /**
     * 通过品牌商单号获取采购单信息
     * @param brandOrderNo 品牌商单号
     * @return
     */
    public List<PurchaseOrder> getByBrandOrderNo(String brandOrderNo){
        return purchaseOrderMapper.selectByBrandOrderNo(brandOrderNo);
    }

    /**
     * 插入采购单信息
     * @param purchaseOrder 采购单
     * @return
     */
    public int insert(PurchaseOrder purchaseOrder){
        return purchaseOrderMapper.insert(purchaseOrder);
    }

    /**
     * 回写采购单信息
     * @param purchaseOrderBackWrite 需要回写的信息
     * @return
     */
    public int updateBackWrite(PurchaseOrderBackWrite purchaseOrderBackWrite){
        return purchaseOrderMapper.updateBackWrite(purchaseOrderBackWrite);
    }

    public int updateBack(PurchaseOrderBackWrite purchaseOrderBackWrite){
        return purchaseOrderMapper.updateBack(purchaseOrderBackWrite);

    }

    /**
     * 记录采购单的预约入库单号
     * @param purchaseOrderNo 采购单号
     * @param ongoingInboundOrderInfo 正在入库的订单信息
     * @param finishedInboundOrderInfo 已完成入库的订单信息
     * @return
     */
    public int updateInboundOrderInfo(String purchaseOrderNo, String ongoingInboundOrderInfo,String finishedInboundOrderInfo){
        return purchaseOrderMapper.updateInboundOrderInfo(purchaseOrderNo,ongoingInboundOrderInfo,finishedInboundOrderInfo);
    }

    /**
     * 获取采购单列表
     * @param purchaseOrderNo 采购单号
     * @param supplierId 品牌ID
     * @param warehouseId 仓库ID
     * @return
     */
    public List<PurchaseOrder> getPurchaseOrderList(String projectId,
                                                    String purchaseOrderNo,
                                                    String supplierId,
                                                    String warehouseId,
                                                    String productCode,
                                                    String requireArrivalDate,
                                                    String arrivalDeadline,
                                                    String businessDate){
        return purchaseOrderMapper.getPurchaseOrderList(projectId,
                purchaseOrderNo,
                supplierId,
                warehouseId,
                productCode,
                requireArrivalDate,
                arrivalDeadline,
                businessDate);
    }

    /**
     * 更新EAS信息
     * @param purchaseOrderNo
     * @param easPurchaseOrderId
     * @param easOrderNumber
     * @return
     */
    public int updateEasInfo(String purchaseOrderNo, String easPurchaseOrderId, String easOrderNumber,byte easStatus){
        return purchaseOrderMapper.updateEasInfo(purchaseOrderNo,easPurchaseOrderId,easOrderNumber,easStatus);
    }

    /**
     * 更新状态为已付款
     * @param purchaseOrderNo   采购单号
     * @return
     */
    public int updateAlreadyPay(String purchaseOrderNo){
        return purchaseOrderMapper.updateAlreadyPay(purchaseOrderNo);
    }

    /**
     * 变更采购单状态
     * @param purchaseOrderNo 采购单号
     * @param orderStatus 采购单状态
     * @return
     */
    public int updatePurchaseOrderStatus(String purchaseOrderNo,int orderStatus){
        return  purchaseOrderMapper.updatePurchaseOrderStatus(purchaseOrderNo,orderStatus);
    }

    /**
     * 变更easstatus
     * @param purchaseOrderId
     * @param currentEasStatus
     * @return
     */
    public int updatePurchaseEasStatus(long purchaseOrderId, int currentEasStatus, int targetEasStatus){
        return purchaseOrderMapper.updatePurchaseEasStatus(purchaseOrderId, currentEasStatus,  targetEasStatus);
    }

    /**
     * 更新操作日记
     * @param purchaseOrderNo 采购单号
     * @param traceLog 操作日期
     * @return
     */
    public int updateOperateTraceLog(String purchaseOrderNo,String traceLog){
        return purchaseOrderMapper.updateOperateTrace(purchaseOrderNo,traceLog);
    }

    /**
     * 更新采购单
     * @param purchaseOrderNo
     * @return
     */
    public int updatePurchaseOrder(PurchaseOrder purchaseOrderNo){
        return purchaseOrderMapper.updateByPurchase(purchaseOrderNo);
    }

    /**
     * 取消采购单
     * @param purchaseOrderNo
     * @return
     */
    public int cancelPurchaseOrder(String purchaseOrderNo){
        return purchaseOrderMapper.cancelPurchaseOrder(purchaseOrderNo);
    }

    /**
     * 通过eas状态获取该状态的采购单列表
     * @param easStatus
     * @return
     */
    public List<PurchaseOrder> selectPurchaseOrderListByEasStatus(int easStatus){
        return purchaseOrderMapper.selectPurchaseOrderListByEasStatus(easStatus);
    }

}
