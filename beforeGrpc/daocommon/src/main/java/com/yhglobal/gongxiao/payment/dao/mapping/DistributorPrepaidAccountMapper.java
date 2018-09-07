package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.DistributorPrepaidAccount;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface DistributorPrepaidAccountMapper extends BaseMapper {

    @Insert({
            "insert into distributor_prepaid_account (rowId, projectId, ",
            "projectName, distributorId, ",
            "distributorName, clientAccount, ",
            "clientName, status, ",
            "frozenAmount, totalAmount, ",
            "dataVersion, createTime, ",
            "lastUpdateTime, frozenInfo, ",
            "tracelog)",
            "values (#{rowId,jdbcType=BIGINT}, #{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{distributorId,jdbcType=BIGINT}, ",
            "#{distributorName,jdbcType=VARCHAR}, #{clientAccount,jdbcType=VARCHAR}, ",
            "#{clientName,jdbcType=VARCHAR}, #{status,jdbcType=TINYINT}, ",
            "#{frozenAmount,jdbcType=BIGINT}, #{totalAmount,jdbcType=BIGINT}, ",
            "#{dataVersion,jdbcType=BIGINT}, #{createTime,jdbcType=TIMESTAMP}, ",
            "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{frozenInfo,jdbcType=LONGVARCHAR}, ",
            "#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(DistributorPrepaidAccount record);


    @Select({
            "select",
            "rowId, projectId, projectName, distributorId, distributorName, clientAccount, ",
            "clientName, status, frozenAmount, totalAmount, dataVersion, createTime,currencyCode, ",
            "lastUpdateTime, frozenInfo, tracelog",
            "from distributor_prepaid_account",
            "where projectId = #{projectId} and distributorId=#{distributorId}"
    })
    DistributorPrepaidAccount getAccount(@Param("projectId") long projectId,
                                         @Param("distributorId") long distributorId);

    @Update({
            "update distributor_prepaid_account",
            "set currencyCode = #{currencyCode,jdbcType=VARCHAR},",
            "projectId = #{projectId,jdbcType=BIGINT},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "distributorId = #{distributorId,jdbcType=BIGINT},",
            "distributorName = #{distributorName,jdbcType=VARCHAR},",
            "clientAccount = #{clientAccount,jdbcType=VARCHAR},",
            "clientName = #{clientName,jdbcType=VARCHAR},",
            "status = #{status,jdbcType=TINYINT},",
            "frozenAmount = #{frozenAmount,jdbcType=BIGINT},",
            "totalAmount = #{totalAmount,jdbcType=BIGINT},",
            "dataVersion = dataVersion+1,",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "frozenInfo = #{frozenInfo,jdbcType=LONGVARCHAR},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where rowId = #{rowId,jdbcType=BIGINT} AND dataVersion =#{dataVersion}"
    })
    int updateByPrimaryKey(DistributorPrepaidAccount record);

    @Select({
            "select",
            "rowId, projectId,currencyCode, projectName, distributorId, distributorName, clientAccount, ",
            "clientName, status, frozenAmount, totalAmount, dataVersion, createTime, ",
            "lastUpdateTime, frozenInfo, tracelog",
            "from distributor_prepaid_account",
            "where projectId = #{projectId}"
    })
    List<DistributorPrepaidAccount> getAccountList(@Param("projectId") long projectId);
}