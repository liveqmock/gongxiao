package com.yhglobal.gongxiao.foundation.supplier.dao;

import com.yhglobal.gongxiao.foundation.supplier.dao.mapping.FoundationSupplierMapper;
import com.yhglobal.gongxiao.foundation.supplier.model.FoundationSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoundationSupplierDao {
    @Autowired
    private FoundationSupplierMapper supplierMapper;

    /**
     * 获取所有供应商列表
     * @return
     */
    public List<FoundationSupplier> selectAll() {
        return supplierMapper.selectAllSupplier();
    }

    /**
     * 通过供应商ID获取供应商信息
     * @param supplierId
     * @return
     */
    public FoundationSupplier getBySupplierId(Long supplierId) {
        return supplierMapper.getBySupplierId(supplierId);
    }

    /**
     * 通过供应商编码获取供应商信息
     * @param supplierCode
     * @return
     */
    public FoundationSupplier getSupplierByCode(String supplierCode) {
        return supplierMapper.getSupplierByCode(supplierCode);
    }

    /**
     * 更新供应商信息
     * @param supplierCode
     * @param easSupplierCode
     * @param easSupplierName
     * @return
     */
    public int updateSupplierInfo(String supplierCode, String easSupplierCode, String easSupplierName) {
        return supplierMapper.updateSupplierInfo(supplierCode, easSupplierCode, easSupplierName);
    }



}
