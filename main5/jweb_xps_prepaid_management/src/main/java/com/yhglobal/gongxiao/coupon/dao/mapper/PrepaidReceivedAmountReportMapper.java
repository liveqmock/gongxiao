package com.yhglobal.gongxiao.coupon.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.coupon.model.PrepaidReceivedAmountReport;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface PrepaidReceivedAmountReportMapper extends BaseMapper{
    @Delete({
        "delete from ${tablePrefix}_prepaid_received_amount_report",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Delete({
            "delete from ${tablePrefix}_prepaid_received_amount_report"
    })
    int deleteAllData(@Param("tablePrefix")String tablePrefix);
    @Insert({
        "insert into ${tablePrefix}_prepaid_received_amount_report (id, month, ",
        "projectId, lastUpdateTime, ",
        "dataVersion, toReceiveAmountTotal, ",
        "receiptAmountTotal, rate)",
        "values (#{id}, #{month}, ",
        "#{projectId}, #{lastUpdateTime}, ",
        "#{dataVersion}, #{toReceiveAmountTotal}, ",
        "#{receiptAmountTotal}, #{rate})"
    })
    int insert(PrepaidReceivedAmountReport record);


    @Select({
        "select",
        "id, month, projectId, lastUpdateTime, dataVersion, toReceiveAmountTotal, receiptAmountTotal",
        "from ${tablePrefix}_prepaid_received_amount_report",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="month", property="month", jdbcType=JdbcType.INTEGER),
        @Result(column="projectId", property="projectId", jdbcType=JdbcType.BIGINT),
        @Result(column="lastUpdateTime", property="lastUpdateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="dataVersion", property="dataVersion", jdbcType=JdbcType.BIGINT),
        @Result(column="toReceiveAmountTotal", property="toReceiveAmountTotal", jdbcType=JdbcType.BIGINT),
        @Result(column="receiptAmountTotal", property="receiptAmountTotal", jdbcType=JdbcType.BIGINT)
    })
    PrepaidReceivedAmountReport selectByPrimaryKey(Long id);

    @Select({
            "select",
            "id, month, projectId, lastUpdateTime, dataVersion, toReceiveAmountTotal, receiptAmountTotal, rate",
            "from ${tablePrefix}_prepaid_received_amount_report",
            "order by month ASC"
    })
    List<PrepaidReceivedAmountReport> selectAll(@Param("tablePrefix")String tablePrefix);
    @Update({
        "update ${tablePrefix}_prepaid_received_amount_report",
        "set month = #{month,jdbcType=INTEGER},",
          "projectId = #{projectId,jdbcType=BIGINT},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "dataVersion = #{dataVersion,jdbcType=BIGINT},",
          "toReceiveAmountTotal = #{toReceiveAmountTotal,jdbcType=BIGINT},",
          "receiptAmountTotal = #{receiptAmountTotal,jdbcType=BIGINT}, rate = #{rate}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PrepaidReceivedAmountReport record);


}