package com.yhglobal.gongxiao.phjd.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.phjd.model.JdQualityGuaranteeDeposit;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface JdQualityGuaranteeDepositMapper extends BaseMapper {
    @Delete({
        "delete from jd_quality_guarantee_deposit",
        "where depositId = #{depositId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long depositId);

    @Insert({
        "insert into jd_quality_guarantee_deposit (depositId, depositStatus, ",
        "documentCode, amount, ",
        "documentTime, buyerName, ",
        "projectId, projectName, ",
        "supplierId, supplierName, ",
        "settlementNo, createdById, ",
        "createdByName, createTime, ",
        "remark, jdProjectId)",
        "values (#{depositId,jdbcType=BIGINT}, #{depositStatus,jdbcType=TINYINT}, ",
        "#{documentCode,jdbcType=TINYINT}, #{amount,jdbcType=DECIMAL}, ",
        "#{documentTime,jdbcType=TIMESTAMP}, #{buyerName,jdbcType=VARCHAR}, ",
        "#{projectId,jdbcType=BIGINT}, #{projectName,jdbcType=VARCHAR}, ",
        "#{supplierId,jdbcType=BIGINT}, #{supplierName,jdbcType=VARCHAR}, ",
        "#{settlementNo,jdbcType=VARCHAR}, #{createdById,jdbcType=BIGINT}, ",
        "#{createdByName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{remark,jdbcType=LONGVARCHAR}, #{jdProjectId, jdbcType=BIGINT})"
    })
    int insert(JdQualityGuaranteeDeposit record);


    @Select({
        "select",
        "depositId, depositStatus, documentCode, amount, documentTime, buyerName, projectId, ",
        "projectName, supplierId, supplierName, settlementNo, createdById, createdByName, ",
        "createTime, remark",
        "from jd_quality_guarantee_deposit",
        "where depositId = #{depositId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="depositId", property="depositId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="depositStatus", property="depositStatus", jdbcType=JdbcType.TINYINT),
        @Result(column="documentCode", property="documentCode", jdbcType=JdbcType.TINYINT),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="documentTime", property="documentTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="buyerName", property="buyerName", jdbcType=JdbcType.VARCHAR),
        @Result(column="projectId", property="projectId", jdbcType=JdbcType.BIGINT),
        @Result(column="projectName", property="projectName", jdbcType=JdbcType.VARCHAR),
        @Result(column="supplierId", property="supplierId", jdbcType=JdbcType.BIGINT),
        @Result(column="supplierName", property="supplierName", jdbcType=JdbcType.VARCHAR),
        @Result(column="settlementNo", property="settlementNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="createdById", property="createdById", jdbcType=JdbcType.BIGINT),
        @Result(column="createdByName", property="createdByName", jdbcType=JdbcType.VARCHAR),
        @Result(column="createTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    JdQualityGuaranteeDeposit selectByPrimaryKey(Long depositId);


    @Update({
        "update jd_quality_guarantee_deposit",
        "set depositStatus = #{depositStatus,jdbcType=TINYINT},",
          "documentCode = #{documentCode,jdbcType=TINYINT},",
          "amount = #{amount,jdbcType=DECIMAL},",
          "documentTime = #{documentTime,jdbcType=TIMESTAMP},",
          "buyerName = #{buyerName,jdbcType=VARCHAR},",
          "projectId = #{projectId,jdbcType=BIGINT},",
          "projectName = #{projectName,jdbcType=VARCHAR},",
          "supplierId = #{supplierId,jdbcType=BIGINT},",
          "supplierName = #{supplierName,jdbcType=VARCHAR},",
          "settlementNo = #{settlementNo,jdbcType=VARCHAR},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where depositId = #{depositId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(JdQualityGuaranteeDeposit record);

    @Update({
        "update jd_quality_guarantee_deposit",
        "set depositStatus = #{depositStatus,jdbcType=TINYINT},",
          "documentCode = #{documentCode,jdbcType=TINYINT},",
          "amount = #{amount,jdbcType=DECIMAL},",
          "documentTime = #{documentTime,jdbcType=TIMESTAMP},",
          "buyerName = #{buyerName,jdbcType=VARCHAR},",
          "projectId = #{projectId,jdbcType=BIGINT},",
          "projectName = #{projectName,jdbcType=VARCHAR},",
          "supplierId = #{supplierId,jdbcType=BIGINT},",
          "supplierName = #{supplierName,jdbcType=VARCHAR},",
          "settlementNo = #{settlementNo,jdbcType=VARCHAR},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP}",
        "where depositId = #{depositId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(JdQualityGuaranteeDeposit record);

    @SelectProvider(type = JdQualityGuaranteeDepositSqlProvider.class, method = "selectByManyConditions")
    List<JdQualityGuaranteeDeposit> selectByManyConditions(@Param("settlementNo") String settlementNo,
                                                           @Param("documentCode") Integer documentCode,
                                                           @Param("depositStatus") Integer depositStatus,
                                                           @Param("jdProjectId") Long jdProjectId);
}