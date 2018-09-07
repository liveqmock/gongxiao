package com.yhglobal.gongxiao.phjd.sales.dao;

import com.yhglobal.gongxiao.phjd.sales.dao.mapper.SalesOrderItemMapper;
import com.yhglobal.gongxiao.phjd.sales.model.SalesOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author weizecheng
 * @date 2018/8/21 8:45
 */
@Repository
public class SalesOrderItemDao {

    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;

    /**
     * 根据销售订单编号 获取 List<SalesOrderItem></>
     *
     * @param prefix 表前缀
     * @param orderNo 销售订单状态
     * @return List<SalesOrderItem>
     */
    public List<SalesOrderItem> listBySalesOrderNo(String prefix, String orderNo){
        return salesOrderItemMapper.listBySalesOrderNo(prefix,orderNo);
    }
    /**
     * 获取销售订单表
     *
     * @auther weizecheng
     * @date 2018/8/24 11:00
     * @param prefix  表前缀
     * @param orderNo 销售订单号
     * @param productCode 商品编码
     * @return
     */
    public SalesOrderItem getBySalesOrderNoAndProductCode(String prefix, String orderNo,String productCode){
        return salesOrderItemMapper.getBySalesOrderNoAndProductCode(prefix,orderNo,productCode);
    }
    /**
     * 审核订单更新
     *
     * @auther weizecheng
     * @date 2018/8/24 10:56
     * @param prefix 表前缀
     * @param dataVersion 版本号 乐观锁
     * @param id 订单商品表Id
     * @param shortageReason 缺货信息
     * @param status 订单商品表状态
     * @param totalOrderAmount 实际付款金额
     * @param totalQuantity 实际总量
     * @return
     */
    public int updateReviewSalesOrderItem(String prefix, Long id, Integer status, String shortageReason, Integer totalQuantity, BigDecimal totalOrderAmount, Long dataVersion){
        return salesOrderItemMapper.updateReviewSalesOrderItem(prefix, id, status, shortageReason, totalQuantity, totalOrderAmount, dataVersion);
    }
    /**
     *更新待预约发货数量
     *
     * @author weizecheng
     * @date 2018/8/29 10:24
     * @param prefix 表前缀
     * @param id 销售订单商品表Id
     * @param dataVersion 版本号
     * @param unhandledQuantity 待预约发货数量
     * @return int
     */
    public int updateUnhandledQuantitySalesOrderItem(String prefix, Long id,Long dataVersion,Integer unhandledQuantity ){
        return salesOrderItemMapper.updateUnhandledQuantitySalesOrderItem(prefix, id, dataVersion, unhandledQuantity);
    }
    /**
     * 插入销售订单
     *
     * @author weizecheng
     * @date 2018/8/29 15:46
     * @param prefix 表前缀
     * @param salesOrderItem 销售订单商品
     * @return int
     */
    public int insertSalesOrderItem(String prefix,SalesOrderItem salesOrderItem){
        return salesOrderItemMapper.insertSalesOrderItem(prefix,salesOrderItem);
    }

    /**
     * 更新已送达数量 和 在途数量
     *
     * @author weizecheng
     * @date 2018/8/31 19:28
     * @param prefix 表前缀
     * @param salesOrderItemId 主键Id
     * @param dataVersion 版本号
     * @param deliveredQuantity 已送达数量
     * @param inTransitQuantity 在途数量
     * @return int
     */
    public int updateDeliveryAndInTransitQuantity(String prefix,Long salesOrderItemId, Long dataVersion, int deliveredQuantity, int inTransitQuantity,
                                                  BigDecimal generatedPrepaid,
                                                  BigDecimal shouldReceiveGrossProfit,
                                                  BigDecimal receivedGrossProfit) {
        return salesOrderItemMapper.updateDeliveryAndInTransitQuantity(prefix, salesOrderItemId, dataVersion, deliveredQuantity, inTransitQuantity, generatedPrepaid, shouldReceiveGrossProfit, receivedGrossProfit);
    }


    /**
     * 更新已送达数量 和 在途数量
     *
     * @author weizecheng
     * @date 2018/8/31 19:28
     * @param prefix 表前缀
     * @param salesOrderItemId 主键Id
     * @param dataVersion 版本号
     * @param deliveredQuantity 已送达数量
     * @param inTransitQuantity 在途数量
     * @return int
     */
    public int updateDeliveryAndInTransitQuantitySign(String prefix,Long salesOrderItemId, Long dataVersion, int deliveredQuantity, int inTransitQuantity){
        return salesOrderItemMapper.updateDeliveryAndInTransitQuantitySign(prefix, salesOrderItemId, dataVersion, deliveredQuantity, inTransitQuantity);
    }

    /**
     * 更新销售订单商品Eas信息
     *
     * @author weizecheng
     * @date 2018/9/3 18:00
     * @param prefix
     * @param salesOrderItemId
     * @param dataVersion
     * @param entryId
     * @return
     */
    public int updateEasEntryId(String prefix,Long salesOrderItemId, Long dataVersion, String entryId) {
        return salesOrderItemMapper.updateEasEntryId(prefix,salesOrderItemId, dataVersion, entryId);
    }

}
