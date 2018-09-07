package com.yhglobal.gongxiao.dao;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.customermodel.InstockOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InstockMapper extends BaseMapper{

     @Insert({
             "insert into customer_instock_order (",
             "record)",
             "values ",
             "(#{record})"
     })
     int insertrecord(@Param("record") String record);

     @Select({
        "select id , record , status from customer_instock_order where status = 0"
     })
     List<InstockOrder> selectrecord();
}
