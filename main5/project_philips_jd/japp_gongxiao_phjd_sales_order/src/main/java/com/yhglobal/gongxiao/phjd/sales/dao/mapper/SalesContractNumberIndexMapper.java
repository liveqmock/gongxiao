package com.yhglobal.gongxiao.phjd.sales.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.phjd.sales.model.SalesContractNumberIndex;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 销售订单索引
 *
 * @author weizecheng
 * @date 2018/8/31 17:07
 */
public interface SalesContractNumberIndexMapper extends BaseMapper {




//    @Insert({
//        "insert into phjd_sales_contract_number_index (id, indexNo, ",
//        "lastUpdateDate, dataVersion, ",
//        "projectId)",
//        "values (#{id,jdbcType=BIGINT}, #{indexNo,jdbcType=INTEGER}, ",
//        "#{lastUpdateDate,jdbcType=VARCHAR}, #{dataVersion,jdbcType=BIGINT}, ",
//        "#{projectId,jdbcType=LONGVARCHAR})"
//    })
//    int insert(SalesContractNumberIndex record);


    /**
     * 根据项目Id获取销售索引
     *
     * @author weizecheng
     * @date 2018/8/30 13:05
     * @param prefix 表前缀
     * @param projectId 项目Id
     * @return SalesContractNumberIndex
     */
    @Select({
        "select",
        "id, indexNo, lastUpdateDate, dataVersion, projectId",
        "from ${prefix}_sales_contract_number_index",
        "where projectId = #{projectId,jdbcType=BIGINT}"
    })
    SalesContractNumberIndex getByProjectId(@Param("prefix") String prefix,@Param("projectId")Long projectId);


    /**
     * 更新销售订单所以
     *
     * @author weizecheng
     * @date 2018/8/30 13:04
     * @param prefix 表前缀
     * @param dataVersion 版本号
     * @param id id
     * @param indexNo 索引
     * @param lastUpdateDate 最后更新日期 YYYYMMDD
     * @return int
     */
    @Update({
        "update ${prefix}_sales_contract_number_index",
        "set indexNo = #{indexNo,jdbcType=INTEGER},",
          "lastUpdateDate = #{lastUpdateDate,jdbcType=VARCHAR},",
          "dataVersion = dataVersion + 1 ",
        "where id = #{id,jdbcType=BIGINT} AND dataVersion = #{dataVersion}"
    })
    int updateById(@Param("prefix") String prefix,
                   @Param("dataVersion")Long dataVersion,
                   @Param("id") Long id,
                   @Param("indexNo") Integer indexNo,
                   @Param("lastUpdateDate")String lastUpdateDate);
}