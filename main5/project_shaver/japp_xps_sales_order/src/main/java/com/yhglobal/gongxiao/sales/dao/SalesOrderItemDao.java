package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.dao.mapping.SalesOrderItemMapper;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 销售订单商品表dao
 *
 * @Author: 葛灿
 * @Date:2018/2/28--11:37
 */
@Repository
public class SalesOrderItemDao {
    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;

    /**
     * 根据销售单号查询
     *
     * @param orderNo 销售单号
     * @return SalesOrderItem
     * @author 葛灿
     * @date 2018/2/28 11:43
     */
    public List<SalesOrderItem> selectListBySalesOrderNo(String prefix, String orderNo) {
        return salesOrderItemMapper.selectListBySalesOrderNo(prefix,orderNo);
    }

//    /**
//     * 更新销售单信息
//     *
//     * @param record
//     * @return int
//     * @author 葛灿
//     * @date 2018/2/28 11:49
//     */
//    public int update(SalesOrderItem record) {
//        return salesOrderItemMapper.updateByPrimaryKeyWithBLOBs(record);
//    }

    /**
     * 插入销售单信息
     *
     * @param record
     * @return int
     * @author 葛灿
     * @date 2018/2/28 11:51
     */
    public int insert(String prefix,SalesOrderItem record) {
        return salesOrderItemMapper.insert(prefix,record);
    }


    /**
     * 根据销售单号、货品编码查询销售单商品信息
     *
     * @param salesOrderNo 销售单号
     * @param productCode  货品编码
     * @return SalesOrderItem
     */
    public SalesOrderItem getSalesOrderItemBySalesOrderNoAndProductCode(String prefix,String salesOrderNo, String productCode) {
        return salesOrderItemMapper.getSalesOrderItemBySalesOrderNoAndProductCode(prefix,salesOrderNo, productCode);
    }

    /**
     * 根据销售单号、货品编码查询销售单商品信息
     *
     * @param salesOrderNo   销售单号
     * @param easProductCode eas货品编码
     * @return SalesOrderItem
     */
    public SalesOrderItem getSalesOrderItemBySalesOrderNoAndEasProductCode(String prefix,String salesOrderNo, String easProductCode) {
        return salesOrderItemMapper.getSalesOrderItemBySalesOrderNoAndEasProductCode(prefix,salesOrderNo, easProductCode);
    }

    /**
     * 更新货品使用的返利、代垫金额
     *
     * @param salesOrderItemId  主键id
     * @param dataVersion       数据版本
     * @param usedCouponAmount  使用返利金额(单价)
     * @param userPrepaidAmount 使用代垫金额(单价)
     * @return 更新成功条数
     */
    public int updateCouponPrepaidAmount(String prefix,Long salesOrderItemId, Long dataVersion, long usedCouponAmount, long userPrepaidAmount) {
        return salesOrderItemMapper.updateCouponPrepaidAmount(prefix,salesOrderItemId, dataVersion, usedCouponAmount, userPrepaidAmount);
    }

    /**
     * 更新未处理数量
     *
     * @param salesOrderItemId  主键id
     * @param dataVersion       数据版本
     * @param unhandledQuantity 未处理数量
     * @return 更新成功条数
     */
    public int updateUnhandledQuantity(String prefix,Long salesOrderItemId, Long dataVersion, int unhandledQuantity) {
        return salesOrderItemMapper.updateUnhandledQuantity(prefix,salesOrderItemId, dataVersion, unhandledQuantity);
    }

    /**
     * 更新在途和送达数量
     *
     * @param salesOrderItemId  主键id
     * @param dataVersion       数据版本
     * @param deliveredQuantity 送达数量
     * @param inTransitQuantity 在途数量
     * @return 更新成功条数
     */
    public int updateDeliveryAndInTransitQuantity(String prefix,Long salesOrderItemId, Long dataVersion, int deliveredQuantity, int inTransitQuantity) {
        return salesOrderItemMapper.updateDeliveryAndInTransitQuantity(prefix,salesOrderItemId, dataVersion, deliveredQuantity, inTransitQuantity);
    }

    public int updateEasEntryId(String prefix,Long salesOrderItemId, Long dataVersion, String entryId) {
        return salesOrderItemMapper.updateEasEntryId(prefix,salesOrderItemId, dataVersion, entryId);
    }


    /**
     * 更新销售单明细退货数量
     *
     * @param salesOrderItemId 主键id
     * @param dataVersion      数据版本
     * @param returnedQuantity 退货数量
     * @return 更新成功条数
     */
    public int updateReturnedQuantity(String prefix,Long salesOrderItemId, Long dataVersion, int returnedQuantity) {
        return salesOrderItemMapper.updateReturnedQuantity(prefix,salesOrderItemId, dataVersion, returnedQuantity);
    }
}
