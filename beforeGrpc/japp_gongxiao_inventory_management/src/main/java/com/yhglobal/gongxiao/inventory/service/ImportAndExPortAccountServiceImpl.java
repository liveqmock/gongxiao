package com.yhglobal.gongxiao.inventory.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foundation.warehouse.dao.ImportAndExportAccountDao;
import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import com.yhglobal.gongxiao.warehouse.model.InventoryLedger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service(timeout = 5000)
public class ImportAndExPortAccountServiceImpl implements InventoryLedgerService {

    private Logger logger = LoggerFactory.getLogger(ImportAndExPortAccountServiceImpl.class);

    @Autowired
    ImportAndExportAccountDao importAndExportAccountDao;

    @Override
    public PageInfo<InventoryLedger> conditionalSelectInventoryLedger(RpcHeader rpcHeader, int pageNumber, int pageSize, String projectId, String productCode, String productName, String warehouseId, Date startDate, Date endDate) {

        try {
            logger.info("#traceId={}# [IN][conditionalSelectInventoryLedger] params: projectId={}, productCode={}, productName={}, warehouseId={}, startDate={}, endDate={}", rpcHeader.getTraceId(),projectId, productCode, productName, warehouseId, startDate, endDate);
            PageInfo<InventoryLedger> pageInfo;
            PageHelper.startPage(pageNumber, pageSize);
            List<InventoryLedger> resultList =  importAndExportAccountDao.selectAccoutInfosByProductModelAndProdutNameAndWarehouseBetweentDate(projectId,productCode,productName,warehouseId,startDate,endDate);
            pageInfo = new PageInfo<InventoryLedger>(resultList);
            PageInfo<InventoryLedger> orderPageInfo = new PageInfo<>(resultList);
            pageInfo.setTotal(orderPageInfo.getTotal());

            logger.info("#traceId={}# [OUT] get conditionalSelectInventoryLedger success: result.size():{}", rpcHeader.getTraceId(),resultList.size());
            return pageInfo;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            throw e;
        }
    }

}
