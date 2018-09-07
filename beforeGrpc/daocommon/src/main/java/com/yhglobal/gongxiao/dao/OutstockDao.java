package com.yhglobal.gongxiao.dao;

import com.yhglobal.gongxiao.warehousemanagement.customermodel.OutstockOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OutstockDao {

    @Autowired
    OutstockMapper outstockMapper;

    public int insertrecord(String record){
        return outstockMapper.insertrecord(record);
    }
    public List<OutstockOrder> selectrecord(){
        return outstockMapper.selectrecord();
    }
}
