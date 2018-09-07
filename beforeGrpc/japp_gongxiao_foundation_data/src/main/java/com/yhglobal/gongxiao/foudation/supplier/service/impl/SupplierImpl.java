package com.yhglobal.gongxiao.foudation.supplier.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foudation.product.service.impl.ProductBasicServiceImpl;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.dao.ProjectDao;
import com.yhglobal.gongxiao.foundation.supplier.model.Supplier;
import com.yhglobal.gongxiao.foundation.supplier.dao.SupplierDao;
import com.yhglobal.gongxiao.foundation.supplier.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SupplierImpl implements SupplierService {
    private static Logger logger = LoggerFactory.getLogger(SupplierImpl.class);

    @Autowired
    private SupplierDao supplierDao;

    @Autowired
    private ProjectDao projectDao;


    @Override
    public Supplier getSupplierByProjectId(RpcHeader rpcHeader, String projectId) {
        logger.info("#traceId={}# [IN][getSupplierByProjectId] params: projectId={}", rpcHeader.traceId,projectId);
        try {
            Project project = projectDao.getByProjectId(Long.parseLong(projectId));
            int supplierId = project.getSupplierId();
            Supplier supplier = supplierDao.getBySupplierId((long) supplierId);
            if (supplier==null) {
                logger.info("#traceId={}# [OUT] fail to getSupplierByProjectId: suppliers=NULL", rpcHeader.traceId, supplier);
            } else {
                logger.info("#traceId={}# [OUT] getSupplierByProjectId success: suppliers={}", rpcHeader.traceId, supplier.toString());
            }
            return supplier;
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(),  e);
            throw e;
        }
    }

    @Override
    public Supplier getSupplierByCode(RpcHeader rpcHeader, String supplierCode) {
        return supplierDao.getSupplierByCode(supplierCode);
    }

    @Override
    public Supplier getSupplierById(RpcHeader rpcHeader, String supplierId) {
        return supplierDao.getBySupplierId(Long.parseLong(supplierId));
    }

    @Override
    public List<Supplier> selectAll(RpcHeader rpcHeader) {
        logger.info("#traceId={}# [IN][selectAll] ", rpcHeader.traceId);
       try{
           List<Supplier> suppliers = supplierDao.selectAll();
           if (suppliers==null) {
               logger.info("#traceId={}# [OUT] fail to selectAll: suppliers=NULL", rpcHeader.traceId, suppliers);
           } else {
               logger.info("#traceId={}# [OUT] selectAll success: suppliers={}", rpcHeader.traceId, suppliers.size());
           }
           return suppliers;
       }catch (Exception e){
           logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(),  e);
           throw e;
       }
    }
}
