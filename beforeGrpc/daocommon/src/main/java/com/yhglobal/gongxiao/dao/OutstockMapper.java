package com.yhglobal.gongxiao.dao;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.customermodel.OutstockOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OutstockMapper  extends BaseMapper {

    @Insert({
            "insert into customer_outstock_order (",
            "record)",
            "values ",
            "( #{record})"
    })
    int insertrecord(@Param("record") String record);

    @Select({
        "select id , record , status from customer_outstock_order"
    })
    List<OutstockOrder> selectrecord();
}
