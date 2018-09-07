package com.yhglobal.gongxiao.foundation.supplier.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.supplier.model.Supplier;

import java.util.List;

public interface SupplierService {

    /**
     * 获取供应商列表
     *
     * @param rpcHeader rpc调用的header
     * @return
     */
    List<Supplier> selectAll(RpcHeader rpcHeader);


    /**
     * 一个项目应该只有一个供应商,一个供应商有多个项目,通过项目可以拿到唯一的供应商
     *
     * @param rpcHeader rpc调用的header
     * @param projectId
     * @return
     */
    Supplier getSupplierByProjectId(RpcHeader rpcHeader, String projectId);

    Supplier getSupplierByCode(RpcHeader rpcHeader, String supplierCode);

    Supplier getSupplierById(RpcHeader rpcHeader, String supplierId);

}
