package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.dao.mapping.SalesReturnOrderItemMapper;
import com.yhglobal.gongxiao.sales.model.cancel.model.SalesReturnOrderItem;
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

    private Logger logger = LoggerFactory.getLogger(SalesReturnOrderItemDao.class);

    @Autowired
    private SalesReturnOrderItemMapper salesReturnOrderItemMapper;


    public int addSalesReturnOrderItem(SalesReturnOrderItem salesReturnOrderItem) {
        int result = salesReturnOrderItemMapper.addSalesReturnOrderItem(salesReturnOrderItem);
        return result;
    }

    public List<SalesReturnOrderItem> selectBySalesReturnedOrderNo(String salesReturnedOrderNo) {
        List<SalesReturnOrderItem> result = salesReturnOrderItemMapper.selectBySalesReturnedOrderNo(salesReturnedOrderNo);
        return result;
    }

    /**
     * 更新入库单号
     *
     * @param rowId,
     * @param dataVersion
     * @return
     */
    public int updateInboundOrderNo(String gongxiaoWarehouseInboundOrderNo, Long rowId, Long dataVersion) {
        int result = salesReturnOrderItemMapper.updateInboundOrderNo(gongxiaoWarehouseInboundOrderNo, rowId, dataVersion);
        return result;
    }

    /**
     * 按入库单号 商品编码查询
     * @param inBOundOrderNo
     * @param productCode
     * @return
     */
    public SalesReturnOrderItem selectSalesReturnOrderItemByInBoundOrderNo(String inBOundOrderNo,String productCode){
        return salesReturnOrderItemMapper.selectSalesReturnOrderItemByInBoundOrderNo(inBOundOrderNo,productCode);
    }

    /**
     * 更新退货到仓数量
     * @param totalInStockQuantity
     * @param rowId
     * @param dataVersion
     * @return
     */
    public int updateInboundRecord(int totalInStockQuantity,String wmsInboundRecord,Long rowId, Long dataVersion ){
        return salesReturnOrderItemMapper.updateInboundRecord(totalInStockQuantity,wmsInboundRecord,rowId,dataVersion);
    }
}
