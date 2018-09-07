package com.yhglobal.gongxiao.foundation.warehouse.dao;

import com.yhglobal.gongxiao.foundation.warehouse.dao.mapping.FoundationProjectWarehouseRelationMapper;
import com.yhglobal.gongxiao.foundation.warehouse.model.FoundationProjectWarehouseRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class FoundationProjectWarehouseRelationDao {

    @Autowired
    FoundationProjectWarehouseRelationMapper foundationProjectWarehouseRelationMapper;

    /**
     * 插入项目仓库关系
     * @param relation
     * @return
     */
    public int insertWarehouseRelation(FoundationProjectWarehouseRelation relation){
        return foundationProjectWarehouseRelationMapper.insert(relation);
    }

    /**
     * 获取所有的关系列表
     * @return
     */
    public List<FoundationProjectWarehouseRelation> selectAllRelationList(){
        return foundationProjectWarehouseRelationMapper.selectAllRelation();
    }


}
