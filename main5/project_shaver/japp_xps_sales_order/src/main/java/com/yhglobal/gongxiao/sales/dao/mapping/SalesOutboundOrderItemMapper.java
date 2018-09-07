package com.yhglobal.gongxiao.sales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.sales.model.SalesOutboundOrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 葛灿
 */
public interface SalesOutboundOrderItemMapper extends BaseMapper {

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
     * @return 插入成功的记录数
     */
    @Insert({
            "INSERT INTO ${prefix}_sales_outbound_order_item",
            "(productCode,  outboundOrderNo,",
            "salesOrderNo, quantity, ",
            "grossProfitValue, wholesalePrice, ",
            "salesGuidePrice, purchasePrice,",
            "yhDiscountPercent, sellHighAmount,",
            "generatedPrepaid, shouldReceiveGrossProfit,",
            "receivedGrossProfit, createTime)",
            "VALUES",
            "( #{productCode},  #{outboundOrderNo},",
            "#{salesOrderNo}, #{quantity}, ",
            "#{grossProfitValue}, #{wholesalePrice}, ",
            "#{salesGuidePrice}, #{purchasePrice},",
            "#{yhDiscountPercent}, #{sellHighAmount},",
            "#{generatedPrepaid}, #{shouldReceiveGrossProfit},",
            "#{receivedGrossProfit}, NOW() )",

    })
    int insert(@Param("prefix") String prefix,
               @Param("productCode") String productCode,
               @Param("outboundOrderNo") String outboundOrderNo,
               @Param("salesOrderNo") String salesOrderNo,
               @Param("quantity") int quantity,
               @Param("grossProfitValue") long grossProfitValue,
               @Param("wholesalePrice") long wholesalePrice,
               @Param("salesGuidePrice") long salesGuidePrice,
               @Param("purchasePrice") long purchasePrice,
               @Param("yhDiscountPercent") long yhDiscountPercent,
               @Param("sellHighAmount") long sellHighAmount,
               @Param("generatedPrepaid") long generatedPrepaid,
               @Param("shouldReceiveGrossProfit") long shouldReceiveGrossProfit,
               @Param("receivedGrossProfit") long receivedGrossProfit);

    @Select({
            "SELECT productCode,  outboundOrderNo,",
            "salesOrderNo, quantity, ",
            "grossProfitValue, wholesalePrice, ",
            "salesGuidePrice, purchasePrice,",
            "yhDiscountPercent, sellHighAmount,",
            "generatedPrepaid, shouldReceiveGrossProfit,",
            "receivedGrossProfit",
            "FROM ${prefix}_sales_outbound_order_item",
            "WHERE outboundOrderNo = #{outboundOrderNo}"
    })
    List<SalesOutboundOrderItem> selectListByOutboundOrderNo(@Param("prefix") String prefix,
                                                             @Param("outboundOrderNo") String outboundOrderNo);
}
