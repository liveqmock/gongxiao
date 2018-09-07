package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.DistributorCashAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface DistributorCashAccountMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_distributor_cash_account (rowId, currencyCode, ",
            "projectId, projectName, ",
            "distributorId, distributorName, ",
            "clientAccount, clientName, ",
            "status, frozenAmount, ",
            "totalAmount, dataVersion, ",
            "createTime, lastUpdateTime, ",
            "frozenInfo, tracelog)",
            "values (#{record.rowId}, #{record.currencyCode}, ",
            "#{record.projectId}, #{record.projectName}, ",
            "#{record.distributorId}, #{record.distributorName}, ",
            "#{record.clientAccount}, #{record.clientName}, ",
            "#{record.status}, #{record.frozenAmount}, ",
            "#{record.totalAmount}, #{record.dataVersion}, ",
            "#{record.createTime}, #{record.lastUpdateTime}, ",
            "#{record.frozenInfo}, #{record.tracelog})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") DistributorCashAccount record);


    @Select({
            "select",
            "rowId, currencyCode, projectId, projectName, distributorId, distributorName, ",
            "clientAccount, clientName, status, frozenAmount, totalAmount, dataVersion, createTime, ",
            "lastUpdateTime, frozenInfo, tracelog",
            "from ${prefix}_distributor_cash_account",
            "where projectId = #{projectId} and distributorId=#{distributorId}"
    })
    DistributorCashAccount getAccount(@Param("prefix") String prefix,
                                      @Param("projectId") long projectId,
                                      @Param("distributorId") long distributorId);

    @Update({
            "update ${prefix}_distributor_cash_account",
            "set currencyCode = #{record.currencyCode},",
            "projectId = #{record.projectId},",
            "projectName = #{record.projectName},",
            "distributorId = #{record.distributorId},",
            "distributorName = #{record.distributorName},",
            "clientAccount = #{record.clientAccount},",
            "clientName = #{record.clientName},",
            "status = #{record.status},",
            "frozenAmount = #{record.frozenAmount},",
            "totalAmount = #{record.totalAmount},",
            "dataVersion = dataVersion+1,",
            "createTime = #{record.createTime},",
            "frozenInfo = #{record.frozenInfo},",
            "tracelog = #{record.tracelog}",
            "where rowId = #{record.rowId} AND dataVersion = #{record.dataVersion}"
    })
    int updateByPrimaryKeyWithBLOBs(@Param("prefix") String prefix,
                                    @Param("record") DistributorCashAccount record);

}