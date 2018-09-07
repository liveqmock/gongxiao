package com.yhglobal.gongxiao.foundation.supplier.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.supplier.model.Supplier;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SupplierMapper extends BaseMapper {
    @Delete({
        "delete from t_supplier",
        "where supplierId = #{supplierid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Long supplierid);

    @Insert({
            "insert into t_supplier (id, supplierId, ",
            "supplierName, supplierCode, ",
            "easSupplierCode, easSupplierName, ",
            "status, address, ",
            "email, contactName, ",
            "countryCode, phoneNumber, ",
            "currentProjectInfo, historyProjectInfo, ",
            "createdById, createdByName, ",
            "createTime, lastUpdateTime, ",
            "tracelog)",
            "values (#{id,jdbcType=INTEGER}, #{supplierId,jdbcType=BIGINT}, ",
            "#{supplierName,jdbcType=VARCHAR}, #{supplierCode,jdbcType=VARCHAR}, ",
            "#{easSupplierCode,jdbcType=VARCHAR}, #{easSupplierName,jdbcType=VARCHAR}, ",
            "#{status,jdbcType=TINYINT}, #{address,jdbcType=VARCHAR}, ",
            "#{email,jdbcType=VARCHAR}, #{contactName,jdbcType=VARCHAR}, ",
            "#{countryCode,jdbcType=VARCHAR}, #{phoneNumber,jdbcType=VARCHAR}, ",
            "#{currentProjectInfo,jdbcType=VARCHAR}, #{historyProjectInfo,jdbcType=VARCHAR}, ",
            "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(Supplier record);

    @Select({
        "select",
            "id, supplierId, supplierName, supplierCode, easSupplierCode, easSupplierName, ",
            "status, address, email, contactName, countryCode, phoneNumber, currentProjectInfo, ",
            "historyProjectInfo, createdById, createdByName, createTime, lastUpdateTime, ",
            "tracelog",
        "from t_supplier",
        "where supplierId = #{supplierId}"
    })
     Supplier getBySupplierId(Long supplierId);

    @Select({
            "select",
            "id, supplierId, supplierName, supplierCode, easSupplierCode, easSupplierName, ",
            "status, address, email, contactName, countryCode, phoneNumber, currentProjectInfo, ",
            "historyProjectInfo, createdById, createdByName, createTime, lastUpdateTime, ",
            "tracelog",
            "from t_supplier",
            "where supplierCode = #{supplierCode}"
    })
    Supplier getSupplierByCode(String  supplierCode);

    @Select({
            "select",
            "id, supplierId, supplierName, supplierCode, easSupplierCode, easSupplierName, ",
            "status, address, email, contactName, countryCode, phoneNumber, currentProjectInfo, ",
            "historyProjectInfo, createdById, createdByName, createTime, lastUpdateTime, ",
            "tracelog",
            "from t_supplier"
    })
    List<Supplier> selectAll();

    @Update({
        "update t_supplier",
        "set supplierName = #{suppliername,jdbcType=VARCHAR},",
          "supplierCode = #{suppliercode,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
          "address = #{address,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "contactName = #{contactname,jdbcType=VARCHAR},",
          "countryCode = #{countrycode,jdbcType=VARCHAR},",
          "phoneNumber = #{phonenumber,jdbcType=VARCHAR},",
          "currentProjectInfo = #{currentprojectinfo,jdbcType=VARCHAR},",
          "historyProjectInfo = #{historyprojectinfo,jdbcType=VARCHAR},",
          "createdById = #{createdbyid,jdbcType=BIGINT},",
          "createdByName = #{createdbyname,jdbcType=VARCHAR},",
          "createTime = #{createtime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastupdatetime,jdbcType=TIMESTAMP},",
          "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
        "where supplierId = #{supplierid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(Supplier record);

    @Update({
        "update t_supplier",
        "set supplierName = #{suppliername,jdbcType=VARCHAR},",
          "supplierCode = #{suppliercode,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
          "address = #{address,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "contactName = #{contactname,jdbcType=VARCHAR},",
          "countryCode = #{countrycode,jdbcType=VARCHAR},",
          "phoneNumber = #{phonenumber,jdbcType=VARCHAR},",
          "currentProjectInfo = #{currentprojectinfo,jdbcType=VARCHAR},",
          "historyProjectInfo = #{historyprojectinfo,jdbcType=VARCHAR},",
          "createdById = #{createdbyid,jdbcType=BIGINT},",
          "createdByName = #{createdbyname,jdbcType=VARCHAR},",
          "createTime = #{createtime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastupdatetime,jdbcType=TIMESTAMP}",
        "where supplierId = #{supplierid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Supplier record);


    @Update({
            "update t_supplier",
            "set easSupplierCode = #{easSupplierCode,jdbcType=VARCHAR},",
            "easSupplierName = #{easSupplierName,jdbcType=VARCHAR}",
            "where supplierCode = #{supplierCode}"
    })
    int updateSupplierInfo(@Param("supplierCode") String supplierCode,
                           @Param("easSupplierCode")String easSupplierCode,
                           @Param("easSupplierName")String easSupplierName);
}