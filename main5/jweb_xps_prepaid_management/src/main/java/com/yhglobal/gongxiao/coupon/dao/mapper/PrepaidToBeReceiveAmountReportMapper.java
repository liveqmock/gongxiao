package com.yhglobal.gongxiao.coupon.dao.mapper;


import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.coupon.model.PrepaidToBeReceiveAmountReport;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface PrepaidToBeReceiveAmountReportMapper extends BaseMapper{
    @Delete({
        "delete from ${tablePrefix}_prepaid_to_be_receive_amount_report",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into ${tablePrefix}_prepaid_to_be_receive_amount_report (id, timeTypeOne, ",
        "timeTypeTwo, timeTypeThree, ",
        "timeTypeFour, projectId, ",
        "lastUpdateTime, dataVersion)",
        "values (#{id,jdbcType=BIGINT}, #{timeTypeOne,jdbcType=BIGINT}, ",
        "#{timeTypeTwo,jdbcType=BIGINT}, #{timeTypeThree,jdbcType=BIGINT}, ",
        "#{timeTypeFour,jdbcType=BIGINT}, #{projectId,jdbcType=BIGINT}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{dataVersion,jdbcType=BIGINT})"
    })
    int insert(PrepaidToBeReceiveAmountReport record);


    @Select({
        "select",
        "id, timeTypeOne, timeTypeTwo, timeTypeThree, timeTypeFour, projectId, lastUpdateTime, ",
        "dataVersion",
        "from ${tablePrefix}_prepaid_to_be_receive_amount_report",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="timeTypeOne", property="timeTypeOne", jdbcType=JdbcType.BIGINT),
        @Result(column="timeTypeTwo", property="timeTypeTwo", jdbcType=JdbcType.BIGINT),
        @Result(column="timeTypeThree", property="timeTypeThree", jdbcType=JdbcType.BIGINT),
        @Result(column="timeTypeFour", property="timeTypeFour", jdbcType=JdbcType.BIGINT),
        @Result(column="projectId", property="projectId", jdbcType=JdbcType.BIGINT),
        @Result(column="lastUpdateTime", property="lastUpdateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="dataVersion", property="dataVersion", jdbcType=JdbcType.BIGINT)
    })
    PrepaidToBeReceiveAmountReport selectByPrimaryKey(@Param("tablePrefix") String tablePrefix,@Param("id") Long id);


    @Update({
        "update ${tablePrefix}_prepaid_to_be_receive_amount_report",
        "set timeTypeOne = #{timeTypeOne,jdbcType=BIGINT},",
          "timeTypeTwo = #{timeTypeTwo,jdbcType=BIGINT},",
          "timeTypeThree = #{timeTypeThree,jdbcType=BIGINT},",
          "timeTypeFour = #{timeTypeFour,jdbcType=BIGINT},",
          "projectId = #{projectId,jdbcType=BIGINT},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "dataVersion = #{dataVersion,jdbcType=BIGINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PrepaidToBeReceiveAmountReport record);
}