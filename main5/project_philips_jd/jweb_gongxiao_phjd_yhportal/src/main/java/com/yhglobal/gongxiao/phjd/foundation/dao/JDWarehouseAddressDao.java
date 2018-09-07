package com.yhglobal.gongxiao.phjd.foundation.dao;

import com.yhglobal.gongxiao.phjd.bean.JDWarehouseBean;
import com.yhglobal.gongxiao.phjd.foundation.dao.mapper.JDWarehouseAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 京东仓库地址管理
 *
 * @author yuping.lin
 */
@Repository
public class JDWarehouseAddressDao {

    @Autowired
    JDWarehouseAddressMapper jdWarehouseAddress;

    /**
     * 京东仓库地址管理查询
     *
     * @param warehouseID 仓库编码
     * @param warehouse   仓库名称
     * @return
     */
    public List<JDWarehouseBean> selectAllWarehouse(String warehouseID, String warehouse) {
        return jdWarehouseAddress.selectAllWarehouse(warehouseID, warehouse);
    }
}
