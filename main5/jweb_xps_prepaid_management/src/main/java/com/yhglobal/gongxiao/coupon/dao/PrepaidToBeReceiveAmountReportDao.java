package com.yhglobal.gongxiao.coupon.dao;

import com.yhglobal.gongxiao.coupon.dao.mapper.PrepaidToBeReceiveAmountReportMapper;
import com.yhglobal.gongxiao.coupon.model.PrepaidToBeReceiveAmountReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 王帅
 */
@Repository
public class PrepaidToBeReceiveAmountReportDao {

    @Autowired
    PrepaidToBeReceiveAmountReportMapper prepaidToBeReceiveAmountReportMapper;

    public int insertData(PrepaidToBeReceiveAmountReport report){
        return prepaidToBeReceiveAmountReportMapper.insert(report);
    }
    public int updateData(PrepaidToBeReceiveAmountReport report){
        return prepaidToBeReceiveAmountReportMapper.updateByPrimaryKey(report);
    }

    public PrepaidToBeReceiveAmountReport getData(String tablePrefix,Long id){
        return prepaidToBeReceiveAmountReportMapper.selectByPrimaryKey(tablePrefix, id);
    }
}
