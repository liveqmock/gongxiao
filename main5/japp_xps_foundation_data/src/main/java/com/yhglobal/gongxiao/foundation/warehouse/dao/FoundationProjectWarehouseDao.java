package com.yhglobal.gongxiao.foundation.warehouse.dao;

import com.yhglobal.gongxiao.foundation.warehouse.dao.mapping.FoundationProjectWarehouseMapper;
import com.yhglobal.gongxiao.foundation.warehouse.model.FoundationProjectWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Repository
public class FoundationProjectWarehouseDao {

    @Autowired
    FoundationProjectWarehouseMapper foundationProjectWarehouseMapper;

    /**
     * 获取项目下所有仓库
     * @param projectId
     * @return
     */
    public List<FoundationProjectWarehouse> selectWarehouseListByProjectId(long projectId){
        return foundationProjectWarehouseMapper.selectWarehouseListByProjectId(projectId);
    }

}
