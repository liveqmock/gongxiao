package com.yhglobal.gongxiao.sales.dao;

import com.yhglobal.gongxiao.sales.dao.mapping.SalesOrderMapper;
import com.yhglobal.gongxiao.sales.model.SalesOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 销售订单dao
 *
 * @Author: 葛灿
 * @Date:2018/2/28--11:37
 */
@Repository
public class SalesOrderDao {
    @Autowired
    private SalesOrderMapper salesOrderMapper;

    /**
     * 根据采购单号查询
     *
     * @param orderNo 采购单号
     * @return SalesOrder
     * @author 葛灿
     * @date 2018/2/28 11:43
     */
    public SalesOrder getByOrderNo(String orderNo) {
        return salesOrderMapper.selectByOrderNo(orderNo);
    }

    /**
     * 更新销售单信息
     *
     * @param record
     * @return int
     * @author 葛灿
     * @date 2018/2/28 11:49
     */
    public int update(SalesOrder record) {
        return salesOrderMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    /**
     * 插入销售单信息
     *
     * @param record
     * @return int
     * @author 葛灿
     * @date 2018/2/28 11:51
     */
    public int insert(SalesOrder record) {
        return salesOrderMapper.insert(record);
    }

    /**
     * 根据条件选择性查询        YH使用
     *
     * @param projectId   项目id
     * @param orderNo     销售单号
     * @param outDate     出库日期
     * @param createTime  创建时间
     * @param orderStatus 订单状态
     * @return java.util.List<SalesOrder>
     */
    public List<SalesOrder> selectListSelectively(long projectId, String orderNo, Date outDate, Date createTime, Integer orderStatus) {
        return salesOrderMapper.getListSelectively(projectId, null, orderNo, null, outDate, createTime, createTime, orderStatus);
    }

    /**
     * 根据条件选择性查询        AD使用
     *
     * @param projectId       项目id
     * @param distributorId   分销商id
     * @param orderNo         销售单号
     * @param productCode     货品编码
     * @param createTimeBegin 创建时间-起始条件
     * @param createTimeEnd   创建时间-截止条件
     * @param orderStatus     订单状态
     * @return java.util.List<SalesOrder>
     */
    public List<SalesOrder> selectListSelectively(long projectId, Long distributorId, String orderNo, String productCode, Date createTimeBegin, Date createTimeEnd, Integer orderStatus) {
        return salesOrderMapper.getListSelectively(projectId, distributorId, orderNo, productCode, null, createTimeBegin, createTimeEnd, orderStatus);
    }

    /**
     * 根据条件选择性查询        YH使用
     *
     * @param projectId  项目id
     * @param orderNo    销售单号
     * @param outDate    出库日期
     * @param createTime 创建时间
     * @return java.util.List<SalesOrder>
     */
    public List<Map<String, Integer>> getCountMap(long projectId, String orderNo, Date outDate, Date createTime) {
        return salesOrderMapper.getCountMap(projectId, null, orderNo, null, outDate, createTime, createTime);
    }

    /**
     * 根据条件选择性查询        AD使用
     *
     * @param projectId       项目id
     * @param distributorId   分销商id
     * @param orderNo         销售单号
     * @param productCode     货品编码
     * @param createTimeBegin 创建时间-起始条件
     * @param createTimeEnd   创建时间-截止条件
     * @return java.util.List<SalesOrder>
     */
    public List<Map<String, Integer>> getCountMap(long projectId, Long distributorId, String orderNo, String productCode, Date createTimeBegin, Date createTimeEnd) {
        return salesOrderMapper.getCountMap(projectId, distributorId, orderNo, productCode, null, createTimeBegin, createTimeEnd);
    }

    /**
     * 根据条件选择性查询        YH使用
     *
     * @param projectId  项目id
     * @param orderNo    销售单号
     * @param outDate    出库日期
     * @param createTime 创建时间
     * @return java.util.List<SalesOrder>
     */
    public int getCountSelective(long projectId, String orderNo, Date outDate, Date createTime) {
        return salesOrderMapper.getCountSelective(projectId, null, orderNo, null, outDate, createTime, createTime);
    }

    /**
     * 根据条件选择性查询        AD使用
     *
     * @param projectId       项目id
     * @param distributorId   分销商id
     * @param orderNo         销售单号
     * @param productCode     货品编码
     * @param createTimeBegin 创建时间-起始条件
     * @param createTimeEnd   创建时间-截止条件
     * @return java.util.List<SalesOrder>
     */
    public int getCountSelective(long projectId, Long distributorId, String orderNo, String productCode, Date createTimeBegin, Date createTimeEnd) {
        return salesOrderMapper.getCountSelective(projectId, distributorId, orderNo, productCode, null, createTimeBegin, createTimeEnd);
    }

    public List<SalesOrder> selectToSyncEasOrder(int syncEas) {
        return salesOrderMapper.selectToSyncEasOrder(syncEas);
    }
}
