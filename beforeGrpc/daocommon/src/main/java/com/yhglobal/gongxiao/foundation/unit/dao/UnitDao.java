package com.yhglobal.gongxiao.foundation.unit.dao;

import com.yhglobal.gongxiao.foundation.unit.Unit;
import com.yhglobal.gongxiao.foundation.unit.dao.mapper.UnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author: 陈浩
 **/
@Repository
public class UnitDao {
    @Autowired
    UnitMapper unitMapper;

    public int insertUnitInfo(Unit unit){
        return unitMapper.insert(unit);
    }

}
