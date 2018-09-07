package com.yhglobal.gongxiao.foundation.supplier.dao;

import com.yhglobal.gongxiao.foundation.supplier.model.Supplier;
import com.yhglobal.gongxiao.foundation.supplier.dao.mapping.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SupplierDao {

    @Autowired
    private SupplierMapper supplierMapper;

    public List<Supplier> selectAll() {
        return supplierMapper.selectAll();
    }

    public Supplier getBySupplierId(Long supplierId) {
        return supplierMapper.getBySupplierId(supplierId);
    }

    public int updateSupplierInfo(String supplierCode, String easSupplierCode, String easSupplierName) {
        return supplierMapper.updateSupplierInfo(supplierCode, easSupplierCode, easSupplierName);
    }

    public Supplier getSupplierByCode(String supplierCode) {
        return supplierMapper.getSupplierByCode(supplierCode);
    }

}
