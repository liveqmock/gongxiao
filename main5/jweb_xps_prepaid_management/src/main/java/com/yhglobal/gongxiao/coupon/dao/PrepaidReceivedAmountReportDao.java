package com.yhglobal.gongxiao.coupon.dao;

import com.yhglobal.gongxiao.coupon.dao.mapper.PrepaidReceivedAmountReportMapper;
import com.yhglobal.gongxiao.coupon.model.PrepaidReceivedAmountReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 王帅
 */
@Repository
public class PrepaidReceivedAmountReportDao {

    @Autowired
    PrepaidReceivedAmountReportMapper prepaidReceivedAmountReportMapper;

    public int insertData(PrepaidReceivedAmountReport report){
        return prepaidReceivedAmountReportMapper.insert(report);
    }
    public int updateData(PrepaidReceivedAmountReport report){
        return prepaidReceivedAmountReportMapper.updateByPrimaryKey(report);
    }

    public int deleteAllData(String tablePrefix){
        return prepaidReceivedAmountReportMapper.deleteAllData(tablePrefix);
    }

    public List<PrepaidReceivedAmountReport> selectData(String tablePrefix){
        return prepaidReceivedAmountReportMapper.selectAll(tablePrefix);
    }
}
