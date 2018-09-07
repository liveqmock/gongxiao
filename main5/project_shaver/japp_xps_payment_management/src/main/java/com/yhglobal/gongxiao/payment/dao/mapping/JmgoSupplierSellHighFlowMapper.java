package com.yhglobal.gongxiao.payment.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.payment.model.JmgoSupplierSellHighFlow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @author 葛灿
 */
public interface JmgoSupplierSellHighFlowMapper extends BaseMapper {


    /**
     * 插入对象
     *
     * @param prefix                   表前缀
     * @param jmgoSupplierSellHighFlow 对象
     * @return 数据库新增记录数
     */
    @Insert({
            "INSERT INTO ${prefix}_supplier_sell_high_flow",
            "(currencyCode, amountBeforeTransaction,",
            "transactionAmount, amountAfterTransaction,",
            "transactionTime, projectId,",
            "projectName, distributorId,",
            "distributorName, createdById,",
            "createdByName, createTime,",
            "flowType, flowNo,",
            "remark, salesOrderNo)",
            "VALUES",
            "( #{record.currencyCode}, #{record.amountBeforeTransaction},",
            "#{record.transactionAmount}, #{record.amountAfterTransaction},",
            "#{record.transactionTime}, #{record.projectId},",
            "#{record.projectName}, #{record.distributorId},",
            "#{record.distributorName}, #{record.createdById},",
            "#{record.createdByName}, #{record.createTime},",
            "#{record.flowType}, #{record.flowNo},",
            "#{record.remark}, #{record.salesOrderNo} )"
    })
    int insertFlow(@Param("prefix") String prefix,
                   @Param("record") JmgoSupplierSellHighFlow jmgoSupplierSellHighFlow);

}
