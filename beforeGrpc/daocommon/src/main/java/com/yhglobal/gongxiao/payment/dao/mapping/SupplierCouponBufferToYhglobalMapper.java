package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.SupplierCouponBufferToYhglobal;
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

public interface SupplierCouponBufferToYhglobalMapper extends BaseMapper {

    @Insert({
            "insert into supplier_coupon_buffer_to_yhglobal (supplierId, supplierName, ",
            "supplierCode, projectId, ",
            "projectName, status, ",
            "totalAmount, dataVersion, ",
            "createTime, lastUpdateTime, ",
            "tracelog)",
            "values (#{supplierId,jdbcType=BIGINT}, #{supplierName,jdbcType=VARCHAR}, ",
            "#{supplierCode,jdbcType=VARCHAR}, #{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{status,jdbcType=TINYINT}, ",
            "#{totalAmount,jdbcType=BIGINT}, #{dataVersion,jdbcType=BIGINT}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(SupplierCouponBufferToYhglobal record);

    @Select({
            "select",
            "supplierId, supplierName, supplierCode, projectId, projectName, status, totalAmount, ",
            "dataVersion, createTime, lastUpdateTime, tracelog",
            "from supplier_coupon_buffer_to_yhglobal",
            "where  projectId = #{projectId}"
    })
    SupplierCouponBufferToYhglobal getAccount(@Param("projectId") long projectId);


    @Update({
            "update supplier_coupon_buffer_to_yhglobal",
            "set supplierName = #{supplierName,jdbcType=VARCHAR},",
            "supplierCode = #{supplierCode,jdbcType=VARCHAR},",
            "projectId = #{projectId,jdbcType=BIGINT},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "status = #{status,jdbcType=TINYINT},",
            "totalAmount = #{totalAmount,jdbcType=BIGINT},",
            "dataVersion = dataVersion+1,",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where supplierId = #{supplierId,jdbcType=BIGINT} AND dataVersion=#{dataVersion}"
    })
    int updateByPrimaryKeyWithBLOBs(SupplierCouponBufferToYhglobal record);

}