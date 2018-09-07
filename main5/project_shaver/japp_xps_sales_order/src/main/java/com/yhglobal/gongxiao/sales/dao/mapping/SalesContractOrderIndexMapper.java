package com.yhglobal.gongxiao.sales.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.sales.model.dto.SalesContractOrderIndex;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 销售合同号索引Mapping
 *
 * @author 葛灿
 */
public interface SalesContractOrderIndexMapper extends BaseMapper {

    /**
     * 获取索引信息
     *
     * @param prefix    数据库表前缀
     * @param projectId 项目id
     * @return 索引信息对象
     */
    @Select({
            "SELECT rowId, projectId, indexNo, lastUpdateDate, dataVersion ",
            "FROM ${prefix}_sales_contract_number_index",
            "WHERE projectId = #{projectId}"
    })
    SalesContractOrderIndex getIndexByProjectId(@Param("prefix") String prefix,
                                                @Param("projectId") long projectId);


    /**
     * 更新索引信息
     *
     * @param prefix         数据库表前缀
     * @param rowId          主键id
     * @param dataVersion    数据版本
     * @param indexNo        索引号
     * @param lastUpdateDate 上次更新日期 yyyyMMdd格式
     * @return 执行更新操作修改的数据库记录数
     */
    @Update({
            "UPDATE ${prefix}_sales_contract_number_index",
            "SET",
            "indexNo = #{indexNo},",
            "lastUpdateDate = #{lastUpdateDate},",
            "dataVersion = dataVersion+1",
            "WHERE",
            "rowId = #{rowId} AND dataVersion = #{dataVersion}"
    })
    int updateIndex(@Param("prefix") String prefix,
                    @Param("rowId") long rowId,
                    @Param("dataVersion") long dataVersion,
                    @Param("indexNo") int indexNo,
                    @Param("lastUpdateDate") String lastUpdateDate);
}
