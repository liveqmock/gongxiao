package com.yhglobal.gongxiao.payment.flow.dao;

import com.yhglobal.gongxiao.payment.flow.YhglobalCouponWriteoffFlow;
import com.yhglobal.gongxiao.payment.flow.dao.mapping.YhglobalCouponLedgerWriteoffFlowMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 越海返利流水表Dao
 *
 * @author  王帅
 */
@Repository
public class YhglobalCouponWriteoffFlowDao {

    @Autowired
    private YhglobalCouponLedgerWriteoffFlowMapper yhglobalCouponLedgerWriteoffFlowMapper;

    /**
     * 插入一条越海返利流水记录
     *
     * @param record 返利流水模型
     * @return int 插入成功条数
     * @author 王帅
     */
    public int insert(YhglobalCouponWriteoffFlow record) {
        return yhglobalCouponLedgerWriteoffFlowMapper.insert(record);
    }


    /**
     *  在流水表中根据上次的记录获取账户余额
     * */
    public Long selectAmountLatestByMaxPrimaryKey(){ return yhglobalCouponLedgerWriteoffFlowMapper.selectAmountLatestByMaxPrimaryKey();}

    public YhglobalCouponWriteoffFlow getByFlowNo(String flowNo){

        return yhglobalCouponLedgerWriteoffFlowMapper.getByFlowNo(flowNo);
    }


}
