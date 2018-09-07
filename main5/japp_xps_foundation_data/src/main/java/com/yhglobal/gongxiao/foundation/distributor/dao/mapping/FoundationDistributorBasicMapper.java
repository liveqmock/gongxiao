package com.yhglobal.gongxiao.foundation.distributor.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.FoundationDistributorBasic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface FoundationDistributorBasicMapper extends BaseMapper {
    @Delete({
        "delete from foundation_distributor_basic",
        "where distributorBasicId = #{distributorBasicId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long distributorBasicId);

    @Insert({
        "insert into foundation_distributor_basic (distributorBasicId, distributorCode, ",
        "distributorName, easDistributorCode, ",
        "easDistributorName, status, ",
        "supplierId, supplierName, ",
        "email, contactNumber, ",
        "contactName, businessAddress, ",
        "countryCode, taxNumber, ",
        "cnyBank, cnyAccount, ",
        "bankHisInfo, createdById, ",
        "createdByName, createTime, ",
        "lastUpdateTime, tracelog)",
        "values (#{distributorBasicId,jdbcType=BIGINT}, #{distributorCode,jdbcType=VARCHAR}, ",
        "#{distributorName,jdbcType=VARCHAR}, #{easDistributorCode,jdbcType=VARCHAR}, ",
        "#{easDistributorName,jdbcType=VARCHAR}, #{status,jdbcType=TINYINT}, ",
        "#{supplierId,jdbcType=BIGINT}, #{supplierName,jdbcType=VARCHAR}, ",
        "#{email,jdbcType=VARCHAR}, #{contactNumber,jdbcType=VARCHAR}, ",
        "#{contactName,jdbcType=VARCHAR}, #{businessAddress,jdbcType=VARCHAR}, ",
        "#{countryCode,jdbcType=VARCHAR}, #{taxNumber,jdbcType=VARCHAR}, ",
        "#{cnyBank,jdbcType=VARCHAR}, #{cnyAccount,jdbcType=VARCHAR}, ",
        "#{bankHisInfo,jdbcType=VARCHAR}, #{createdById,jdbcType=BIGINT}, ",
        "#{createdByName,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{lastUpdateTime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(FoundationDistributorBasic record);

    @Select({
        "select",
        "distributorBasicId, distributorCode, distributorName, easDistributorCode, easDistributorName, ",
        "status, supplierId, supplierName, email, contactNumber, contactName, businessAddress, ",
        "countryCode, taxNumber, cnyBank, cnyAccount, bankHisInfo, createdById, createdByName, ",
        "createTime, lastUpdateTime, tracelog",
        "from foundation_distributor_basic",
        "where distributorBasicId = #{distributorBasicId,jdbcType=BIGINT}"
    })

    FoundationDistributorBasic getByDistributorBasicId(Long distributorBasicId);

    @Update({
        "update foundation_distributor_basic",
        "set distributorCode = #{distributorCode,jdbcType=VARCHAR},",
          "distributorName = #{distributorName,jdbcType=VARCHAR},",
          "easDistributorCode = #{easDistributorCode,jdbcType=VARCHAR},",
          "easDistributorName = #{easDistributorName,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=TINYINT},",
          "supplierId = #{supplierId,jdbcType=BIGINT},",
          "supplierName = #{supplierName,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "contactNumber = #{contactNumber,jdbcType=VARCHAR},",
          "contactName = #{contactName,jdbcType=VARCHAR},",
          "businessAddress = #{businessAddress,jdbcType=VARCHAR},",
          "countryCode = #{countryCode,jdbcType=VARCHAR},",
          "taxNumber = #{taxNumber,jdbcType=VARCHAR},",
          "cnyBank = #{cnyBank,jdbcType=VARCHAR},",
          "cnyAccount = #{cnyAccount,jdbcType=VARCHAR},",
          "bankHisInfo = #{bankHisInfo,jdbcType=VARCHAR},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
          "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
        "where distributorBasicId = #{distributorBasicId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(FoundationDistributorBasic record);

    @Update({
            "update foundation_distributor_basic",
            "set ",
            "status = #{recordStatus},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
            "where distributorBasicId = #{distributorBasicId}"
    })
    int updateDistributorBasicInfo(long  distributorBasicId,int recordStatus);

}