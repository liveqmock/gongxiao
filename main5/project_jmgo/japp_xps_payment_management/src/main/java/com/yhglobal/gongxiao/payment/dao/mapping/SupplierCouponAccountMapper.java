package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.SupplierCouponAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SupplierCouponAccountMapper extends BaseMapper {

    @Insert({
            "insert into ${prefix}_supplier_coupon_account (supplierId, supplierName, ",
            "projectId, ",
            "projectName, status, ",
            "totalCouponAmount, dataVersion, ",
            "createTime, lastUpdateTime, ",
            "tracelog)",
            "values (#{record.supplierId}, #{record.supplierName}, ",
            "#{record.projectId}, ",
            "#{record.projectName}, #{record.status}, ",
            "#{record.totalCouponAmount}, #{record.dataVersion}, ",
            "#{record.createTime}, #{record.lastUpdateTime}, ",
            "#{record.tracelog})"
    })
    int insert(@Param("prefix") String prefix,
               @Param("record") SupplierCouponAccount record);


    @Select({
            "select",
            "supplierId, supplierName,  projectId, projectName, status, currencyCode, ",
            "totalAmount,  dataVersion, createTime, lastUpdateTime,",
            "tracelog",
            "from ${prefix}_supplier_coupon_account",
            "where projectId = #{projectId}"
    })
    SupplierCouponAccount getAccount(@Param("prefix") String prefix,
                                     @Param("projectId") long projectId);


    @Update({
            "update ${prefix}_supplier_coupon_account",
            "set supplierName = #{record.supplierName},",
            "projectId = #{record.projectId},",
            "projectName = #{record.projectName},",
            "status = #{record.status},",
            "currencyCode = #{record.currencyCode},",
            "totalAmount = #{record.totalAmount},",
            "dataVersion = dataVersion+1,",
            "createTime = #{record.createTime},",
            "tracelog = #{record.tracelog}",
            "where supplierId = #{record.supplierId} AND dataVersion=#{record.dataVersion}"
    })
    int updateByPrimaryKeyWithBLOBs(@Param("prefix") String prefix,
                                    @Param("record")SupplierCouponAccount record);

}