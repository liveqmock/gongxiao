package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidBufferToYhglobal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SupplierPrepaidBufferToYhglobalMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_supplier_prepaid_buffer_to_yhglobal (supplierId, supplierName, ",
            "projectId, ",
            "projectName, status, ",
            "totalAmount, dataVersion, ",
            "createTime, lastUpdateTime, ",
            "tracelog)",
            "values (#{record.supplierId,jdbcType=BIGINT}, #{record.supplierName,jdbcType=VARCHAR}, ",
            "#{record.projectId,jdbcType=BIGINT}, ",
            "#{record.projectName,jdbcType=VARCHAR}, #{record.status,jdbcType=TINYINT}, ",
            "#{record.totalAmount,jdbcType=BIGINT}, #{record.dataVersion,jdbcType=BIGINT}, ",
            "#{record.createTime,jdbcType=TIMESTAMP}, #{record.lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{record.tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") SupplierPrepaidBufferToYhglobal record);

    @Select({
            "select",
            "supplierId, supplierName, projectId, projectName, status, totalAmount, ",
            "dataVersion, createTime, lastUpdateTime, tracelog",
            "from ${prefix}_supplier_prepaid_buffer_to_yhglobal",
            "where projectId = #{projectId}"
    })
    SupplierPrepaidBufferToYhglobal getAccount(@Param("prefix") String prefix,
                                               @Param("projectId") long projectId);

    @Update({
            "update ${prefix}_supplier_prepaid_buffer_to_yhglobal",
            "set supplierName = #{record.supplierName,jdbcType=VARCHAR},",
            "projectId = #{record.projectId,jdbcType=BIGINT},",
            "projectName = #{record.projectName,jdbcType=VARCHAR},",
            "status = #{record.status,jdbcType=TINYINT},",
            "totalAmount = #{record.totalAmount,jdbcType=BIGINT},",
            "dataVersion = dataVersion+1,",
            "createTime = #{record.createTime,jdbcType=TIMESTAMP},",
            "tracelog = #{record.tracelog,jdbcType=LONGVARCHAR}",
            "where supplierId = #{record.supplierId,jdbcType=BIGINT} AND dataVersion =#{record.dataVersion}"
    })
    int updateByPrimaryKeyWithBLOBs(@Param("prefix") String prefix,
                                    @Param("record") SupplierPrepaidBufferToYhglobal record);

}