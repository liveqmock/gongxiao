package com.yhglobal.gongxiao.warehouse.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehouse.model.dto.ShaverReportPsiOverview;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface ReportPsiOverviewMapper extends BaseMapper {
    @Delete({
            "delete from shaver_report_psi_overview",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into shaver_report_psi_overview (id, yearMonth, ",
            "purchaseAmount, saleAmount, ",
            "inventoryAmount, inventoryTurnoverRate)",
            "values (#{id,jdbcType=BIGINT}, #{yearMonth,jdbcType=BIGINT}, ",
            "#{purchaseAmount,jdbcType=BIGINT}, #{saleAmount,jdbcType=BIGINT}, ",
            "#{inventoryAmount,jdbcType=BIGINT}, #{inventoryTurnoverRate,jdbcType=BIGINT})"
    })
    int insert(ShaverReportPsiOverview record);

    @Select({
            "select",
            "id, yearMonth, purchaseAmount, saleAmount, inventoryAmount, inventoryTurnoverRate",
            "from shaver_report_psi_overview",
            "where id = #{id,jdbcType=BIGINT}"
    })
    ShaverReportPsiOverview selectByPrimaryKey(Long id);

    @Select({
            "select",
            "id, yearMonth, purchaseAmount, saleAmount, inventoryAmount, inventoryTurnoverRate",
            "from shaver_report_psi_overview order by yearMonth",
    })
    List<ShaverReportPsiOverview> selectAllData();


    @Update({
            "update shaver_report_psi_overview",
            "set yearMonth = #{yearMonth,jdbcType=BIGINT},",
            "purchaseAmount = #{purchaseAmount,jdbcType=BIGINT},",
            "saleAmount = #{saleAmount,jdbcType=BIGINT},",
            "inventoryAmount = #{inventoryAmount,jdbcType=BIGINT},",
            "inventoryTurnoverRate = #{inventoryTurnoverRate,jdbcType=BIGINT}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ShaverReportPsiOverview record);


    /**
     * 更新某月的销售金额
     *
     * @param prefix     表前缀
     * @param yearMonth  年月yyyyMM
     * @param saleAmount 销售额
     * @return 更新成功记录数
     */
    @Update({
            "UPDATE ${prefix}_report_psi_overview",
            "SET saleAmount = #{saleAmount}",
            "WHERE yearMonth = #{yearMonth}"
    })
    int updateSalesAmountByYearMonth(@Param("prefix") String prefix,
                                     @Param("yearMonth") String yearMonth,
                                     @Param("saleAmount") long saleAmount);

    @Update({
            "UPDATE ${prefix}_report_psi_overview",
            "SET inventoryAmount = #{inventoryAmount},",
            "inventoryTurnoverRate = #{inventoryTurnoverRate}",
            "WHERE yearMonth = #{yearMonth}"
    })
    int updateInventoryAmountByYearMonth(@Param("yearMonth")String yearMonth, @Param("inventoryAmount")long inventoryAmount,@Param("inventoryTurnoverRate")double inventoryTurnoverRate,@Param("prefix")String prefix);
}