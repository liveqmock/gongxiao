package com.yhglobal.gongxiao.warehousemanagement.dao;

import com.yhglobal.gongxiao.warehousemanagement.dao.mapping.InboundOrderMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.InboundOrder;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InBoundOrderDao {

    @Autowired
    InboundOrderMapper inboundOrderMapper;

    public int insertInStorageInfo(InboundOrder inStorageInfo) {
        return inboundOrderMapper.insertOutStorageInfos(inStorageInfo);
    }

    public List<InboundOrder> selectInStorageInfo(String projectId,String gonxiaoInboundNo,String purchaseNo, String productCode, String goodCode, String createTime, String warehouseId, String supplierName) {
        return inboundOrderMapper.selectInStorageInfo(projectId,gonxiaoInboundNo, purchaseNo, productCode, goodCode, createTime, warehouseId, supplierName);
    }

    public int updateInStorageInfo(long rowId,int afterRealInStockQuantity,int afterImperfectQuantity,int afterInStockQuantity,int afterInTransitQuantity,String tracelog,int orderStatus,long dataVerSion) {
        return inboundOrderMapper.updateInStorageInfo(rowId,afterRealInStockQuantity,afterImperfectQuantity,afterInStockQuantity,afterInTransitQuantity,tracelog,orderStatus,dataVerSion);
    }

    public InboundOrder getRecordById(String projectId,String inventoryNum) {
        return inboundOrderMapper.selectRecordById(projectId,inventoryNum);
    }

    public List<InboundOrder> selectRecordByPurchaseNo(String projectId, String purchaseNo, String inventoryNum, String productCode, String createTime, String warehouseId, String supplierName) {
        return inboundOrderMapper.selectRecordByPurchaseNo(projectId,purchaseNo,inventoryNum,productCode,createTime,warehouseId,supplierName);
    }

    public List<InboundOrder> selectInboundRecordByPurchaseNo(String projectId, String purchaseNo) {
        return inboundOrderMapper.selectInboundRecordByPurchaseNo(projectId, purchaseNo);
    }

    public int updateByProjectIdAndGongxiaoInboundOrderNo(String projectId,String gongxiaoInboundOrderNo,int targetQuantity) {
        return inboundOrderMapper.updateByProjectIdAndGongxiaoInboundOrderNo(projectId,gongxiaoInboundOrderNo,targetQuantity);
    }

    public List<InboundOrder> getInboundRecord(String projectId, String gongxiaoOutboundOrder, String signature) {
        return inboundOrderMapper.getInboundRecord(projectId, gongxiaoOutboundOrder, signature);
    }

    public int deleteOrderByOrderNo(String projectId,String gongxiaoInboundOrderNo){
        return inboundOrderMapper.deleteOrderByOrderNo(projectId,gongxiaoInboundOrderNo);
    }

    public InboundOrder getRecordByInBoundNo(String gongxiaoInboundOrderNo){
        return inboundOrderMapper.getRecordByInBoundNo(gongxiaoInboundOrderNo);
    }

    public int judgeExit(String uniqueNo){
        return inboundOrderMapper.judgeExit(uniqueNo);
    }

    public int updateOrderStatus(String projectId, String orderNo){
        return inboundOrderMapper.updateOrderStatus(projectId, orderNo);

    }

    public InboundOrder getInboundRecordByOrderNo(String gongxiaoOutboundOrder) {
        return inboundOrderMapper.getInboundRecordByOrderNo(gongxiaoOutboundOrder);
    }

    public InboundOrder selectRecordByOrderNo(String projectId,String orderNo) {
        return inboundOrderMapper.selectRecordByOrderNo(projectId,orderNo);
    }

    public int updateSyncWmsFlag(String gongxiaoInboundOrderNo){
        return inboundOrderMapper.updateSyncWmsFlag(gongxiaoInboundOrderNo);
    }

    public List<InboundOrder> selectInboundRecordByWmsFlag(int wmsFlag){
        return inboundOrderMapper.selectInboundRecordByWmsFlag(wmsFlag);
    }

    public int cancelOrder(String gongxiaoInboundOrderNo,int orderStatus,String traceLog){
        return inboundOrderMapper.cancelOrder(gongxiaoInboundOrderNo,orderStatus,traceLog);
    }

    public int cancelOrderFail(String gongxiaoInboundOrderNo,String traceLog){
        return inboundOrderMapper.cancelOrderFail(gongxiaoInboundOrderNo,traceLog);
    }

    public int closeOrder(String gongxiaoInboundOrderNo,int orderStatus, String traceLog){
        return inboundOrderMapper.closeOrder(gongxiaoInboundOrderNo,orderStatus,traceLog);
    }

    public int closeOrderFail(String gongxiaoInboundOrderNo,String traceLog){
        return inboundOrderMapper.closeOrderFail(gongxiaoInboundOrderNo,traceLog);
    }

    public int notifyFail(String gongxiaoInboundOrderNo,String traceLog){
        return inboundOrderMapper.notifyFail(gongxiaoInboundOrderNo,traceLog);
    }

    public int notifySuccess(String gongxiaoInboundOrderNo, int syncWmsFlag, String traceLog){
        return inboundOrderMapper.notifySuccess(gongxiaoInboundOrderNo,syncWmsFlag,traceLog);
    }
}
