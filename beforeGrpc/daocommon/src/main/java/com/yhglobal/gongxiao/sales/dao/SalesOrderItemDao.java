package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.dao.mapping.SalesOrderItemMapper;
import com.yhglobal.gongxiao.sales.model.SalesOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 销售订单商品表dao
 *
 * @Author: 葛灿
 * @Date:2018/2/28--11:37
 */
@Repository
public class SalesOrderItemDao {
    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;

    /**
     * 根据销售单号查询
     *
     * @param orderNo 销售单号
     * @return SalesOrderItem
     * @author 葛灿
     * @date 2018/2/28 11:43
     */
    public List<SalesOrderItem> selectListBySalesOrderNo(String orderNo) {
        return salesOrderItemMapper.selectListBySalesOrderNo(orderNo);
    }

    /**
     * 更新销售单信息
     *
     * @param record
     * @return int
     * @author 葛灿
     * @date 2018/2/28 11:49
     */
    public int update(SalesOrderItem record) {
        return salesOrderItemMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    /**
     * 插入销售单信息
     *
     * @param record
     * @return int
     * @author 葛灿
     * @date 2018/2/28 11:51
     */
    public int insert(SalesOrderItem record) {
        return salesOrderItemMapper.insert(record);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return SalesOrderItem
     * @author 葛灿
     * @date 2018/3/1 9:45
     */
    public SalesOrderItem getById(long id) {
        return salesOrderItemMapper.selectById(id);
    }

    /**
     * 根据销售单号、货品编码查询销售单商品信息
     *
     * @param salesOrderNo 销售单号
     * @param productCode  货品编码
     * @return
     */
    public SalesOrderItem getSalesOrderItemBySalesOrderNoAndProductCode(String salesOrderNo, String productCode) {
        return salesOrderItemMapper.getSalesOrderItemBySalesOrderNoAndProductCode(salesOrderNo,productCode);
    }

    /**
     * 根据销售单号、货品编码查询销售单商品信息
     *
     * @param salesOrderNo 销售单号
     * @param easProductCode  eas货品编码
     * @return
     */
    public SalesOrderItem getSalesOrderItemBySalesOrderNoAndEasProductCode(String salesOrderNo, String easProductCode) {
        return salesOrderItemMapper.getSalesOrderItemBySalesOrderNoAndEasProductCode(salesOrderNo,easProductCode);
    }

    /**
     * 选择性查询详情
     * @param salesOrderNo 销售单号
     * @param productCodes  货品编码 有多条，使用","分割
     * @return
     */
    public List<SalesOrderItem> selectListSelective(String salesOrderNo, String productCodes) {
        return salesOrderItemMapper.selectListSelective(salesOrderNo,productCodes);
    }
}
