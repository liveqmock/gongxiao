package com.yhglobal.gongxiao.foudation.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.foudation.product.service.impl.ProductBasicServiceImpl;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.dao.WarehouseDao;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(timeout = 5000)
public class WarehouseServiceImpl implements WarehouseService {
    private static Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);

    @Autowired
    private WarehouseDao warehouseDao;

    @Override
    public int createWarehouse(RpcHeader rpcHeader, String warehouseName, String warehouseCode, String longitude, String latitude, String countryCode, String countryName, Integer provinceId, String provinceName, Integer cityId, String cityName, String districtName, String streetAddress, Long createdById, String createdByName, Date createTime) {
//        logger.info("#traceId={}# [IN][insertProject] params: ", rpcHeader.traceId);
//        logger.info(rpcHeader.traceId);
//        logger.info("out foundation createWarehouse service");
        return 0;
    }
    @Override
    public Warehouse getWarehouseById(RpcHeader rpcHeader, String warehouseId) {
        logger.info("#traceId={}# [IN][getWarehouseById] params: warehouseId={}", rpcHeader.traceId,warehouseId);
        try {
            Warehouse warehouse = warehouseDao.selectByPrimaryKey(Long.parseLong(warehouseId));
            if (warehouse == null) {
                logger.info("#traceId={}# [OUT] fail togetWarehouseById: warehouseId={} Currency=NULL", rpcHeader.traceId);
            } else {
                logger.info("#traceId={}# [OUT] getWarehouseById success: warehouse={}", rpcHeader.traceId, warehouse.toString());
            }
            return warehouse;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Warehouse> selectAll(RpcHeader rpcHeader) {
        logger.info("#traceId={}# [IN][insertProject] params: ", rpcHeader.traceId);
        try {
            List<Warehouse> warehouses = warehouseDao.selectAll();
            if (warehouses == null) {
                logger.info("#traceId={}# [OUT] fail to selectAll: warehouses=NULL", rpcHeader.traceId, warehouses);
            } else {
                logger.info("#traceId={}# [OUT] selectAll success: warehouses={}", rpcHeader.traceId, warehouses.size());
            }
            return  warehouses;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Warehouse getWarehouseByEASName(String easWarehouseName) {
        logger.info("[IN][getWarehouseByName] params: warehouseId={}",easWarehouseName);
        try {
            Warehouse warehouse = warehouseDao.getWarehouseByEASName(easWarehouseName);
            if (warehouse == null) {
                logger.info("[OUT] fail getWarehouseByEASName: warehouseId={} Currency=NULL");
            } else {
                logger.info("[OUT] getWarehouseByEASName success: warehouse={}", warehouse.toString());
            }
            return warehouse;
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Warehouse getwarehoueseByWmswarehouseCode(String WmswarehouseCode) {
        logger.info("[IN][getwarehoueseByWmswarehouseCode] params: WmswarehouseCode={}",WmswarehouseCode);
        try {
            Warehouse warehouse = warehouseDao.getWarehoueseByWmsCode(WmswarehouseCode);
            if (warehouse == null) {
                logger.info("[OUT] fail getwarehoueseByWmswarehouseCode: WmswarehouseCode={} Currency=NULL");
            } else {
                logger.info("[OUT] getwarehoueseByWmswarehouseCode success: warehouse={}", warehouse.toString());
            }
            return warehouse;
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    public List<Warehouse> selectByIdAndName(RpcHeader rpcHeader, Long warehouseId, String warehouseName) {
        logger.info("#traceId={}# [IN][insertProject] params: ", rpcHeader.traceId);
        try {
            List<Warehouse> warehouses = warehouseDao.selectByIdAndName(warehouseId, warehouseName);
            if (warehouses == null) {
                logger.info("#traceId={}# [OUT] fail to selectByIdAndName: warehouses=NULL", rpcHeader.traceId, warehouses);
            } else {
                logger.info("#traceId={}# [OUT] selectByIdAndName success: warehouses={}", rpcHeader.traceId, warehouses.size());
            }
            return warehouses;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    public int createOne(RpcHeader rpcHeader, Warehouse record) {
        logger.info("#traceId={}# [IN][insertProject] params: ", rpcHeader.traceId);
        try {
            int insert = warehouseDao.insert(record);
            logger.info("#traceId={}# [OUT] createOne success: insert={}", rpcHeader.traceId, insert);
            return insert;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
