package com.yhglobal.gongxiao.foundation.distributor.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.Distributor;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DistributorMapper extends BaseMapper {
    @Delete({
            "delete from t_distributor",
            "where distributorId = #{distributorId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer distributorId);

    @Insert({
            "insert into t_distributor (distributorId, distributorName, ",
            "easCustomerCode, easCustomerName, ",
            "status, supplierId, ",
            "supplierName, address, ",
            "email, contactName, ",
            "countryCode, phoneNumber, ",
            "paymentMode, creditDays, ",
            "taxNumber, bankInfo, ",
            "currentProjectInfo, historyProjectInfo, ",
            "createdById, createdByName, ",
            "createTime, lastUpdateTime, ",
            "tracelog)",
            "values (#{distributorId,jdbcType=INTEGER}, #{distributorName,jdbcType=VARCHAR}, ",
            "#{easCustomerCode,jdbcType=VARCHAR}, #{easCustomerName,jdbcType=VARCHAR}, ",
            "#{status,jdbcType=TINYINT}, #{supplierId,jdbcType=BIGINT}, ",
            "#{supplierName,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
            "#{email,jdbcType=VARCHAR}, #{contactName,jdbcType=VARCHAR}, ",
            "#{countryCode,jdbcType=VARCHAR}, #{phoneNumber,jdbcType=VARCHAR}, ",
            "#{paymentMode,jdbcType=TINYINT}, #{creditDays,jdbcType=INTEGER}, ",
            "#{taxNumber,jdbcType=VARCHAR}, #{bankInfo,jdbcType=VARCHAR}, ",
            "#{currentProjectInfo,jdbcType=VARCHAR}, #{historyProjectInfo,jdbcType=VARCHAR}, ",
            "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP}, ",
            "#{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(Distributor record);

    @Insert({
            "insert into t_distributor ( distributorName, ",
            "easCustomerCode, easCustomerName,status)",
            "values (#{easCustomerName},#{easCustomerCode},#{easCustomerName},1)"
    })
    int insertDistributor(@Param("easCustomerCode") String easCustomerCode,
                          @Param("easCustomerName") String easCustomerName);


    @Select({
            "select",
            "distributorId, distributorName, easCustomerCode, easCustomerName, status, supplierId, ",
            "supplierName, address, email, contactName, countryCode, phoneNumber, paymentMode, ",
            "creditDays, taxNumber, bankInfo, currentProjectInfo, historyProjectInfo, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from t_distributor",
            "where distributorId = #{distributorId,jdbcType=INTEGER}"
    })
    Distributor selectByPrimaryKey(Integer distributorId);

    @Select({
            "select",
            "distributorId, distributorName, easCustomerCode, easCustomerName, status, supplierId, ",
            "supplierName, address, email, contactName, countryCode, phoneNumber, paymentMode, ",
            "creditDays, taxNumber, bankInfo, currentProjectInfo, historyProjectInfo, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from t_distributor"
    })
    List<Distributor> selectAll();

    @Update({
            "update t_distributor",
            "set distributorName = #{distributorName,jdbcType=VARCHAR},",
            "easCustomerCode = #{easCustomerCode,jdbcType=VARCHAR},",
            "easCustomerName = #{easCustomerName,jdbcType=VARCHAR},",
            "status = #{status,jdbcType=TINYINT},",
            "supplierId = #{supplierId,jdbcType=BIGINT},",
            "supplierName = #{supplierName,jdbcType=VARCHAR},",
            "address = #{address,jdbcType=VARCHAR},",
            "email = #{email,jdbcType=VARCHAR},",
            "contactName = #{contactName,jdbcType=VARCHAR},",
            "countryCode = #{countryCode,jdbcType=VARCHAR},",
            "phoneNumber = #{phoneNumber,jdbcType=VARCHAR},",
            "paymentMode = #{paymentMode,jdbcType=TINYINT},",
            "creditDays = #{creditDays,jdbcType=INTEGER},",
            "taxNumber = #{taxNumber,jdbcType=VARCHAR},",
            "bankInfo = #{bankInfo,jdbcType=VARCHAR},",
            "currentProjectInfo = #{currentProjectInfo,jdbcType=VARCHAR},",
            "historyProjectInfo = #{historyProjectInfo,jdbcType=VARCHAR},",
            "createdById = #{createdById,jdbcType=BIGINT},",
            "createdByName = #{createdByName,jdbcType=VARCHAR},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where distributorId = #{distributorId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(Distributor record);

    @Update({
            "update t_distributor",
            "set distributorName = #{distributorName,jdbcType=VARCHAR},",
            "easCustomerCode = #{easCustomerCode,jdbcType=VARCHAR},",
            "easCustomerName = #{easCustomerName,jdbcType=VARCHAR},",
            "status = #{status,jdbcType=TINYINT},",
            "supplierId = #{supplierId,jdbcType=BIGINT},",
            "supplierName = #{supplierName,jdbcType=VARCHAR},",
            "address = #{address,jdbcType=VARCHAR},",
            "email = #{email,jdbcType=VARCHAR},",
            "contactName = #{contactName,jdbcType=VARCHAR},",
            "countryCode = #{countryCode,jdbcType=VARCHAR},",
            "phoneNumber = #{phoneNumber,jdbcType=VARCHAR},",
            "paymentMode = #{paymentMode,jdbcType=TINYINT},",
            "creditDays = #{creditDays,jdbcType=INTEGER},",
            "taxNumber = #{taxNumber,jdbcType=VARCHAR},",
            "bankInfo = #{bankInfo,jdbcType=VARCHAR},",
            "currentProjectInfo = #{currentProjectInfo,jdbcType=VARCHAR},",
            "historyProjectInfo = #{historyProjectInfo,jdbcType=VARCHAR},",
            "createdById = #{createdById,jdbcType=BIGINT},",
            "createdByName = #{createdByName,jdbcType=VARCHAR},",
            "createTime = #{createTime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where distributorId = #{distributorId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Distributor record);

    @Select({
            "select",
            "distributorId, distributorName, easCustomerCode, easCustomerName, status, supplierId, ",
            "supplierName, address, email, contactName, countryCode, phoneNumber, paymentMode, ",
            "creditDays, taxNumber, bankInfo, currentProjectInfo, historyProjectInfo, createdById, ",
            "createdByName, createTime, lastUpdateTime, tracelog",
            "from t_distributor where distributorId = #{distributorId}"
    })
    Distributor selectRecordById(String distributorId);
}