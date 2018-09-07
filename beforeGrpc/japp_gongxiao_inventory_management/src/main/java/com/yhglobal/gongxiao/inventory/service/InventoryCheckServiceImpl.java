package com.yhglobal.gongxiao.inventory.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.warehousemanagement.bo.InventoryCheckModel;
import com.yhglobal.gongxiao.warehousemanagement.dao.CheckInventoryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 库存核对的结果的接口实现类
 */
@Service
public class InventoryCheckServiceImpl implements InventoryCheckService {

    private static Logger logger = LoggerFactory.getLogger(InventoryCheckServiceImpl.class);

    @Autowired
    CheckInventoryDao checkInventoryDao;

    @Override
    public PageInfo<InventoryCheckModel> getInventoryCheck(RpcHeader rpcHeader, String projectId, String warehouseId, String productCode, String productName, int pageNumber, int pageSize) {
        logger.info("#traceId={}# [IN][getInventoryCheck] params: projectId={}, warehouseId={}, productCode={}, productName={}, pageNumber={}, pageSize={}", rpcHeader.getTraceId(), projectId, warehouseId, productCode, productName, pageNumber, pageSize);
        PageInfo<InventoryCheckModel> pageInfo;

        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<InventoryCheckModel> resultList = checkInventoryDao.selectRecords(projectId, warehouseId, productCode, productName);
            pageInfo = new PageInfo<>(resultList);
            logger.info("#traceId={}# [OUT] get getInventoryCheck success: resultList.size()={}", rpcHeader.getTraceId(), resultList.size());
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
        return pageInfo;
    }

}
