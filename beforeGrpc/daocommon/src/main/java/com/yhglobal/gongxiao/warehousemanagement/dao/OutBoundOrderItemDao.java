package com.yhglobal.gongxiao.warehousemanagement.dao;

import com.yhglobal.gongxiao.warehousemanagement.dao.mapping.OutboundOrderItemMapper;
import com.yhglobal.gongxiao.warehousemanagement.model.OutboundOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OutBoundOrderItemDao {
    @Autowired
    OutboundOrderItemMapper outboundOrderItemMapper;

    public int insertOutboundOrderItem(List<OutboundOrderItem> outboundOrderItemList) {
        return outboundOrderItemMapper.insertOutboundOrderItem(outboundOrderItemList);
    }

    public List<OutboundOrderItem> selectOutboundOrderItems(String projectId, String gongxiaoOutboundOrderNo) {
        return outboundOrderItemMapper.selectOutboundOrderItems(projectId,gongxiaoOutboundOrderNo);
    }

    public int updateOutboundOrderItemInfo(long rowId, String outboundOrderNo, int outStockQuantity, int imperfectQuantity, int realOutStockQuantity, long dataVersion) {
        return outboundOrderItemMapper.updateOutboundOrderItemInfo(rowId,outboundOrderNo,outStockQuantity,imperfectQuantity,realOutStockQuantity, dataVersion);
    }

    public int updateByCondition(String projectId,String gongxiaoOutboundOrderNo,String productCode,int adjustmentQuantity) {
        return outboundOrderItemMapper.updateByCondition(projectId,gongxiaoOutboundOrderNo,productCode,adjustmentQuantity);
    }

    public List<OutboundOrderItem> selectRecordItemByOutboundOrderNo(String projectId, String gongxiaoOutboundOrderNo) {
        return outboundOrderItemMapper.selectRecordItemByOutboundOrderNo(projectId,gongxiaoOutboundOrderNo);
    }

    public int deleteOrderByOrderNo(String projectId,String gongxiaoOutboundOrderNo){
        return outboundOrderItemMapper.deleteOrderByOrderNo(projectId,gongxiaoOutboundOrderNo);
    }

    public int sureSighIn(String gongxiaoOutboundOrderNo){
        return outboundOrderItemMapper.sureSighIn(gongxiaoOutboundOrderNo);
    }

    public List<OutboundOrderItem> selectOutboundOrderItemByNo(String gongxiaoOutboundOrderNo){
        return outboundOrderItemMapper.selectOutboundOrderItemByNo(gongxiaoOutboundOrderNo);
    }

    public int cancelOrder(String gongxiaoOutboundOrderNo){
        return outboundOrderItemMapper.cancelOrder(gongxiaoOutboundOrderNo);
    }

    public int closeOrder(String gongxiaoOutboundOrderNo){
        return outboundOrderItemMapper.closeOrder(gongxiaoOutboundOrderNo);
    }

    public List<OutboundOrderItem> selectItemByNoAndProductCode(String gongxiaoOutboundOrderNo,String productCode,int inventoryType){
        return outboundOrderItemMapper.selectItemByNoAndProductCode(gongxiaoOutboundOrderNo,productCode,inventoryType);
    }

    public OutboundOrderItem getOutboundOrderItemById(long rowId) {
        return outboundOrderItemMapper.getOutboundOrderItemById(rowId);
    }

    public OutboundOrderItem getOutboundOrderItemByItemNo(String outboundOrderItemNo) {
        return outboundOrderItemMapper.getOutboundOrderItemByItemNo(outboundOrderItemNo);
    }

    public OutboundOrderItem selectRecordBySalseNoAndProduct(String projectId, String salesOrderNo, String productCode){
        return outboundOrderItemMapper.selectRecordBySalseNoAndProduct(projectId, salesOrderNo, productCode);
    }

    public OutboundOrderItem selectItemByNoAndProductCodeAndBatchNo(String gongxiaoOutboundOrderNo,String batchNo,String productCode,int inventoryType){
        return outboundOrderItemMapper.selectItemByNoAndProductCodeAndBatchNo(gongxiaoOutboundOrderNo,batchNo,productCode,inventoryType);
    }
}
