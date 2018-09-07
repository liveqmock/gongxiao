package com.yhglobal.gongxiao.phjd.foundation.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.phjd.bean.JDWarehouseBean;
import com.yhglobal.gongxiao.phjd.foundation.dao.JDWarehouseAddress.JDWarehouseAddress;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 京东仓库地址管理
 *
 * @author yuping.lin
 */
public interface JDWarehouseAddressMapper extends BaseMapper {
    /**
     * 京东仓库地址管理查询
     *
     * @param warehouseID 仓库编码
     * @param warehouse   仓库名称
     * @return
     */
    @SelectProvider(type = JDWarehouseAddress.class, method = "selectAllWarehouse")
    List<JDWarehouseBean> selectAllWarehouse(@Param("warehouseID") String warehouseID, @Param("warehouse") String warehouse);

}
