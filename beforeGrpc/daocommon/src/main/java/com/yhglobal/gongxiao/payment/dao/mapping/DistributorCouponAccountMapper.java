package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.DistributorCouponAccount;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface DistributorCouponAccountMapper extends BaseMapper {

    @Insert({
            "insert into distributor_coupon_account (rowId, projectId, ",
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
    int insert(DistributorCouponAccount record);


    @Select({
            "select",
            "rowId, currencyCode, projectId, projectName, distributorId, distributorName, ",
            "clientAccount, clientName, status, frozenAmount, totalAmount, dataVersion, createTime, ",
            "lastUpdateTime, frozenInfo, tracelog",
            "from distributor_coupon_account",
            "where projectId = #{projectId} and distributorId=#{distributorId}"
    })
    DistributorCouponAccount getAccount(@Param("projectId") long projectId,
                                        @Param("distributorId") long distributorId);

    @Update({
            "update distributor_coupon_account",
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
            "where rowId = #{rowId,jdbcType=BIGINT} AND dataVersion=#{dataVersion}"
    })
    int updateByPrimaryKey(DistributorCouponAccount record);

    @Select({
            "select",
            "rowId, currencyCode, projectId, projectName, distributorId, distributorName, ",
            "clientAccount, clientName, status, frozenAmount, totalAmount, dataVersion, createTime, ",
            "lastUpdateTime, frozenInfo, tracelog",
            "from distributor_coupon_account",
            "where projectId = #{projectId}"
    })
    List<DistributorCouponAccount> getAccountList(@Param("projectId") long projectId);
}