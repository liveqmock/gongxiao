package com.yhglobal.gongxiao.warehouse.dao;

import com.yhglobal.gongxiao.warehouse.dao.mapping.OutboundOrderMapper;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OutBoundOrderDao {
    @Autowired
    OutboundOrderMapper outboundOrderMapper;

    public int insertOutStorageInfo(OutboundOrder outboundOrder,String prefix) {
        return outboundOrderMapper.insertOutStorageInfo(outboundOrder,prefix);
    }

    public List<OutboundOrder> selectOutStorageInfoByRequire(String projectId, String inventoryNum, String salseNum, String createTimeBeging, String createTimeLast, String warehouseId, String productCode, String finishTimeBegin, String finishTimeLast, String supplier, String customer,String prefix){
        return outboundOrderMapper.selectOutStorageInfoByRequire(projectId, inventoryNum, salseNum, createTimeBeging, createTimeLast, warehouseId, productCode, finishTimeBegin, finishTimeLast, supplier, customer,prefix);
    }

    public int sureSighIn(OutboundOrder outboundOrder,String prefix){
        return outboundOrderMapper.sureSighIn(outboundOrder,prefix);
    }

    public List<OutboundOrder> selectAllOutStorageInfoByProjectId(String projectId,String prefix) {
        return outboundOrderMapper.selectAllOutStorageInfoByProjectId(projectId,prefix);
    }

    public OutboundOrder getOutStorageInfoById(String projectId, String gongxiaoOutboundOrder,String prefix) {
        return outboundOrderMapper.selectOutStorageInfoById(projectId,gongxiaoOutboundOrder,prefix);
    }

    public OutboundOrder selectRecordBySalseNoAndProduct(String projectId, String salesOrderNo, String productCode,String prefix) {
        return outboundOrderMapper.selectRecordBySalseNoAndProduct(projectId, salesOrderNo, productCode,prefix);
    }

    public List<OutboundOrder> getOutboundRecord(String projectId, String gongxiaoOutboundOrder, String signature,String prefix) {
        return outboundOrderMapper.getOutboundRecord(projectId,gongxiaoOutboundOrder,signature,prefix);
    }

    public int updateByProjectAndOutboundNo(String projectId,String gongxiaoOutboundOrderNo,int adjustmentQuantity,String prefix){
        return outboundOrderMapper.updateByProjectAndOutboundNo(projectId,gongxiaoOutboundOrderNo,adjustmentQuantity,prefix);
    }

    public List<OutboundOrder> selectRecordBySalesNo(String projectId, String salesNo,String prefix) {
        return outboundOrderMapper.selectRecordBySalesNo(projectId,salesNo,prefix);
    }

    public int deleteOrderByOrderNo(String projectId,String gongxiaoOutboundOrderNo,String prefix){
        return outboundOrderMapper.deleteOrderByOrderNo(projectId,gongxiaoOutboundOrderNo,prefix);
    }

    public String getProjectId(String gongxiaoOutboundOrderNo,String prefix){
        return outboundOrderMapper.getProjectId(gongxiaoOutboundOrderNo,prefix);
    }

    public OutboundOrder getOutboundRecordByOrderNo(String gongxiaoOutboundOrderNo,String productCode,String prefix){
        return outboundOrderMapper.getOutboundRecordByOrderNo(gongxiaoOutboundOrderNo,productCode,prefix);
    }

    public int updateOutboundOrderByNo(OutboundOrder outboundOrder,String prefix){
        return outboundOrderMapper.updateOutboundOrderByNo(outboundOrder,prefix);
    }

    public int updateSyncWmsFlag(String gongxiaoOutboundOrderNo,String productCode,String prefix){
        return outboundOrderMapper.updateSyncWmsFlag(gongxiaoOutboundOrderNo,productCode,prefix);
    }

    public List<OutboundOrder> selectOutboundRecordByWmsFlag(int wmsFlag,String prefix){
        return outboundOrderMapper.selectOutboundRecordByWmsFlag(wmsFlag,prefix);
    }

    public int cancelOrder(String gongxiaoOutboundOrderNo,int orderStatus,String tracelog,String prefix){
        return outboundOrderMapper.cancelOrder(gongxiaoOutboundOrderNo,orderStatus,tracelog,prefix);
    }

    public int cancelOrderFail(String gongxiaoOutboundOrderNo,String tracelog,String prefix){
        return outboundOrderMapper.cancelOrderFail(gongxiaoOutboundOrderNo,tracelog,prefix);
    }


    public int closeOrder(String gongxiaoOutboundOrderNo,int orderStatus,String tracelog,String prefix){
        return outboundOrderMapper.closeOrder(gongxiaoOutboundOrderNo,orderStatus,tracelog,prefix);
    }

    public int closeOrderFail(String gongxiaoOutboundOrderNo,String tracelog,String prefix){
        return outboundOrderMapper.closeOrderFail(gongxiaoOutboundOrderNo,tracelog,prefix);
    }

    public int notifyFail(String gongxiaoOutboundOrderNo,int syncWmsFlag, String tracelog,String prefix){
        return outboundOrderMapper.notifyFail(gongxiaoOutboundOrderNo,syncWmsFlag,tracelog,prefix);
    }

    public int notifySuccess(String gongxiaoOutboundOrderNo, String productCode, int syncWmsFlag, String tracelog,String prefix){
        return outboundOrderMapper.notifySuccess(gongxiaoOutboundOrderNo,productCode,syncWmsFlag, tracelog,prefix);
    }

    public int notifyTMSFail(String gongxiaoOutboundOrderNo,String productCode,String tracelog,String prefix){
        return outboundOrderMapper.notifyTMSFail(gongxiaoOutboundOrderNo,productCode,tracelog,prefix);
    }

    public int notifyTMSSuccess(String gongxiaoOutboundOrderNo,String productCode,String tracelog,String prefix){
        return outboundOrderMapper.notifyTMSSuccess(gongxiaoOutboundOrderNo,productCode,tracelog,prefix);
    }

    public int notifyWMSSuccessTmsFail(String gongxiaoOutboundOrderNo,String tracelog,String prefix){
        return outboundOrderMapper.notifyWMSSuccessTmsFail(gongxiaoOutboundOrderNo,tracelog,prefix);
    }

    public List<OutboundOrder> selectRecordByWmsFlagAndTmsFlag(int syncWmsFlag,int syncTmsFlag,String prefix){
        return outboundOrderMapper.selectRecordByWmsFlagAndTmsFlag(syncWmsFlag,syncTmsFlag,prefix);
    }

    public OutboundOrder getOutboundRecordByGoxiaoOutNo(String gongxiaoOutboundOrderNo,String prefix){
        return outboundOrderMapper.getOutboundRecordByGoxiaoOutNo(gongxiaoOutboundOrderNo,prefix);
    }

    public List<OutboundOrder> judeOrderIfFinish(String gongxiaoOutboundOrderNo,String prefix){
        return outboundOrderMapper.judeOrderIfFinish(gongxiaoOutboundOrderNo,prefix);
    }

    public OutboundOrder getOutStorageInfoByNo(String gongxiaoOutboundOrderNo,String prefix){
        return outboundOrderMapper.getOutStorageInfoByNo(gongxiaoOutboundOrderNo,prefix);
    }

    public List<OutboundOrder> judeOrderIfFinishSight(String salesOrderNo,String prefix){
        return outboundOrderMapper.judeOrderIfFinishSight(salesOrderNo,prefix);
    }

    public int updateWmsFlagByOrder(String gongxiaoOutboundOrderNo,int syncToWmsFlag,long dataVersion,String prefix){
        return outboundOrderMapper.updateWmsFlagByOrder(gongxiaoOutboundOrderNo,syncToWmsFlag,dataVersion,prefix);
    }

    public int updateOutboundWmsStatus(String gongxiaoOutboundOrderNo, int syncWmsStatus, String prefix){
        return outboundOrderMapper.updateOutboundWmsStatus(gongxiaoOutboundOrderNo,syncWmsStatus,prefix);
    }
}
