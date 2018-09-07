package com.yhglobal.gongxiao.coupon.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;

import com.yhglobal.gongxiao.coupon.model.YhglobalCouponWriteoffRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

/**
 * 越海返利记录表Mapper
 *
 * @Author: 王帅
 */
public interface YhglobalCouponLedgerWriteoffRecordMapper extends BaseMapper {

    /**
     *  插入一条返利核销记录
     * */
    @Insert({
            "insert into ${tablePrefix}_yhglobal_coupon_ledger_writeoff_record ( ",
            "confirmId, toReceiveCouponId, ",
            "confirmAmount, receiptAmount, ",
            "flowNo, dataVersion, ",
            "createTime,lastUpdateTime, ",
            "receivedCurrencyCode, confirmCurrencyCode,philipDocumentNo, ",
            "createdById, createdByName,accountType,isRollback, projectId,useDate)",
            "values (",
            "#{confirmId}, #{toReceiveCouponId}, ",
            "#{confirmAmount}, #{receiptAmount}, ",
            "#{flowNo}, #{dataVersion}, ",
            "#{createTime}, #{lastUpdateTime}, ",
            "#{receivedCurrencyCode}, #{confirmCurrencyCode},#{philipDocumentNo}, ",
            "#{createdById}, #{createdByName}," ,
            "#{accountType}, #{isRollback}, #{projectId}, #{useDate})"
    })
    int insert(YhglobalCouponWriteoffRecord record);


    /**
     *  更新流水号
     * */
    @Update({"update ${tablePrefix}_yhglobal_coupon_ledger_writeoff_record" +
            "set flowNo = #{flowNo} " +
            "where confirmId = #{confirmId}"})
    int updateFlowNoByPrimaryKey(@Param("tablePrefix")String tablePrefix, @Param("flowNo") String flowNo, @Param("confirmId") long confirmId);


    @Select({
            "select",
            "confirmId, toReceiveCouponId, confirmAmount, receiptAmount, flowNo, dataVersion, ",
            "createTime, lastUpdateTime, receivedCurrencyCode, confirmCurrencyCode, createdById, ",
            "createdByName,philipDocumentNo",
            "from ${tablePrefix}_yhglobal_coupon_ledger_writeoff_record",
            "where confirmId = #{confirmId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="confirmId", property="confirmId", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="toReceiveCouponId", property="toReceiveCouponId", jdbcType= JdbcType.BIGINT),
            @Result(column="confirmAmount", property="confirmAmount", jdbcType= JdbcType.BIGINT),
            @Result(column="receiptAmount", property="receiptAmount", jdbcType= JdbcType.BIGINT),
            @Result(column="flowNo", property="flowNo", jdbcType= JdbcType.VARCHAR),
            @Result(column="dataVersion", property="dataVersion", jdbcType= JdbcType.BIGINT),
            @Result(column="createTime", property="createTime", jdbcType= JdbcType.TIMESTAMP),
            @Result(column="lastUpdateTime", property="lastUpdateTime", jdbcType= JdbcType.TIMESTAMP),
            @Result(column="receivedCurrencyCode", property="receivedCurrencyCode", jdbcType= JdbcType.VARCHAR),
            @Result(column="confirmCurrencyCode", property="confirmCurrencyCode", jdbcType= JdbcType.VARCHAR),
            @Result(column="createdById", property="createdById", jdbcType= JdbcType.BIGINT),
            @Result(column="createdByName", property="createdByName", jdbcType= JdbcType.VARCHAR),
            @Result(column = "philipDocumentNo", property = "philipDocumentNo", jdbcType = JdbcType.VARCHAR)
    })
    YhglobalCouponWriteoffRecord selectByPrimaryKey(@Param("tablePrefix")String tablePrefix,@Param("confirmId")Long confirmId);


    @Select({
            "select",
            "confirmId, toReceiveCouponId, confirmAmount, receiptAmount, flowNo, dataVersion, ",
            "createTime, lastUpdateTime, receivedCurrencyCode, confirmCurrencyCode, createdById,isRollback ",
            "createdByName,accountType,philipDocumentNo",
            "from ${tablePrefix}_yhglobal_coupon_ledger_writeoff_record",
            "where flowNo = #{flowNo,jdbcType=VARCHAR}"})
    @Results({
            @Result(column="confirmId", property="confirmId", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="toReceiveCouponId", property="toReceiveCouponId", jdbcType= JdbcType.BIGINT),
            @Result(column="confirmAmount", property="confirmAmount", jdbcType= JdbcType.BIGINT),
            @Result(column="receiptAmount", property="receiptAmount", jdbcType= JdbcType.BIGINT),
            @Result(column="flowNo", property="flowNo", jdbcType= JdbcType.VARCHAR),
            @Result(column="dataVersion", property="dataVersion", jdbcType= JdbcType.BIGINT),
            @Result(column="createTime", property="createTime", jdbcType= JdbcType.TIMESTAMP),
            @Result(column="lastUpdateTime", property="lastUpdateTime", jdbcType= JdbcType.TIMESTAMP),
            @Result(column="receivedCurrencyCode", property="receivedCurrencyCode", jdbcType= JdbcType.VARCHAR),
            @Result(column="confirmCurrencyCode", property="confirmCurrencyCode", jdbcType= JdbcType.VARCHAR),
            @Result(column="createdById", property="createdById", jdbcType= JdbcType.BIGINT),
            @Result(column="isRollback", property="isRollback", jdbcType= JdbcType.INTEGER),
            @Result(column="createdByName", property="createdByName", jdbcType= JdbcType.INTEGER),
            @Result(column="accountType", property="accountType", jdbcType= JdbcType.BIGINT),
            @Result(column = "philipDocumentNo",property = "philipDocumentNo", jdbcType = JdbcType.VARCHAR)
    })
    List<YhglobalCouponWriteoffRecord> selectByFlowId(@Param("tablePrefix")String tablePrefix,@Param("flowNo")String flowNo);

    /*@UpdateProvider(type=${tablePrefix}_yhglobal_coupon_ledger_writeoff_recordSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(${tablePrefix}_yhglobal_coupon_ledger_writeoff_record record);*/

    @Update({
            "update ${tablePrefix}_yhglobal_coupon_ledger_writeoff_record",
            "set isRollback = #{isRollback,jdbcType=INTEGER}",
            "where confirmId = #{confirmId,jdbcType=BIGINT}"})
    int updateByPrimaryKey(@Param("tablePrefix")String tablePrefix,@Param("record") YhglobalCouponWriteoffRecord record);

    @Update({
            "update ${tablePrefix}_yhglobal_coupon_ledger_writeoff_record",
            "set isRollback = #{isRollback,jdbcType=INTEGER}",
            "where confirmId = #{confirmId,jdbcType=BIGINT}"})
    int updateRollbackStatus(@Param("tablePrefix")String tablePrefix,@Param("isRollback") int isRollback,@Param("confirmId") Long confirmId);

    @SelectProvider(type = YhglobalCouponRecordSqlProvider.class, method = "searchByToReceiveCouponIdAndFlowNo")
    List<YhglobalCouponWriteoffRecord> selectByToReceiveCouponId(@Param("tablePrefix")String tablePrefix,@Param("toReceiveCouponId") Long toReceiveCouponId,
                                                                 @Param("flowNo") String flowNo);

    @SelectProvider(type = YhglobalCouponRecordSqlProvider.class, method = "searchCouponConfirm")
    List<YhglobalCouponWriteoffRecord> searchCouponConfirm(@Param("tablePrefix")String tablePrefix,@Param("projectId") Long projectId,
                                                           @Param("flowNo") String flowNo,
                                                           @Param("accountType") Integer accountType,
                                                           @Param("useDateStart") Date useDateStart,
                                                           @Param("useDateEnd") Date useDateEnd,
                                                           @Param("dateStart") Date dateStart,
                                                           @Param("dateEnd") Date dateEnd);

}