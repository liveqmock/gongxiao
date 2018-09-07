package com.yhglobal.gongxiao.phjd.sales.dao;

import com.yhglobal.gongxiao.phjd.sales.dao.mapper.SalesOrderAddressMapper;
import com.yhglobal.gongxiao.phjd.sales.model.SalesOrderAddressDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 销售订单地址 Dao
 *
 * @author weizecheng
 * @date 2018/8/21 18:27
 */
@Repository
public class SalesOrderAddressDao {

    @Autowired
    private SalesOrderAddressMapper addressMapper;

    /**
     * 获取销售地址
     *
     * @param prefix 表前缀
     * @param salesOrderId 销售订单Id
     * @return
     */
    public SalesOrderAddressDO getAddressBySalesOrderId(String prefix, Long salesOrderId){
        return addressMapper.getAddressBySalesOrder( prefix, salesOrderId);
    }

    /**
     * 更新销售订单地址
     *
     * @param prefix 表前缀
     * @param dataVersion 版本号
     * @param id 关联的销售订单号
     * @param receivingAddress 详细收货地址
     * @param receiverTel 收货电话
     * @param receiver 收货人姓名
     * @param arrived 最终抵达的省
     * @return int
     */
    public int updateSalesOrderAddress(String prefix,Long dataVersion, Long id, String receivingAddress, String receiverTel, String receiver, String arrived){
        return addressMapper.updateSalesOrderAddress(prefix, dataVersion, id, receivingAddress, receiverTel, receiver, arrived);
    }

    /**
     * 插入销售订单地址
     *
     * @author weizecheng
     * @date 2018/8/29 15:50
     * @param prefix 表前缀
     * @param salesOrderAddressDO 销售订单地址
     * @return int
     */
    public int insertSalesOrderAddress(String prefix,SalesOrderAddressDO salesOrderAddressDO){
        return addressMapper.insertSalesOrderAddress(prefix, salesOrderAddressDO);
    }

}
