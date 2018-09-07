package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidTransferToDistributorFlow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

public interface SupplierPrepaidTransferToDistributorFlowMapper extends BaseMapper {
    @Delete({
            "delete from supplier_prepaid_transfer_to_distributor_flow",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long flowId);

    @Insert({
            "insert into supplier_prepaid_transfer_to_distributor_flow (flowId, flowNo, ",
            "currencyCode, amountBeforeTransaction, ",
            "transactionAmount, amountAfterTransaction, ",
            "transferTime, supplierId, ",
            "supplierName, projectId, ",
            "projectName, createdById, ",
            "createdByName, purchaseOrderNo, ",
            "flowType, createTime, sourceFlowNo)",
            "values (#{flowId,jdbcType=BIGINT}, #{flowNo,jdbcType=VARCHAR}, ",
            "#{currencyCode,jdbcType=VARCHAR}, #{amountBeforeTransaction,jdbcType=BIGINT}, ",
            "#{transactionAmount,jdbcType=BIGINT}, #{amountAfterTransaction,jdbcType=BIGINT}, ",
            "#{transferTime,jdbcType=TIMESTAMP}, #{supplierId,jdbcType=BIGINT}, ",
            "#{supplierName,jdbcType=VARCHAR}, #{projectId,jdbcType=BIGINT}, ",
            "#{projectName,jdbcType=VARCHAR}, #{createdById,jdbcType=BIGINT}, ",
            "#{createdByName,jdbcType=VARCHAR}, #{purchaseOrderNo,jdbcType=VARCHAR}, ",
            "#{flowType,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, #{sourceFlowNo})"
    })
    int insert(SupplierPrepaidTransferToDistributorFlow record);

    @Select({
            "select",
            "flowId, flowNo, currencyCode, amountBeforeTransaction, transactionAmount, amountAfterTransaction, ",
            "transferTime, supplierId, supplierName, projectId, projectName, createdById, ",
            "createdByName, purchaseOrderNo, flowType, createTime",
            "from supplier_prepaid_transfer_to_distributor_flow",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    SupplierPrepaidTransferToDistributorFlow selectByPrimaryKey(Long flowId);

    @Update({
            "update supplier_prepaid_transfer_to_distributor_flow",
            "set flowNo = #{flowNo,jdbcType=VARCHAR},",
            "currencyCode = #{currencyCode,jdbcType=VARCHAR},",
            "transactionAmount = #{transactionAmount,jdbcType=BIGINT},",
            "transferTime = #{transferTime,jdbcType=TIMESTAMP},",
            "supplierId = #{supplierId,jdbcType=BIGINT},",
            "supplierName = #{supplierName,jdbcType=VARCHAR},",
            "projectId = #{projectId,jdbcType=BIGINT},",
            "projectName = #{projectName,jdbcType=VARCHAR},",
            "createdById = #{createdById,jdbcType=BIGINT},",
            "createdByName = #{createdByName,jdbcType=VARCHAR},",
            "purchaseOrderNo = #{purchaseOrderNo,jdbcType=VARCHAR}",
            "where flowId = #{flowId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SupplierPrepaidTransferToDistributorFlow record);

    @SelectProvider(type = SupplierPrepaidTransferToDistributorFlowSqlProvider.class, method = "selectBufferPrepaidFlowList")
    List<SupplierPrepaidTransferToDistributorFlow> selectBufferPrepaidFlowList(@Param("currencyCode") String currencyCode,
                                                                               @Param("supplierId") long supplierId,
                                                                               @Param("projectId") long projectId,
                                                                               @Param("moneyFlow") Integer moneyFlow,
                                                                               @Param("beginDate") Date beginDate,
                                                                               @Param("endDate") Date endDate);

    @SelectProvider(type = SupplierPrepaidTransferToDistributorFlowSqlProvider.class, method = "selectIncomeAndExpenditure")
    List<FlowSubtotal> selectIncomeAndExpenditure(@Param("currencyCode") String currencyCode,
                                                  @Param("supplierId") long supplierId,
                                                  @Param("projectId") long projectId,
                                                  @Param("moneyFlow") Integer moneyFlow,
                                                  @Param("beginDate") Date beginDate,
                                                  @Param("endDate") Date endDate);

}