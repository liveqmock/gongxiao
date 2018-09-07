package com.yhglobal.gongxiao.warehouse.dao;

import com.yhglobal.gongxiao.warehouse.dao.mapping.InboundOrderMapper;
import com.yhglobal.gongxiao.warehouse.model.InboundOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InBoundOrderDao {

    @Autowired
    InboundOrderMapper inboundOrderMapper;

    public int insertInStorageInfo(InboundOrder inStorageInfo,String prefix) {
        return inboundOrderMapper.insertOutStorageInfos(inStorageInfo,prefix);
    }

    public List<InboundOrder> selectInStorageInfo(String projectId,String gonxiaoInboundNo,String purchaseNo, String productCode, String goodCode, String createTime, String warehouseId, String supplierName,String prefix) {
        return inboundOrderMapper.selectInStorageInfo(projectId,gonxiaoInboundNo, purchaseNo, productCode, goodCode, createTime, warehouseId, supplierName,prefix);
    }

    public int updateInStorageInfo(long rowId,int afterRealInStockQuantity,int afterImperfectQuantity,int afterInStockQuantity,int afterInTransitQuantity,String tracelog,int orderStatus,long dataVerSion,String prefix) {
        return inboundOrderMapper.updateInStorageInfo(rowId,afterRealInStockQuantity,afterImperfectQuantity,afterInStockQuantity,afterInTransitQuantity,tracelog,orderStatus,dataVerSion,prefix);
    }

    public InboundOrder getRecordById(String projectId,String inventoryNum,String prefix) {
        return inboundOrderMapper.selectRecordById(projectId,inventoryNum,prefix);
    }

    public List<InboundOrder> selectRecordByPurchaseNo(String projectId, String purchaseNo, String inventoryNum, String productCode, String createTime, String warehouseId, String supplierName,String prefix) {
        return inboundOrderMapper.selectRecordByPurchaseNo(projectId,purchaseNo,inventoryNum,productCode,createTime,warehouseId,supplierName,prefix);
    }

    public List<InboundOrder> selectInboundRecordByPurchaseNo(String projectId, String purchaseNo,String prefix) {
        return inboundOrderMapper.selectInboundRecordByPurchaseNo(projectId, purchaseNo,prefix);
    }

    public int updateByProjectIdAndGongxiaoInboundOrderNo(String projectId,String gongxiaoInboundOrderNo,int targetQuantity,String prefix) {
        return inboundOrderMapper.updateByProjectIdAndGongxiaoInboundOrderNo(projectId,gongxiaoInboundOrderNo,targetQuantity,prefix);
    }

    public List<InboundOrder> getInboundRecord(String projectId, String gongxiaoOutboundOrder, String signature,String prefix) {
        return inboundOrderMapper.getInboundRecord(projectId, gongxiaoOutboundOrder, signature,prefix);
    }

    public int deleteOrderByOrderNo(String projectId,String gongxiaoInboundOrderNo,String prefix){
        return inboundOrderMapper.deleteOrderByOrderNo(projectId,gongxiaoInboundOrderNo,prefix);
    }

    public InboundOrder getRecordByInBoundNo(String gongxiaoInboundOrderNo,String prefix){
        return inboundOrderMapper.getRecordByInBoundNo(gongxiaoInboundOrderNo,prefix);
    }

    public int judgeExit(String uniqueNo,String prefix){
        return inboundOrderMapper.judgeExit(uniqueNo,prefix);
    }

    public int updateOrderStatus(String projectId, String orderNo,String prefix){
        return inboundOrderMapper.updateOrderStatus(projectId, orderNo,prefix);

    }

    public InboundOrder getInboundRecordByOrderNo(String gongxiaoOutboundOrder,String prefix) {
        return inboundOrderMapper.getInboundRecordByOrderNo(gongxiaoOutboundOrder,prefix);
    }

    public InboundOrder selectRecordByOrderNo(String projectId,String orderNo,String prefix) {
        return inboundOrderMapper.selectRecordByOrderNo(projectId,orderNo,prefix);
    }

    public int updateSyncWmsFlag(String gongxiaoInboundOrderNo,String prefix){
        return inboundOrderMapper.updateSyncWmsFlag(gongxiaoInboundOrderNo,prefix);
    }

    public List<InboundOrder> selectInboundRecordByWmsFlag(int wmsFlag,String prefix){
        return inboundOrderMapper.selectInboundRecordByWmsFlag(wmsFlag,prefix);
    }

    public int cancelOrder(String gongxiaoInboundOrderNo,int orderStatus,String traceLog,String prefix){
        return inboundOrderMapper.cancelOrder(gongxiaoInboundOrderNo,orderStatus,traceLog,prefix);
    }

    public int cancelOrderFail(String gongxiaoInboundOrderNo,String traceLog,String prefix){
        return inboundOrderMapper.cancelOrderFail(gongxiaoInboundOrderNo,traceLog,prefix);
    }

    public int closeOrder(String gongxiaoInboundOrderNo,int orderStatus,String traceLog,String prefix){
        return inboundOrderMapper.closeOrder(gongxiaoInboundOrderNo,orderStatus,traceLog,prefix);
    }

    public int closeOrderFail(String gongxiaoInboundOrderNo,String traceLog,String prefix){
        return inboundOrderMapper.closeOrderFail(gongxiaoInboundOrderNo,traceLog,prefix);
    }

    public int notifyFail(String gongxiaoInboundOrderNo,String traceLog,String prefix){
        return inboundOrderMapper.notifyFail(gongxiaoInboundOrderNo,traceLog,prefix);
    }

    public int notifySuccess(String gongxiaoInboundOrderNo, int syncWmsFlag, String traceLog,String prefix){
        return inboundOrderMapper.notifySuccess(gongxiaoInboundOrderNo,syncWmsFlag,traceLog,prefix);
    }

    public InboundOrder selectRecordByBatchNo(String batchNo,String prefix){
        return inboundOrderMapper.selectRecordByBatchNo(batchNo,prefix);
    }

    public int updateTrsInWmsStatus(String gongxiaoInboundOrderNo, int syncToWmsFlag, String prefix){
        return inboundOrderMapper.updateTrsInWmsStatus(gongxiaoInboundOrderNo, syncToWmsFlag,prefix);
    }
}
