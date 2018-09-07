package com.yhglobal.gongxiao.foundation.supplier.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.supplier.model.FoundationSupplier;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FoundationSupplierMapper extends BaseMapper {
    @Delete({
        "delete from foundation_supplier",
        "where supplierId = #{supplierId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long supplierId);

    @Insert({
        "insert into foundation_supplier (supplierId, supplierCode, ",
        "supplierName, easSupplierCode, ",
        "easSupplierName, recordStatus, ",
        "address, email, ",
        "contactName, countryCode, ",
        "phoneNumber, currentProjectInfo, ",
        "historyProjectInfo, createdById, ",
        "createdByName, createTime, ",
        "lastUpdateTime, tracelog)",
        "values (#{supplierId,jdbcType=BIGINT}, #{supplierCode,jdbcType=VARCHAR}, ",
        "#{supplierName,jdbcType=VARCHAR}, #{easSupplierCode,jdbcType=VARCHAR}, ",
        "#{easSupplierName,jdbcType=VARCHAR}, #{recordStatus,jdbcType=TINYINT}, ",
        "#{address,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, ",
        "#{contactName,jdbcType=VARCHAR}, #{countryCode,jdbcType=VARCHAR}, ",
        "#{phoneNumber,jdbcType=VARCHAR}, #{currentProjectInfo,jdbcType=VARCHAR}, ",
        "#{historyProjectInfo,jdbcType=VARCHAR}, #{createdById,jdbcType=BIGINT}, ",
        "#{createdByName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(FoundationSupplier record);

    @Select({
        "select",
        "supplierId, supplierCode, supplierName, easSupplierCode, easSupplierName, recordStatus, ",
        "address, email, contactName, countryCode, phoneNumber, currentProjectInfo, historyProjectInfo, ",
        "createdById, createdByName, createTime, lastUpdateTime, tracelog",
        "from foundation_supplier",
        "where supplierId = #{supplierId,jdbcType=BIGINT}"
    })
    FoundationSupplier selectByPrimaryKey(Long supplierId);

    @Select({
            "select",
            "supplierId, supplierCode, supplierName, easSupplierCode, easSupplierName, recordStatus, ",
            "address, email, contactName, countryCode, phoneNumber, currentProjectInfo, historyProjectInfo, ",
            "createdById, createdByName, createTime, lastUpdateTime, tracelog",
            "from foundation_supplier",
            "where supplierCode = #{supplierCode}"
    })
    FoundationSupplier getSupplierByCode(String supplierCode);

    @Select({
            "select",
            "supplierId, supplierCode, supplierName, easSupplierCode, easSupplierName, recordStatus, ",
            "address, email, contactName, countryCode, phoneNumber, currentProjectInfo, historyProjectInfo, ",
            "createdById, createdByName, createTime, lastUpdateTime, tracelog",
            "from foundation_supplier",
            "where supplierId = #{supplierId,jdbcType=BIGINT}"
    })
    FoundationSupplier getBySupplierId(Long supplierId);


    @Select({
            "select",
            "supplierId, supplierCode, supplierName, easSupplierCode, easSupplierName, recordStatus, ",
            "address, email, contactName, countryCode, phoneNumber, currentProjectInfo, historyProjectInfo, ",
            "createdById, createdByName, createTime, lastUpdateTime, tracelog",
            "from foundation_supplier",
    })
    List<FoundationSupplier> selectAllSupplier();

    @Update({
        "update foundation_supplier",
        "set supplierCode = #{supplierCode,jdbcType=VARCHAR},",
          "supplierName = #{supplierName,jdbcType=VARCHAR},",
          "easSupplierCode = #{easSupplierCode,jdbcType=VARCHAR},",
          "easSupplierName = #{easSupplierName,jdbcType=VARCHAR},",
          "recordStatus = #{recordStatus,jdbcType=TINYINT},",
          "address = #{address,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "contactName = #{contactName,jdbcType=VARCHAR},",
          "countryCode = #{countryCode,jdbcType=VARCHAR},",
          "phoneNumber = #{phoneNumber,jdbcType=VARCHAR},",
          "currentProjectInfo = #{currentProjectInfo,jdbcType=VARCHAR},",
          "historyProjectInfo = #{historyProjectInfo,jdbcType=VARCHAR},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
        "where supplierId = #{supplierId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(FoundationSupplier record);

    @Update({
            "update foundation_supplier",
            "easSupplierCode = #{easSupplierCode,jdbcType=VARCHAR},",
            "easSupplierName = #{easSupplierName,jdbcType=VARCHAR},",
            "lastUpdateTime = NOW()",
            "where supplierCode = #{supplierCode}"
    })
    int updateSupplierInfo(String supplierCode, String easSupplierCode, String easSupplierName);
}