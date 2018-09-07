package com.yhglobal.gongxiao.inventory.dao;

import com.yhglobal.gongxiao.inventory.dao.mapping.CheckEasInventoryMapper;
import com.yhglobal.gongxiao.inventory.model.bo.EasInventoryCheckResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CheckEasInventoryDao {
    @Autowired
    CheckEasInventoryMapper checkEasInventoryMapper;

    public int insertRecords(EasInventoryCheckResult easInventoryCheckResult, String prefix){
        return checkEasInventoryMapper.insertRecords(easInventoryCheckResult,prefix);
    }

    public List<EasInventoryCheckResult> selectRecords(long projectId, String prefix){
        return checkEasInventoryMapper.selectEasInventoryCheckRecords(projectId,prefix);
    }
}
