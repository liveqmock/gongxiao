package dao;

import dao.mapping.TsupplierMapper;
import model.Tsupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 供应商表 dao
 */
@Repository
public class TsupplierDao {
    @Autowired
    private TsupplierMapper tsupplierMapper;

    public int deleteOne(Long supplierid){
        return tsupplierMapper.deleteByPrimaryKey(supplierid);
    }

    public int insertOne(Tsupplier record){
        return tsupplierMapper.insert(record);
    }

    public Tsupplier selectByPrimaryKey(Long supplierid){
        return tsupplierMapper.selectByPrimaryKey(supplierid);
    }

    public int updateByPrimaryKey(Tsupplier record){
        return tsupplierMapper.updateByPrimaryKey(record);
    }
}
