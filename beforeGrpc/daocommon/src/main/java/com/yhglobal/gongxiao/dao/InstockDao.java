package com.yhglobal.gongxiao.dao;

import com.yhglobal.gongxiao.warehousemanagement.customermodel.InstockOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstockDao {

    @Autowired
    InstockMapper instockMapper;

    public int insertrecord(String record){
        return instockMapper.insertrecord(record);
    }
    public List<InstockOrder> selectrecord(){
        return instockMapper.selectrecord();
    }
}
