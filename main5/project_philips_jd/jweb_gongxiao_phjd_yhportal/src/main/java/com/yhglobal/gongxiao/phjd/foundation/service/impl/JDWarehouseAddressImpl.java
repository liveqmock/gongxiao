package com.yhglobal.gongxiao.phjd.foundation.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.phjd.bean.JDWarehouseBean;
import com.yhglobal.gongxiao.phjd.foundation.dao.JDWarehouseAddressDao;
import com.yhglobal.gongxiao.phjd.foundation.service.JDWarehouseAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JDWarehouseAddressImpl implements JDWarehouseAddressService {
    private static Logger logger = LoggerFactory.getLogger(JDWarehouseAddressImpl.class);

    @Autowired
    JDWarehouseAddressDao jdWarehouseAddressDao;

    /**
     * 京东仓库地址管理查询
     *
     * @param warehouseID 仓库编码
     * @param warehouse   仓库名称
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<JDWarehouseBean> selectAllWarehouseAddress(String warehouseID, String warehouse, int pageNumber, int pageSize) {
        logger.info("#traceId={}# [IN][selectAllWarehouseAddress] params: warehouseID={},warehouse={},pageNumber={},pageSize={}", warehouseID, warehouse, pageNumber, pageSize);
        PageInfo<JDWarehouseBean> pageInfo;
        try {
            PageHelper.startPage(pageNumber, pageSize);
            List<JDWarehouseBean> list = jdWarehouseAddressDao.selectAllWarehouse(warehouseID, warehouse);
            int total = list.size();
            pageInfo = new PageInfo<JDWarehouseBean>(list);
            pageInfo.setPageNum(pageNumber);
            pageInfo.setPageSize(pageSize);
            pageInfo.setTotal(total);
            logger.info("#traceId={}# [OUT] get getAllRecordByCondition success: resultList.size()={}", list.size());
        } catch (Exception e) {
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }
        return pageInfo;
    }
}
