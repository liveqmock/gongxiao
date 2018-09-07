package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.dao.mapping.SalesContractOrderIndexMapper;
import com.yhglobal.gongxiao.sales.model.dto.SalesContractOrderIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 销售合同号索引DAO
 *
 * @author 葛灿
 */
@Repository
public class SalesContractOrderIndexDao {

    @Autowired
    private SalesContractOrderIndexMapper salesContractOrderIndexMapper;

    /**
     * 获取索引信息
     *
     * @param prefix    数据库表前缀
     * @param projectId 项目id
     * @return 索引信息对象
     */
    public SalesContractOrderIndex getIndexByProjectId(String prefix, long projectId) {
        return salesContractOrderIndexMapper.getIndexByProjectId(prefix, projectId);
    }

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
    public int updateIndex(String prefix, long rowId, long dataVersion, int indexNo, String lastUpdateDate) {
        return salesContractOrderIndexMapper.updateIndex(prefix, rowId, dataVersion, indexNo, lastUpdateDate);
    }
}
