package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.dao.mapping.SalesReturnOrderItemMapper;
import com.yhglobal.gongxiao.sales.model.SalesReturnOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 销售退货详情DAO
 * @Author: LUOYI
 * @Date: Created in 10:11 2018/3/21
 */
@Repository
public class SalesReturnOrderItemDao {

    @Autowired
    private SalesReturnOrderItemMapper salesReturnOrderItemMapper;


    public int addSalesReturnOrderItem(String prefix, SalesReturnOrderItem salesReturnOrderItem) {
        int result = salesReturnOrderItemMapper.addSalesReturnOrderItem(prefix, salesReturnOrderItem);
        return result;
    }

    public List<SalesReturnOrderItem> selectBySalesReturnedOrderNo(String prefix, String salesReturnedOrderNo) {
        List<SalesReturnOrderItem> result = salesReturnOrderItemMapper.selectBySalesReturnedOrderNo(prefix, salesReturnedOrderNo);
        return result;
    }

    /**
     * 更新入库单号
     *
     * @param rowId,
     * @param dataVersion
     * @return
     */
    public int updateInboundOrderNo(String prefix, String gongxiaoWarehouseInboundOrderNo, Long rowId, Long dataVersion) {
        int result = salesReturnOrderItemMapper.updateInboundOrderNo(prefix, gongxiaoWarehouseInboundOrderNo, rowId, dataVersion);
        return result;
    }

    /**
     * 按入库单号 商品编码查询
     *
     * @param inBOundOrderNo
     * @param productCode
     * @return
     */
    public SalesReturnOrderItem selectSalesReturnOrderItemByInBoundOrderNo(String prefix, String inBOundOrderNo, String productCode) {
        return salesReturnOrderItemMapper.selectSalesReturnOrderItemByInBoundOrderNo(prefix, inBOundOrderNo, productCode);
    }

    /**
     * 更新退货到仓数量
     *
     * @param totalInStockQuantity
     * @param rowId
     * @param dataVersion
     * @return
     */
    public int updateInboundRecord(String prefix, int totalInStockQuantity, String wmsInboundRecord, Long rowId, Long dataVersion) {
        return salesReturnOrderItemMapper.updateInboundRecord(prefix, totalInStockQuantity, wmsInboundRecord, rowId, dataVersion);
    }
}
