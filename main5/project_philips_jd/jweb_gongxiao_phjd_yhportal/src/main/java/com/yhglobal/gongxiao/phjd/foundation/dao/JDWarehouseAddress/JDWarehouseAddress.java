package com.yhglobal.gongxiao.phjd.foundation.dao.JDWarehouseAddress;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 京东仓库地址管理
 *
 * @author yuping.lin
 */
public class JDWarehouseAddress {
    /**
     * 京东仓库地址管理查询
     *
     * @param warehouseID 仓库编码
     * @param warehouse   仓库名称
     * @return
     */
    public String selectAllWarehouse(@Param("warehouseID") String warehouseID, @Param("warehouse") String warehouse) {
        return new SQL() {{
            SELECT("t.warehouseID,t.warehouse,t.receiver,t.receiverTel,t.receivingAddress,",
                    "t.distributionCenter,t.warehouseGLN,t.createTime");
            FROM("phjd_foundation_warehouse t");
            if (warehouseID != null && !"".equals(warehouseID)) {
                WHERE("t.warehouseID=#{warehouseID}");
            }
            if (warehouse != null && !"".equals(warehouse)) {
                WHERE("t.warehouse like concat('%', #{warehouse},'%')");
            }
        }}.toString();
    }
}
