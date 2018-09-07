package com.yhglobal.gongxiao.foundation.distributor.dao;

import com.yhglobal.gongxiao.foundation.distributor.dao.mapping.DistributorMapper;
import com.yhglobal.gongxiao.foundation.distributor.model.Distributor;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class DistributorDao {
    @Autowired
    private DistributorMapper distributorMapper;

    public Distributor getByPrimaryKey(Integer distributorId){
        return distributorMapper.selectByPrimaryKey(distributorId);
    }

    public List<Distributor> selectAll(){
        return distributorMapper.selectAll();
    }

    public int insertDistributor( String easCustomerCode, String easCustomerName){
        return distributorMapper.insertDistributor(easCustomerCode,easCustomerName);
    }

    public Distributor selectRecordById(String distributorId){
        return distributorMapper.selectRecordById(distributorId);
    }

}
