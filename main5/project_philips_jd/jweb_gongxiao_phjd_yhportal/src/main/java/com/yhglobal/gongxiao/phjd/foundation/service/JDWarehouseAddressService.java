package com.yhglobal.gongxiao.phjd.foundation.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.phjd.bean.JDWarehouseBean;

/**
 * 京东仓库地址管理
 *
 * @author yuping.lin
 */
public interface JDWarehouseAddressService {
    /**
     * 京东仓库地址管理查询
     *
     * @param warehouseID   仓库编码
     * @param warehouse 仓库名称
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageInfo<JDWarehouseBean> selectAllWarehouseAddress(String warehouseID, String warehouse, int pageNumber, int pageSize);
}
