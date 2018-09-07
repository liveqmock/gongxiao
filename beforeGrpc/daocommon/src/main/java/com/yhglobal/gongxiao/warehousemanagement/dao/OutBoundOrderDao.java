package com.yhglobal.gongxiao.warehousemanagement.dao;

import com.yhglobal.gongxiao.warehousemanagement.dao.mapping.OutboundOrderMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrder;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OutBoundOrderDao {
    @Autowired
    OutboundOrderMapper outboundOrderMapper;

    public int insertOutStorageInfo(OutboundOrder outboundOrder) {
        return outboundOrderMapper.insertOutStorageInfo(outboundOrder);
    }

    public List<OutboundOrder> selectOutStorageInfoByRequire(String projectId, String inventoryNum, String salseNum, String createTimeBeging, String createTimeLast, String warehouseId, String productCode, String finishTimeBegin, String finishTimeLast, String supplier, String customer){
        return outboundOrderMapper.selectOutStorageInfoByRequire(projectId, inventoryNum, salseNum, createTimeBeging, createTimeLast, warehouseId, productCode, finishTimeBegin, finishTimeLast, supplier, customer);
    }

    public int sureSighIn(OutboundOrder outboundOrder){
        return outboundOrderMapper.sureSighIn(outboundOrder);
    }

    public List<OutboundOrder> selectAllOutStorageInfoByProjectId(String projectId) {
        return outboundOrderMapper.selectAllOutStorageInfoByProjectId(projectId);
    }

    public OutboundOrder getOutStorageInfoById(String projectId, String gongxiaoOutboundOrder) {
        return outboundOrderMapper.selectOutStorageInfoById(projectId,gongxiaoOutboundOrder);
    }

    public OutboundOrder selectRecordBySalseNoAndProduct(String projectId, String salesOrderNo, String productCode) {
        return outboundOrderMapper.selectRecordBySalseNoAndProduct(projectId, salesOrderNo, productCode);
    }

    public List<OutboundOrder> getOutboundRecord(String projectId, String gongxiaoOutboundOrder, String signature) {
        return outboundOrderMapper.getOutboundRecord(projectId,gongxiaoOutboundOrder,signature);
    }

    public int updateByProjectAndOutboundNo(String projectId,String gongxiaoOutboundOrderNo,int adjustmentQuantity){
        return outboundOrderMapper.updateByProjectAndOutboundNo(projectId,gongxiaoOutboundOrderNo,adjustmentQuantity);
    }

    public List<OutboundOrder> selectRecordBySalesNo(String projectId, String salesNo) {
        return outboundOrderMapper.selectRecordBySalesNo(projectId,salesNo);
    }

    public int deleteOrderByOrderNo(String projectId,String gongxiaoOutboundOrderNo){
        return outboundOrderMapper.deleteOrderByOrderNo(projectId,gongxiaoOutboundOrderNo);
    }

    public String getProjectId(String gongxiaoOutboundOrderNo){
        return outboundOrderMapper.getProjectId(gongxiaoOutboundOrderNo);
    }

    public OutboundOrder getOutboundRecordByOrderNo(String gongxiaoOutboundOrderNo,String productCode){
        return outboundOrderMapper.getOutboundRecordByOrderNo(gongxiaoOutboundOrderNo,productCode);
    }

    public int updateOutboundOrderByNo(OutboundOrder outboundOrder){
        return outboundOrderMapper.updateOutboundOrderByNo(outboundOrder);
    }

    public int updateSyncWmsFlag(String gongxiaoOutboundOrderNo,String productCode){
        return outboundOrderMapper.updateSyncWmsFlag(gongxiaoOutboundOrderNo,productCode);
    }

    public List<OutboundOrder> selectOutboundRecordByWmsFlag(int wmsFlag){
        return outboundOrderMapper.selectOutboundRecordByWmsFlag(wmsFlag);
    }

    public int cancelOrder(String gongxiaoOutboundOrderNo,int orderStatus,String tracelog){
        return outboundOrderMapper.cancelOrder(gongxiaoOutboundOrderNo,orderStatus,tracelog);
    }

    public int cancelOrderFail(String gongxiaoOutboundOrderNo,String tracelog){
        return outboundOrderMapper.cancelOrderFail(gongxiaoOutboundOrderNo,tracelog);
    }

    public int closeOrder(String gongxiaoOutboundOrderNo,int orderStatus,String tracelog){
        return outboundOrderMapper.closeOrder(gongxiaoOutboundOrderNo,orderStatus,tracelog);
    }

    public int closeOrderFail(String gongxiaoOutboundOrderNo,String tracelog){
        return outboundOrderMapper.closeOrderFail(gongxiaoOutboundOrderNo,tracelog);
    }

    public int notifyFail(String gongxiaoOutboundOrderNo, String productCode, int syncWmsFlag, String tracelog){
        return outboundOrderMapper.notifyFail(gongxiaoOutboundOrderNo,productCode,syncWmsFlag,tracelog);
    }

    public int notifySuccess(String gongxiaoOutboundOrderNo, String productCode, int syncWmsFlag, String tracelog){
        return outboundOrderMapper.notifySuccess(gongxiaoOutboundOrderNo,productCode,syncWmsFlag, tracelog);
    }

    public int notifyTMSFail(String gongxiaoOutboundOrderNo,String productCode,String tracelog){
        return outboundOrderMapper.notifyTMSFail(gongxiaoOutboundOrderNo,productCode,tracelog);
    }

    public int notifyTMSSuccess(String gongxiaoOutboundOrderNo,String productCode,String tracelog){
        return outboundOrderMapper.notifyTMSSuccess(gongxiaoOutboundOrderNo,productCode,tracelog);
    }

    public int notifyWMSSuccessTmsFail(String gongxiaoOutboundOrderNo,String productCode,String tracelog){
        return outboundOrderMapper.notifyWMSSuccessTmsFail(gongxiaoOutboundOrderNo,productCode,tracelog);
    }

    public List<OutboundOrder> selectRecordByWmsFlagAndTmsFlag(int syncWmsFlag,int syncTmsFlag){
        return outboundOrderMapper.selectRecordByWmsFlagAndTmsFlag(syncWmsFlag,syncTmsFlag);
    }

    public OutboundOrder getOutboundRecordByGoxiaoOutNo(String gongxiaoOutboundOrderNo){
        return outboundOrderMapper.getOutboundRecordByGoxiaoOutNo(gongxiaoOutboundOrderNo);
    }

    public List<OutboundOrder> judeOrderIfFinish(String gongxiaoOutboundOrderNo){
        return outboundOrderMapper.judeOrderIfFinish(gongxiaoOutboundOrderNo);
    }

    public OutboundOrder getOutStorageInfoByNo(String gongxiaoOutboundOrderNo){
        return outboundOrderMapper.getOutStorageInfoByNo(gongxiaoOutboundOrderNo);
    }

    public List<OutboundOrder> judeOrderIfFinishSight(String salesOrderNo){
        return outboundOrderMapper.judeOrderIfFinishSight(salesOrderNo);
    }

    public int updateWmsFlagByOrder(String gongxiaoOutboundOrderNo,int syncToWmsFlag,long dataVersion){
        return outboundOrderMapper.updateWmsFlagByOrder(gongxiaoOutboundOrderNo,syncToWmsFlag,dataVersion);
    }
}
