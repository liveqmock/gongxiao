package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.dao.mapping.SalesOutboundOrderItemMapper;
import com.yhglobal.gongxiao.sales.model.SalesOutboundOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 销售出库单商品明细DAO
 *
 * @author 葛灿
 */
@Repository
public class SalesOutboundOrderItemDao {

    @Autowired
    private SalesOutboundOrderItemMapper salesOutboundOrderItemMapper;

    /**
     * 插入出库单商品明细
     *
     * @param productCode              货品编码
     * @param outboundOrderNo          出库单号
     * @param salesOrderNo             销售单号
     * @param quantity                 数量
     * @param grossProfitValue         毛利点位
     * @param wholesalePrice           出货价
     * @param salesGuidePrice          销售指导价
     * @param purchasePrice            采购价
     * @param yhDiscountPercent        越海折扣金额
     * @param sellHighAmount           应收高卖金额
     * @param generatedPrepaid         应收代垫
     * @param shouldReceiveGrossProfit 应收毛利
     * @param receivedGrossProfit      实收毛利
     * @param batchNo                  批次号
     * @return 插入成功的记录数
     */
    public int insert(String prefix,
                      String productCode, String outboundOrderNo,
                      String salesOrderNo, int quantity, long grossProfitValue,
                      long wholesalePrice, long salesGuidePrice, long purchasePrice,
                      long yhDiscountPercent, long sellHighAmount, long generatedPrepaid,
                      long shouldReceiveGrossProfit, long receivedGrossProfit, String batchNo) {
        return salesOutboundOrderItemMapper.insert(prefix, productCode, outboundOrderNo,
                salesOrderNo, quantity, grossProfitValue, wholesalePrice, salesGuidePrice,
                purchasePrice, yhDiscountPercent, sellHighAmount, generatedPrepaid,
                shouldReceiveGrossProfit, receivedGrossProfit,batchNo);
    }

    /**
     * 查询出库单明细
     *
     * @param prefix          表前缀
     * @param outboundOrderNo 出库单号
     * @return 查询结果集
     */
    public List<SalesOutboundOrderItem> selectListByOutboundOrderNo(String prefix, String outboundOrderNo) {
        return salesOutboundOrderItemMapper.selectListByOutboundOrderNo(prefix, outboundOrderNo);
    }
}
