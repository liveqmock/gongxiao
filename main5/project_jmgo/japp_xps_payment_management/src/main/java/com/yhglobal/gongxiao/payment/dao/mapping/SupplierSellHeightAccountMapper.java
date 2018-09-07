package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SupplierSellHeightAccountMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_supplier_sell_height_account (supplierId, supplierName, ",
            "supplierCode, projectId, ",
            "projectName, status, ",
            "totalAmount, dataVersion, ",
            "createTime, lastUpdateTime, ",
            "tracelog)",
            "values (#{record.supplierId,jdbcType=BIGINT}, #{record.supplierName,jdbcType=VARCHAR}, ",
            "#{record.supplierCode,jdbcType=VARCHAR}, #{record.projectId,jdbcType=BIGINT}, ",
            "#{record.projectName,jdbcType=VARCHAR}, #{record.status,jdbcType=TINYINT}, ",
            "#{record.totalAmount,jdbcType=BIGINT}, #{record.dataVersion,jdbcType=BIGINT}, ",
            "#{record.createTime,jdbcType=TIMESTAMP}, #{record.lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{record.tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record")SupplierSellHeightAccount record);


    @Select({
            "select",
            "supplierId, supplierName, supplierCode, projectId, projectName, status, totalAmount, ",
            "dataVersion, createTime, lastUpdateTime, tracelog",
            "from ${prefix}_supplier_sell_height_account",
            "where projectId = #{projectId}"
    })
    SupplierSellHeightAccount getAccount(@Param("prefix") String prefix,
                                         @Param("projectId") long projectId);


    @Update({
            "update ${prefix}_supplier_sell_height_account",
            "set supplierName = #{record.supplierName,jdbcType=VARCHAR},",
            "supplierCode = #{record.supplierCode,jdbcType=VARCHAR},",
            "projectId = #{record.projectId,jdbcType=BIGINT},",
            "projectName = #{record.projectName,jdbcType=VARCHAR},",
            "status = #{record.status,jdbcType=TINYINT},",
            "totalAmount = #{record.totalAmount,jdbcType=BIGINT},",
            "dataVersion = dataVersion+1,",
            "createTime = #{record.createTime,jdbcType=TIMESTAMP},",
            "tracelog = #{record.tracelog,jdbcType=LONGVARCHAR}",
            "where supplierId = #{record.supplierId,jdbcType=BIGINT} AND dataVersion= #{record.dataVersion}"
    })
    int updateByPrimaryKeyWithBLOBs(@Param("prefix") String prefix,
                                    @Param("record") SupplierSellHeightAccount record);

}