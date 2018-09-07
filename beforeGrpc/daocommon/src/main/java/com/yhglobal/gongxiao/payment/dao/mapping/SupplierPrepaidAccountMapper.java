package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidAccount;
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

public interface SupplierPrepaidAccountMapper extends BaseMapper {

    @Insert({
            "insert into supplier_prepaid_account (supplierId, supplierName, ",
            "supplierCode, projectId, ",
            "projectName, status, ",
            "totalPrepaidAmount, dataVersion, ",
            "createTime, lastUpdateTime, ",
            "tracelog)",
            "values (#{supplierId}, #{supplierName}, ",
            "#{supplierCode}, #{projectId}, ",
            "#{projectName}, #{status}, ",
            "#{totalPrepaidAmount}, #{dataVersion}, ",
            "#{createTime}, #{lastUpdateTime}, ",
            "#{tracelog})"
    })
    int insert(SupplierPrepaidAccount record);


    @Select({
            "select",
            "supplierId, supplierName, supplierCode, projectId, projectName, status, currencyCode, ",
            "totalAmount, frozenAmount, dataVersion, createTime, lastUpdateTime, frozenInfo, ",
            "tracelog",
            "from supplier_prepaid_account",
            "where projectId = #{projectId}"
    })
    SupplierPrepaidAccount getAccount(@Param("projectId") long projectId);

    @Update({
            "update supplier_prepaid_account",
            "set supplierName = #{supplierName},",
            "supplierCode = #{supplierCode},",
            "projectId = #{projectId},",
            "projectName = #{projectName},",
            "status = #{status},",
            "currencyCode = #{currencyCode},",
            "totalAmount = #{totalAmount},",
            "frozenAmount = #{frozenAmount},",
            "dataVersion = dataVersion+1,",
            "createTime = #{createTime},",
            "frozenInfo = #{frozenInfo},",
            "tracelog = #{tracelog}",
            "where supplierId = #{supplierId} AND dataVersion=#{dataVersion}"
    })
    int updateByPrimaryKeyWithBLOBs(SupplierPrepaidAccount record);

}