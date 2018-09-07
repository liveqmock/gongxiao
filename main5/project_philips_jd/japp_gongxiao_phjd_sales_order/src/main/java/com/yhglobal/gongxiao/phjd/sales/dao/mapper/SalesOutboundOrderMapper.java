package com.yhglobal.gongxiao.phjd.sales.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.phjd.sales.model.SalesOutboundOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * 销售出库单
 *
 * @author weizecheng
 * @date 2018/8/29 11:18
 */
public interface SalesOutboundOrderMapper  extends BaseMapper {

    /**
     * 插入销售订单出库单
     *
     * @author weizecheng
     * @date 2018/8/29 11:22
     * @param prefix 表前缀
     * @param order 销售订单出库
     * @return int
     */
    @Insert({
            "insert into ${prefix}_sales_outbound_order ( outboundOrderNo, ",
            "expectedArrivalTime, salesOrderNo, ",
            "totalQuantity, orderStatus, ",
            "outboundFinishedTime, syncToEas, ",
            "syncToTms, tmsOrdNo, ",
            "tmsPostedBy, tmsSignedPhone, ",
            "tmsSignedBy, transporter, ",
            "signedRemark, dataVersion, ",
            "createdById, createdByName, ",
            "createTime, lastUpdateTime, ",
            "items,warehouseId)",
            "values (#{record.outboundOrderNo}, ",
            "#{record.expectedArrivalTime}, #{record.salesOrderNo}, ",
            "#{record.totalQuantity}, #{record.orderStatus}, ",
            "#{record.outboundFinishedTime}, #{record.syncToEas}, ",
            "#{record.syncToTms}, #{record.tmsOrdNo}, ",
            "#{record.tmsPostedBy}, #{record.tmsSignedPhone}, ",
            "#{record.tmsSignedBy}, #{record.transporter}, ",
            "#{record.signedRemark}, #{record.dataVersion}, ",
            "#{record.createdById}, #{record.createdByName}, ",
            "NOW(),NOW(), ",
            "#{record.items},#{record.warehouseId})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") SalesOutboundOrder order);

    /**
     *根据出库单号获取出库单
     *
     * @author weizecheng
     * @date 2018/8/29 12:20
     * @param prefix 表前缀
     * @param outboundOrderNo  出库单号
     * @return SalesOutboundOrder
     */
    @Select({
            "select",
            "oid, outboundOrderNo",
            "expectedArrivalTime, salesOrderNo",
            "totalQuantity, orderStatus",
            "outboundFinishedTime, syncToEas",
            "syncToTms, tmsOrdNo",
            "tmsPostedBy, tmsSignedPhone",
            "tmsSignedBy, transporter",
            "signedRemark, dataVersion",
            "createdById, createdByName",
            "createTime, lastUpdateTime",
            "items,warehouseId",
            "from ${prefix}_sales_outbound_order",
            "WHERE outboundOrderNo = #{outboundOrderNo}"
    })
    SalesOutboundOrder getByOutboundOrderNo(@Param("prefix") String prefix,
                                            @Param("outboundOrderNo")String outboundOrderNo);

    /**
     * 更新Tms状态
     *
     * @author weizecheng
     * @date 2018/8/29 16:23
     * @param prefix 表前缀
     * @param id 出库单Id
     * @param dataVersion 版本号
     * @param status Tms状态
     * @return
     */
    @Update({
            "update ${prefix}_sales_outbound_order",
            "set ",
            "syncToTms = #{syncTms},",
            "dataVersion = dataVersion + 1",
            "where oid = #{oid} and dataVersion = #{dataVersion}"
    })
    int updateSyncToTms(@Param("prefix") String prefix,
                        @Param("oid")Long id,
                        @Param("dataVersion")Long dataVersion,
                        @Param("syncTms")Integer status);




    /**
     * 更新出库状态和时间
     *
     * @author weizecheng
     * @date 2018/8/31 18:54
     * @param prefix 表前缀
     * @param oid 主键ID
     * @param dataVersion 版本号
     * @param orderStatus 出库单状态
     * @param outboundFinishedTime 出库时间
     * @return int
     */
    @Update({
            "update ${prefix}_sales_outbound_order",
            "set ",
            "orderStatus = #{orderStatus},",
            "outboundFinishedTime = #{outboundFinishedTime},",
            "dataVersion = dataVersion + 1",
            "where oid = #{oid} and dataVersion = #{dataVersion}"
    })
    int updateOutboundTime(@Param("prefix") String prefix,
                           @Param("oid") long oid,
                           @Param("dataVersion") long dataVersion,
                           @Param("orderStatus") int orderStatus,
                           @Param("outboundFinishedTime") Date outboundFinishedTime);

    /**
     * 更新信息
     *
     * @param prefix 表前缀
     * @param oid 主表Id
     * @param dataVersion 版本号
     * @param orderStatus 出库单状态
     * @param tmsOrdNo tms订单号
     * @param signedRemark 签收备注
     * @param signedBy 实际签收人
     * @param postedBy 签收时间维护人
     * @param tmsSignedTime 签收状态
     * @param tmsSignedPhone 签收电话
     * @param transporter 签收时间
     * @return
     */
    @Update({
            "update ${prefix}_sales_outbound_order",
            "set ",
            "orderStatus = #{orderStatus},",
            "tmsOrdNo = #{tmsOrdNo},",
            "tmsPostedBy = #{tmsPostedBy},",
            "tmsSignedPhone = #{tmsSignedPhone},",
            "tmsSignedBy = #{tmsSignedBy},",
            "transporter = #{transporter},",
            "tmsSignedTime = #{tmsSignedTime},",
            "signedRemark = #{signedRemark},",
            "dataVersion = dataVersion + 1",
            "where oid = #{oid} and dataVersion = #{dataVersion}"
    })
    int updateWhenSigned(@Param("prefix") String prefix,
                         @Param("oid") long oid,
                         @Param("dataVersion") long dataVersion,
                         @Param("orderStatus") int orderStatus,
                         @Param("tmsOrdNo") String tmsOrdNo,
                         @Param("signedRemark") String signedRemark,
                         @Param("tmsSignedBy") String signedBy,
                         @Param("tmsPostedBy") String postedBy,
                         @Param("tmsSignedTime") Date tmsSignedTime,
                         @Param("tmsSignedPhone") String tmsSignedPhone,
                         @Param("transporter") String transporter);


    /**
     * 获取同步TMS
     *
     * @author weizecheng
     * @date 2018/9/3 18:16
     * @param prefix 表前缀
     * @param syncToTms 同步TMS状态
     * @return int
     */
    @Select({
            "select",
            "outboundOrderNo",
            "from ${prefix}_sales_outbound_order",
            "where syncToTms = #{syncToTms}"
    })
    List<String> listByTmsStatus(@Param("prefix") String prefix,
                                       @Param("syncToTms") Integer syncToTms);



}
