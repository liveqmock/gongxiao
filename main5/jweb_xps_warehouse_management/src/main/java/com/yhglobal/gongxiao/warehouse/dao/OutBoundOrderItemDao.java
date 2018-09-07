package com.yhglobal.gongxiao.warehouse.dao;

import com.yhglobal.gongxiao.warehouse.dao.mapping.OutboundOrderItemMapper;
import com.yhglobal.gongxiao.warehouse.model.OutboundOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OutBoundOrderItemDao {
    @Autowired
    OutboundOrderItemMapper outboundOrderItemMapper;

    public int insertOutboundOrderItem(List<OutboundOrderItem> outboundOrderItemList,String prefix) {
        return outboundOrderItemMapper.insertOutboundOrderItem(outboundOrderItemList,prefix);
    }

    public List<OutboundOrderItem> selectOutboundOrderItems(String projectId, String gongxiaoOutboundOrderNo,String prefix) {
        return outboundOrderItemMapper.selectOutboundOrderItems(projectId,gongxiaoOutboundOrderNo,prefix);
    }

    public int updateOutboundOrderItemInfo(long rowId, String outboundOrderNo, int outStockQuantity, int imperfectQuantity, int realOutStockQuantity, long dataVersion,String prefix) {
        return outboundOrderItemMapper.updateOutboundOrderItemInfo(rowId,outboundOrderNo,outStockQuantity,imperfectQuantity,realOutStockQuantity, dataVersion,prefix);
    }

    public int updateByCondition(String projectId,String gongxiaoOutboundOrderNo,String productCode,int adjustmentQuantity,String prefix) {
        return outboundOrderItemMapper.updateByCondition(projectId,gongxiaoOutboundOrderNo,productCode,adjustmentQuantity,prefix);
    }

    public List<OutboundOrderItem> selectRecordItemByOutboundOrderNo(String projectId, String gongxiaoOutboundOrderNo,String prefix) {
        return outboundOrderItemMapper.selectRecordItemByOutboundOrderNo(projectId,gongxiaoOutboundOrderNo,prefix);
    }

    public int deleteOrderByOrderNo(String projectId,String gongxiaoOutboundOrderNo,String prefix){
        return outboundOrderItemMapper.deleteOrderByOrderNo(projectId,gongxiaoOutboundOrderNo,prefix);
    }

    public int sureSighIn(String gongxiaoOutboundOrderNo,String prefix){
        return outboundOrderItemMapper.sureSighIn(gongxiaoOutboundOrderNo,prefix);
    }

    public List<OutboundOrderItem> selectOutboundOrderItemByNo(String gongxiaoOutboundOrderNo,String prefix){
        return outboundOrderItemMapper.selectOutboundOrderItemByNo(gongxiaoOutboundOrderNo,prefix);
    }

    public int cancelOrder(String gongxiaoOutboundOrderNo,String prefix){
        return outboundOrderItemMapper.cancelOrder(gongxiaoOutboundOrderNo,prefix);
    }

    public int closeOrder(String gongxiaoOutboundOrderNo,String prefix){
        return outboundOrderItemMapper.closeOrder(gongxiaoOutboundOrderNo,prefix);
    }

    public List<OutboundOrderItem> selectItemByNoAndProductCode(String gongxiaoOutboundOrderNo,String productCode,int inventoryType,String prefix){
        return outboundOrderItemMapper.selectItemByNoAndProductCode(gongxiaoOutboundOrderNo,productCode,inventoryType,prefix);
    }

    public OutboundOrderItem getOutboundOrderItemById(long rowId,String prefix) {
        return outboundOrderItemMapper.getOutboundOrderItemById(rowId,prefix);
    }

    public OutboundOrderItem getOutboundOrderItemByItemNo(String outboundOrderItemNo,String prefix) {
        return outboundOrderItemMapper.getOutboundOrderItemByItemNo(outboundOrderItemNo,prefix);
    }

    public OutboundOrderItem selectRecordBySalseNoAndProduct(String projectId, String salesOrderNo, String productCode,String prefix){
        return outboundOrderItemMapper.selectRecordBySalseNoAndProduct(projectId, salesOrderNo, productCode,prefix);
    }

    public OutboundOrderItem selectItemByNoAndProductCodeAndBatchNo(String gongxiaoOutboundOrderNo,String batchNo,String productCode,int inventoryType,String prefix){
        return outboundOrderItemMapper.selectItemByNoAndProductCodeAndBatchNo(gongxiaoOutboundOrderNo,batchNo,productCode,inventoryType,prefix);
    }

    public int midifyReturnQuantity(String gongxiaoOutboundOrderNo, String productCode, int quantity, String projectPrefix){
        return outboundOrderItemMapper.midifyReturnQuantity(gongxiaoOutboundOrderNo,productCode,quantity,projectPrefix);
    }
}
