package com.yhglobal.gongxiao.warehouse.dao;

import com.yhglobal.gongxiao.warehouse.dao.mapping.ReportPsiOverviewMapper;
import com.yhglobal.gongxiao.warehouse.model.dto.ShaverReportPsiOverview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class ReportPsiOverviewDao {

    @Autowired
    ReportPsiOverviewMapper reportPsiOverviewMapper;

    /**
     * 更新某月的销售金额
     *
     * @param prefix     表前缀
     * @param yearMonth  年月yyyyMM
     * @param saleAmount 销售额
     * @return 更新成功记录数
     */
    public int updateSalesAmountByYearMonth(String prefix, String yearMonth, long saleAmount) {
        return reportPsiOverviewMapper.updateSalesAmountByYearMonth(prefix, yearMonth, saleAmount);
    }

 /**
     * 插入数据,并设置采购数据
     * @param record
     * @return
     */
    public int insertPurchaseData(ShaverReportPsiOverview record){
        return reportPsiOverviewMapper.insert(record);
    }

    /**
     * 获取所有psi数据
     * @return
     */
    public List<ShaverReportPsiOverview> selectAllData(){
        return reportPsiOverviewMapper.selectAllData();
    }

    /**
     * 更新库存金额
     * @param yearMonth
     * @param inventoryAmount
     * @param prefix
     * @return
     */
    public int updateInventoryAmountByYearMonth(String yearMonth, long inventoryAmount,double inventoryTurnoverRate,String prefix){
        return reportPsiOverviewMapper.updateInventoryAmountByYearMonth(yearMonth, inventoryAmount,inventoryTurnoverRate, prefix);
    }
}
