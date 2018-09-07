package com.yhglobal.gongxiao.phjd.sales.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.phjd.sales.dao.mapper.provider.SalesOrderAddressSqlProvider;
import com.yhglobal.gongxiao.phjd.sales.model.SalesOrderAddressDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * 销售订单地址 Dao
 *
 * @author weizecheng
 * @date 2018/8/21 18:29
 */
public interface SalesOrderAddressMapper extends BaseMapper {

    /**
     * 根据销售订单编号 获取销售 地址
     *
     * @author weizecheng
     * @date 2018/8/21 18:33
     * @param prefix 表前缀
     * @param salesOrderId 销售订单号
     * @return
     */
    @Select({
            "select",
            "id, salesOrderId, shippingMode, distributionCenter, distributionCenterId, warehouse, warehouseId",
            "warehouseGln, receiver, receiverTel, receivingAddress, arrived, createTime, lastUpdateTime,dataVersion",
            "from ${prefix}_sales_order_address",
            "where salesOrderId = #{salesOrderId}"
    })
    SalesOrderAddressDO getAddressBySalesOrder(@Param("prefix") String prefix,
                                               @Param("salesOrderId") Long salesOrderId);

    /**
     * 销售订单地址更新
     *
     * @auther weizecheng
     * @date 2018/8/28 12:04
     * @param prefix 表前缀
     * @param dataVersion 版本号
     * @param id 关联的销售订单号
     * @param receivingAddress 详细收货地址
     * @param receiverTel 收货电话
     * @param receiver 收货人姓名
     * @param arrived 最终抵达的省
     * @return int
     */
    @UpdateProvider(type = SalesOrderAddressSqlProvider.class, method = "updateSalesOrderAddress")
    int updateSalesOrderAddress(@Param("prefix") String prefix,
                                @Param("dataVersion")Long dataVersion,
                                @Param("id") Long id,
                                @Param("receivingAddress")String receivingAddress,
                                @Param("receiverTel")String receiverTel,
                                @Param("receiver")String receiver,
                                @Param("arrived")String arrived);

    /**
     * 插入销售订单地址
     *
     * @author weizecheng
     * @date 2018/8/29 15:50
     * @param prefix 表前缀
     * @param salesOrderAddressDO 销售订单地址
     * @return int
     */
    @Insert({
            "insert into ${prefix}_sales_order_address (salesOrderId, ",
            "shippingMode, distributionCenter, ",
            "distributionCenterId, warehouse, ",
            "warehouseId, warehouseGln, ",
            "receiver, receiverTel, ",
            "receivingAddress, arrived, ",
            "createTime,",
            "dataVersion)",
            "values (#{address.salesOrderId,jdbcType=BIGINT}, ",
            "#{address.shippingMode,jdbcType=TINYINT}, #{address.distributionCenter,jdbcType=VARCHAR}, ",
            "#{address.distributionCenterId,jdbcType=VARCHAR}, #{address.warehouse,jdbcType=VARCHAR}, ",
            "#{address.warehouseId,jdbcType=VARCHAR}, #{address.warehouseGln,jdbcType=VARCHAR}, ",
            "#{address.receiver,jdbcType=VARCHAR}, #{address.receiverTel,jdbcType=VARCHAR}, ",
            "#{address.receivingAddress,jdbcType=VARCHAR}, #{address.arrived,jdbcType=VARCHAR}, ",
            "NOW(),",
            "#{address.dataVersion,jdbcType=BIGINT})"
    })
    int insertSalesOrderAddress(@Param("prefix")String prefix,
                                @Param("address")SalesOrderAddressDO salesOrderAddressDO
                                );

}
